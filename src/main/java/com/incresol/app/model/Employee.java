
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
//@AllArgsConstructor
//@NoArgsConstructor

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
	public long getEmp_id() {
		return Emp_id;
	}
	public void setEmp_id(long emp_id) {
		Emp_id = emp_id;
	}
	public String getEmail_id() {
		return Email_id;
	}
	public void setEmail_id(String email_id) {
		Email_id = email_id;
	}
	public long getMobile_no() {
		return Mobile_no;
	}
	public void setMobile_no(long mobile_no) {
		Mobile_no = mobile_no;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDepartment() {
		return Department;
	}
	public void setDepartment(String department) {
		Department = department;
	}
	public String getRole() {
		return Role;
	}
	public void setRole(String role) {
		Role = role;
	}
	public String getOrg_name() {
		return Org_name;
	}
	public void setOrg_name(String org_name) {
		Org_name = org_name;
	}
	public String getEmp_type() {
		return Emp_type;
	}
	public void setEmp_type(String emp_type) {
		Emp_type = emp_type;
	}
	public String getBp() {
		return bp;
	}
	public void setBp(String bp) {
		this.bp = bp;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public int getDeleteStatus() {
		return deleteStatus;
	}
	public void setDeleteStatus(int deleteStatus) {
		this.deleteStatus = deleteStatus;
	}
	public Employee(long emp_id, String email_id, long mobile_no, String password, String department, String role,
			String org_name, String emp_type, String bp, String address, int deleteStatus) {
		super();
		Emp_id = emp_id;
		Email_id = email_id;
		Mobile_no = mobile_no;
		this.password = password;
		Department = department;
		Role = role;
		Org_name = org_name;
		Emp_type = emp_type;
		this.bp = bp;
		Address = address;
		this.deleteStatus = deleteStatus;
	}
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	

	
}
