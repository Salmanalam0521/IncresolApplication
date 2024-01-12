package com.incresol.app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incresol.app.entities.Project;
import com.incresol.app.entities.User;
@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
	
	
	Project findByName(String projectName);
	
	List<Project> findByProjectId(int id);
	Project findById(int id);

	List<Project> findByProjectIdNot(int i);

	List<Project> findAllByUser(User user);

	Optional<Project> findByIdAndUser(int id, User user);
	
	

}
