package com.incresol.app.entities;



import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name="task_tbl")

public class Task {

	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private int id;
	 private String name;
	 private String type;
	 private String description;
	 private String status;
	 private int hours;
	 private String startDate;
	 private String endDate;
	 private String assignee;
	 
	 @JsonBackReference
	 @ManyToOne
	 @JoinColumn(name="project_id")
	 private Project project;

	public Task() {
		super();
		
	}

	public Task(int id, String name, String type, String description, String status, int hours, String startDate,
			String endDate, String assignee, Project project) {
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
		this.project = project;
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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", name=" + name + ", type=" + type + ", description=" + description + ", status="
				+ status + ", hours=" + hours + ", startDate=" + startDate + ", endDate=" + endDate + ", assignee="
				+ assignee + ", project=" + project + "]";
	}
	 
	 
	 
	 
	 
	 
	 
}
