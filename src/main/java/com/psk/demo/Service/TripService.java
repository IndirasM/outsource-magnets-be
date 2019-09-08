package com.psk.demo.Service;

import com.psk.demo.Entity.Trip;
import com.psk.demo.Helper.DateHelper;
import com.psk.demo.Repository.ITripRepository;
import com.psk.demo.Entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TripService implements ITripService {
	@Autowired
	private ITripRepository tripRepository;

	@Autowired
	private IEmployeeService employeeService;


	@Override
	public List<Trip> getApprovedTripsByUserName(String username) {
		Employee employee = employeeService.findByEmail(username);
		List<Trip> trips = tripRepository.findByEmployee(employee);

		trips.removeIf(t -> {
			String tripDateTo = t.getTripDescription().getDateTo();
			String formattedDate = DateHelper.formatDate(new Date());

			boolean isIrrelevant = DateHelper.isLower(tripDateTo, formattedDate);
			boolean notApproved = t.getIsApproved() == null || !t.getIsApproved();
			return isIrrelevant || notApproved;
		});
		return trips;
	}

	@Override
	public List<Trip> getUnapprovedTripsByUserName(String username) {
		Employee employee = employeeService.findByEmail(username);
		List<Trip> trips = tripRepository.findByEmployee(employee);

		trips.removeIf(t -> {
			String tripDateTo = t.getTripDescription().getDateTo();
			String formattedDate = DateHelper.formatDate(new Date());

			boolean isIrrelevant = DateHelper.isLower(tripDateTo, formattedDate);
			boolean hasResponse = t.getIsApproved() != null;
			return isIrrelevant || hasResponse;
		});
		return trips;
	}

	@Override
	public List<Trip> findAll() {
		return tripRepository.findAll();
	}
}
