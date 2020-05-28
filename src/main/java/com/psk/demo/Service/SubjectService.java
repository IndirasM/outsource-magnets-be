package com.psk.demo.Service;

import com.psk.demo.Entity2.Team;
import com.psk.demo.Exception.ResourceNotFoundException;
import com.psk.demo.Repository.IEmployeeRepository;
import com.psk.demo.Entity2.Employee;
import com.psk.demo.Repository.ITeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements IEmployeeService {
	@Autowired
	private IEmployeeRepository employeeRepository;
	@Autowired
	private ITeamRepository teamRepository;

	public EmployeeService(IEmployeeRepository employeeRepository, ITeamRepository teamRepository) {
		this.employeeRepository = employeeRepository;
		this.teamRepository = teamRepository;
	}

	@Override
	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}

	@Override
	public Optional<Employee> findById(long id) {
		return employeeRepository.findById(id);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Employee employee = employeeRepository.findByEmail(email);

		return employee;
	}

	public Employee findByEmail(String email) throws UsernameNotFoundException {
		Employee employee = employeeRepository.findByEmail(email);
		return employee;
	}

	public List<Employee> findByNameStartingWith(String fragment) {
		return employeeRepository.findByNameStartingWith(fragment);
	}
}
