package com.incresol.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.demo.model.Project;
import com.project.demo.model.Task;
@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

	List<Task> findByProject(Project project);
	Task findById(int id);
	Task findByName(String name);

}
