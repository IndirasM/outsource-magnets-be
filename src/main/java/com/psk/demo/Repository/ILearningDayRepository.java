package com.psk.demo.Repository;

import com.psk.demo.Entity2.Employee;
import com.psk.demo.Entity2.LearningDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ILearningDayRepository extends JpaRepository<LearningDay, Long> {
    List<LearningDay> findByEmployee(Employee employee);
    List<LearningDay> findByEmployeeIn(List<Employee> employees);
}
