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
@NoArgsConstructor
@AllArgsConstructor
public class ResponseHandler {
	

	private Map<String,Object> response=new HashMap<>();
	private String message;
	private int statusCode;
	private int errorCode;

}
