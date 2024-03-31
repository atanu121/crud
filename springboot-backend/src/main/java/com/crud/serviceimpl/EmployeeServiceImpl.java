package com.crud.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.crud.model.Employee;
import com.crud.repository.EmployeeRepository;
import com.crud.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	Logger logger=LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Async
	@Override
	public CompletableFuture<List<Employee>> getAllEmployeesData() {

		logger.info("Get list of employee data by:"+Thread.currentThread().getName());
		List<Employee> employees=employeeRepository.findAll();
		return CompletableFuture.completedFuture(employees);
	}

	@Async
	@Override
	public CompletableFuture<Employee> saveEmployeeData(Employee employee) {
		
		Employee employees=employeeRepository.save(employee);
		return CompletableFuture.completedFuture(employees);
	}

	@Async
	@Override
	public CompletableFuture<Employee> getEmployeeDataById(Long id) {
		
		logger.info("get list of employee data by {} "+id+Thread.currentThread().getName());
		
		return CompletableFuture.completedFuture(employeeRepository.findById(id).orElse(null));
	}

	@Async
	@Override
	public CompletableFuture<Employee> updateEmployeeData(Long id, Employee employeeDetails) {
		
		Optional<Employee> employeeData=employeeRepository.findById(id);
		Employee employee=null;
		if(employeeData.isPresent()) {
			Employee newEmployee=employeeData.get();
			newEmployee.setFirstName(employeeDetails.getFirstName());
			newEmployee.setLastName(employeeDetails.getLastName());
			newEmployee.setEmailId(employeeDetails.getEmailId());
			employee=employeeRepository.save(newEmployee);
		}
		
		
		return CompletableFuture.completedFuture(employee);
	}

	@Async
	@Override
	public CompletableFuture<String> deleteEmployeeData(Long id) {
		
		Optional<Employee> givenId=employeeRepository.findById(id);
		if(givenId.isEmpty())
		{
			return CompletableFuture.completedFuture("Data Already Deleted");
		}
		else {
			employeeRepository.deleteById(id);
		}
		return CompletableFuture.completedFuture("Employee Data removed"+id);
	}
	
	

}
