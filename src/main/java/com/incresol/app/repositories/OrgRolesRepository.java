package com.incresol.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incresol.app.entities.OrgRoles;

@Repository
public interface OrgRolesRepository extends JpaRepository<OrgRoles, Integer> {

	OrgRoles findByRoleDescription(String string);

	OrgRoles findByShortId(String string);

	OrgRoles findById(int role);

	
	//List<OrgRoles> findByUserId(String id);
	//List<OrgRoles> findByShortId(String shortId);
}
