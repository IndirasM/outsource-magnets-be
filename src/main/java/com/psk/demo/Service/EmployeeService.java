package com.psk.demo.Service;

import com.psk.demo.Repository.IEmployeeRepository;
import com.psk.demo.Entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements IEmployeeService {
	@Autowired
	private IEmployeeRepository repository;

	public EmployeeService(IEmployeeRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Employee> findAll() {
		return repository.findAll();
	}

	@Override
	public Optional<Employee> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		List<Employee> employees = repository.findByEmail(email);

		return employees.get(0);
	}

	public Employee loadUserByEmail(String email) throws UsernameNotFoundException {
		List<Employee> employees = repository.findByEmail(email);

		return employees.get(0);
	}
}
