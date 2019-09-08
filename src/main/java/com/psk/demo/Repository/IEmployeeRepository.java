package com.psk.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psk.demo.Entity.Employee;

import java.util.List;

public interface IEmployeeRepository extends JpaRepository<Employee, Long> {
	List<Employee> findByEmail(String email);
}
