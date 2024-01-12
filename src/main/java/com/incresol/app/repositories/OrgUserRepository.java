package com.incresol.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.incresol.app.entities.BusinessPlace;
import com.incresol.app.entities.OrgRoles;
import com.incresol.app.entities.OrgUser;
import com.incresol.app.entities.Organization;
import com.incresol.app.entities.Project;
import com.incresol.app.entities.Task;
import com.incresol.app.entities.User;

@Repository
public interface OrgUserRepository extends JpaRepository<OrgUser, String> {

	List<OrgUser> findByUser(User savedUser);

	@Query("SELECT DISTINCT ou.org FROM OrgUser ou WHERE ou.user = :user")
    List<Organization> findDistinctOrgByUser(@Param("user") User user);

	@Query("SELECT DISTINCT ou.userBusinessPlace FROM OrgUser ou WHERE ou.user = :user")
	List<BusinessPlace> findDistinctUserBusinessPlaceByUser(@Param("user") User user);


//	@Query("SELECT DISTINCT ou.project FROM OrgUser ou WHERE ou.user = :user")
//	List<Project> findDistinctProjectByUser(@Param("user") User user);
//	
//	@Query("SELECT DISTINCT ou.task FROM OrgUser ou WHERE ou.user = :user")
//	List<Task> findDistinctTaskByUser(@Param("user") User user);
	
	 @Query("SELECT DISTINCT ou.userBusinessPlace FROM OrgUser ou WHERE ou.user = :user AND ou.org = :organization")          
	    List<BusinessPlace> findDistinctUserBusinessPlaceByUserAndOrg(
	            @Param("user") User user,
	            @Param("organization") Organization organization
	    );
	
	 
	 
	OrgUser findByUserBusinessPlace(BusinessPlace businessPlace);

	OrgUser findProjectByUser(User existingUser);

	OrgUser findTaskByUser(User existingUser);

	//List<User> findDistinctUserByOrgAndMainRole(Organization organization, OrgRoles role);

	//int findDistinctMainRoleByUserAndOrg(User user, Organization organization);


	//String findDistinctSubRolesByUserAndOrg(User user, Organization organization);

	//List<User> findDistinctUserByOrgAndMainRoleNot1(Organization organization);

	@Query("SELECT DISTINCT ou.mainRole FROM OrgUser ou WHERE ou.user = :user")
	int findDistinctMainRoleByUser(@Param("user") User user);

	@Query("SELECT DISTINCT ou.subRoles FROM OrgUser ou WHERE ou.user = :user")
	String findDistinctSubRolesByUser(@Param("user") User user);

	
	@Query("SELECT DISTINCT ou.user FROM OrgUser ou WHERE ou.org = :organization AND ou.userBusinessPlace = :businessPlace")
	List<User> findDistinctUserByOrgAndUserBusinessPlace(@Param("organization") Organization organization,@Param("businessPlace") BusinessPlace businessPlace);

	@Query("SELECT DISTINCT ou.user FROM OrgUser ou WHERE ou.org = :organization")
	List<User> findDistinctUserByOrg(Organization organization);


	@Query("SELECT DISTINCT ou.user FROM OrgUser ou WHERE ou.org = :organization AND ou.mainRole > 1")
	List<User> findDistinctUserByOrgAndMainRoleNotAdmin(@Param("organization") Organization organization);
	
	@Query("SELECT DISTINCT ou.userBusinessPlace FROM OrgUser ou WHERE ou.org = :organization")
	List<BusinessPlace> findDistinctUserBusinessPlaceByOrg(@Param("organization") Organization organization);

	@Query("SELECT DISTINCT ou.org FROM OrgUser ou WHERE ou.mainRole = 1")
	List<Organization> findDistinctOrgByMainRoleAdmin();

//	List<OrgUser> findByUserEmail(String string);

	//Optional<OrgUser> findByBusinessPlaceId(String businessPlaceId);
	
	//List<OrgUser> findByOrgUserId(String userId);

	//List<OrgUser> findByUserId(User user);

	//List<OrgUser> findByOrgId(String orgId);

	//List<OrgUser> findByOrgName(String organizationName);
}
