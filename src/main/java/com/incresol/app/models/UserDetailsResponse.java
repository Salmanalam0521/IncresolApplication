package com.incresol.app.models;

import java.util.List;

import lombok.Data;

@Data
public class UserDetailsResponse {

	private UserResponse userDetails;
	private List<OrganizationPojp> listOrganizationPojp;
	
	
}
