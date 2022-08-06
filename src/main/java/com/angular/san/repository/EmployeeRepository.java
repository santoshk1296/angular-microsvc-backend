package com.angular.san.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.angular.san.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	@Query("SELECT coalesce(max(e.id), 0) FROM Employee e")
	Long getMaxId();
	
	public List<Employee> findAllByOrderByIdAsc();

}
