package com.incresol.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incresol.app.entities.PasswordResetToken;

@Repository
public interface PasswordTokenRepository extends JpaRepository<PasswordResetToken, String> {
	
	PasswordResetToken findByToken(String token);
}
