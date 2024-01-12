package com.incresol.app.models;

import lombok.Data;

@Data
public class UserPojo {
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private String address;
	private String department;
	private String createdBy;
	private OrgRolesPojo roles;
}
