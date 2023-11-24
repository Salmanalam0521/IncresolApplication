package com.incresol.app.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incresol.app.model.Employee;
import com.incresol.app.pojo.Pojo;
import com.incresol.app.service.EmployeService;
@CrossOrigin(value ="http://localhost:4200/" )
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
  
	@Autowired 
	private EmployeService employeeservice;
	
	@GetMapping("/getAll")
	public List<Employee> getAllEmployees(){
		return employeeservice.getAll();
	}
	
	@PostMapping("/post")
	public String createEmployee(@RequestBody Pojo employee) { 
		return employeeservice.create(employee);
	}
	

	
	@PutMapping("/put")
	public Employee update(@RequestBody Employee employee){
		return employeeservice.updateSer(employee);
	}
	

	
	 @DeleteMapping("/Delete/{id}")
	    public ResponseEntity<String> deleteEmployeeById(@PathVariable Long id) {
	        Optional<Employee> employee = employeeservice.getEmployee(id);

	        if (employee.isPresent()) {
	            employeeservice.deleteEmployeeById(id);
	            return new ResponseEntity<>("Employee with ID " +id + " deleted successfully.", HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Employee with ID " + id + " not found.", HttpStatus.NOT_FOUND);
	        }
	    }

}
