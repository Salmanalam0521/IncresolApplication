package com.incresol.app.services;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.springframework.stereotype.Service;

@Service
public class OTPService {

	private static final int OTP_LENGTH = 6;

	public String generateSecureOTP() {

		SecureRandom secureRandom;
		try {
			secureRandom = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Failed to initialize secure random number generator", e);
		}

		int otpValue = secureRandom.nextInt((int) Math.pow(10, OTP_LENGTH));

		String otpString = String.format("%0" + OTP_LENGTH + "d", otpValue);

		return otpString;
	}
}
