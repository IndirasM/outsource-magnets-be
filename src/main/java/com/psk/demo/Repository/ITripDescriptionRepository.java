package com.psk.demo.Repository;

import com.psk.demo.Entity.Employee;
import com.psk.demo.Entity.Trip;
import com.psk.demo.Entity.TripDescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ITripDescriptionRepository extends JpaRepository<TripDescription, Long> {
	Optional<TripDescription> findById(Long id);
	List<TripDescription> findByNameStartingWith(String fragment);
}
