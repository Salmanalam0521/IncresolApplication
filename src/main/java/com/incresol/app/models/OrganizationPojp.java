package com.incresol.app.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationPojp {
	
	private String orgId;
	private String organizationName;
	private String countryName;
	private String stateName;
	private String addressLine1;
	private String addressLine2;
	private String zipCode;
	private String contact;
	private List<BusinessPojo> businessPlaces;

}
