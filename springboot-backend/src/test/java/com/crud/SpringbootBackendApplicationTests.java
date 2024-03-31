package com.crud;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.crud.model.Employee;
import com.crud.repository.EmployeeRepository;

@SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
class SpringbootBackendApplicationTests {
	
	@Autowired
	private EmployeeRepository repository;

	@Test
	void contextLoads() {
	}
	
	@Test
	@Rollback(false)
	public void testCreateEmployee() {
		Employee employee=new Employee();
		employee.setFirstName("atanu");
		employee.setLastName("sarkar");
		employee.setEmailId("atanu@gmail.com");
		
		Employee savedData=repository.save(employee);
		assertThat(savedData.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListEmployeeData() {
		List<Employee> employee=repository.findAll();
		assertThat(employee).size().isGreaterThan(0);
	}
	
	@Test
	@Rollback
	public void testUpdateEmployeeData() {
		Optional<Employee> employeeData=repository.findById((long) 1);
		Employee employee=null;
		if(employeeData.isPresent()) {
		Employee newEmployee=employeeData.get();
		newEmployee.setEmailId("sarkar@yahoo.com");
		employee=repository.save(newEmployee);
		
		}
		assertThat(employee.getEmailId()).isEqualTo("sarkar@yahoo.com");
		
	}
	
	@Test
	@Rollback(false)
	public void testDeleteEmployeeData() {
		Optional<Employee> getEmployeeById=repository.findById((long) 1);
		if(getEmployeeById.isPresent()) {
			repository.deleteById(getEmployeeById.get().getId());
		}
		
		Optional<Employee> employee=repository.findById((long) 1);
		assertThat(employee.isEmpty());
		
		
	}

}
