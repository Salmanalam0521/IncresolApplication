package com.incresol.app.entities;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@NoArgsConstructor
//@AllArgsConstructor
public class ResponseHandler {
	

	private Map<String,Object> response=new HashMap<>();
	private String message;
	private int statusCode;
	private int errorCode;
	public ResponseHandler() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Map<String, Object> getResponse() {
		return response;
	}
	public void setResponse(Map<String, Object> response) {
		this.response = response;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
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
	public ResponseHandler(Map<String, Object> response, String message, int statusCode, int errorCode) {
		super();
		this.response = response;
		this.message = message;
		this.statusCode = statusCode;
		this.errorCode = errorCode;
	}

}
