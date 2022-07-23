package com.poc.onpassive.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.poc.onpassive.entity.Employee;
import com.poc.onpassive.services.EmployeeService;


@RestController
public class EmployeeController  {
	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/employee")
	public Employee createEmployee(@RequestBody Employee employee) {
		 Employee save = employeeService.save(employee);
		 System.err.println("save::::"+save);
		 return save;
	}
	
	@GetMapping("/viewemployeelist")
	public List<Employee> ViewEmployee()
	{
		System.out.println("hellow world");
		List<Employee> view =employeeService.viewEmployee();
		return view;
	}
	
//	@PostMapping("/viewbyid")
//	public  void view(@RequestBody Employee employee,@PathVariable("id") Long id)
//	{
//		System.err.println("employee controller by id"+id);
//		employeeService.
//	}

}
