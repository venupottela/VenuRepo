package com.poc.onpassive.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poc.onpassive.entity.Employee;
import com.poc.onpassive.repository.EmployeeRepository;

@Transactional
@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;


	public Employee save(Employee employee) {
		return employeeRepository.save(employee);

	}

	@Override
	public List<Employee> viewEmployee() {
		List<Employee> view=employeeRepository.findAll();
		return view;
	}

//	@Override
//	public void viewEmployeebyid(long id) {
//		System.out.println("employee service by id"+id);
//		
//	}
//
	@Override
	public void viewEmployeebyid(Employee employee, Long id) {
		
	}

}
