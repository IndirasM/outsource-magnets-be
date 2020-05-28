package com.psk.demo.Service;

import com.psk.demo.Entity2.Employee;
import com.psk.demo.Entity2.Subject;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface ISubjectService {
	public List<Subject> findAll();
}
