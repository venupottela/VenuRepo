package com.poc.onpassive.services;

import java.util.List;

import com.poc.onpassive.entity.Employee;

public interface EmployeeService {

	Employee save(Employee employee);

	public List<Employee> viewEmployee();

	public void viewEmployeebyid(Employee employee,Long id);

	//void viewEmployeebyid(long id);

}
