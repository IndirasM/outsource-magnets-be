package com.psk.demo.Service;

import com.psk.demo.Entity.Employee;
import com.psk.demo.Entity.LearningDay;

import java.util.List;

public interface ILearningDayService {
    List<LearningDay> findByEmployee(Employee employee);
    List<LearningDay> findByManager(Employee manager);
    void delete(Long id);
}
