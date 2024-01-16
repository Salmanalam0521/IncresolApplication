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
import com.incresol.app.entities.Organization;
import com.incresol.app.entities.ResponseHandler;
import com.incresol.app.models.BusinessPojo;
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
	public ResponseHandler saveBusinessPlace(BusinessPojo businessPojo,String id, String orgId) {

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
				businessPlace.setDeleteStatus(0);
				businessPlace.setOrganization(organization);
				businessPlace.setCreatedBy(id);
				businessPlace = businessPlaceRepository.save(businessPlace);
				BeanUtils.copyProperties(businessPlace, businessPojo);
				return getResponse("Business created successfully", 0, 0, businessPojo);
			
			} catch (Exception e) {
				// TODO: handle exception
				return getResponse("Something went wrong", 1, 1, "BusinessPlace not saved ");	
			}	
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
//
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

    public ResponseHandler updateBusinessPlace(String businessPlaceId, BusinessPojo businessPojo) {
        logger.info("Entered into update business place section");
        String message = "";
        ResponseHandler response = null;

        try {
            // Find the existing business place by ID
            Optional<BusinessPlace> existingBusinessPlaceOptional = businessPlaceRepository.findById(businessPlaceId);

            if (existingBusinessPlaceOptional.isPresent()) {
                BusinessPlace existingBusinessPlace = existingBusinessPlaceOptional.get();

                // Update fields with new data
                existingBusinessPlace.setBusinessPlaceLegalName(businessPojo.getBusinessPlaceLegalName());
                existingBusinessPlace.setBusinessPlaceLocation(businessPojo.getBusinessPlaceLocation());
                existingBusinessPlace.setBusinessPlaceZipCode(businessPojo.getBusinessPlaceZipCode());
                existingBusinessPlace.setStateName(businessPojo.getStateName());
                existingBusinessPlace.setCountryName(businessPojo.getCountryName());
                existingBusinessPlace.setBusinessPlaceContact(businessPojo.getBusinessPlaceContact());

                // Save the updated business place
                BusinessPlace updatedBusinessPlace = businessPlaceRepository.save(existingBusinessPlace);

                // Convert the updated entity back to BusinessPojo
                BusinessPojo updatedBusinessPojo = new BusinessPojo();
                BeanUtils.copyProperties(updatedBusinessPlace, updatedBusinessPojo);

                message = "Business place updated successfully";
                response = getResponse(message, 0, 0, updatedBusinessPojo);
                logger.info(message);
            } else {
                // Business place not found
                message = "Business place with ID " + businessPlaceId + " not found";
                response = getResponse(message, 1, 1, "Business place not found");
                logger.error(message);
            }
        } catch (Exception e) {
            // Handle exceptions
            message = "Something went wrong";
            response = getResponse(message, 1, 1, "Business place not updated");
            logger.error(message, e);
        }

        logger.info("Exited from update business place section");
        return response;
    }
    
    
    //Delete businessPlace
    public ResponseHandler deleteBusinessPlace(String businessPlaceId) {
        logger.info("Entered into delete business place section");
        String message = "";
        ResponseHandler handler = null;
    	
        try {
            Optional<BusinessPlace> optionalBusinessPlace = businessPlaceRepository.findById(businessPlaceId);

            if (optionalBusinessPlace.isPresent()) {
                BusinessPlace businessPlace = optionalBusinessPlace.get();
                // Set delete status to 1 which means deleted
                businessPlace.setDeleteStatus(1);
                businessPlaceRepository.save(businessPlace);
                handler=getResponse("Business place deleted successfully", 0, 0, new BusinessPlace());
                return handler;
            } else {
            	handler=getResponse("Business place not found", 1, 1, new BusinessPlace());
                return handler;
            }
        } catch (Exception e) {
        	handler=getResponse("Something went wrong", 1, 1, new BusinessPlace());
            return handler;
        }
    }


}

