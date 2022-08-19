package com.poc.onpassive.controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.onpassive.entity.Employee;
import com.poc.onpassive.entity.Sendmail;
import com.poc.onpassive.exception.ResourceNotFoundException;
import com.poc.onpassive.model.Mail;
import com.poc.onpassive.model.ResponseData;
import com.poc.onpassive.services.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	/*
	 * @Autowired private EmployeeRepository abhiRepo;
	 */

	@PostMapping("/employee")
	public ResponseEntity<ResponseData> createEmployee(@RequestParam("data") String data,
			@RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
		log.info("employee singn up started");

		List<Employee> list = new ArrayList<>();
		Employee employee = new ObjectMapper().readValue(data, Employee.class);

		Employee save = employeeService.upload(employee, file);
		list.add(save);
		log.info("employee singn up ended");
		if (Boolean.TRUE.equals(save != null)) {
			return ResponseEntity.ok(
					new ResponseData(HttpURLConnection.HTTP_CREATED, "success", "EMPLOYEE CREATED SUCCESSFULLU", list));

		} else {
			return ResponseEntity.ok(new ResponseData(HttpURLConnection.HTTP_BAD_REQUEST, "Failed",
					"EMPLOYEE CREATED SUCCESSFULLU FAILED", list));

		}

	}

	@PostMapping("/createEmployee")
	public ResponseEntity<ResponseData> saveEmployee(@RequestBody Employee employee) throws IOException {
		List<Employee> list = new ArrayList<>();
		Employee saveEmployee = employeeService.saveEmployee(employee);
		list.add(saveEmployee);
		if (saveEmployee != null) {
			return ResponseEntity.ok(
					new ResponseData(HttpURLConnection.HTTP_CREATED, "success", "EMPLOYEE CREATED SUCCESSFULLY", list));

		} else {
			return ResponseEntity.ok(new ResponseData(HttpURLConnection.HTTP_BAD_REQUEST, "Failed",
					"EMPLOYEE CREATED SUCCESSFULLU FAILED", list));

		}

	}

	@GetMapping("/viewemployeelist")
	public List<Employee> ViewEmployee() {
		System.out.println("hellow world");
		List<Employee> view = employeeService.viewEmployee();
		return view;
	}

	@GetMapping("/viewbyid/{id}")
	public ResponseEntity<ResponseData> view(@PathVariable("id") Long id) {
		// List<Employee> al = new ArrayList<>();
		System.err.println("employee controller by id" + id);
		Employee viewEmployeebyid = employeeService.viewEmployeebyid(id);
		// al.add(viewEmployeebyid.get().getId());
		if (viewEmployeebyid.getId() != 0) {
			return ResponseEntity.ok(new ResponseData(HttpURLConnection.HTTP_CREATED, "SUCCESS",
					"EMPLOYEE DETAILS  CREATED SUCCESSFULLY"));

		} else {
			return ResponseEntity
					.ok(new ResponseData(HttpURLConnection.HTTP_NOT_FOUND, "FAILED", "EMPLOYEE DETAILS NOT CREATED"));
		}
	}

	@GetMapping("/viewbymail/{mail}")
	public Employee mailBy(@PathVariable("mail") String mail) {
		System.out.println("EmployeeController.mailBy()" + mail);
		Employee viewByMail = employeeService.viewByMail(mail);
		return viewByMail;
	}

	// Update operation
	@PutMapping("/employeeupdate/{id}")

	public Employee updateDepartment(@PathVariable("id") Long id, @RequestBody Employee employee) {
		System.out.println("update employee ");

		Employee updateEmployee = employeeService.updateEmployee(employee, id);
		return updateEmployee;
	}

	// Delete operation
	@DeleteMapping("/delte/{id}")

	public String deleteById(@PathVariable("id") Long Id)

	{
		System.out.println("delete controller" + Id);
		employeeService.deleteServ(Id);
		return "Deleted Successfully";
	}

	@PostMapping("/employeedetails")
	public ResponseEntity<ResponseData> createEmployeeData(@RequestBody Employee employee) {
		System.out.println("createEmployeeData in controller");
		List<Employee> list = new ArrayList<>();
		Employee empsave = employeeService.saveEmployeeDetails(employee);
		list.add(empsave);
		if (empsave != null) {
			return ResponseEntity.ok(new ResponseData(HttpURLConnection.HTTP_CREATED, "SUCCESS",
					"EMPLOYEE DETAILS  CREATED SUCCESSFULLY", list));

		} else {
			return ResponseEntity.ok(new ResponseData(HttpURLConnection.HTTP_BAD_METHOD, "FAILED",
					"EMPLOYEE DETAILS NOT CREATED", list));
		}
	}

	@GetMapping("viewemployees")
	public ResponseEntity<ResponseData> viewEmployees() {
		System.out.println("view the all the employees");
		List<Employee> list = new ArrayList<Employee>();
		List<Employee> view = employeeService.viewEmployeedetails();
		list.addAll(view);
		if (view != null) {
			return ResponseEntity
					.ok(new ResponseData(HttpURLConnection.HTTP_CREATED, "success", "view all employee details", list));
		} else {
			return ResponseEntity.ok(new ResponseData(HttpURLConnection.HTTP_BAD_REQUEST, "failed",
					"not show the employee details", list));
		}

	}

	@GetMapping("/getingEmployee/{id}")
	public ResponseEntity<ResponseData> getbyidEmployeedata(@PathVariable("id") long id)
			throws ResourceNotFoundException {
		System.out.println("update employees in controller" + id);
		List<Employee> list = new ArrayList<Employee>();
		Employee data = employeeService.updateEmployeedetails(id);
		System.out.println("employee response data in conroller id" + id);
		System.out.println("employee response data in conroller data " + data);
		list.add(data);

		if (data != null) {
			return ResponseEntity.ok(new ResponseData(new Date(), HttpURLConnection.HTTP_OK, "success",
					"view the employee data by id", list));
		} else {
			return ResponseEntity
					.ok(new ResponseData(new Date(), HttpURLConnection.HTTP_NOT_FOUND, "failed", "enter correct id"));
		}

	}

	@PutMapping("/updatedetails/{id}")
	public ResponseEntity<ResponseData> updateEmployee(@RequestBody Employee employee, @PathVariable("id") long id) {
		System.out.println("EmployeeController.updateEmployee()");
		List<Employee> list = new ArrayList<Employee>();
		Employee update = employeeService.updateData(employee, id);
		list.add(update);
		if (update != null) {
			return ResponseEntity
					.ok(new ResponseData(HttpURLConnection.HTTP_CREATED, "success", "update employee details", list));
		} else {
			return ResponseEntity.ok(new ResponseData(HttpURLConnection.HTTP_BAD_REQUEST, "failed",
					"not updated employee details", list));
		}

	}

