package com.incresol.app.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incresol.app.entities.User;

//@Repository

public interface UserRepository extends JpaRepository<User, String> {

	public User findByEmail(String email);


	public List<User> findByAccountNonLockedFalseAndLockedUntilBefore(LocalDateTime now);


	public boolean existsByUserId(String newUserId);


}
