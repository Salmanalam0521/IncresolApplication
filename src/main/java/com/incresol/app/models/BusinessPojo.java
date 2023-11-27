package com.incresol.app.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
	
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BusinessPojo {
	
		private String businessPlaceId;
	
		private String businessPlaceLegalName;
	
		private String businessPlaceLocation;
	
		private String businessPlaceZipCode;
	
		private String stateName;
	
		private String countryName;
	
		private String businessPlaceContact;
	
		private OrganizationPojp organization;
		
	
	}