//	@PostMapping("/uploadImage")
//	public void uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("id") long id) throws IOException 
//	{
//		System.out.println("EmployeeController.updateimage controller");
//		 Employee uploadImage = employeeService.uploadImage(file,id);
//		 System.err.println(uploadImage);
//		
//	}

	@PostMapping("/uploadImage")
	public ResponseEntity<ResponseData> uploadFile(@RequestParam("file") MultipartFile file,
			@RequestParam("data") String data) throws IOException {
		log.info("upload image started");
		Employee employee = new ObjectMapper().readValue(data, Employee.class);
		Employee uploadImage = employeeService.uploadImage(file, employee);
		log.info("upload image ended");
		if (uploadImage != null) {
			return ResponseEntity
					.ok(new ResponseData(HttpURLConnection.HTTP_CREATED, "success", "view all employee details"));
		} else {
			return ResponseEntity.ok(
					new ResponseData(HttpURLConnection.HTTP_BAD_REQUEST, "failed", "not show the employee details"));
		}

	}
	
	//@post(value = "/mail/send/", method = RequestMethod.POST)
	
	@PostMapping("/sendmail")
    public void sendMail(@RequestBody Sendmail mail) {
		System.out.println("EmployeeController.sendMail()");
		employeeService.mailsend(mail);
    }
}
