package com.psk.demo.Repository;

import com.psk.demo.Entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ISubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findById(Long id);
}
