package com.psk.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psk.demo.Entity.Office;

import java.util.List;

public interface IOfficeRepository extends JpaRepository<Office, Long> {
	List<Office> findAllById(Long id);
}
