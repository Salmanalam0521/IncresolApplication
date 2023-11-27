package com.incresol.app.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.incresol.app.dao.FileDao;
import com.incresol.app.entities.File_Entity;


@Service
public class FileService {
	@Autowired
	private FileDao dao;

	public ResponseEntity<Object> uploadServ(MultipartFile file) throws IOException {

		String contentType = file.getContentType();
		
		if (contentType != null && (contentType.equals("application/vnd.ms-excel")
				|| contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))) {
			if(dao.isFileExist(file.getOriginalFilename())) {
				return ResponseEntity.status(HttpStatus.FOUND).body("The file name already exist..");
			}
			File_Entity uploadFile = new File_Entity();
			uploadFile.setFileName(file.getOriginalFilename());
			uploadFile.setFileType(file.getContentType());
			uploadFile.setData(file.getBytes());
			uploadFile.setDate(new Date());

			File_Entity entity = dao.saveFile(uploadFile);
			if (entity != null)
				return ResponseEntity.status(HttpStatus.OK).body(entity);
			else
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(entity);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Only xl and xlsx files are allowed...");
		}

	}

	public ResponseEntity<Resource> downloadFileSer(String fileName) throws FileNotFoundException {
		File_Entity file = dao.downloadFile(fileName);
		if (file == null) {
			throw new FileNotFoundException("file not exist...");
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; file_name=file.getFileName()")
				.body(new ByteArrayResource(file.getData()));

//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); // Set content type to binary
//            headers.setContentDispositionFormData("attachment", file.getFileName()); // Set content disposition
//
//            return ResponseEntity.ok()
//                .headers(headers)
//                .body(new ByteArrayResource(file.getData()));
	}

	public List<com.incresol.app.entities.File_Entity> getAllFileSer() {
		return dao.getAllFiles();
	}
}
