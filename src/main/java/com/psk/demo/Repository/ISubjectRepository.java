package com.psk.demo.Repository;

import com.psk.demo.Entity2.Employee;
import com.psk.demo.Entity2.LearningDay;
import com.psk.demo.Entity2.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ISubjectRepository extends JpaRepository<Subject, Long> {
}
