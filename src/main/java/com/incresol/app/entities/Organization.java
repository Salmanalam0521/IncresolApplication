package com.incresol.app.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "org_tbl")
@Getter
@Setter
//@NoArgsConstructor
//@AllArgsConstructor
@ToString
public class Organization {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "org_id")
	private String orgId;

	@Column(name = "org_name")
	private String organizationName;

	@Column(name = "country_name")
	private String countryName;

	@Column(name = "state_name")
	private String stateName;

	@Column(name = "addr_line1")
	private String addressLine1;

	@Column(name = "addr_line2")
	private String addressLine2;

	@Column(name = "zip_code")
	private String zipCode;

	//@Column(name = "contact")
	private String contact;
	
	//@Column(name = "createdBy")
	private String createdBy;

	@OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   // @JsonIgnoreProperties
    private List<BusinessPlace> businessPlaces;

    @JsonIgnoreProperties
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) 
    private OrgUser orgUser;
    
//    @OneToMany(mappedBy="organization",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	private List<Project> project;

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

	public List<BusinessPlace> getBusinessPlaces() {
		return businessPlaces;
	}

	public void setBusinessPlaces(List<BusinessPlace> businessPlaces) {
		this.businessPlaces = businessPlaces;
	}

	public Organization(String orgId, String organizationName, String countryName, String stateName,
			String addressLine1, String addressLine2, String zipCode, String contact,
			List<BusinessPlace> businessPlaces) {
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

	public Organization() {
		super();
		// TODO Auto-generated constructor stub
	}

}
