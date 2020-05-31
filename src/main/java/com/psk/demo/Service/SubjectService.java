package com.psk.demo.Service;

import com.psk.demo.Entity.Subject;
import com.psk.demo.Repository.ISubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService implements ISubjectService {
	@Autowired
	private ISubjectRepository subjectRepository;

	public SubjectService(ISubjectRepository subjectRepository) {
		this.subjectRepository = subjectRepository;
	}

	@Override
	public List<Subject> findAll() {
		return subjectRepository.findAll();
	}

	@Override
	public Optional<Subject> findById(Long id) {
		return subjectRepository.findById(id);
	}
}
