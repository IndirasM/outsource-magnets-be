package com.psk.demo.Service;

import com.psk.demo.Entity.Employee;
import com.psk.demo.Entity.EmployeeSubject;
import com.psk.demo.Entity.Subject;
import com.psk.demo.Entity.Team;
import com.psk.demo.Helper.DateHelper;
import com.psk.demo.Repository.IEmployeeRepository;
import com.psk.demo.Repository.IEmployeeSubjectRepository;
import com.psk.demo.Repository.ISubjectRepository;
import com.psk.demo.Repository.ITeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeSubjectService implements IEmployeeSubjectService {
	@Autowired
	private IEmployeeSubjectRepository employeeSubjectRepository;
	@Autowired
	private IEmployeeRepository employeeRepository;
	@Autowired
	private ISubjectRepository subjectRepository;

	public EmployeeSubjectService(IEmployeeSubjectRepository employeeSubjectRepository, IEmployeeRepository employeeRepository, ISubjectRepository subjectRepository) {
		this.employeeSubjectRepository = employeeSubjectRepository;
		this.employeeRepository = employeeRepository;
		this.subjectRepository = subjectRepository;
	}

	@Override
	public void addEmployeeSubject(Long subjectId, List<Long> employeeIds) {
		Subject subject = subjectRepository.findById(subjectId).get();
		List<EmployeeSubject> employeeSubjects = employeeIds.stream().map(id -> {
			Employee employee = employeeRepository.findById(id).get();
			return new EmployeeSubject(subject, employee, DateHelper.formatDate(new Date()));
		}).collect(Collectors.toList());

		employeeSubjectRepository.saveAll(employeeSubjects);
	}
}
