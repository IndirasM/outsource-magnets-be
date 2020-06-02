package com.psk.demo.Service;

import com.psk.demo.Controller.Model.NewSubjectModel;
import com.psk.demo.Entity.Subject;
import com.psk.demo.Repository.IEmployeeRepository;
import com.psk.demo.Repository.ILearningDayRepository;
import com.psk.demo.Repository.ISubjectRepository;
import com.psk.demo.Repository.ITeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectService implements ISubjectService {
	@Autowired
	private ISubjectRepository subjectRepository;
	@Autowired
	private IEmployeeRepository employeeRepository;
	@Autowired
	private ITeamRepository teamRepository;
	@Autowired
	private ILearningDayRepository learningDayRepository;

	public SubjectService(ISubjectRepository subjectRepository, IEmployeeRepository employeeRepository, ITeamRepository teamRepository, ILearningDayRepository learningDayRepository) {
		this.subjectRepository = subjectRepository;
		this.employeeRepository = employeeRepository;
		this.teamRepository = teamRepository;
		this.learningDayRepository = learningDayRepository;
	}

	@Override
	public List<Subject> findAll() {
		return subjectRepository.findAll();
	}

	@Override
	public Optional<Subject> findById(Long id) {
		return subjectRepository.findById(id);
	}

	@Override
	public Long Insert(NewSubjectModel subject) {
		System.out.println(subject.name);
		Subject newSubject = new Subject();
		newSubject.setDescription(subject.description);
		newSubject.setName(subject.name);
		newSubject.setDate(new Date().toString());
		if (subject.parentId != null) {
			newSubject.setParentSubject(subjectRepository.getOne(subject.parentId));
		}
		Subject createdSubject = subjectRepository.save(newSubject);
		return createdSubject.getId();
	}
}
