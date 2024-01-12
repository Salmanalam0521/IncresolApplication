package com.incresol.app.models;

import java.util.List;

import com.incresol.app.entities.Project;
import com.incresol.app.entities.Task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectPojo {

	private int id;
	private int projectId;
	private String name;
	private String type;
	private String description;
	private int hours;
	private String startDate;
	private String endDate;
	private String status;
	private String createdBy;
	private String assignedTo;
	 private List<String> userIds;
	private List<SubProjects> subProjects;
//	public ProjectPojo(int id, int projectId, String name, String type, String description, int hours, String startDate,
//			String endDate, String status, String lead, List<String> employees, List<SubProjects> subProjects) {
//		super();
//		this.id = id;
//		this.projectId = projectId;
//		this.name = name;
//		this.type = type;
//		this.description = description;
//		this.hours = hours;
//		this.startDate = startDate;
//		this.endDate = endDate;
//		this.status = status;
//		this.assignee = lead;
////		this.employees = employees;
//		this.subProjects = subProjects;
//	}
//	public ProjectPojo() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//	public int getId() {
//		return id;
//	}
//	public void setId(int id) {
//		this.id = id;
//	}
//	public int getProjectId() {
//		return projectId;
//	}
//	public void setProjectId(int projectId) {
//		this.projectId = projectId;
//	}
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
//	public String getType() {
//		return type;
//	}
//	public void setType(String type) {
//		this.type = type;
//	}
//	public String getDescription() {
//		return description;
//	}
//	public void setDescription(String description) {
//		this.description = description;
//	}
//	public int getHours() {
//		return hours;
//	}
//	public void setHours(int hours) {
//		this.hours = hours;
//	}
//	public String getStartDate() {
//		return startDate;
//	}
//	public void setStartDate(String startDate) {
//		this.startDate = startDate;
//	}
//	public String getEndDate() {
//		return endDate;
//	}
//	public void setEndDate(String endDate) {
//		this.endDate = endDate;
//	}
//	public String getStatus() {
//		return status;
//	}
//	public void setStatus(String status) {
//		this.status = status;
//	}
//	
//	
//	public List<SubProjects> getSubProjects() {
//		return subProjects;
//	}
//	public void setSubProjects(List<SubProjects> subProjects) {
//		this.subProjects = subProjects;
//	}
//	@Override
//	public String toString() {
//		return "ProjectPojo [id=" + id + ", projectId=" + projectId + ", name=" + name + ", type=" + type
//				+ ", description=" + description + ", hours=" + hours + ", startDate=" + startDate + ", endDate="
//				+ endDate + ", status=" + status + ", lead=" + assignee + ", subProjects="
//				+ subProjects + "]";
//	}
//	public String getAssignee() {
//		return assignee;
//	}
//	public void setAssignee(String assignee) {
//		this.assignee = assignee;
//	}
//	 

}
