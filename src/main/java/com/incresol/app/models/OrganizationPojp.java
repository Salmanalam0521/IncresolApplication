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
	public OrganizationPojp(String orgId, String organizationName, String countryName, String stateName,
			String addressLine1, String addressLine2, String zipCode, String contact,
			List<BusinessPojo> businessPlaces) {
		super();
		this.orgId = orgId;
		this.organizationName = organizationName;
		this.countryName = countryName;
		this.stateName = stateName;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.zipCode = zipCode;
		this.contact = contact;
		this.businessPlaces = businessPlaces;
	}
	public OrganizationPojp() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public List<BusinessPojo> getBusinessPlaces() {
		return businessPlaces;
	}
	public void setBusinessPlaces(List<BusinessPojo> businessPlaces) {
		this.businessPlaces = businessPlaces;
	}

}
