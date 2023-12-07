package com.incresol.app.models;



public class TaskPojo {
	
	  private int id;
	  private String name;
	  private String type;
	  private String description;
	  private String status;
	  private int hours;
	  private String startDate;
	  private String endDate;
	  private String assignee;
	  private int projectId;
	  private String checkType;
	public TaskPojo(int id, String name, String type, String description, String status, int hours, String startDate,
			String endDate, String assignee, int projectId, String findType) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.description = description;
		this.status = status;
		this.hours = hours;
		this.startDate = startDate;
		this.endDate = endDate;
		this.assignee = assignee;
		this.projectId = projectId;
		this.checkType = findType;
	}
	public TaskPojo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String findType) {
		this.checkType = findType;
	}
	@Override
	public String toString() {
		return "TaskPojo [id=" + id + ", name=" + name + ", type=" + type + ", description=" + description + ", status="
				+ status + ", hours=" + hours + ", startDate=" + startDate + ", endDate=" + endDate + ", assignee="
				+ assignee + ", projectId=" + projectId + ", findType=" + checkType + "]";
	}
	  
	  
	  

}
