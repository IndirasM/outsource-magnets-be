package com.psk.demo.Service;

import com.psk.demo.Entity.Subject;

import java.util.List;
import java.util.Optional;

public interface ISubjectService {
	List<Subject> findAll();
	Optional<Subject> findById(Long id);
}
