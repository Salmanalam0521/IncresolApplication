package com.incresol.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incresol.app.entities.User;
import com.incresol.app.models.ForgotPassword;
import com.incresol.app.models.HttpStatusResponse;
import com.incresol.app.models.PasswordResponseHandler;
import com.incresol.app.services.MailService;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/password")
public class MailController {
	
	@Autowired
	private MailService mailService;
	
	
//	@GetMapping("/forgot-password")
//	public ModelAndView forgotPassword() {
//		return new ModelAndView("ReadEmail.html");
//	}
	
	@GetMapping("/sendmail/{email}")
	public ResponseEntity<PasswordResponseHandler> sendMailForResetPassword(@PathVariable("email") String email) {
		User user = mailService.sendMailForChangePassword(email);
		PasswordResponseHandler prh=new PasswordResponseHandler();
		if(user!=null) {
		prh.getData().put("user",user);
		prh.setMessage("Mail Sent Successfully");
		prh.setErrorCode(0);
		prh.setStatusCode(0);
		return new ResponseEntity<>(prh, HttpStatus.OK);
		}
		
		prh.setMessage("Failed To sent mail");
		prh.setErrorCode(1);
		prh.setStatusCode(21);
		
		return new ResponseEntity<>(prh, HttpStatus.NOT_FOUND);
	}
	
//	@GetMapping("/resetform")
//	public ModelAndView resetPasswordForm(@RequestParam("token") String token,Model model) {
//		model.addAttribute("token",token);
//		return new ModelAndView("ResetPassword.html");
//	}
	
	@PostMapping("/reset-password")
	public ResponseEntity<HttpStatusResponse> resetPassword(@RequestBody ForgotPassword password, Model model) {
		
		String token = password.getToken();
		String newPassword = password.getNewPassword();
		
		System.out.println(token);
		System.out.println(newPassword);
	    HttpStatusResponse resetPassword = mailService.resetPassword(token, newPassword);
		return new ResponseEntity<>(resetPassword, HttpStatus.OK);
	}
//	@Scheduled(fixedRate = 60000)
//	public void sendAccountLockedMail() {
//		List<>
//	}
}
