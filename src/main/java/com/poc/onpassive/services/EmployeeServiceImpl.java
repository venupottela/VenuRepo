package com.poc.onpassive.services;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.poc.onpassive.entity.Employee;
import com.poc.onpassive.entity.Sendmail;
import com.poc.onpassive.exception.ResourceNotFoundException;
import com.poc.onpassive.repository.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private JavaMailSender javaMailSender;

	public Employee upload(Employee employee, MultipartFile file) throws IOException {
		log.info("employee service impl started");
		String fileName = StringUtils(file.getOriginalFilename());
		// Employee FileDB = new Employee(file.getBytes());
		log.info("employee service impl ended");
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
	public Employee viewEmployeebyid(Long id) {
		System.out.println("EmployeeServiceImpl.viewEmployeebyid()" + id);

		return employeeRepository.findById(id).get();

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
		log.info("upload image setvice started");
		emp.setFileName(file.getOriginalFilename());
		emp.setImage(file.getBytes());
		String originalFilename = file.getOriginalFilename();
		String[] split = originalFilename.split("\\.");
		emp.setType(split[split.length - 1]);
		log.info("upload image setvice ended");
		return employeeRepository.save(emp);
	}

	/*
	 * @Override public void mailsend(Mail mail) {
	 * 
	 * System.out.println("EmployeeServiceImpl.mailsend()"); StringBuilder sb = new
	 * StringBuilder();
	 * sb.append("Name: ").append(mail.getName()).append(System.lineSeparator());
	 * sb.append("\n Message: ").append(mail.getMessage());
	 * 
	 * SimpleMailMessage mail1 = new SimpleMailMessage();
	 * 
	 * mail1.setTo(mail.getEmail()); mail1.setFrom("gvenu350@gmail.com");
	 * mail1.setSubject(mail.getMessage()); mail1.setText(sb.toString());
	 * 
	 * employeeRepository.send(mail1);
	 * 
	 * }
	 */
	@Override
	public void mailsend(Sendmail mail) {
		try {
			System.out.println("Mail sending is started");
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setFrom("kameshkumarbysani95@gmail.com");
			simpleMailMessage.setTo(mail.getTo());
			simpleMailMessage.setSubject(mail.getTopic());
			simpleMailMessage.setText(mail.getTextBody());
			javaMailSender.send(simpleMailMessage);
			System.out.println("Mail sending is completed");
		} catch (Exception e) {
			System.out.println("Error occured: " + e.getMessage());
		}

	}

	// @Override
	// public Employee uploadImage(MultipartFile file,long id) throws IOException {
	// Employee emp = new Employee();
	// emp.setId(id);
	// emp.setFileName(file.getOriginalFilename());
	// emp.setImage(file.getBytes());
	// return employeeRepository.save(emp);
	//
	//
	//
	// }

	/*
	 * @Override public void mailsend(Sendmail sendmail, String to) {
	 * System.out.println("EmployeeServiceImpl.mailsend()");
	 * 
	 * try { System.out.println("Mail sending is started"); SimpleMailMessage
	 * simpleMailMessage = new SimpleMailMessage();
	 * simpleMailMessage.setTo(sendmail.getTo());
	 * simpleMailMessage.setSubject(sendmail.getTextBody());
	 * simpleMailMessage.setText(sendmail.getTopic());
	 * simpleMailMessage.setFrom(sendmail.getSetFrom());
	 * 
	 * 
	 * simpleMailMessage.setFrom("gvenu350@gmail.com"); simpleMailMessage.setTo(to);
	 * simpleMailMessage.setSubject(to); simpleMailMessage.setText(to);
	 * 
	 * 
	 * employeeRepository.send(simpleMailMessage);
	 * System.out.println("Mail sending is completed"); }catch (Exception e){
	 * System.out.println("Error occured: "+e.getMessage()); }
	 */

}
