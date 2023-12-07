package com.incresol.app.services;


import java.util.List;

import com.project.demo.model.Project;
import com.project.demo.pojo.ProjectPojo;
import com.project.demo.pojo.ResponseHandler;
import com.project.demo.pojo.SubProjects;
import com.project.demo.pojo.TaskPojo;

public interface ProjectServiceInter {
	
	
	public ResponseHandler getResponse(String message, int status, int errorCode, Object o);
	public ResponseHandler createProjectpojo(ProjectPojo pojo);
	public ResponseHandler getProjectByName(String pName);
	public void extracted(ProjectPojo pPojo, List<SubProjects> list);
	public ResponseHandler getProjectById(int projectId);
	public ResponseHandler getAllParentChildTasks();
	public List<TaskPojo> getTasksByProjectId(Project project);
	public ResponseHandler getAllParentProject();
	public ResponseHandler getProjectDetails(List<Project> projects,String message);
	public ResponseHandler getAllChildProjects();
	public ResponseHandler createTasksBasedOnProjectId(TaskPojo pojo, int projectId);
	 public ResponseHandler getAll();
	 public ResponseHandler getParentTasks();
	 public ResponseHandler getParentTasksByProjectId(int projectId);
	 public ResponseHandler getChildTasksByProjectId(int projectId);
	 public ResponseHandler updateProject( ProjectPojo projectPojo);
	 public ResponseHandler update(TaskPojo pojo);
	 public ResponseHandler getParentTasksByName(String name);
}
