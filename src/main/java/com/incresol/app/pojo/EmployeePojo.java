package com.incresol.app.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeePojo {
	 private long emp_id;
	 private String email_id ;
	 private long mobile_no;
	 private String password;
	 private String department;
	 private String role;
	 private String org_name;
	 private String emp_type;
	 private String bp;
	 private String address;
	 private int deleteStatus;
	 
}
