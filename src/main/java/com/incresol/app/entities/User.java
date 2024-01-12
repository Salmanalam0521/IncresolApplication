package com.incresol.app.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private String address;
	private String department;
	private String createdBy;

	private String password;

	@JsonIgnoreProperties
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private OrgUser orgUser;
	
//	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	private Project project;

	private LocalDateTime accountExpiredDate;
	private boolean accountNonLocked;
	private LocalDateTime passwordExpiredDate;
	private boolean enabled;
	private int failedLoginAttempts;
	private LocalDateTime lockedUntil;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

//	public User(String userId, String userName, String email, String password, LocalDateTime accountExpiredDate,
//			boolean accountNonLocked, LocalDateTime passwordExpiredDate, boolean enabled, int failedLoginAttempts,
//			LocalDateTime lockedUntil) {
//		super();
//		this.userId = userId;
//		this.userName = userName;
//		this.email = email;
//		this.password = password;
//		this.accountExpiredDate = accountExpiredDate;
//		this.accountNonLocked = accountNonLocked;
//		this.passwordExpiredDate = passwordExpiredDate;
//		this.enabled = enabled;
//		this.failedLoginAttempts = failedLoginAttempts;
//		this.lockedUntil = lockedUntil;
//	}
//	public User() {
//		super();
//		// TODO Auto-generated constructor stub
//	}

}
