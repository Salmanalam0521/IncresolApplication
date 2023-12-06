package com.incresol.app.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerateNewPassword {
	private String oldPassword;
	private String newPassword;
	public GenerateNewPassword(String oldPassword, String newPassword) {
		super();
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
 
	
}
