package com.incresol.app.models;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HttpStatusResponse {
	
	private Map<String,Object> data = new HashMap<>();
	private int statusCode;
	private int errorCode;
	private String message;
	public HttpStatusResponse() {
		super();
		this.data = data;
		this.statusCode = statusCode;
		this.errorCode = errorCode;
		this.message = message;
	}
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
