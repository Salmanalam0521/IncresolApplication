package com.incresol.app.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskPojo {

	private int id;
	private String name;
	private String type;
	private String description;
	private String status;
	private int hours;
	private String startDate;
	private String endDate;
	private String createdBy;
	private String assignedTo;
	private int projectId;
	private String checkType;
	private List<String> userIds;

}
