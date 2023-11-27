package com.incresol.app.services;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incresol.app.entities.BusinessPlace;
import com.incresol.app.entities.ResponseHandler;
import com.incresol.app.models.BusinessPojo;
import com.incresol.app.models.Organization;
import com.incresol.app.repositories.BusinessPlaceRepository;


@Service
public class BusinessPlaceServiceImp {

	@Autowired
	private BusinessPlaceRepository businessPlaceRepository;

	private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger();

	// get response handler
	public ResponseHandler getResponse(String message, int status, int errorCode, Object o) {
		ResponseHandler handler = new ResponseHandler();
		Map<String, Object> response = handler.getResponse();
		response.put("data", o);
		handler.setErrorCode(errorCode);
		handler.setMessage(message);
		handler.setStatusCode(status);
		return handler;
	}

	// Create business place
	public ResponseHandler saveBusinessPlace(BusinessPojo businessPojo, String orgId) {

		logger.info("Entered into save business place section");
		String message = "";
		ResponseHandler response = null;
		BusinessPlace businessPlace = new BusinessPlace();
		Organization organization = new Organization();
		BusinessPlace bpTemp = null;
		try {
			businessPojo.setBusinessPlaceId(UUID.randomUUID().toString());
			organization.setOrgId(orgId);
			BeanUtils.copyProperties(businessPojo, businessPlace);
			businessPlace.setOrganization(organization);
			// System.out.println(businessPlace);
			logger.info(message);
			businessPlace = businessPlaceRepository.save(businessPlace);
			message = "Business created successfully";
			response = getResponse(message, 0, 0, businessPlace);
			// handler.responseBuilder(status, HttpStatus.OK,businessPlace);
		} catch (Exception e) {
			// TODO: handle exception
			message = "Something went wrong";
			response = getResponse(message, 1, 1, "BusinessPlace not saved ");
			logger.error(message);
		}

		logger.info("Existed from save business place section");
		BeanUtils.copyProperties(businessPlace, businessPojo);
		return response;
	}

	// Get business place by id
	public ResponseHandler getBusinessPlace(String orgBpId) {
		logger.info("Entered into get business place section");
		Optional<BusinessPojo> pojo = null;
		String message = "";
		ResponseHandler response = null;
		try {

			BeanUtils.copyProperties(businessPlaceRepository.findById(orgBpId), pojo);
			message = orgBpId + " is fetched successfully";
			response = getResponse(message, 0, 0, pojo);
			logger.info(orgBpId + " is fetched successfully");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(orgBpId + " is invalid something went wrong");
			message = orgBpId + " is invalid something went wrong";

			response = getResponse(message, 1, 1, "Something went wrong");
		}
		logger.info("Exited from get business place section");
		return response;
	}

	// Get all business places
	public ResponseHandler getAllBusinessPlaces() {
		logger.info("Entered into get all business place section");
		List<BusinessPojo> businessPojos = new ArrayList<>();
		String message = "";
		ResponseHandler response = null;
		try {
			List<BusinessPlace> list = businessPlaceRepository.findAll();
			// BeanUtils.copyProperties(list,businessPojos );
			list.stream().forEach(businessPlace -> {
				BusinessPojo businessPojo = new BusinessPojo();
				BeanUtils.copyProperties(businessPlace, businessPojo);
				businessPojos.add(businessPojo);
			}

			);
			message = "All business places fecthed successfully";
			response = getResponse(message, 0, 0, businessPojos);

			logger.info("All busines places fetched successfully..!!");
		} catch (Exception e) {
			// TODO: handle exception
			message = "Something went wrong";

			response = getResponse(message, 1, 1, "Something went wrong");
			logger.error("Something-went-wrong...!!");
		}
		logger.info("Exited from get all business place section");
		return response;

	}

	// delete business place
	public String deleteBusinessPlace(String orgBpId) {
		logger.info("Entered into delete business place section");
		String status = "";
		try {
			businessPlaceRepository.deleteById(orgBpId);
			status = "Sucessfully deleted";
			logger.info(status);
		} catch (Exception e) {
			// TODO: handle exception
			status = "Something went wrong";
			logger.error(status);
		}
		logger.info("Exited from delete business place section");
		return status;
	}

	// Get businessplaces based on organization
//	  public List<BusinessPojo> getBusinessPlacePojosByOrgId(String orgId) {
//	        List<BusinessPlace> businessPlaces = businessPlaceRepository.findByOrganizationOrgId(orgId);
//	        List<BusinessPojo> businessPlacePojos = new ArrayList<>();
//
//	        for (BusinessPlace businessPlace : businessPlaces) {
//	        	BusinessPojo businessPlacePojo = new BusinessPojo();
//	            BeanUtils.copyProperties(businessPlace, businessPlacePojo);
//	            businessPlacePojos.add(businessPlacePojo);
//	        }
//	        return businessPlacePojos;
//	    }

}

