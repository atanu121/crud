package com.crud.service;

import java.util.concurrent.CompletableFuture;
import org.springframework.scheduling.annotation.Async;
import java.util.*;
import com.crud.model.Employee;

public interface EmployeeService {
	
@Async
public CompletableFuture<List<Employee>> getAllEmployeesData();

@Async
public CompletableFuture<Employee> saveEmployeeData(Employee employee);

@Async
public CompletableFuture<Employee> getEmployeeDataById(Long id);

@Async
public CompletableFuture<Employee> updateEmployeeData(Long id,Employee employeeDetails);

@Async
public CompletableFuture<String> deleteEmployeeData(Long id);

}
