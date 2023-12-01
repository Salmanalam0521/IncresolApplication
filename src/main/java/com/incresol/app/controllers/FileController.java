package com.incresol.app.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.incresol.app.entities.File_Entity;
import com.incresol.app.models.HttpStatusResponse;
import com.incresol.app.services.FileService;



@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/files")
public class FileController {

	@Autowired
	private FileService serv;

	@PostMapping("/upload")
	public ResponseEntity<Object> uploadFile(@RequestParam("f") MultipartFile file) throws IOException {
		
		return new ResponseEntity<>(serv.uploadServ(file),HttpStatus.OK);
		//return serv.uploadServ(file);
	}
	
	@GetMapping("/download/{fileName}")
	public ResponseEntity<?> downloadFile(@PathVariable String fileName) throws FileNotFoundException{
		return serv.downloadFileSer(fileName);	
	}
	
	@GetMapping("/getFiles")
	public ResponseEntity<Object> getFiles(){
    	return new ResponseEntity<>(serv.getAllFileSer(),HttpStatus.OK);
	}
}
