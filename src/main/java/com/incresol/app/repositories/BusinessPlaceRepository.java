package com.incresol.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incresol.app.entities.BusinessPlace;

@Repository
public interface BusinessPlaceRepository extends JpaRepository<BusinessPlace, String>{

	// find bp by using orgId
	 List<BusinessPlace> findByOrganizationOrgId(String orgId);
}
