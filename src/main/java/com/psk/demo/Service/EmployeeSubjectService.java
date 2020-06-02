package com.psk.demo.Service;

import com.psk.demo.Entity.Employee;
import com.psk.demo.Entity.EmployeeSubject;
import com.psk.demo.Entity.LearningDay;
import com.psk.demo.Entity.Subject;
import com.psk.demo.Helper.DateHelper;
import com.psk.demo.Repository.IEmployeeRepository;
import com.psk.demo.Repository.IEmployeeSubjectRepository;
import com.psk.demo.Repository.ILearningDayRepository;
import com.psk.demo.Repository.ISubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeSubjectService implements IEmployeeSubjectService {
	@Autowired
	private IEmployeeSubjectRepository employeeSubjectRepository;
	@Autowired
	private IEmployeeRepository employeeRepository;
	@Autowired
	private ISubjectRepository subjectRepository;
	@Autowired
	private ILearningDayRepository learningDayRepository;

	public EmployeeSubjectService(IEmployeeSubjectRepository employeeSubjectRepository, IEmployeeRepository employeeRepository, ISubjectRepository subjectRepository, ILearningDayRepository learningDayRepository) {
		this.employeeSubjectRepository = employeeSubjectRepository;
		this.employeeRepository = employeeRepository;
		this.subjectRepository = subjectRepository;
		this.learningDayRepository = learningDayRepository;
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

	@Override
	public List<EmployeeSubject> findByEmployeeId(Employee employee) {
		List<EmployeeSubject> allES = employeeSubjectRepository.findByEmployee(employee);
		List<Subject> esSubjects = allES.stream().map(EmployeeSubject::getSubject).collect(Collectors.toList());
		List<LearningDay> learningDaysBySubjects = learningDayRepository.findByEmployeeAndSubjectIn(employee, esSubjects);
		List<Long> learningDaySubjects = learningDaysBySubjects.stream().map(ld -> ld.getSubject().getId()).collect(Collectors.toList());

		List<EmployeeSubject> filteredEs = allES.stream().filter(es -> {
			List<LearningDay> f = learningDaysBySubjects.stream()
					.filter(ld -> ld.getSubject().getId() == es.getSubject().getId() && es.getCreated().compareTo(ld.getCreated()) <= 0)
					.collect(Collectors.toList());

			return f.isEmpty();
		}).collect(Collectors.toList());

		return filteredEs;
	}
}
