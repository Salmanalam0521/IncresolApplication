package com.incresol.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incresol.app.entities.ResponseHandler;
import com.incresol.app.models.BusinessPojo;
import com.incresol.app.models.OrgDetailsRequest;
import com.incresol.app.models.OrganizationPojp;
import com.incresol.app.models.ProjectPojo;
import com.incresol.app.models.TaskPojo;
import com.incresol.app.models.UserPojo;
import com.incresol.app.models.UserResponse;
import com.incresol.app.services.AdminService;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/admin")
@RestController
public class AdminController {

	@Autowired
	private AdminService adminService;

	@GetMapping("/getuserdetails/{email}")
	public UserResponse atLogin(@PathVariable String email) {
		return adminService.atLogin(email);
	}
	
	@PostMapping("/savedetails")
	public String saveUser(@RequestBody OrgDetailsRequest details) throws Exception {

		System.out.println("Reached at /savedetails");
		return adminService.saveAllDetailsOfUser(details);
	}

	@GetMapping("/getbusinessdetails/{id}/{orgId}")
	public List<BusinessPojo> getAllDetails(@PathVariable("id") String id,@PathVariable("orgId") String orgId) {
		return adminService.getBusinessPlaces(id,orgId);
	}

	@GetMapping("/getAllUsersInOrg/{id}/{orgId}")
	public List<UserPojo> getAllUsersInOrg(@PathVariable("id") String id,@PathVariable("orgId") String orgId){
		return adminService.getAllUsersInOrg(id, orgId);
	}
	
	@PostMapping("/saveorg")
	public Object organizationPojp(@RequestBody OrganizationPojp organizationPojp) {
		return adminService.createOrganization(organizationPojp);
	}

	@PostMapping("/savebusiness/{orgId}")
	public BusinessPojo businessPojo(@RequestBody BusinessPojo businessPojo, @PathVariable("orgId") String orgId)
			throws Exception {
		return adminService.addBusinessPojo(businessPojo, orgId);
	}

//	@GetMapping("/getAllOrgUserDetails/{orgId}")
//	public List<UserResponse> getAllOrgUserDetails(@PathVariable("orgId") String orgId){
//		return adminService.getAllOrgUserDetails(orgId);
//	}
	
	// ============================================================================

	@PostMapping("/createProject")
	public ResponseEntity<Object> createProject1(@RequestBody ProjectPojo project) {
		ResponseHandler handler = adminService.createProjectpojo(project);

		return new ResponseEntity<>(handler, HttpStatus.OK);
	}

	@GetMapping("/get")
	public ResponseEntity<Object> getAll() {
		ResponseHandler responseHandler = adminService.getAllParentChildTasks();
		return new ResponseEntity<>(responseHandler, HttpStatus.OK);
	}

	@PostMapping("/createTask/{projectId}")
	public ResponseEntity<Object> creatTaskBasedOnId(@RequestBody TaskPojo pojo,
			@PathVariable("projectId") int projectId) {
		ResponseHandler handler = adminService.createTasksBasedOnProjectId(pojo, projectId);
		return new ResponseEntity<>(handler, HttpStatus.OK);
	}
}
