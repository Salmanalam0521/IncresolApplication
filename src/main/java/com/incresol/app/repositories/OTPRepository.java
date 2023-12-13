package com.incresol.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incresol.app.entities.OTP;

public interface OTPRepository extends JpaRepository<OTP, Long>{
	OTP findByOneTimePassword(String otp);
}
