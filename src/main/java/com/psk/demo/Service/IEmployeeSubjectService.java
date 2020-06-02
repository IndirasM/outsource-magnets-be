package com.psk.demo.Service;

import com.psk.demo.Entity.Employee;
import com.psk.demo.Entity.EmployeeSubject;

import java.util.List;

public interface IEmployeeSubjectService {
	void addEmployeeSubject(Long subjectId, List<Long> employeeIds);
	List<EmployeeSubject> findByEmployeeId(Employee employee);
}
