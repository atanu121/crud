package com.crud.controller;


import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.model.Employee;
import com.crud.service.EmployeeService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	// get all employees
	@SuppressWarnings("rawtypes")
	@GetMapping(value="/employees", produces="application/json")
	public CompletableFuture<ResponseEntity> getAllEmployees(){
		return employeeService.getAllEmployeesData().thenApply(ResponseEntity::ok);
	}		
	
	// create employee rest api
	@SuppressWarnings("rawtypes")
	@PostMapping(value="/employees", produces="application/json")
	public ResponseEntity createEmployee(@RequestBody Employee employee) throws Exception {
		CompletableFuture<Employee> employeeData=employeeService.saveEmployeeData(employee);
		
		return ResponseEntity.status(201).body(employeeData);
	}
	
	// get employee by id rest api
	@SuppressWarnings("rawtypes")
	@GetMapping(value="/employees/{id}", produces="application/json")
	public CompletableFuture<ResponseEntity> getEmployeeById(@PathVariable Long id) {
		
		return employeeService.getEmployeeDataById(id).thenApply(ResponseEntity::ok);
	}
	
	// update employee rest api
	
	@PutMapping(value="/employees/{id}",produces="application/json")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) throws Exception{
		CompletableFuture<Employee> updateEmployeeDataById = employeeService.updateEmployeeData(id,employeeDetails);
		return ResponseEntity.status(HttpStatus.OK).body(updateEmployeeDataById.get());
	}
	
	// delete employee rest api
	@SuppressWarnings("rawtypes")
	@DeleteMapping("/employees/{id}")
	public ResponseEntity deleteEmployee(@PathVariable Long id)throws Exception {
		
		CompletableFuture<String> deleteEmployeeData=employeeService.deleteEmployeeData(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(deleteEmployeeData.get());
		
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping(value="/getEmployeeDataByThread", produces="application/json")
	public ResponseEntity getEmployeeData() {
		
		CompletableFuture<List<Employee>> employee1=employeeService.getAllEmployeesData();
		CompletableFuture<List<Employee>> employee2=employeeService.getAllEmployeesData();
		CompletableFuture<List<Employee>> employee3=employeeService.getAllEmployeesData();
		CompletableFuture.allOf(employee1,employee2,employee3).join();
		
		return ResponseEntity.status(HttpStatus.OK).build();
		
	}
	
	
}
