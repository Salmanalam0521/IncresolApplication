//package com.incresol.app.controllers;
//
//import java.io.IOException;
//
////import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.incresol.app.services.Emp_ExpoServ;
//
//import jakarta.servlet.http.HttpServletResponse;
//
//@RestController
//@CrossOrigin(origins = "http://localhost:4200")
//public class Excel_Export_Controller {
//@Autowired
//private Emp_ExpoServ serv;
//
//@GetMapping("/getexcel")
//public void generateExcel(HttpServletResponse response) throws IOException {
//
//	response.setContentType("application/octet-stream");
//
//	String headerKey = "Content-Disposition";
//	String headerValue = "attachment;filename=Emp_List.xls";
//
//	response.setHeader(headerKey, headerValue);
//
//	serv.generateExcelServ(response);
//}
//}
