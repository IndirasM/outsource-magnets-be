package com.psk.demo.Service;

import com.psk.demo.Entity2.Employee;
import com.psk.demo.Entity2.LearningDay;

import java.util.List;

public interface ILearningDaysService {
    List<LearningDay> findByLearningDayEmployee(Employee employee);
}
