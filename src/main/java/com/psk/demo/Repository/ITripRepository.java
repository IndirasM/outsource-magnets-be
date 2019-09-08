package com.psk.demo.Repository;

import com.psk.demo.Entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITripRepository extends JpaRepository<Trip, Long> {

	//public List<Trip> findRelevantByEmployeeId(Long id);
}
