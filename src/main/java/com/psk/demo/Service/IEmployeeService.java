package com.psk.demo.Service;

import java.util.List;
import java.util.Optional;

import com.psk.demo.Entity.Employee;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IEmployeeService extends UserDetailsService {
	public List<Employee> findAll();
	public Optional<Employee> findById(long id);
	public Employee findByEmail(String email);
	public List<Employee> findByNameStartingWith(String fragment);
}
