package com.psk.demo.Service;

import com.psk.demo.Entity.Employee;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService extends UserDetailsService {
	List<Employee> findAll();
	Optional<Employee> findById(long id);
	Employee findByEmail(String email);
	List<Employee> findByNameStartingWith(String fragment);
	List<Employee> findByManager(Employee manager);
}
