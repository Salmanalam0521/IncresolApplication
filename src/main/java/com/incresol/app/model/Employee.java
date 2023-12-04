
package com.incresol.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name =" Employee_List")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Emp_id;
	private String Email_id;
	private long Mobile_no;
	private String password;
	private String Department;
	private String Role;
	private String Org_name;
	private String Emp_type;
	private String bp;
	private String Address;
	private int deleteStatus;

	
}
