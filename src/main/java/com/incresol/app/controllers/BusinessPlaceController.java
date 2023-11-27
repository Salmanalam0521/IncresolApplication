package com.incresol.app.controllers;

import org.apache.logging.log4j.LogManager;
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
import com.incresol.app.services.BusinessPlaceServiceImp;



@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/businessplaces")
public class BusinessPlaceController {

	@Autowired
	private BusinessPlaceServiceImp businessPlaceService;

	private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger();

	@PostMapping("/create/{orgId}")
	public ResponseEntity<Object> createBusinessPlace(@RequestBody BusinessPojo businessPojo,
			@PathVariable("orgId") String orgId) {

		logger.info("Entered into create business place api");
		ResponseHandler handler = businessPlaceService.saveBusinessPlace(businessPojo, orgId);
		return new ResponseEntity<>(handler,HttpStatus.OK);
				
	}

	// Get businessPlace details by using organizationId
	
	@GetMapping("/by-org/{orgId}")
	public ResponseEntity<Object> getBusinessPlacePojosByOrgId(@PathVariable String orgId) {
		logger.info("Entered into get business place api by using organizationId");
		ResponseHandler handler = businessPlaceService.getBusinessPlace(orgId);
		return new ResponseEntity<>(handler,HttpStatus.OK);
		
	}
	
	@GetMapping("/{orgBpId}")
	public ResponseEntity<Object>  getBusinessPlace(@PathVariable String orgBpId) {
		logger.info("Entered into get business place api");
		ResponseHandler handler = businessPlaceService.getBusinessPlace(orgBpId);
	    return new ResponseEntity<>(handler,HttpStatus.OK);
	}

	@GetMapping("/findAll")
	public ResponseEntity<Object>  getAllBusinessPlaces() {
		logger.info("Entered into getAllBusinessPlaces api");
		ResponseHandler handler = businessPlaceService.getAllBusinessPlaces();
		return new ResponseEntity<>(handler,HttpStatus.OK);
	}
	
//
//	@DeleteMapping("/{orgBpId}")
//	public void deleteBusinessPlace(@PathVariable String orgBpId) {
//		logger.info("Entered into delete organization api");
//		businessPlaceService.deleteBusinessPlace(orgBpId);
//	}
}
