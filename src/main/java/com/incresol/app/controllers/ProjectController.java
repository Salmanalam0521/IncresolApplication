package com.incresol.app.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incresol.app.models.ProjectPojo;
import com.incresol.app.entities.ResponseHandler;
import com.incresol.app.models.TaskPojo;
import com.incresol.app.repositories.ProjectRepository;
import com.incresol.app.services.ProjectService;


@RestController
@RequestMapping(path="/project")
@CrossOrigin("http://localhost:4200/")
public class ProjectController {
	
	@Autowired
	  private ProjectService projectService;
	  @Autowired
	  ProjectRepository projectRepository;
	
	  
	  @PostMapping("/createProject1")
	  public ResponseEntity<Object> createProject1(@RequestBody ProjectPojo project)
	  {
	    ResponseHandler handler = projectService.createProjectpojo(project);
	    
	    return new ResponseEntity<>(handler, HttpStatus.OK);
	  }
	  
	  
	  @GetMapping("/getAllParentProjects")
	  public ResponseEntity<Object>  getAllParentProjects()
	  {
	     ResponseHandler responseHandler=projectService.getAllParentProject();
	     return new ResponseEntity<>(responseHandler, HttpStatus.OK);

	  }
	  
	  
	  @GetMapping("/get")
	  public ResponseEntity<Object>  getAll(){
	     ResponseHandler responseHandler = projectService.getAllParentChildTasks();
	     return new ResponseEntity<>(responseHandler, HttpStatus.OK);
	  }
	  
	  @GetMapping("/getProjectByName/{projectname}")
	  public ResponseEntity<Object>  getProjectByName2(@PathVariable("projectname") String projectName)
	  {
		  ResponseHandler handler=projectService.getProjectByName(projectName);
	    return new ResponseEntity<>(handler,HttpStatus.OK);
	  }
	  
	  @PostMapping("/createTask/{projectId}")
	  public ResponseEntity<Object> creatTaskBasedOnId(@RequestBody TaskPojo pojo,@PathVariable("projectId") int projectId) {
		  ResponseHandler handler=projectService.createTasksBasedOnProjectId(pojo, projectId);
		  return new ResponseEntity<>(handler,HttpStatus.OK);
	  }
	  
	  @GetMapping("/getProjectById/{projectId}")
	  public ResponseEntity<Object> getProjectById(@PathVariable("projectId") int id){
	     ResponseHandler responseHandler = projectService.getProjectById(id);
	     return new ResponseEntity<>(responseHandler, HttpStatus.OK);
	  }
	  
	  @GetMapping("/getAll")
	  
		 public ResponseEntity<Object> getAll1(){
			  ResponseHandler handler=projectService.getAll();
			  return new ResponseEntity<>(handler,HttpStatus.OK);
		  }
	  
	  
	  @PutMapping("/update")
		public ResponseEntity<Object> updateProject(@RequestBody ProjectPojo pojo){
			ResponseHandler handler=projectService.updateProject(pojo);
			return new ResponseEntity<>(handler,HttpStatus.OK);
		}
	  @PutMapping("/updateTask")
		public ResponseEntity<Object > upd(@RequestBody TaskPojo pojo){
			ResponseHandler handler=projectService.update(pojo);
			return new ResponseEntity<Object>(handler,HttpStatus.OK);
		}
	  
	  @GetMapping("/parent/{name}")
		public ResponseEntity<Object> getParentTasks(@PathVariable("name") String name){
			ResponseHandler handler=projectService.getParentTasksByName(name);
			return new ResponseEntity<Object>(handler,HttpStatus.OK);
		}



	  
	 
	  
	 
	  

	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  //	  @GetMapping("/getAllChild")
//	  public ResponseEntity<Object> getAllChild(){
//	    ResponseHandler responseHandler = projectService.getAllChildProjects();
//	     return new ResponseEntity<>(responseHandler, HttpStatus.OK);
//	  }
//	  
//	 
//	  
//	 
//	  
//	 
//	  
//	 	 
//	 
//	 @GetMapping("/getParentTaskById/{id}")
//	 public ResponseEntity<Object> getParenttasksById(@PathVariable("id") int projectId) {
//		 ResponseHandler handler=projectService.getParentTasksByProjectId(projectId);
//		 return new ResponseEntity<>(handler,HttpStatus.OK);
//	 }
//	 
//	 
//	 
//	 @GetMapping("/getChildTaskById/{id}")
//	 public ResponseEntity<Object> getChildtasksById(@PathVariable("id") int projectId) {
//		 ResponseHandler handler=projectService.getChildTasksByProjectId(projectId);
//		 return new ResponseEntity<>(handler,HttpStatus.OK);
//	 }
//	 
//	
//	
	

	
	
	
	
	
	
	
//	@GetMapping("/getParentTasks")
//	 public ResponseEntity<Object> getParentTasks() {
//		 ResponseHandler handler=projectService.getParentTasks();
//		 return new ResponseEntity<>(handler, HttpStatus.OK);
//	 }

	
	 
	 
	
}

