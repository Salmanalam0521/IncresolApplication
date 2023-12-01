package com.incresol.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incresol.app.entities.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, String> {
	
}
