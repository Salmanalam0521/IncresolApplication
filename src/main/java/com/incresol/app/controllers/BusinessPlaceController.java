package com.incresol.app.controllers;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	@PostMapping("/create/{id}")
	public ResponseEntity<Object> createBusinessPlace(@RequestBody BusinessPojo businessPojo,@PathVariable("id") String id) {

		logger.info("Entered into create business place api");
		ResponseHandler responseHandler = businessPlaceService.saveBusinessPlace(businessPojo,id);
		return new ResponseEntity<>(responseHandler, HttpStatus.OK);
				
	}


	
	@GetMapping("/{orgBpId}")
	public ResponseEntity<Object>  getBusinessPlace(@PathVariable String orgBpId) {
		logger.info("Entered into get business place api");
		ResponseHandler responseHandler = businessPlaceService.getBusinessPlace(orgBpId);
		return new ResponseEntity<>(responseHandler, HttpStatus.OK);
	}

	@GetMapping("/findAll")
	public ResponseEntity<Object>  getAllBusinessPlaces() 
	{
		logger.info("Entered into getAllBusinessPlaces api");
		ResponseHandler responseHandler = businessPlaceService.getAllBusinessPlaces();
		return new ResponseEntity<>(responseHandler, HttpStatus.OK);
		
	      
	}
	
	 // Update business place
    @PutMapping("/update/{businessPlaceId}")
    public ResponseEntity<Object> updateBusinessPlace(@PathVariable String businessPlaceId, @RequestBody BusinessPojo businessPojo) 
    {
        ResponseHandler responseHandler = businessPlaceService.updateBusinessPlace(businessPlaceId, businessPojo);
        return new ResponseEntity<>(responseHandler, HttpStatus.OK);
      
    }
	
//  Delete business place
    
	@DeleteMapping("/{orgBpId}")
	public ResponseEntity<Object> deleteBusinessPlace(@PathVariable("orgBpId") String orgBpId) {
		
		logger.info("Entered into delete organization api");
		ResponseHandler responseHandler =businessPlaceService.deleteBusinessPlace(orgBpId);
		return new ResponseEntity<>(responseHandler, HttpStatus.OK);
	}
}
