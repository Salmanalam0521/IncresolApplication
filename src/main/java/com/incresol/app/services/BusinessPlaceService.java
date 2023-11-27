package com.incresol.app.services;
	

import java.util.List;
import java.util.Optional;

import com.incresol.app.models.BusinessPojo;


public interface BusinessPlaceService {
	
	public String saveBusinessPlace(BusinessPojo businessPojo);
	public Optional<BusinessPojo> getBusinessPlace(String orgBpId);
	public List<BusinessPojo> getAllBusinessPlaces();
	public String deleteBusinessPlace(String orgBpId);
}
