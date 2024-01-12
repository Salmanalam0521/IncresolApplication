package com.incresol.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incresol.app.entities.User;

@Repository
public interface DemoUserRepo extends JpaRepository<User, String>{
	
    public User findByEmail(String email);
}
