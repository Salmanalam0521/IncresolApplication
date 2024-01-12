package com.incresol.app.entities;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bp_tbl")
//@Getter
//@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BusinessPlace {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "org_bp_id")
	private String businessPlaceId;

	@Column(name = "bp_legal_name")
	private String businessPlaceLegalName;

	@Column(name = "bp_location")
	private String businessPlaceLocation;

	@Column(name = "bp_zip_code")
	private String businessPlaceZipCode;

	@Column(name = "state_name")
	private String stateName;

	@Column(name = "country_name")
	private String countryName;

	@Column(name = "delete_status")
	private int deleteStatus;

	@Column(name = "bp_contact")
	private String businessPlaceContact;

	private String createdBy;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "org_id")
	@JsonIgnoreProperties
	private Organization organization;

	@JsonIgnoreProperties
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private OrgUser orgUser;

//	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	private Project project;
	
//	public String getBusinessPlaceId() {
//		return businessPlaceId;
//	}
//
//	public void setBusinessPlaceId(String businessPlaceId) {
//		this.businessPlaceId = businessPlaceId;
//	}
//
//	public String getBusinessPlaceLegalName() {
//		return businessPlaceLegalName;
//	}
//
//	public void setBusinessPlaceLegalName(String businessPlaceLegalName) {
//		this.businessPlaceLegalName = businessPlaceLegalName;
//	}
//
//	public String getBusinessPlaceLocation() {
//		return businessPlaceLocation;
//	}
//
//	public void setBusinessPlaceLocation(String businessPlaceLocation) {
//		this.businessPlaceLocation = businessPlaceLocation;
//	}
//
//	public String getBusinessPlaceZipCode() {
//		return businessPlaceZipCode;
//	}
//
//	public void setBusinessPlaceZipCode(String businessPlaceZipCode) {
//		this.businessPlaceZipCode = businessPlaceZipCode;
//	}
//
//	public String getStateName() {
//		return stateName;
//	}
//
//	public void setStateName(String stateName) {
//		this.stateName = stateName;
//	}
//
//	public String getCountryName() {
//		return countryName;
//	}
//
//	public void setCountryName(String countryName) {
//		this.countryName = countryName;
//	}
//
//	public int getDeleteStatus() {
//		return deleteStatus;
//	}
//
//	public void setDeleteStatus(int deleteStatus) {
//		this.deleteStatus = deleteStatus;
//	}
//
//	public String getBusinessPlaceContact() {
//		return businessPlaceContact;
//	}
//
//	public void setBusinessPlaceContact(String businessPlaceContact) {
//		this.businessPlaceContact = businessPlaceContact;
//	}
//
//	public BusinessPlace(String businessPlaceId, String businessPlaceLegalName, String businessPlaceLocation,
//			String businessPlaceZipCode, String stateName, String countryName, int deleteStatus,
//			String businessPlaceContact) {
//		super();
//		this.businessPlaceId = businessPlaceId;
//		this.businessPlaceLegalName = businessPlaceLegalName;
//		this.businessPlaceLocation = businessPlaceLocation;
//		this.businessPlaceZipCode = businessPlaceZipCode;
//		this.stateName = stateName;
//		this.countryName = countryName;
//		this.deleteStatus = deleteStatus;
//		this.businessPlaceContact = businessPlaceContact;
//	}
//
//	public BusinessPlace() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//
//	public Organization getOrganization() {
//		return organization;
//	}
//
//	public void setOrganization(Organization organization) {
//		this.organization = organization;
//	}
//

}
