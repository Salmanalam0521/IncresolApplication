package com.incresol.app.pojo;

public class Pojo {
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
  	public long getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(long emp_id) {
		this.emp_id = emp_id;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	public long getMobile_no() {
		return mobile_no;
	}
	public void setMobile_no(long mobile_no) {
		this.mobile_no = mobile_no;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	public String getEmp_type() {
		return emp_type;
	}
	public void setEmp_type(String emp_type) {
		this.emp_type = emp_type;
	}
	public String getBp() {
		return bp;
	}
	public void setBp(String bp) {
		this.bp = bp;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Pojo(long emp_id, String email_id, long mobile_no, String password, String department, String role,
			String org_name, String emp_type, String bp, String address) {
		super();
		this.emp_id = emp_id;
		this.email_id = email_id;
		this.mobile_no = mobile_no;
		this.password = password;
		this.department = department;
		this.role = role;
		this.org_name = org_name;
		this.emp_type = emp_type;
		this.bp = bp;
		this.address = address;
	}
	public Pojo() {
		super();
		// TODO Auto-generated constructor stub
	}
	 
	 
	
	 
	 
}
