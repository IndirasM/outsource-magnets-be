package com.psk.demo.Service;

import com.psk.demo.Entity.Employee;
import com.psk.demo.Entity.EmployeeSubject;
import com.psk.demo.Entity.Team;

import java.util.List;
import java.util.Optional;

public interface IEmployeeSubjectService {
	void addEmployeeSubject(Long subjectId, List<Long> employeeIds);
	List<EmployeeSubject> findByEmployeeId(Employee employee);
}
