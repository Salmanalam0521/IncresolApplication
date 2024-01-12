package com.incresol.app.models;

import java.util.List;

import lombok.Data;

@Data
public class UserResponse {
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private String address;
	private String department;
	private String createdBy;
	private OrgRolesPojo roles;
	private List<OrganizationPojp> organizations;
//	public String getUserName() {
//		return userName;
//	}
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}
//	public String getEmail() {
//		return email;
//	}
//	public void setEmail(String email) {
//		this.email = email;
//	}
	
	
		
}
