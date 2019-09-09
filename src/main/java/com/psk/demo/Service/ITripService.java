package com.psk.demo.Service;

import com.psk.demo.Entity.Employee;
import com.psk.demo.Entity.Trip;
import com.psk.demo.Entity.TripDescription;

import java.util.List;

public interface ITripService {
	List<Trip> getApprovedTripsByUserName(String username);
	List<Trip> getUnapprovedTripsByUserName(String username);
	List<Trip> findAll();
	List<TripDescription> findByNameStartingWith(String fragment);
	Trip setApproved(long id, int value);
	Trip findById(long id);
	TripDescription createTrips(List<Trip> trips);
	TripDescription findDescriptionById(long id);
	TripDescription saveDescription(TripDescription tripDescription);
	List<Trip> getApprovedTripsByDescriptionId(Long id);
	List<Trip> getUnapprovedTripsByDescriptionId(Long id);
	List<Trip> getDeclinedTripsByDescriptionId(Long id);
	List<TripDescription> findDescriptionsByCreatedBy(Employee employee);
	List<Trip> findByTripDescription(TripDescription tripDescription);
}
