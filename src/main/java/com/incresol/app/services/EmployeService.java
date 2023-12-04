package com.incresol.app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incresol.app.model.Employee;
import com.incresol.app.pojo.EmployeePojo;
import com.incresol.app.repositories.EmployeeRepository;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

@Service
public class EmployeService {
	@Autowired
	private EmployeeRepository employeeRepository;

	public String create(EmployeePojo employeePojo) {
		Employee e = new Employee();
		e.setAddress(employeePojo.getAddress());
//    	e.setPassword(new BCryptPasswordEncoder().encode(pojo.getPassword()));
		e.setBp(employeePojo.getBp());
		e.setDepartment(employeePojo.getDepartment());
		e.setEmail_id(employeePojo.getEmail_id());
		e.setEmp_id(employeePojo.getEmp_id());
		e.setEmp_type(employeePojo.getEmp_type());
		e.setMobile_no(employeePojo.getMobile_no());
		e.setOrg_name(employeePojo.getOrg_name());
		e.setRole(employeePojo.getRole());
		employeeRepository.save(e);
		return "Success";
	}

	public Optional<Employee> getEmployee(long Emp_id) {
		return employeeRepository.findById(Emp_id);
	}

	public List<EmployeePojo> getAll() {
		List<EmployeePojo> listOfpojo = new ArrayList<>();
		List<Employee> listOFEmp = employeeRepository.findAll();
		for (Employee emp : listOFEmp) {
			EmployeePojo ep = new EmployeePojo();
			BeanUtils.copyProperties(emp, ep);
			listOfpojo.add(ep);
		}

		return listOfpojo;
	}

	public Employee updateSer(Employee employee) {
		Optional<Employee> optional = employeeRepository.findById(employee.getEmp_id());

		if (optional != null) {
			employeeRepository.save(employee);
		}
		return optional.get();
	}

	public Employee deleteEmployeeById(Long Emp_id) {
		Optional<Employee> findById = employeeRepository.findById(Emp_id);

		if (findById.isPresent()) {
			Employee employee = findById.get();
			employee.setDeleteStatus(1);
			Employee save = employeeRepository.save(employee);
			return save;
		}

		return null;
	}

	public List<EmployeePojo> getAllNotDeletedEmp() {
		List<EmployeePojo> listOfpojo = new ArrayList<>();
		List<Employee> listOFEmp = employeeRepository.findByDeleteStatusNot(1);
		System.out.println(listOFEmp);
		for (Employee emp : listOFEmp) {
			EmployeePojo ep = new EmployeePojo();
			BeanUtils.copyProperties(emp, ep);
			listOfpojo.add(ep);
		}
		return listOfpojo;
	}

	public List<EmployeePojo> getAllDeletedEmp() {
		List<EmployeePojo> listOfpojo = new ArrayList<>();
		List<Employee> listOFEmp = employeeRepository.findByDeleteStatusNot(0);
		for (Employee emp : listOFEmp) {
			EmployeePojo ep = new EmployeePojo();
			BeanUtils.copyProperties(emp, ep);
			listOfpojo.add(ep);
		}
		return listOfpojo;
	}

}
