package com.incresol.app.services;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incresol.app.entities.BusinessPlace;
import com.incresol.app.entities.ResponseHandler;
import com.incresol.app.models.BusinessPojo;
import com.incresol.app.models.Organization;
import com.incresol.app.models.OrganizationPojp;
import com.incresol.app.repositories.OrganizationRepository;

@Service
public class OrganizationServiceImp implements OrganizationService {

	@Autowired
	private OrganizationRepository organizationRepository;

	private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger();

	public ResponseHandler getResponse(String message, int status, int errorCode, Object o) {
		ResponseHandler handler = new ResponseHandler();
		Map<String, Object> response = handler.getResponse();
		response.put("data", o);
		handler.setErrorCode(errorCode);
		handler.setMessage(message);
		handler.setStatusCode(status);

		return handler;
	}

	// save or create organization
	public ResponseHandler saveOrganization(OrganizationPojp organizationPojo) {

		logger.info("Entered into save-organize-section");

		Organization organization = new Organization();
		String message = "";
		ResponseHandler response = null;
		try {
			organizationPojo.setOrgId(UUID.randomUUID().toString());
			BeanUtils.copyProperties(organizationPojo, organization);
			if (organizationPojo.getBusinessPlaces() != null) {
				List<BusinessPojo> businessPlacesPojo = organizationPojo.getBusinessPlaces();

				List<BusinessPlace> bpPlace = new ArrayList<>();
				businessPlacesPojo.stream().forEach(bpPojo -> {
					bpPojo.setBusinessPlaceId(UUID.randomUUID().toString());
					BusinessPlace businessPlace = new BusinessPlace();
					BeanUtils.copyProperties(bpPojo, businessPlace);
				});
				organization.setBusinessPlaces(bpPlace);

			}

			message = "Organization created successfully";
			response = getResponse(message, 0, 0, organizationRepository.save(organization));
			logger.info("Organization saved successfully..!!");

		} catch (Exception e) {

			e.printStackTrace();
			message = "Something went wrong";
			response = getResponse(message, 1, 1, "Organization is not saved..!!");
			logger.error("Organization is not saved..!!");

		}
		logger.info("Exited from save-organization-section");
		// BeanUtils.copyProperties(organization, organizationPojo);
		return response;

	}

	// fetch organization

	public ResponseHandler getOrganization(String orgId) {

		logger.info("Entered into get-organization-by-id-section");
		OrganizationPojp organizationPojp = null;
		String message = "";
		ResponseHandler response = null;
		try {
			Organization findById = organizationRepository.findById(orgId).orElse(new Organization());
			List<BusinessPlace> businessPlaces = findById.getBusinessPlaces();
			// businessPlaces.stream().forEach(bp->System.out.println(bp.getBusinessPlaceLegalName()));
			// BeanUtils.copyProperties(findById, organizationPojp);
			List<BusinessPojo> businessPojos = new ArrayList<>();
			if (findById != null) {
				organizationPojp = new OrganizationPojp(); // Create an instance if not already created.
				for (BusinessPlace businessPlace : businessPlaces) {
					BusinessPojo pojo = new BusinessPojo();
					BeanUtils.copyProperties(businessPlace, pojo);
					businessPojos.add(pojo);
				}
				organizationPojp.setBusinessPlaces(businessPojos);
				BeanUtils.copyProperties(findById, organizationPojp);
				message = "Organization fecthed successfully";
				response = getResponse(message, 0, 0, organizationPojp);

			}
			logger.info("Details with " + orgId + " is fecthed successfully..!!");

		} catch (Exception e) {
			// TODO: handle exception
			logger.error(orgId + "is Invalid");
			message = "Something went wrong..!!";
			response = getResponse(message, 0, 0, "Organization details not fetched successfully");

		}
		logger.info("Exited from get-organization-section");
		return response;
	}

	// fetch all organization details

	public ResponseHandler getAllOrganizations() {

		logger.info("Entered into get-all-organization-section");
		//List<OrganizationPojp> list = null;
		String message = "";
		ResponseHandler response = null;
		List<OrganizationPojp> orgPojo = new ArrayList<>();

		try {

			List<Organization> findAll = organizationRepository.findAll();

			findAll.stream().forEach(organization -> {
				OrganizationPojp organizationPojp = new OrganizationPojp();
				BeanUtils.copyProperties(organization, organizationPojp);
				List<BusinessPlace> businessPlaces = organization.getBusinessPlaces();
				List<BusinessPojo> businessPojos = new ArrayList<>();
				businessPlaces.stream().forEach(businessPlace -> {
					BusinessPojo businessPojo = new BusinessPojo();
					BeanUtils.copyProperties(businessPlace, businessPojo);
					businessPojos.add(businessPojo);
				});
				organizationPojp.setBusinessPlaces(businessPojos);
				orgPojo.add(organizationPojp);

			});

			message = "Organization fecthed successfully";
			response = getResponse(message, 0, 0, orgPojo);

			logger.info("All organizations are fetched successufully");
		} catch (Exception e) {
			// TODO: handle exception
			message = "Something went wrong..!!";
			response = getResponse(message, 0, 0, "Organization details not fetched successfully");
			logger.error("Something went wrong");

		}
		logger.info("Exited from get-all-organization-section");
		return response;
	}

	// delete organization

	public void deleteOrganization(String orgId) {

		logger.info("Entered into delete-organization-section");
		try {
			organizationRepository.deleteById(orgId);
			logger.info(orgId + " is deleted successfully");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(orgId + " is Invalid");
		}
		logger.info("Exited from delete-organization-section");

	}

}

