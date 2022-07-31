package com.poc.onpassive.services;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.poc.onpassive.entity.Employee;
import com.poc.onpassive.exception.ResourceNotFoundException;
import com.poc.onpassive.repository.EmployeeRepository;

@Transactional
@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public Employee upload(Employee employee, MultipartFile file) throws IOException {
		String fileName = StringUtils(file.getOriginalFilename());
		// Employee FileDB = new Employee(file.getBytes());
		return employeeRepository.save1(employee, file);

	}

	private String StringUtils(String originalFilename) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Employee> viewEmployee() {
		List<Employee> view = employeeRepository.findAll();
		return view;
	}

	@Override
	public Optional<Employee> viewEmployeebyid(Long id) {
		System.out.println("EmployeeServiceImpl.viewEmployeebyid()" + id);

		Optional<Employee> view = employeeRepository.findById(id);
		return view;

	}

	@Override
	public Employee viewByMail(String mail) {
		System.out.println("viw by  mail in service");

		Employee findByMail = employeeRepository.findByMail(mail);
		return findByMail;

	}

	@Override
	public Employee updateEmployee(Employee employee, Long id) {

		Employee updateEmployee = employeeRepository.findById(id).orElse(null);

		if (updateEmployee.getId() == id) {
			updateEmployee.setFirstName(employee.getFirstName());
			updateEmployee.setLastName(employee.getLastName());
			updateEmployee.setEmailId(employee.getEmailId());
			updateEmployee.setAddress(employee.getAddress());
			updateEmployee.setMobileno(employee.getMobileno());

		}
		Employee emp = employeeRepository.save(updateEmployee);
		return emp;

	}

	@Override
	public void deleteServ(Long id) {
		System.out.println("EmployeeServiceImpl.deleteServ()" + id);
		employeeRepository.deleteById(id);
	}

	@Override
	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Override
	public Employee saveEmployeeDetails(Employee employee) {
		Employee save = employeeRepository.save(employee);
		return save;
	}

	@Override
	public List<Employee> viewEmployeedetails() {
		System.out.println("view all the employees in services");
		List<Employee> view = employeeRepository.findAll();
		return view;

	}

	@Override
	public Employee updateEmployeedetails(long id) throws ResourceNotFoundException {

		System.out.println(" employee find by id in servicelayer" + id);

		return employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id ::" + id));
	}

	/*
	 * Optional<Employee> optional = employeeRepository.findById(id); Employee
	 * employee = null; if (optional.isPresent()) { employee = optional.get(); }
	 * else { throw new RuntimeException(" Employee not found for id :: " + id); }
	 * return employee;
	 */

	@Override
	public Employee updateData(Employee employee, long id) {
		// employeeRepository.save(employee,id);

		Employee update = employeeRepository.findById(id).get();
		System.out.println(update.toString());
		if (update.getId() == id) {
			update.setFirstName(employee.getFirstName());
			update.setLastName(employee.getLastName());
			update.setEmailId(employee.getEmailId());
			update.setMobileno(employee.getMobileno());
			Employee updatedata = employeeRepository.save(update);
			return updatedata;

		} else {
			throw new RuntimeException(" Employee not found for id :: " + id);

		}

	}

	@Override
	/*
	 * public Employee uploadImage(MultipartFile file, Employee emp) throws
	 * IOException {
	 * 
	 * emp.setFileName(emp.getFirstName()); emp.setLastName(emp.getLastName());
	 * emp.setEmailId(emp.getEmailId()); emp.setAddress(emp.getAddress());
	 * emp.setMobileno(emp.getMobileno());
	 * emp.setFileName(file.getOriginalFilename()); emp.setImage(file.getBytes());
	 * return employeeRepository.save(emp);
	 * 
	 * }
	 */

	public Employee uploadImage(MultipartFile file, Employee emp) throws IOException {

		/*
		 * emp.setFileName(emp.getFirstName()); emp.setLastName(emp.getLastName());
		 * emp.setEmailId(emp.getEmailId()); emp.setAddress(emp.getAddress());
		 * emp.setMobileno(emp.getMobileno());
		 */
		emp.setFileName(file.getOriginalFilename());
		emp.setImage(file.getBytes());
		String originalFilename = file.getOriginalFilename();
		String[] split = originalFilename.split("\\.");
		emp.setType(split[split.length - 1]);

		return employeeRepository.save(emp);

	}

//	@Override
//	public Employee uploadImage(MultipartFile file,long id) throws IOException {
//		Employee emp = new Employee();
//		emp.setId(id);
//		emp.setFileName(file.getOriginalFilename());
//		emp.setImage(file.getBytes());
//		return employeeRepository.save(emp);
//		
//		
//		
//	}

}
