package com.psk.demo.Repository;

import com.psk.demo.Entity.Employee;
import com.psk.demo.Entity.Trip;
import com.psk.demo.Entity.TripDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ITripRepository extends JpaRepository<Trip, Long> {
	List<Trip> findAllById(Long id);
	List<Trip> findByEmployee(Employee employee);
	List<Trip> findByTripDescriptionIn(TripDescription tripDescription);
}
