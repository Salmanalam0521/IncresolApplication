package com.incresol.app.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="project_tbl")
public class Project {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private int id;
	 
	 @Column(name="parent_pro_id")
	 private int projectId;
	 
	 @Column(name="pro_name")
	 private String name;
	 
	 @Column(name="pro_type")
	 private String type;
	 
	 @Column(name="pro_description")
	 private String description;
	 
	 @Column(name="pro_hrs")
	 private int hours;
	 
	 @Column(name="pro_start_date")
	 private String startDate;
	 
	 @Column(name="pro_end_date")
	 private String endDate;
	 
	 @Column(name="pro_status")
	 private String status;
	 
	 @Column(name="lead")
	 private String assignee;
	 private List<String> employees;
	 
	 @JsonManagedReference
	 @OneToMany(mappedBy="project",cascade = CascadeType.ALL)
	 private List<Task> tasks;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLead() {
		return assignee;
	}

	public void setLead(String lead) {
		this.assignee = lead;
	}

	public List<String> getEmployees() {
		return employees;
	}

	public void setEmployees(List<String> employees) {
		this.employees = employees;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public Project(int id, int projectId, String name, String type, String description, int hours, String startDate,
			String endDate, String status, String lead, List<String> employees, List<Task> tasks) {
		super();
		this.id = id;
		this.projectId = projectId;
		this.name = name;
		this.type = type;
		this.description = description;
		this.hours = hours;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.assignee = lead;
		this.employees = employees;
		this.tasks = tasks;
	}

	public Project() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	 
	 
	 
	 
	
	

}
