package com.incresol.app.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.incresol.app.entities.BusinessPlace;
import com.incresol.app.entities.OrgRoles;
import com.incresol.app.entities.OrgUser;
import com.incresol.app.entities.Organization;
import com.incresol.app.entities.Project;
import com.incresol.app.entities.ResponseHandler;
import com.incresol.app.entities.Task;
import com.incresol.app.entities.User;
import com.incresol.app.models.BusinessPojo;
import com.incresol.app.models.GenerateNewPassword;
import com.incresol.app.models.HttpStatusResponse;
import com.incresol.app.models.OrgDetails;
import com.incresol.app.models.OrgDetailsRequest;
import com.incresol.app.models.OrgRolesPojo;
import com.incresol.app.models.OrganizationPojp;
import com.incresol.app.models.ProjectPojo;
import com.incresol.app.models.RolePojo;
import com.incresol.app.models.SubProjects;
import com.incresol.app.models.TaskPojo;
import com.incresol.app.models.UserPojo;
import com.incresol.app.models.UserResponse;
import com.incresol.app.repositories.BusinessPlaceRepository;
import com.incresol.app.repositories.OrgRolesRepository;
import com.incresol.app.repositories.OrgUserRepository;
import com.incresol.app.repositories.OrganizationRepository;
import com.incresol.app.repositories.ProjectRepository;
import com.incresol.app.repositories.TaskRepository;
import com.incresol.app.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AdminService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private OrgRolesRepository orgRolesRepository;

	@Autowired
	private OrgUserRepository orgUserRepository;

	@Autowired
	private OrganizationRepository organizationRepository;

	@Autowired
	private BusinessPlaceRepository businessPlaceRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MailService mailService;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private TaskRepository repository;
	
