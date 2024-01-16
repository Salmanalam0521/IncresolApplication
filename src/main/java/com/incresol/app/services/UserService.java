package com.incresol.app.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import com.incresol.app.entities.OTP;
import com.incresol.app.entities.User;
import com.incresol.app.models.GenerateNewPassword;
import com.incresol.app.models.HttpStatusResponse;
import com.incresol.app.models.UserDetailsResponse;
import com.incresol.app.models.UserResponse;
import com.incresol.app.repositories.OTPRepository;
import com.incresol.app.repositories.UserRepository;
import com.incresol.app.security.JwtHelper;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private OTPRepository otpRepository;

	@Autowired
	private OTPService otpService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtHelper helper;

	@Autowired
	private MailService mailService;

	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private AdminService adminService;

	public UserResponse findUser() {
		User user = userRepo.findByEmail(this.getUserName());
		UserResponse userRes = new UserResponse();
		userRes.setFirstName(user.getFirstName());
		userRes.setLastName(user.getLastName());
		userRes.setEmail(user.getEmail());
		return userRes;
	}

	private String getUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String name = authentication.getName();
		System.out.println("Name is " + name);
		return name;
	}

	public HttpStatusResponse userLogin(String email, String password)

			throws javax.security.sasl.AuthenticationException {

		UserDetails userDetails = userDetailsService.loadUserByUsername(email);
		User user = userRepo.findByEmail(email);
		LocalDateTime currentDate = LocalDateTime.now();

		if (!user.isAccountNonLocked()) {
			mailService.accountLockedMail(user.getFirstName() + " " + user.getLastName(), user.getEmail());
			return this.getHttpStatusResponse(1, null, 13, "Account is Locked");
		}
		if (!user.isEnabled()) {

			return this.getHttpStatusResponse(1, null, 12, "Account is Disabled");
		}

		try {

			this.doAuthenticate(userDetails, email, password);

		} catch (AccountExpiredException e) {

			return this.getHttpStatusResponse(1, null, 11, e.getMessage());

		} catch (CredentialsExpiredException e) {

			return this.getHttpStatusResponse(1, null, 10, e.getMessage());

		} catch (BadCredentialsException e) {

			user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
			userRepo.save(user);

			return this.getHttpStatusResponse(1, null, 9, e.getMessage());

		} catch (AuthenticationException e) {

			return this.getHttpStatusResponse(1, null, 8, e.getMessage());
		}

		user.setFailedLoginAttempts(0);
		user.setAccountExpiredDate(currentDate.plus(45, ChronoUnit.DAYS));
		User save = userRepo.save(user);

		String generateSecureOTP = otpService.generateSecureOTP();
		OTP otp = new OTP();
		otp.setOneTimePassword(this.hashOTP(generateSecureOTP));
		otp.setExpiryDate(currentDate.plus(5, ChronoUnit.HOURS));
		otp.setUser(save);
		otpRepository.save(otp);

		boolean sendOTPMail = mailService.sendOTPMail(user.getEmail(), user.getFirstName() + " " + user.getLastName(),
				generateSecureOTP);
		if (sendOTPMail) {
			return this.getHttpStatusResponse(0, null, 0, "OTP Sent Successfully");
		} else {
			return this.getHttpStatusResponse(1, null, 14, "Failed to Sent OTP");
		}
	}

	private void doAuthenticate(UserDetails userDetails, String email, String password)
			throws javax.security.sasl.AuthenticationException, LockedException {
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password,
				userDetails.getAuthorities());
		manager.authenticate(authentication);
	}

	public HttpStatusResponse validateOTP(String username, String otp) {

		OTP otpDetails = otpRepository.findByOneTimePassword(this.hashOTP(otp));

		User user = otpDetails.getUser();

		if (user.getEmail().equals(username)) {
			if (!otpDetails.getExpiryDate().isBefore(LocalDateTime.now())) {
				if (this.hashOTP(otp).equals(otpDetails.getOneTimePassword())) {
					UserDetails userDetails = userDetailsService.loadUserByUsername(username);
					String token = this.helper.generateToken(userDetails);

					UserResponse userData = adminService.atLogin(user.getEmail());

					Map<String, Object> data = new HashMap<>();
					data.put("token", token);
					data.put("userdetails", userData);

					return this.getHttpStatusResponse(0, data, 0, "Login Successfull");
				} else {
					return this.getHttpStatusResponse(1, null, 15, "OTP Mismatch please enter valid OTP");
				}
			} else {
				return this.getHttpStatusResponse(1, null, 16, "OTP Expired");
			}
		} else {
			return this.getHttpStatusResponse(1, null, 17, "Email mismatch, Enter valid email");
		}
	}

	private String hashOTP(String otp) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hashedBytes = digest.digest(otp.getBytes());
			return Base64.getEncoder().encodeToString(hashedBytes);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Failed to hash OTP", e);
		}
	}

	public HttpStatusResponse getHttpStatusResponse(int statusCode, Map<String, Object> data, int errorCode,
			String message) {
		HttpStatusResponse response = new HttpStatusResponse();
		response.setData(data);
		response.setStatusCode(statusCode);
		response.setErrorCode(errorCode);
		response.setMessage(message);
		return response;
	}

	public HttpStatusResponse changePassword(GenerateNewPassword generatePassword) {
		String oldPass = generatePassword.getOldPassword();
		String newPass = generatePassword.getNewPassword();

		User user = userRepo.findByEmail("salmanrobin125@gmail.com");

		if (!passwordEncoder.matches(oldPass, user.getPassword())) {
			return getHttpStatusResponse(1, null, 20, "Invalid old password. Please enter a valid password.");
		}

		if (passwordEncoder.matches(newPass, user.getPassword())) {
			return getHttpStatusResponse(1, null, 19, "New password should be different from the old password.");
		}

		user.setPassword(passwordEncoder.encode(newPass));
		userRepo.save(user);

		return getHttpStatusResponse(0, null, 0, "Password changed successfully.");
	}

	@Scheduled(fixedRate = 60000)
	public void unlockAccounts() throws MessagingException {
		LocalDateTime now = LocalDateTime.now();
		List<User> users = userRepo.findByAccountNonLockedFalseAndLockedUntilBefore(now);
		for (User user : users) {
			user.setAccountNonLocked(true);
			user.setLockedUntil(null);
			user.setFailedLoginAttempts(0);
			userRepo.save(user);
			mailService.accountUnlockedMail(user.getFirstName() + " " + user.getLastName(), user.getEmail());
		}
	}

	public HttpStatusResponse logout(HttpServletRequest request, HttpServletResponse response) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
			return this.getHttpStatusResponse(0, null, 0, "Logout successfull");
		}
		return this.getHttpStatusResponse(1, null, 21, "Failed");
	}

}
