package com.psk.demo.Service;

import com.psk.demo.Entity.Trip;
import com.psk.demo.Entity.TripDescription;
import com.psk.demo.Exception.ResourceNotFoundException;
import com.psk.demo.Repository.IEmployeeRepository;
import com.psk.demo.Entity.Employee;
import com.psk.demo.Repository.ITripDescriptionRepository;
import com.psk.demo.Repository.ITripRepository;
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
	private ITripDescriptionRepository tripDescriptionRepository;

	@Autowired
	private ITripRepository tripRepository;

	public EmployeeService(IEmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}

	@Override
	public Optional<Employee> findById(Long id) {
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

	public List<Employee> findByTripDescription(Long id) {
		TripDescription tripDescription = tripDescriptionRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
		List<Trip> trips = tripRepository.findByTripDescriptionIn(tripDescription);

		List<Employee> employees = new ArrayList<>();
		trips.forEach(t -> {
			employees.add(t.getEmployee());
		});
		return employees;
	}
}