//	@Autowired
//	private UserService userService;

	
	public UserResponse findUser() {
		User user = userRepo.findByEmail(this.getUserName());
		UserResponse userRes = new UserResponse();
		userRes.setFirstName(user.getFirstName());
		userRes.setLastName(user.getLastName());
		userRes.setEmail(user.getEmail());
		return userRes;
	}

	private String getUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String name = authentication.getName();
		System.out.println("Name is " + name);
		return name;
	}
	
	public Object createOrganization(OrganizationPojp organizationPojp) {

		Organization orgFound = organizationRepository.findByOrganizationName(organizationPojp.getOrganizationName());

		if (orgFound == null) {

			Organization organization = new Organization();
			BeanUtils.copyProperties(organizationPojp, organization);

			Organization save = organizationRepository.save(organization);

			BeanUtils.copyProperties(save, organizationPojp);

			return organizationPojp;
		}
		return "Org already existed";
	}

	@Transactional
	public BusinessPojo addBusinessPojo(BusinessPojo businessPojo, String orgId) throws Exception {
		BusinessPlace businessPlace = new BusinessPlace();
		BeanUtils.copyProperties(businessPojo, businessPlace);

		Optional<Organization> organization = organizationRepository.findById(orgId);

		if (organization.isPresent()) {
			businessPlace.setOrganization(organization.get());
		} else {
			throw new RuntimeException("Org not found");
		}

		BusinessPlace save = businessPlaceRepository.save(businessPlace);

		BeanUtils.copyProperties(save, businessPojo);

		return businessPojo;

	}

	public List<RolePojo> roles() {
		List<RolePojo> rolespojo = new ArrayList<>();
		List<OrgRoles> dbRoles = orgRolesRepository.findAll();
		for (OrgRoles role : dbRoles) {
			RolePojo rolepojo = new RolePojo();
			BeanUtils.copyProperties(role, rolepojo);
			rolespojo.add(rolepojo);
			;
		}
		return rolespojo;
	}

	// \/
	@Transactional(rollbackOn = Exception.class)
	public String saveAllDetailsOfUser(OrgDetailsRequest orgUserDetails) {

		User existingUser = userRepo.findByEmail(orgUserDetails.getEmail());
		if (existingUser == null) {
			User user = new User();
			BeanUtils.copyProperties(orgUserDetails, user);

			// Generate Password

			Random random = new Random();

			StringBuilder password = new StringBuilder();
			char randomUppercaseLetter = (char) (random.nextInt(26) + 'A');
			password.append(randomUppercaseLetter);
			for (int i = 0; i < 4; i++) {
				char randomLowercaseLetter = (char) (random.nextInt(26) + 'a');
				password.append(randomLowercaseLetter);
			}
			password.append('@');
			for (int i = 0; i < 3; i++) {
				int randomDigit = random.nextInt(10);
				password.append(randomDigit);
			}

			user.setPassword(passwordEncoder.encode(password.toString()));
			user.setAccountExpiredDate(LocalDateTime.now().plus(45, ChronoUnit.DAYS));
			user.setPasswordExpiredDate(LocalDateTime.now().plus(45, ChronoUnit.DAYS));
			user.setAccountNonLocked(true);
			user.setEnabled(true);
			user.setFailedLoginAttempts(0);
			User savedUser = userRepo.save(user);

			// Send Mail

			String subject = "Account Created ";
			String message = "Hello " + savedUser.getFirstName() + " " + savedUser.getLastName()
					+ "\nPlease change you password.\nUsername : " + savedUser.getEmail() + "\n" + "password "
					+ password;
			mailService.mailUtils(savedUser.getEmail(), subject, message);

//===================================================================================
			List<OrgDetails> orgDetails = orgUserDetails.getOrgDetails();
			boolean flag = false;
			for (OrgDetails orgDetail : orgDetails) {
				Optional<Organization> organization = organizationRepository.findById(orgDetail.getOrgId());
				if (organization.isPresent()) {
					List<String> bpIds = orgDetail.getBpIds();
					for (String bpId : bpIds) {
						Optional<BusinessPlace> businessplace = businessPlaceRepository.findById(bpId);
						if (businessplace.isPresent()) {
							OrgRolesPojo roles = orgUserDetails.getRoles();
							int mainRole = roles.getRole();
							String subRoles = roles.getSubRoles();
							OrgUser orgUser = new OrgUser();
							orgUser.setUserBusinessPlace(businessplace.get());
							orgUser.setOrg(organization.get());
							orgUser.setUser(savedUser);
							orgUser.setMainRole(mainRole);
							orgUser.setSubRoles(subRoles);
							orgUserRepository.save(orgUser);
							flag = true;

						} else {
							throw new RuntimeException("Businnes place dosen't exit by id " + bpId);
						}
					}
				} else {
					throw new RuntimeException("Organization not found");
				}

			}
			return "Success";

		} else {

			List<OrgDetails> orgDetails = orgUserDetails.getOrgDetails();
			for (OrgDetails orgDetail : orgDetails) {
				Optional<Organization> organization = organizationRepository.findById(orgDetail.getOrgId());
				if (organization.isPresent()) {
					List<String> bpIds = orgDetail.getBpIds();
					for (String bpId : bpIds) {
						Optional<BusinessPlace> businessplace = businessPlaceRepository.findById(bpId);
						if (businessplace.isPresent()) {

							List<User> usersFound = orgUserRepository
									.findDistinctUserByOrgAndUserBusinessPlace(organization.get(), businessplace.get());
							if (usersFound.size() != 0) {
								continue;
							}

							OrgRolesPojo roles = orgUserDetails.getRoles();
							int mainRole = roles.getRole();
							String subRoles = roles.getSubRoles();
							OrgUser orgUser = new OrgUser();
							orgUser.setUserBusinessPlace(businessplace.get());
							orgUser.setOrg(organization.get());
							orgUser.setUser(existingUser);
							orgUser.setMainRole(mainRole);
							orgUser.setSubRoles(subRoles);
							orgUserRepository.save(orgUser);
							// flag=true;

						}
					}
				} else {
					throw new RuntimeException("Organization not found");
				}

			}
			return "Success";
		}

	}

	// Sending user and organizations list , After login successfull.
	// \/
	public UserResponse userDetails(String id) {
		Optional<User> user = userRepo.findById(id);

		UserResponse response = new UserResponse();
		BeanUtils.copyProperties(user.get(), response);
		int mainrole = orgUserRepository.findDistinctMainRoleByUser(user.get());
		String subRoles = orgUserRepository.findDistinctSubRolesByUser(user.get());
		OrgRolesPojo orgRolesPojo = new OrgRolesPojo();
		orgRolesPojo.setRole(mainrole);
		orgRolesPojo.setSubRoles(subRoles);
		response.setRoles(orgRolesPojo);

		List<OrganizationPojp> list = new ArrayList<>();
		if (mainrole == 1) {
			List<Organization> adminOrgList = orgUserRepository.findDistinctOrgByMainRoleAdmin();
			if (adminOrgList.size() != 0) {
				for (Organization organization : adminOrgList) {
					OrganizationPojp organizationPojp = new OrganizationPojp();
					BeanUtils.copyProperties(organization, organizationPojp);
					list.add(organizationPojp);
				}
			}
		} else {
			List<Organization> userOrgList = orgUserRepository.findDistinctOrgByUser(user.get());
			if (userOrgList.size() != 0) {
				for (Organization organization : userOrgList) {
					OrganizationPojp organizationPojp = new OrganizationPojp();
					BeanUtils.copyProperties(organization, organizationPojp);
					list.add(organizationPojp);
				}
			}
		}

		response.setOrganizations(list);
		return response;
	}

	// \/

	// Sending business places based on User and His organization
	public List<BusinessPojo> getBusinessPlaces(String orgId) {
		User user = userRepo.findByEmail(this.getUserName());
		Optional<Organization> organizationFound = organizationRepository.findById(orgId);
		Organization organization = organizationFound.get();
		int mainrole = orgUserRepository.findDistinctMainRoleByUser(user);
		List<BusinessPojo> businessPojoList = new ArrayList<BusinessPojo>();

		if (mainrole == 1) {
			List<BusinessPlace> adminBusinessPlaces = orgUserRepository
					.findDistinctUserBusinessPlaceByOrg(organization);

			if (adminBusinessPlaces.size() != 0) {
				for (BusinessPlace businessPlace : adminBusinessPlaces) {
					BusinessPojo businessPojo = new BusinessPojo();
					BeanUtils.copyProperties(businessPlace, businessPojo);
					businessPojoList.add(businessPojo);
				}
			} else {
				return null;
			}
		} else {
			List<BusinessPlace> userBusinessPlaces = orgUserRepository
					.findDistinctUserBusinessPlaceByUserAndOrg(user, organization);
			if (userBusinessPlaces.size() != 0) {
				for (BusinessPlace businessPlace : userBusinessPlaces) {
					BusinessPojo businessPojo = new BusinessPojo();
					BeanUtils.copyProperties(businessPlace, businessPojo);
					businessPojoList.add(businessPojo);
				}
			} else {
				return null;
			}
		}
		return businessPojoList;

	}

	// Sending list of users based on admain id and his organization For admin only

	public List<UserPojo> getAllUsersInOrg(String orgId) {

		Optional<Organization> organization = organizationRepository.findById(orgId);
		List<UserPojo> list = new ArrayList<>();

		List<User> users = orgUserRepository.findDistinctUserByOrgAndMainRoleNotAdmin(organization.get());

		for (User user : users) {
			UserPojo pojo = new UserPojo();
			BeanUtils.copyProperties(user, pojo);
			OrgRolesPojo orgRolesPojo = new OrgRolesPojo();
			int mainRole = orgUserRepository.findDistinctMainRoleByUser(user);
			String subRoles = orgUserRepository.findDistinctSubRolesByUser(user);
			orgRolesPojo.setRole(mainRole);
			orgRolesPojo.setSubRoles(subRoles);
			pojo.setRoles(orgRolesPojo);
			list.add(pojo);
		}
		return list;
	}

	public HttpStatusResponse changePassword(GenerateNewPassword generatePassword) {
		String oldPass = generatePassword.getOldPassword();
		String newPass = generatePassword.getNewPassword();

		User user = userRepo.findByEmail("salmanrobin125@gmail.com");

		if (!passwordEncoder.matches(oldPass, user.getPassword())) {
			return this.getHttpStatusResponse(1, null, 20, "Invalid old password. Please enter a valid password.");
		}

		if (passwordEncoder.matches(newPass, user.getPassword())) {
			return this.getHttpStatusResponse(1, null, 19, "New password should be different from the old password.");
		}

		user.setPassword(passwordEncoder.encode(newPass));
		userRepo.save(user);

		return this.getHttpStatusResponse(0, null, 0, "Password changed successfully.");
	}

	
	private HttpStatusResponse getHttpStatusResponse(int statusCode, Map<String, Object> data, int errorCode,
			String message) {
		HttpStatusResponse response = new HttpStatusResponse();
		response.setData(data);
		response.setStatusCode(statusCode);
		response.setErrorCode(errorCode);
		response.setMessage(message);
		return response;
	}
	
	// =============================================================================
	// Admin Projects and Tasks

	public ResponseHandler createProjectpojo(ProjectPojo pojo) {

		Project project = projectRepository.findByName(pojo.getName());
		ResponseHandler responseHandler = new ResponseHandler();
		Project pro = new Project();
		if (project == null) {
			try {

				BeanUtils.copyProperties(pojo, pro);
				List<SubProjects> subProjects = null;
				if (pojo.getSubProjects() != null) {
					subProjects = pojo.getSubProjects();
					for (SubProjects subPro : subProjects) {

						List<TaskPojo> taskPojos = subPro.getTask();
						List<Task> taskList = new ArrayList<>();
						for (TaskPojo taskPojo : taskPojos) {
							Task task = new Task();
							BeanUtils.copyProperties(taskPojo, task);
							task.setProject(pro);
							taskList.add(task);

						}
						pro.setTasks(taskList);
					}
				} else {
					pro.setTasks(null);
				}
				project = projectRepository.save(pro);
				responseHandler = getResponse("Project created successfully", 0, 0, project);
				BeanUtils.copyProperties(project, pojo);

			} catch (Exception e) {
				responseHandler = getResponse("Something went wrong", 1, 1, project);
			}

		} else {

			responseHandler = getResponse("Project Already exist", 1, 1, project);

		}

		return responseHandler;
	}

	public ResponseHandler getResponse(String message, int status, int errorCode, Object o) {
		ResponseHandler handler = new ResponseHandler();
		Map<String, Object> response = handler.getResponse();
		response.put("data", o);
		handler.setErrorCode(errorCode);
		handler.setMessage(message);
		handler.setStatusCode(status);

		return handler;
	}

	public ResponseHandler createTasksBasedOnProjectId(TaskPojo pojo, int projectId) {
		Project pId = projectRepository.findById(projectId);
		ResponseHandler handler = new ResponseHandler();
		try {
			Task tasks = new Task();
			BeanUtils.copyProperties(pojo, tasks);
			tasks.setProject(pId);
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

	public ResponseHandler getAllParentChildTasks() {

		ResponseHandler responseHandler = new ResponseHandler();
		List<Project> projects = projectRepository.findAll();

		List<ProjectPojo> listProject = new ArrayList<>();
		Map<Integer, ProjectPojo> mapParent = new HashMap<>();
		try {

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

//	private String generateUniqueUserId() {
//		String newUserId = UUID.randomUUID().toString();
//		while (userRepo.existsByUserId(newUserId)) {
//			newUserId = UUID.randomUUID().toString();
//		}
//		return newUserId;
//	}

}
