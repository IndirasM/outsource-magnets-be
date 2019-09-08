package com.psk.demo.Service;

import com.psk.demo.Entity.Trip;
import com.psk.demo.Entity.TripDescription;

import java.util.List;

public interface ITripService {
	List<Trip> getApprovedTripsByUserName(String username);
	List<Trip> getUnapprovedTripsByUserName(String username);
	List<Trip> findAll();
}