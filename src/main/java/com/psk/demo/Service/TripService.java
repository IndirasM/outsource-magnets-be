package com.psk.demo.Service;

import com.psk.demo.Entity.Trip;
import com.psk.demo.Repository.ITripRepository;
import com.psk.demo.Entity.Employee;
import com.psk.demo.Entity.TripDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//@Service
//public class TripService implements ITripService {
//	@Autowired
//	private ITripRepository tripRepository;
//
//	@Autowired
//	private IEmployeeService employeeService;
//
//
//	@Override
//	public List<Trip> getApprovedTripsByUserName(String username) {
//		Employee employee = employeeService.loadUserByEmail(username);
//		List<Trip> trips = new ArrayList<>();
//		trips.removeIf(t -> {
//
//		});
//		trips.addAll(employee.getTrips());
//		return trips;
//	}
//
//	@Override
//	public List<Trip> getUnapprovedTripsByUserName(String username) {
//		Employee employee = employeeService.loadUserByEmail(username);
//		List<TripDescription> tripDescriptions = new ArrayList<>();
//		employee.getTrips().forEach(t -> {
//			if (t.getIsApproved() == null)
//				tripDescriptions.add(t.getTripDescription());
//		});
//
//		return tripDescriptions;
//	}
//}
