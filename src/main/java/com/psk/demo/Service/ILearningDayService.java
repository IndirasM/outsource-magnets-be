package com.psk.demo.Service;

import com.psk.demo.Entity2.Employee;
import com.psk.demo.Entity2.LearningDay;

import java.util.List;

public interface ILearningDayService {
    List<LearningDay> findByEmployee(Employee employee);
    List<LearningDay> findByManager(Employee manager);
}
