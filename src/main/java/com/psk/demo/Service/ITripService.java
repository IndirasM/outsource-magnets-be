package com.psk.demo.Service;

import com.psk.demo.Entity.Trip;
import com.psk.demo.Entity.TripDescription;

import java.util.List;

public interface ITripService {
	List<Trip> getApprovedTripsByUserName(String username);
	List<Trip> getUnapprovedTripsByUserName(String username);
	List<Trip> findAll();
	List<TripDescription> findByNameStartingWith(String fragment);
	Trip setApproved(long id, boolean value);
	Trip findById(long id);
	TripDescription createTrips(List<Trip> trips);
}
