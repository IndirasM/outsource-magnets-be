package com.psk.demo.Service;

import java.util.List;
import java.util.Optional;

import com.psk.demo.entity.Employee;

public interface IEmployeeService {
	public List<Employee> findAll();
	public Optional<Employee> findById(Long id);
}
