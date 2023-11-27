package com.incresol.app.services;
import com.incresol.app.entities.ResponseHandler;
import com.incresol.app.models.OrganizationPojp;

public interface OrganizationService {
	
	public ResponseHandler saveOrganization(OrganizationPojp organizationPojo);
	
	public ResponseHandler getOrganization(String orgId);
	
	public ResponseHandler getAllOrganizations();
	
	public void deleteOrganization(String orgId);

}
