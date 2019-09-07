package com.psk.demo.Service;

import java.util.List;
import java.util.Optional;

import com.psk.demo.entity.Employee;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IEmployeeService extends UserDetailsService {
	public List<Employee> findAll();
	public Optional<Employee> findById(Long id);
}
