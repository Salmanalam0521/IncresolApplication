package com.incresol.app.models;

import java.util.List;

import com.incresol.app.entities.OrgRoles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrgDetailsRequest {
	private String firstName;
	private String lastName;
	private String email;
	private String address;
	private String department;
	private OrgRolesPojo roles;
	private List<OrgDetails> orgDetails;
}

