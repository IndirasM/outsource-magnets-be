package com.psk.demo.Repository;

import com.psk.demo.Entity.Employee;
import com.psk.demo.Entity.Trip;
import com.psk.demo.Entity.TripDescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITripRepository extends JpaRepository<Trip, Long> {
	public List<Trip> findByEmployee(Employee employee);
	List<Trip> findByTripDescriptionIn(TripDescription tripDescription);
}
