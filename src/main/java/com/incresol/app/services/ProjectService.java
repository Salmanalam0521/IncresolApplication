package com.incresol.app.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incresol.app.entities.BusinessPlace;
import com.incresol.app.entities.Organization;
import com.incresol.app.entities.Project;
import com.incresol.app.entities.ResponseHandler;
import com.incresol.app.entities.Task;
import com.incresol.app.entities.User;
import com.incresol.app.models.ProjectPojo;
import com.incresol.app.models.SubProjects;
import com.incresol.app.models.TaskPojo;
import com.incresol.app.repositories.BusinessPlaceRepository;
import com.incresol.app.repositories.OrganizationRepository;
import com.incresol.app.repositories.ProjectRepository;
import com.incresol.app.repositories.TaskRepository;
import com.incresol.app.repositories.UserRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private TaskRepository repository;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private OrganizationRepository organizationRepository;
	@Autowired
	private BusinessPlaceRepository businessPlaceRepository;

	public ResponseHandler getResponse(String message, int status, int errorCode, Object o) {
		ResponseHandler handler = new ResponseHandler();
		Map<String, Object> response = handler.getResponse();
		response.put("data", o);
		handler.setErrorCode(errorCode);
		handler.setMessage(message);
		handler.setStatusCode(status);

		return handler;
	}

	public ResponseHandler createProjectpojo(ProjectPojo pojo, String orgId, String bpId) {

		Project project = projectRepository.findByName(pojo.getName());
		ResponseHandler responseHandler = new ResponseHandler();

		if (project == null) {

			try {

				Project newProject = new Project();
				BeanUtils.copyProperties(pojo, newProject);

				Optional<Organization> organization = organizationRepository.findById(orgId);
				Optional<BusinessPlace> businessplace = businessPlaceRepository.findById(bpId);
				newProject.setOrganization(organization.get());
				newProject.setBusinessPlace(businessplace.get());
				// newProject.setUser(user.get());
				List<User> userList = new ArrayList<>();
				List<String> userIds = pojo.getUserIds();
				for (String userId : userIds) {
					Optional<User> user = userRepo.findById(userId);
					userList.add(user.get());
				}

				newProject.setUser(userList);
				List<SubProjects> subProjects = pojo.getSubProjects();
				if (subProjects != null && !subProjects.isEmpty()) {
					List<Task> taskList = new ArrayList<>();

					for (SubProjects subPro : subProjects) {
						List<TaskPojo> taskPojos = subPro.getTask();
						for (TaskPojo taskPojo : taskPojos) {
							Task task = new Task();
							BeanUtils.copyProperties(taskPojo, task);
							task.setProject(newProject);
							taskList.add(task);
						}
					}
					newProject.setTasks(taskList);
				} else {
					newProject.setTasks(null);
				}

				Project save = projectRepository.save(newProject);
				BeanUtils.copyProperties(save, pojo);
				responseHandler = getResponse("Project created successfully", 0, 0, pojo);

			} catch (Exception e) {
				responseHandler = getResponse("Something went wrong", 1, 1, null);
			}

		} else {
			List<User> dbUserList = project.getUser();
			List<User> userList = new ArrayList<>();
			userList.addAll(dbUserList);
			List<String> userIds = pojo.getUserIds();
			for (String userId : userIds) {
				Optional<User> user = userRepo.findById(userId);
				Optional<Project> projectFound = projectRepository.findByIdAndUser(project.getId(),user.get());
				if(projectFound.isPresent()) {
					continue;
				}
				userList.add(user.get());
			}
			project.setUser(userList);
			Project save = projectRepository.save(project);
			pojo.setUserIds(null);
			BeanUtils.copyProperties(save, pojo);
			responseHandler = getResponse("Project updated successfully", 0, 0, pojo);
			
		}

		return responseHandler;
	}

	public ResponseHandler getUser1(String id) {
		Optional<User> userOptional = userRepo.findById(id);
		List<Project> projects = projectRepository.findAllByUser(userOptional.get());
		ResponseHandler responseHandler = new ResponseHandler();
		List<ProjectPojo> listProject = new ArrayList<>();

		try {
			if (userOptional.isPresent()) {
				for (Project project : projects) {
					ProjectPojo projectPojo = new ProjectPojo();
					BeanUtils.copyProperties(project, projectPojo);
					listProject.add(projectPojo);
				}
				responseHandler = getResponse("Projects with tasks fetched successfully", 0, 0, listProject);
			} else {
				responseHandler = getResponse("No Projects assigned to this id " + id, 1, 1, null);
			}

		} catch (Exception e) {
			responseHandler = getResponse("Something went wrong while fetching projects", 1, 1, null);
		}

		return responseHandler;
	}

	
	public ResponseHandler getAllProjectSubProjectTasksBasedOnUserId(String userId) {
		ResponseHandler responseHandler = new ResponseHandler();
		Optional<User> findById = userRepo.findById(userId);
		User user=findById.get();
		
		List<Project> projects = projectRepository.findAllByUser(user);

		List<ProjectPojo> listProject = new ArrayList<>();
		Map<Integer, ProjectPojo> mapParent = new HashMap<>();
		
		try {
			if(findById.isPresent()) {
			for (Project project : projects) {

				if (project.getProjectId() == 0) {

					ProjectPojo parentPojo = new ProjectPojo();

					BeanUtils.copyProperties(project, parentPojo);

					List<TaskPojo> tasksFound = getTasksByProjectId(project);
					List<SubProjects> listOfSub = new ArrayList<>();
					for (TaskPojo task : tasksFound) {
						SubProjects pro = new SubProjects();
						BeanUtils.copyProperties(task, pro);
						pro.setCheckType("Parent-Task");
						listOfSub.add(pro);
					}

					parentPojo.setSubProjects(listOfSub);

					mapParent.put(project.getId(), parentPojo);

				}
			}

			List<SubProjects> listOFChild = new ArrayList<>();

			List<TaskPojo> listOfTask = new ArrayList<>();

			for (Project project : projects) {
				if (project.getProjectId() != 0) {

					SubProjects childProject = new SubProjects();
					childProject.setProjectId(project.getProjectId());
					List<TaskPojo> tasksFound = getTasksByProjectId(project);
					System.out.println(tasksFound);
					listOfTask.addAll(tasksFound);
					childProject.setTask(tasksFound);
					BeanUtils.copyProperties(project, childProject);
					childProject.setCheckType("Child Project");

					ProjectPojo parentPojo = mapParent.get(project.getProjectId());

					if (parentPojo != null) {
						List<SubProjects> childProjects = parentPojo.getSubProjects();
						if (childProjects == null) {
							childProjects = new ArrayList<>();

							parentPojo.setSubProjects(childProjects);

						}

						childProjects.add(childProject);

						listOFChild.add(childProject);

					}
				}
			}

			for (Map.Entry<Integer, ProjectPojo> pp : mapParent.entrySet()) {
				ProjectPojo value = pp.getValue();
				List<SubProjects> listOfChild = new ArrayList<>();

				List<SubProjects> subProjects = value.getSubProjects();
				listOfChild.addAll(subProjects);

				for (SubProjects cp : listOFChild) {

					if (value.getId() == cp.getProjectId()) {

						boolean isChildProjectExists = listOfChild.stream().anyMatch(obj -> obj instanceof SubProjects
								&& ((SubProjects) obj).getProjectId() == cp.getProjectId());

						if (!isChildProjectExists) {
							SubProjects childP = new SubProjects();
							BeanUtils.copyProperties(cp, childP);
							listOfChild.add(childP);
						}

					}
				}

				value.setSubProjects(listOfChild);
				listProject.add(value);
				responseHandler = getResponse("Project with childs and tasks fetched successfully", 0, 0, listProject);

			}
		}

		} catch (Exception e) {
			// TODO: handle exception
			responseHandler = getResponse("Something went wrong while fecthing projetcs", 1, 1, new ProjectPojo());
		}

		return responseHandler;
	}
	
	
	
	
	public ResponseHandler getProjectByName(String pName) {

		Project findAll = projectRepository.findByName(pName);
		ResponseHandler handler = new ResponseHandler();
		try {
			ProjectPojo pPojo = new ProjectPojo();
			BeanUtils.copyProperties(findAll, pPojo);
			List<SubProjects> list = new ArrayList<SubProjects>();
			List<Task> task = findAll.getTasks();
			for (Task tasks : task) {
				SubProjects childProjects = new SubProjects();
				BeanUtils.copyProperties(tasks, childProjects);
				childProjects.setProjectId(findAll.getId());
				childProjects.setCheckType("Task");
				list.add(childProjects);

			}

			extracted(pPojo, list);
			pPojo.setSubProjects(list);
			handler = getResponse("Project by name fetched successfully", 0, 0, pPojo);

		} catch (Exception e) {
			handler = getResponse("Project by name Not found", 1, 1, new ProjectPojo());
		}

		return handler;
	}

	public void extracted(ProjectPojo pPojo, List<SubProjects> list) {
		List<Project> findChild = projectRepository.findByProjectId(pPojo.getId());

		if (findChild.size() != 0) {
			for (Project p : findChild) {
				SubProjects childProjects = new SubProjects();
				BeanUtils.copyProperties(p, childProjects);
				childProjects.setProjectId(p.getProjectId());
				childProjects.setCheckType("Child Project ");
				list.add(childProjects);
//        List<Task> listOfTasks = p.getTasks();
//        List<TaskPojo> taskPojos = new ArrayList<TaskPojo>();
//        List<SubProjects> task3=new ArrayList<SubProjects>();
//        
//        

//        
//        listOfTasks.stream().forEach(task2 -> {
//          TaskPojo pojo = new TaskPojo();
//          task2.setProject(p);
//          BeanUtils.copyProperties(task2, pojo);
//          childProjects.setTask(taskPojos);
////          childProjects.add(taskPojos);
//
//    });
//     childProjects.setTask(taskPojos);
			}
		}
	}

	public ResponseHandler getProjectById(int projectId) {

		Project findById = projectRepository.findById(projectId);
		ResponseHandler responseHandler = new ResponseHandler();

		if (findById != null) {
			try {

				ProjectPojo pojo = new ProjectPojo();
				BeanUtils.copyProperties(findById, pojo);
				List<SubProjects> objects = new ArrayList<SubProjects>();
				List<Task> tasks = findById.getTasks();
				for (Task t : tasks) {
					SubProjects childProjects = new SubProjects();
					BeanUtils.copyProperties(t, childProjects);
					childProjects.setCheckType("Parent-Task");
					objects.add(childProjects);
				}
				pojo.setSubProjects(objects);
				responseHandler = getResponse("Project fetched -successfully", 0, 0, pojo);

			} catch (Exception e) {
				responseHandler = getResponse("Something went wrong", 1, 1, new ProjectPojo());
			}

		} else {

			responseHandler = getResponse("Invalid Project id", 1, 1, new ProjectPojo());
		}

		return responseHandler;

	}

	public ResponseHandler getAllParentChildTasks() {

		ResponseHandler responseHandler = new ResponseHandler();
		List<Project> projects = projectRepository.findAll();

		List<ProjectPojo> listProject = new ArrayList<>();
		Map<Integer, ProjectPojo> mapParent = new HashMap<>();
		try {

			for (Project project : projects) {

				if (project.getProjectId() == 0) {
					List<User> user = project.getUser();
                    List<String> ids=new ArrayList<>();
                    user.stream().forEach(u->{
                    	 ids.add(u.getUserId());
                    	
                    });
					ProjectPojo parentPojo = new ProjectPojo();

					BeanUtils.copyProperties(project, parentPojo);
					parentPojo.setUserIds(ids);

					List<TaskPojo> tasksFound = getTasksByProjectId(project);
					List<SubProjects> listOfSub = new ArrayList<>();
					for (TaskPojo task : tasksFound) {
						SubProjects pro = new SubProjects();
						BeanUtils.copyProperties(task, pro);
						pro.setCheckType("Parent-Task");
						listOfSub.add(pro);
					}

					parentPojo.setSubProjects(listOfSub);

					mapParent.put(project.getId(), parentPojo);

				}
			}

			List<SubProjects> listOFChild = new ArrayList<>();

			List<TaskPojo> listOfTask = new ArrayList<>();

			for (Project project : projects) {
				if (project.getProjectId() != 0) {

					SubProjects childProject = new SubProjects();
					childProject.setProjectId(project.getProjectId());
					List<TaskPojo> tasksFound = getTasksByProjectId(project);
					System.out.println(tasksFound);
					listOfTask.addAll(tasksFound);
					childProject.setTask(tasksFound);
					BeanUtils.copyProperties(project, childProject);
					childProject.setCheckType("Child Project");

					ProjectPojo parentPojo = mapParent.get(project.getProjectId());

					if (parentPojo != null) {
						List<SubProjects> childProjects = parentPojo.getSubProjects();
						if (childProjects == null) {
							childProjects = new ArrayList<>();

							parentPojo.setSubProjects(childProjects);

						}

						childProjects.add(childProject);

						listOFChild.add(childProject);

					}
				}
			}

			for (Map.Entry<Integer, ProjectPojo> pp : mapParent.entrySet()) {
				ProjectPojo value = pp.getValue();
				List<SubProjects> listOfChild = new ArrayList<>();

				List<SubProjects> subProjects = value.getSubProjects();
				listOfChild.addAll(subProjects);

				for (SubProjects cp : listOFChild) {

					if (value.getId() == cp.getProjectId()) {

						boolean isChildProjectExists = listOfChild.stream().anyMatch(obj -> obj instanceof SubProjects
								&& ((SubProjects) obj).getProjectId() == cp.getProjectId());

						if (!isChildProjectExists) {
							SubProjects childP = new SubProjects();
							BeanUtils.copyProperties(cp, childP);
							listOfChild.add(childP);
						}

					}
				}

				value.setSubProjects(listOfChild);
				listProject.add(value);
				responseHandler = getResponse("Project with childs and tasks fetched successfully", 0, 0, listProject);

			}

		} catch (Exception e) {
			// TODO: handle exception
			responseHandler = getResponse("Something went wrong while fecthing projetcs", 1, 1, new ProjectPojo());
		}

		return responseHandler;
	}

	public List<TaskPojo> getTasksByProjectId(Project project) {
		List<TaskPojo> taskPojos = new ArrayList<>();

		try {
			List<Task> findByProject = repository.findByProject(project);

			if (!findByProject.isEmpty()) {
				for (Task task : findByProject) {
					TaskPojo taskPojo = new TaskPojo();
					BeanUtils.copyProperties(task, taskPojo);
					taskPojo.setProjectId(project.getId());
					taskPojo.setCheckType("Parent-Task");
					taskPojos.add(taskPojo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return taskPojos;
	}

	public ResponseHandler getAllParentProject() {

		List<Project> projects = projectRepository.findByProjectId(0);
		String message = "Parent-project";
		ResponseHandler handler = getProjectDetails(projects, message);
		return handler;
	}

	public ResponseHandler getProjectDetails(List<Project> projects, String message) {

		ResponseHandler responseHandler = new ResponseHandler();
		List<ProjectPojo> listProject = new ArrayList<>();
		Map<Integer, ProjectPojo> mapParent = new HashMap<>();
		try {
			for (Project project : projects) {

				ProjectPojo parentPojo = new ProjectPojo();

				BeanUtils.copyProperties(project, parentPojo);

				mapParent.put(project.getId(), parentPojo);
				listProject.add(parentPojo);

			}

			responseHandler = getResponse("Projects fetched succesfully", 0, 0, listProject);

		} catch (Exception e) {
			// TODO: handle exception
			message = "Something went wrong";
			responseHandler = getResponse(message, 1, 1, new ProjectPojo());
		}

		return responseHandler;
	}

	public ResponseHandler getAllChildProjects() {
		ResponseHandler responseHandler = new ResponseHandler();
		List<Project> projects = projectRepository.findByProjectIdNot(0);
		String message = "Child Project";
		responseHandler = getProjectDetails(projects, message);
		return responseHandler;
	}

	public ResponseHandler createTasksBasedOnProjectId(TaskPojo pojo, int projectId) {
		Project pId = projectRepository.findById(projectId);
		ResponseHandler handler = new ResponseHandler();
		try {
			Task tasks = new Task();
			BeanUtils.copyProperties(pojo, tasks);
			tasks.setProject(pId);

			List<User> userList = new ArrayList<>();
			List<String> userIds = pojo.getUserIds();
			for (String userId : userIds) {
				Optional<User> user = userRepo.findById(userId);
				userList.add(user.get());
			}
			tasks.setUser(userList);
			Task save = repository.save(tasks);
			BeanUtils.copyProperties(save, pojo);
			pojo.setCheckType("Task");
			handler = getResponse("Task created successfully", 0, 0, pojo);

		} catch (Exception e) {
			System.out.println(e);

			handler = getResponse("Something went wrong", 1, 1, new TaskPojo());
		}
		return handler;

	}

	public ResponseHandler getAll() {
		List<Project> findAll = projectRepository.findAll();
		ResponseHandler handler = getProjectDetails(findAll, "");
		return handler;
	}

	public ResponseHandler getParentTasks() {
		List<Project> projects = projectRepository.findByProjectId(0);
		List<TaskPojo> parentTasks = new ArrayList<>();

		try {
			for (Project project : projects) {
				List<TaskPojo> tasks = getTasksByProjectId(project);

				for (TaskPojo task : tasks) {
					if ("Parent-Task".equals(task.getCheckType())) {
						parentTasks.add(task);
					}
				}
			}

			ResponseHandler responseHandler = getResponse("Parent tasks fetched successfully", 0, 0, parentTasks);
			return responseHandler;
		} catch (Exception e) {
			ResponseHandler responseHandler = getResponse("Something went wrong", 1, 1, new ArrayList<>());
			return responseHandler;
		}
	}

	public ResponseHandler getParentTasksByProjectId(int projectId) {
		Project project = projectRepository.findById(projectId);

		if (project == null || project.getProjectId() != 0) {
			return getResponse("Invalid project or not a parent project", 1, 1, new ArrayList<>());
		}

		List<TaskPojo> parentTasks = new ArrayList<>();

		try {
			List<TaskPojo> tasks = getTasksByProjectId(project);

			for (TaskPojo task : tasks) {
				if ("Parent-Task".equals(task.getCheckType())) {
					task.setCheckType("Task for " + project.getName());

					parentTasks.add(task);
				}
			}

			ResponseHandler responseHandler = getResponse("Parent tasks fetched successfully", 0, 0, parentTasks);
			return responseHandler;
		} catch (Exception e) {
			ResponseHandler responseHandler = getResponse("Something went wrong", 1, 1, new ArrayList<>());
			return responseHandler;
		}
	}

	public ResponseHandler getChildTasksByProjectId(int projectId) {
		Project project = projectRepository.findById(projectId);

		if (project == null || project.getProjectId() == 0) {
			return getResponse("Invalid project or not a Child project", 1, 1, new ArrayList<>());
		}

		List<TaskPojo> parentTasks = new ArrayList<>();

		try {
			List<TaskPojo> tasks = getTasksByProjectId(project);

			for (TaskPojo task : tasks) {
				if ("Parent-Task".equals(task.getCheckType())) {
					task.setCheckType("Task for " + project.getName());
					parentTasks.add(task);
				}
			}

			ResponseHandler responseHandler = getResponse("Child tasks fetched successfully", 0, 0, parentTasks);
			return responseHandler;
		} catch (Exception e) {
			ResponseHandler responseHandler = getResponse("Something went wrong", 1, 1, new ArrayList<>());
			return responseHandler;
		}
	}

	public ResponseHandler updateProject(ProjectPojo projectPojo) {
		ResponseHandler handler = new ResponseHandler();
		try {

			Project existingProject = projectRepository.findByName(projectPojo.getName());

			if (existingProject != null) {
				existingProject.setName(projectPojo.getName());
				existingProject.setType(projectPojo.getType());
				existingProject.setDescription(projectPojo.getDescription());
				existingProject.setStatus(projectPojo.getStatus());
				existingProject.setStartDate(projectPojo.getStartDate());
				existingProject.setEndDate(projectPojo.getEndDate());
				existingProject.setHours(projectPojo.getHours());
				existingProject.setAssignedTo(projectPojo.getAssignedTo());

				existingProject = projectRepository.save(existingProject);

				handler = getResponse("Project updated successfully", 0, 0, existingProject);
			} else {
				handler = getResponse("Project not found", 1, 1, null);
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return handler;
	}

	public ResponseHandler update(TaskPojo pojo) {
		ResponseHandler handler = new ResponseHandler();
		try {

			Task findByName = repository.findByName(pojo.getName());
			if (findByName != null) {
				findByName.setType(pojo.getType());
				findByName.setDescription(pojo.getDescription());
				findByName.setHours(pojo.getHours());
				findByName.setStartDate(pojo.getStartDate());
				findByName.setEndDate(pojo.getEndDate());
				findByName.setStatus(pojo.getStatus());
				findByName.setAssignedTo(pojo.getAssignedTo());

				Task save = repository.save(findByName);
				handler = getResponse("Updated", 0, 0, save);
			} else {
				handler = getResponse("Error", 1, 1, null);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return handler;
	}

	public ResponseHandler getParentTasksByName(String name) {
		Task project = repository.findByName(name);

		if (project == null) {
			return getResponse("Invalid project or not a parent project", 1, 1, new ArrayList<>());
		}

		try {
			TaskPojo pojo = new TaskPojo();
			BeanUtils.copyProperties(project, pojo);

			ResponseHandler responseHandler = getResponse("Parent tasks fetched successfully", 0, 0, pojo);
			return responseHandler;
		} catch (Exception e) {
			ResponseHandler responseHandler = getResponse("Something went wrong", 1, 1, null);
			return responseHandler;
		}
	}

	public ResponseHandler getTaskBasedOnUserId(String id) {
		ResponseHandler handler=new ResponseHandler();
		Optional<User> findById = userRepo.findById(id);
		User user = findById.get();
		List<Task> findByProject = repository.findByProject(null);
		List<Task> findAll = repository.findAllByUser(user);
		List<TaskPojo> taskPojos=new ArrayList<TaskPojo>();
		
		try {
			if(findById.isPresent()) {
				for(Task task:findAll) {
					TaskPojo pojo=new TaskPojo();
					BeanUtils.copyProperties(task, pojo);
					
					Project project = task.getProject();
					
					pojo.setProjectId(project.getId());
					pojo.setCheckType("Task");
			
					taskPojos.add(pojo);
				}
				handler=getResponse("Task for "+user.getLastName()+" fetched successfully", 0, 0, taskPojos);
			}
			else {
				handler=getResponse("No tasks found", 1, 1, null);
			}
		} catch (Exception e) {
			handler=getResponse("Something went wrong", 1, 1, null);
		}
		return handler;
	}
}