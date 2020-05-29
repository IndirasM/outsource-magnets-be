package com.psk.demo.Service;

import java.util.List;
import java.util.Optional;

import com.psk.demo.Entity.Employee;
import com.psk.demo.Entity.Team;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IEmployeeService extends UserDetailsService {
	List<Employee> findAll();
	Optional<Employee> findById(long id);
	Employee findByEmail(String email);
	List<Employee> findByNameStartingWith(String fragment);
	List<Employee> findByManager(Employee manager);
}
