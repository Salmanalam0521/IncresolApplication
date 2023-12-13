package com.incresol.app.models;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class PasswordResponseHandler {
	
	  private Map<String,Object> data=new HashMap<>();
	  private String message;
	  private int statusCode;
	  private int errorCode;
}
