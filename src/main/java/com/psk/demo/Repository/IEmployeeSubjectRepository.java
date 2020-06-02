package com.psk.demo.Repository;

import com.psk.demo.Entity.Employee;
import com.psk.demo.Entity.EmployeeSubject;
import com.psk.demo.Entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IEmployeeSubjectRepository extends JpaRepository<EmployeeSubject, Long> {
    List<EmployeeSubject> findByEmployee(Employee employee);
}
