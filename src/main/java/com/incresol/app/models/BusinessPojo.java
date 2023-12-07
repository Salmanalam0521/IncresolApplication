package com.incresol.app.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
	
@Getter
@Setter

@ToString
public class BusinessPojo {
	
		private String businessPlaceId;
	
		public BusinessPojo() {
			super();
			// TODO Auto-generated constructor stub
		}

		public BusinessPojo(String businessPlaceId, String businessPlaceLegalName, String businessPlaceLocation,
				String businessPlaceZipCode, String stateName, String countryName, String businessPlaceContact,
				OrganizationPojp organization) {
			super();
			this.businessPlaceId = businessPlaceId;
			this.businessPlaceLegalName = businessPlaceLegalName;
			this.businessPlaceLocation = businessPlaceLocation;
			this.businessPlaceZipCode = businessPlaceZipCode;
			this.stateName = stateName;
			this.countryName = countryName;
			this.businessPlaceContact = businessPlaceContact;
			this.organization = organization;
		}

		public String getBusinessPlaceId() {
			return businessPlaceId;
		}

		public void setBusinessPlaceId(String businessPlaceId) {
			this.businessPlaceId = businessPlaceId;
		}

		public String getBusinessPlaceLegalName() {
			return businessPlaceLegalName;
		}

		public void setBusinessPlaceLegalName(String businessPlaceLegalName) {
			this.businessPlaceLegalName = businessPlaceLegalName;
		}

		public String getBusinessPlaceLocation() {
			return businessPlaceLocation;
		}

		public void setBusinessPlaceLocation(String businessPlaceLocation) {
			this.businessPlaceLocation = businessPlaceLocation;
		}

		public String getBusinessPlaceZipCode() {
			return businessPlaceZipCode;
		}

		public void setBusinessPlaceZipCode(String businessPlaceZipCode) {
			this.businessPlaceZipCode = businessPlaceZipCode;
		}

		public String getStateName() {
			return stateName;
		}

		public void setStateName(String stateName) {
			this.stateName = stateName;
		}

		public String getCountryName() {
			return countryName;
		}

		public void setCountryName(String countryName) {
			this.countryName = countryName;
		}

		public String getBusinessPlaceContact() {
			return businessPlaceContact;
		}

		public void setBusinessPlaceContact(String businessPlaceContact) {
			this.businessPlaceContact = businessPlaceContact;
		}

		public OrganizationPojp getOrganization() {
			return organization;
		}

		public void setOrganization(OrganizationPojp organization) {
			this.organization = organization;
		}

		private String businessPlaceLegalName;
	
		private String businessPlaceLocation;
	
		private String businessPlaceZipCode;
	
		private String stateName;
	
		private String countryName;
	
		private String businessPlaceContact;
	
		private OrganizationPojp organization;

		public String getBusinessPlaceId() {
			return businessPlaceId;
		}

		public void setBusinessPlaceId(String businessPlaceId) {
			this.businessPlaceId = businessPlaceId;
		}

		public String getBusinessPlaceLegalName() {
			return businessPlaceLegalName;
		}

		public void setBusinessPlaceLegalName(String businessPlaceLegalName) {
			this.businessPlaceLegalName = businessPlaceLegalName;
		}

		public String getBusinessPlaceLocation() {
			return businessPlaceLocation;
		}

		public void setBusinessPlaceLocation(String businessPlaceLocation) {
			this.businessPlaceLocation = businessPlaceLocation;
		}

		public String getBusinessPlaceZipCode() {
			return businessPlaceZipCode;
		}

		public void setBusinessPlaceZipCode(String businessPlaceZipCode) {
			this.businessPlaceZipCode = businessPlaceZipCode;
		}

		public String getStateName() {
			return stateName;
		}

		public void setStateName(String stateName) {
			this.stateName = stateName;
		}

		public String getCountryName() {
			return countryName;
		}

		public void setCountryName(String countryName) {
			this.countryName = countryName;
		}

		public String getBusinessPlaceContact() {
			return businessPlaceContact;
		}

		public void setBusinessPlaceContact(String businessPlaceContact) {
			this.businessPlaceContact = businessPlaceContact;
		}

		public OrganizationPojp getOrganization() {
			return organization;
		}

		public void setOrganization(OrganizationPojp organization) {
			this.organization = organization;
		}

		public BusinessPojo(String businessPlaceId, String businessPlaceLegalName, String businessPlaceLocation,
				String businessPlaceZipCode, String stateName, String countryName, String businessPlaceContact,
				OrganizationPojp organization) {
			super();
			this.businessPlaceId = businessPlaceId;
			this.businessPlaceLegalName = businessPlaceLegalName;
			this.businessPlaceLocation = businessPlaceLocation;
			this.businessPlaceZipCode = businessPlaceZipCode;
			this.stateName = stateName;
			this.countryName = countryName;
			this.businessPlaceContact = businessPlaceContact;
			this.organization = organization;
		}

		public BusinessPojo() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		
		
		
		
	
	}

