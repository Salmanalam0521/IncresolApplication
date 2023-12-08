package com.incresol.app.models;

import java.util.List;




public class SubProjects {
	
	  private int id;
	  private int projectId;
	  private String name;
	  private String type;
	  private String description;
	  private int hours;
	  private String startDate;
	  private String endDate;
	  private String status;
	  private String assignee;
	  private List<String> employees;
	  private String checkType;
	  private List<TaskPojo> task;
	public SubProjects(int id, int parenttId, String name, String type, String description, int hours, String startDate,
			String endDate, String status, String lead, List<String> employees, String checkType, List<TaskPojo> task) {
		super();
		this.id = id;
		this.projectId = parenttId;
		this.name = name;
		this.type = type;
		this.description = description;
		this.hours = hours;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.assignee = lead;
		this.employees = employees;
		this.checkType = checkType;
		this.task = task;
	}
	public SubProjects() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int parenttId) {
		this.projectId = parenttId;
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
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	public List<TaskPojo> getTask() {
		return task;
	}
	public void setTask(List<TaskPojo> task) {
		this.task = task;
	}
	@Override
	public String toString() {
		return "SubProjects [id=" + id + ", parenttId=" + projectId + ", name=" + name + ", type=" + type
				+ ", description=" + description + ", hours=" + hours + ", startDate=" + startDate + ", endDate="
				+ endDate + ", status=" + status + ", lead=" + assignee + ", employees=" + employees + ", checkType="
				+ checkType + ", task=" + task + "]";
	}
	  
	  
	

}
