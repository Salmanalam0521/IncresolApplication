package com.incresol.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class OrgRoles {
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int roleId;
	private String roleDescription;
	private String shortId;

	@JsonIgnoreProperties
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private OrgUser orgUser;

}
