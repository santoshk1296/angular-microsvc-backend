package com.angular.san.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.angular.san.exception.ResourceNotFoundException;
import com.angular.san.model.Employee;
import com.angular.san.repository.EmployeeRepository;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3003"})
@RestController
@RequestMapping("/emp-api/v1/")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository empRepos;

	//HATEOAS being used to return the link
	
	//jackson-dataformat-xml This is being used to support JASON and XML response/request
	//Just add dependency in pom.xml
	
	//For RestFul services there is no standard like WSDL documentation so we use Swagger Documentation
	//Add springfox-swagger2 and springfox-swagger-ui
	
	@GetMapping("/employees")
	public List<Employee> getAllEmployee(){
		
		//return empRepos.findAll();
		return empRepos.findAllByOrderByIdAsc();
	}
	
	@PostMapping("/employees")
	public Employee createEmployee(@Valid @RequestBody Employee employee) {
		
		long empid;
		
		empid = empRepos.getMaxId();
		
		empid = empid + 1;
		
		System.out.println("emp id: "+empid);
		
		employee.setId(empid);
		
		return empRepos.save(employee);
	}
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		
		Employee employee =  empRepos.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with id: "+id));
		return ResponseEntity.ok(employee);
		
	}
	
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee empDetails) {
		
		Employee employee =  empRepos.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with id: "+id));
		
		employee.setFirstName(empDetails.getFirstName());
		employee.setLastName(empDetails.getLastName());
		employee.setEmailId(empDetails.getEmailId());
		
		Employee updatedEmployee  = empRepos.save(employee);
		
		return ResponseEntity.ok(updatedEmployee);
		
	}
	
	//This method would delete the record by id and send deleted record as response
	@DeleteMapping("/employees-id/{id}")
	public ResponseEntity<Employee> deleteEmployeeById(@PathVariable Long id) {
		
		Employee employee =  empRepos.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with id: "+id));
		
		empRepos.deleteById(id);
		
		return ResponseEntity.ok(employee);
		
	}
	
	//This method would delete the record with object  and send response with status
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
		
		Employee employee =  empRepos.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with id: "+id));
		
		empRepos.delete(employee);
		
		Map<String, Boolean> response =  new HashMap<>();
		response.put("record_deleted", Boolean.TRUE);
		
		return ResponseEntity.ok(response);
		
	}
}
