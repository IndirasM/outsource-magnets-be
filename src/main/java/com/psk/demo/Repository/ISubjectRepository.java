package com.psk.demo.Repository;

import com.psk.demo.Entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISubjectRepository extends JpaRepository<Subject, Long> {
}
