package com.incresol.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incresol.app.entities.File_Entity;

@Repository
public interface FileRepo extends JpaRepository<File_Entity, Long>{
	Optional<File_Entity>	findByFileName(String fileName); 
}
