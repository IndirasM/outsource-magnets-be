package com.psk.demo.Repository;

import com.psk.demo.Entity.Employee;
import com.psk.demo.Entity.LearningDay;
import com.psk.demo.Entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ILearningDayRepository extends JpaRepository<LearningDay, Long> {
    List<LearningDay> findByEmployee(Employee employee);
    List<LearningDay> findByEmployeeIn(List<Employee> employees);
    List<LearningDay> findByDateAndEmployee(String date, Employee employee);
    List<LearningDay> findByEmployeeAndDateGreaterThanEqualAndDateLessThan(Employee employee, String date1, String date2);
    List<LearningDay> findByEmployeeAndSubjectIn(Employee employee, List<Subject> subject);
    List<LearningDay> findBySubjectAndDateLessThan(Subject subject, String date);
}
