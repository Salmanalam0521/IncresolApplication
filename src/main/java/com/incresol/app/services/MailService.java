package com.incresol.app.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.incresol.app.entities.PasswordResetToken;
import com.incresol.app.entities.User;
import com.incresol.app.models.HttpStatusResponse;
import com.incresol.app.models.UserResponse;
import com.incresol.app.repositories.PasswordTokenRepository;
import com.incresol.app.repositories.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private UserRepository repository;
	@Autowired
	private PasswordTokenRepository passwordTokenRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public static final long EXPIRATION_MINUTES = 10;

	SimpleMailMessage smm;
	
	private static final Logger logger = LoggerFactory.getLogger(MailService.class);

	public PasswordResetToken validateTokenAndUpdateUserPassword() {

		return null;
	}

	public void LoginSuccessfulMail(String email) {

		smm = new SimpleMailMessage();
		smm.setFrom("salmanrobin125@gmail.com");
		smm.setTo(email);
		smm.setSubject("Incresol");
		smm.setText("Hello " + email + "\nLogin Successfull \nWelcome to Incresol");
		javaMailSender.send(smm);
	}

	
	public void accountUnlockedMail(String userName, String email) throws MessagingException {
		 try {
		        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");

		        helper.setFrom("salmanrobin125@gmail.com");
		        helper.setTo(email);
		        helper.setSubject("Incresol");

		        String loginUrl = "http://localhost:4200/";

		        // Use Thymeleaf to process the template
//		        Context context = new Context();
//		        context.setVariable("userName", userName);
//		        context.setVariable("loginUrl", loginUrl);

		       // String emailBody = templateEngine.process("email-template", context);

		        helper.setText("Hello " + userName + "\nYour account is unlocked.");

		        javaMailSender.send(mimeMessage);
		    } catch (MessagingException e) {
		        // Handle the exception or log it for further investigation
		        logger.error("Error sending email", e);
		    }
	}
	public void accountLockedMail(String userName, String email) {
		smm = new SimpleMailMessage();
		smm.setFrom("salmanrobin125@gmail.com");
		smm.setTo(email);
		smm.setSubject("Incresol");
		smm.setText("Hello " + userName + "\nYour account is locked.");
		javaMailSender.send(smm);
	}

	public User sendMailForChangePassword(String userEmail) {

		User user = repository.findByEmail(userEmail);
		if (user != null) {

			// String resetToken = helper.generateToken(user);
			String resetToken = UUID.randomUUID().toString();
			createPasswordResetToken(user, resetToken);

			smm = new SimpleMailMessage();

			String resetUrl = "http://localhost:4200/forgot-password?token=" + resetToken;

			smm.setFrom("salmanrobin125@gmail.com");
			smm.setTo(userEmail);
			smm.setSubject("Incresol");
			smm.setText("Hello "+user.getFirstName()+" "+user.getLastName()+"\n Use Below link to change password \n" + resetUrl);
			javaMailSender.send(smm);
			return user;
		} else {
			throw new UsernameNotFoundException("User not found with mail " + userEmail);
		}
	}

	private void createPasswordResetToken(User user, String resetToken) {

		LocalDateTime now = LocalDateTime.now();

		PasswordResetToken prt = new PasswordResetToken();
		prt.setToken(resetToken);
		prt.setUser(user);
		prt.setExpiryDate(now.plus(EXPIRATION_MINUTES, ChronoUnit.DAYS));
		passwordTokenRepository.save(prt);
	}

	public HttpStatusResponse resetPassword(String token, String password) {

		PasswordResetToken findByToken = passwordTokenRepository.findByToken(token);
		System.out.println(findByToken);
		boolean checkTokenExpiry = checkTokenExpiry(findByToken.getExpiryDate());
		HttpStatusResponse httpStatusResponse = new HttpStatusResponse();

		if (checkTokenExpiry) {
			User user = findByToken.getUser();
			System.out.println("User OF Password TOken Respository " + user);
			if (user != null) {
				user.setPassword(bCryptPasswordEncoder.encode(password));
				User save = repository.save(user);
				UserResponse ur = new UserResponse();
				BeanUtils.copyProperties(save, ur);
				httpStatusResponse.getData().put("user", ur);
				httpStatusResponse.setErrorCode(0);
				httpStatusResponse.setStatusCode(0);
				httpStatusResponse.setMessage("Password Changed Successfull");
				return httpStatusResponse;
			} else {
				throw new UsernameNotFoundException("User not found");
			}
		} else {
			httpStatusResponse.setErrorCode(22);
			httpStatusResponse.setStatusCode(1);
			httpStatusResponse.setMessage("Token Expired");
			return httpStatusResponse;
		}
	}

	private boolean checkTokenExpiry(LocalDateTime exDate) {
		return !exDate.isBefore(LocalDateTime.now());
	}

	public boolean sendOTPMail(String email, String userName,String generateSecureOTP) {
		boolean sendmail = false;
		if (email != null && generateSecureOTP != null) {
			smm = new SimpleMailMessage();
			smm.setFrom("salmanrobin125@gmail.com");
			smm.setTo(email);
			smm.setSubject("Incresol");
			smm.setText("Hello " + userName + "\nWelcome to Incresol \n" + "Your 6 Digit OTP is - " + generateSecureOTP);
			javaMailSender.send(smm);
			sendmail = true;
			return sendmail;
		}
		return sendmail;
	}

	public void mailUtils(String to, String subject, String message) {
		smm = new SimpleMailMessage();
		smm.setFrom("salmanrobin125@gmail.com");
		smm.setTo(to);
		smm.setSubject(subject);
		smm.setText(message);
		javaMailSender.send(smm);
		
	}

}
