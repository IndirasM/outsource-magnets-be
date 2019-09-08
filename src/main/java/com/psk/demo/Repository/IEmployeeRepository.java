package com.psk.demo.Repository;

import com.psk.demo.Entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import com.psk.demo.Entity.Employee;

import java.util.List;

public interface IEmployeeRepository extends JpaRepository<Employee, Long> {
	Employee findByEmail(String email);
	List<Employee> findByNameStartingWith(String fragment);
	//List<Employee> findByTripIn(List<Trip> trips);
}
