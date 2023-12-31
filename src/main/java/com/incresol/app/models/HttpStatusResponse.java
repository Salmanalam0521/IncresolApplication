package com.incresol.app.models;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HttpStatusResponse {
	
	private Map<String,Object> data = new HashMap<>();
	private int statusCode;
	private int errorCode;
	private String message;
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
//	public String convertTOJson(HttpStatusResponse data) {
//		ObjectMapper map=new ObjectMapper();
//		String jsonString = null;
//		try {
//			jsonString = map.writeValueAsString(data);
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//		return jsonString; 
//		
//	}
	
	
	
	
}
