package com.incresol.app.models;


import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.incresol.app.entities.BusinessPlace;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "org_tbl")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Organization {

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@Column(name = "contact")
	private String contact;
	
	// one to many relation
	@OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
	@JsonBackReference
	private List<BusinessPlace> businessPlaces;
	


}

