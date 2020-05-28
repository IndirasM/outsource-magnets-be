package com.psk.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psk.demo.Entity2.Employee;

import java.util.List;

public interface IEmployeeRepository extends JpaRepository<Employee, Long> {
	Employee findByEmail(String email);
	List<Employee> findByNameStartingWith(String fragment);
}
