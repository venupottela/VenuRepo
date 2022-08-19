package com.poc.onpassive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.poc.onpassive.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	@Query(value = "select * from employees where email_id= :mail ", nativeQuery = true)
	public Employee findByMail(String mail);

	@Query(value = "insert into employees (id,image) values(?,?)", nativeQuery = true)
	public Employee save(Long id, MultipartFile file);

	@Query(value="insert into employees (id,first_name,last_name,email_id,mobileno,,address,image) values(?,?,?,?,?,?,?)",nativeQuery = true)
//	public Employee save1(MultipartFile file, Long id);

	public Employee save1(Employee employee, MultipartFile file);


	
	

	 

}
