package com.incresol.app.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@ToString
@Data

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER_TABLE")
public class User {

	@Id
	private String userId;
	private String userName;
	private String email;
	private String password;
	private LocalDateTime accountExpiredDate;
	private boolean accountNonLocked;
	private LocalDateTime passwordExpiredDate;
	private boolean enabled;
	private int failedLoginAttempts;
	private LocalDateTime lockedUntil;
	
	
	
	public User() {
		super();
	}

	public User(String userId, String userName, String email, String password, LocalDateTime accountExpiredDate,
			boolean accountNonLocked, LocalDateTime passwordExpiredDate, boolean enabled, int failedLoginAttempts,
			LocalDateTime lockedUntil) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.accountExpiredDate = accountExpiredDate;
		this.accountNonLocked = accountNonLocked;
		this.passwordExpiredDate = passwordExpiredDate;
		this.enabled = enabled;
		this.failedLoginAttempts = failedLoginAttempts;
		this.lockedUntil = lockedUntil;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getAccountExpiredDate() {
		return accountExpiredDate;
	}

	public void setAccountExpiredDate(LocalDateTime accountExpiredDate) {
		this.accountExpiredDate = accountExpiredDate;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public LocalDateTime getPasswordExpiredDate() {
		return passwordExpiredDate;
	}

	public void setPasswordExpiredDate(LocalDateTime passwordExpiredDate) {
		this.passwordExpiredDate = passwordExpiredDate;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getFailedLoginAttempts() {
		return failedLoginAttempts;
	}

	public void setFailedLoginAttempts(int failedLoginAttempts) {
		this.failedLoginAttempts = failedLoginAttempts;
	}

	public LocalDateTime getLockedUntil() {
		return lockedUntil;
	}

	public void setLockedUntil(LocalDateTime lockedUntil) {
		this.lockedUntil = lockedUntil;
	}

	
}
