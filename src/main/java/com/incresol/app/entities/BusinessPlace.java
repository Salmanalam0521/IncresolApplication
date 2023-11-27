package com.incresol.app.entities;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.incresol.app.models.Organization;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "bp_tbl")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BusinessPlace {

    @Id
  //  @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "bp_contact")
    private String businessPlaceContact;

    // ManyTo One relationship
    @ManyToOne
    @JoinColumn(name = "org_id")
    @JsonManagedReference
    private Organization organization;


}

