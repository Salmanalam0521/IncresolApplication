package com.incresol.app.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "org_user")
@Data
public class OrgUser {
	
	
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String orgUserId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="org_id")
    private Organization org;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="bp_id")
    private BusinessPlace userBusinessPlace;
    
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name="role_id")
//    private OrgRoles role;
    
    private int mainRole;
    private String subRoles;
	
}







//@Id
//@GeneratedValue(strategy = GenerationType.UUID)
//private String orgUserId;
//
//@ManyToMany
//@JoinTable(
//        name = "org_user_organization",
//        joinColumns = @JoinColumn(name = "org_user_id"),
//        inverseJoinColumns = @JoinColumn(name = "organization_id")
//)
//private List<Organization> userOrg;
//
//@OneToOne
//@JoinColumn(name = "user_id")
//private User user;
//
//@ManyToMany
//@JoinTable(
//        name = "org_user_businessplaces",
//        joinColumns = @JoinColumn(name = "org_user_id"),
//        inverseJoinColumns = @JoinColumn(name = "businessplace_id")
//)
//private List<BusinessPlace> userBusinessPlaces;
