package com.incresol.app.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.incresol.app.entities.ResponseHandler;
import com.incresol.app.models.HttpStatusResponse;

@Service
public class FileService {
	@Autowired
	private FileDao dao;

	// get response handler
	public ResponseHandler getResponse(String message, int status, int errorCode, Object o) {
		ResponseHandler handler = new ResponseHandler();
		Map<String, Object> response = handler.getResponse();
		response.put("data", o);
		handler.setErrorCode(errorCode);
		handler.setMessage(message);
		handler.setStatusCode(status);
		return handler;
	}

	public ResponseHandler uploadServ(MultipartFile file) {
		try {
			String contentType = file.getContentType();

			if (contentType != null && (contentType.equals("application/vnd.ms-excel")
					|| contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))) {
				if (dao.isFileExist(file.getOriginalFilename())) {
					return getResponse("The file name already exist!", 1, 302, null);
				}
				File_Entity uploadFile = new File_Entity();
				uploadFile.setFileName(file.getOriginalFilename());
				uploadFile.setFileType(file.getContentType());
				uploadFile.setData(file.getBytes());
				uploadFile.setDate(new Date());

				File_Entity entity = dao.saveFile(uploadFile);
				if (entity != null)
					return getResponse(file.getOriginalFilename()+" uploaded successfully", 0, 20, entity);
				else
					return getResponse(file.getOriginalFilename()+" failed to upload!", 1, 51, null);
			} else {
				return getResponse("Upload only xl or xlsx files!", 1, 52, null);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return getResponse("Error during file upload", 1, 50, null);
		}

	}

	public ResponseEntity<Resource> downloadFileSer(String fileName) {
		try {
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
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return ResponseEntity.status(404).body(null);
		}
	}

	public ResponseHandler getAllFileSer() {
		try {
			List<File_Entity> allFiles = dao.getAllFiles();

			if (allFiles != null) {
				return getResponse("All Files fetched successfully..", 0, 20, allFiles);
			}
			return getResponse("Somthing went wrong!", 0, 53, null);
		} catch (Exception e) {
			e.printStackTrace();
			return getResponse("Error while retriving files", 1, 50, null);
		}
	}
}
