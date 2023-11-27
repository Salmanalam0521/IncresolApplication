package com.incresol.app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.incresol.app.entities.File_Entity;
import com.incresol.app.repositories.FileRepo;




@Repository
public class FileDao {

	@Autowired
	private FileRepo repo;

	public File_Entity saveFile(File_Entity uploadFile) {
		return repo.save(uploadFile);
	}

	public File_Entity downloadFile(String fileName) {
		Optional<File_Entity> file=repo.findByFileName(fileName);
		
		return file.get();
	}
	public List<File_Entity> getAllFiles(){
		return repo.findAll();
	}
	public boolean isFileExist(String fileName) {
		Optional<File_Entity> file=repo.findByFileName(fileName);
		//return file!=Optional.empty();
		return !file.isEmpty();    
	}
    
}
