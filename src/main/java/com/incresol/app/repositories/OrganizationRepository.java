package com.incresol.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incresol.app.entities.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, String> {

	//List<Organization> findByOrganizationName(String organizationName);
	
	Organization findByOrganizationName(String orgName);
	//Organization findByOrgId(String orgId);
	
}
