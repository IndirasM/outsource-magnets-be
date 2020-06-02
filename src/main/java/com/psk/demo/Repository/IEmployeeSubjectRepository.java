package com.psk.demo.Repository;

import com.psk.demo.Entity.Employee;
import com.psk.demo.Entity.EmployeeSubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IEmployeeSubjectRepository extends JpaRepository<EmployeeSubject, Long> {
    List<EmployeeSubject> findByEmployee(Employee employee);
}
