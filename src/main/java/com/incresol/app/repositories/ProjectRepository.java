package com.incresol.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.demo.model.Project;
@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
	
	
	Project findByName(String projectName);
	
	List<Project> findByProjectId(int id);
	Project findById(int id);

	List<Project> findByProjectIdNot(int i);
	
	

}
