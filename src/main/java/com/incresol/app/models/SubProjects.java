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
	  private String createdBy;
	  private String assignedTo;
//	  private List<String> employees;
	  private String checkType;
	  private List<TaskPojo> task;
	

}
