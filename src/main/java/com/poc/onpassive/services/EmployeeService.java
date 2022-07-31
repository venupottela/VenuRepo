package com.poc.onpassive.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.poc.onpassive.entity.Employee;
import com.poc.onpassive.exception.ResourceNotFoundException;

public interface EmployeeService {

	Employee upload(Employee employee, MultipartFile file) throws IOException;

	public List<Employee> viewEmployee();

	public Optional<Employee> viewEmployeebyid(Long id);

	public Employee viewByMail(String mail);

	public Employee updateEmployee(Employee employee, Long id);

	public void deleteServ(Long id);

	Employee saveEmployee(Employee employee);

	public Employee saveEmployeeDetails(Employee employee);

	public List<Employee> viewEmployeedetails();

	public Employee updateEmployeedetails( long id) throws ResourceNotFoundException;

	public Employee updateData(Employee employee,long id);

	Employee uploadImage(MultipartFile file, Employee employee) throws IOException;


}
