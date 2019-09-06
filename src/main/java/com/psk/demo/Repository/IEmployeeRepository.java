package com.psk.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psk.demo.entity.Employee;

public interface IEmployeeRepository extends JpaRepository<Employee, Long> {
}
