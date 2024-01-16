package com.incresol.app.controllers;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.incresol.app.models.HttpStatusResponse;
import com.incresol.app.models.JwtRequest;
import com.incresol.app.security.JwtHelper;
import com.incresol.app.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtHelper helper;

	// private Logger logger = LoggerFactory.getLogger(AuthController.class);

	@PostMapping("/login")
	public ResponseEntity<HttpStatusResponse> login(@RequestBody JwtRequest request) throws AuthenticationException {

		HttpStatusResponse response = userService.userLogin(request.getEmail(), request.getPassword());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/otp-token")
	public ResponseEntity<HttpStatusResponse> validateOTP(@RequestParam String username, @RequestParam String otp) {
		System.out.println("Reached at token controller");
		HttpStatusResponse response = userService.validateOTP(username, otp);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@GetMapping("/logout")
	public HttpStatusResponse logout(HttpServletRequest request, HttpServletResponse response) {
		return userService.logout(request, response);
	}

}
