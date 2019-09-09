package com.psk.demo.Service;

import com.psk.demo.Entity.Trip;
import com.psk.demo.Entity.TripDescription;
import com.psk.demo.Exception.ResourceNotFoundException;
import com.psk.demo.Helper.DateHelper;
import com.psk.demo.Repository.ITripDescriptionRepository;
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

	@Autowired
	private ITripDescriptionRepository tripDescriptionRepository;

	@Override
	public List<Trip> getApprovedTripsByUserName(String username) {
		Employee employee = employeeService.findByEmail(username);
		List<Trip> trips = tripRepository.findByEmployee(employee);

		trips = filterTripsByApproval(trips, 1);
		trips = filterTripsByRelevancy(trips);

		return trips;
	}

	@Override
	public List<Trip> getUnapprovedTripsByUserName(String username) {
		Employee employee = employeeService.findByEmail(username);
		List<Trip> trips = tripRepository.findByEmployee(employee);

		trips = filterTripsByApproval(trips, 0);
		trips = filterTripsByRelevancy(trips);

		return trips;
	}

	public List<Trip> getApprovedTripsByDescriptionId(Long id) {
		TripDescription tripDescription = tripDescriptionRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
		List<Trip> trips = tripDescription.getTrips();
		return filterTripsByApproval(trips, 1);
	}

	public List<Trip> getUnapprovedTripsByDescriptionId(Long id) {
		TripDescription tripDescription = tripDescriptionRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
		List<Trip> trips = tripRepository.findByTripDescription(tripDescription);
		return filterTripsByApproval(trips, 0);
	}

	public List<Trip> getDeclinedTripsByDescriptionId(Long id) {
		TripDescription tripDescription = tripDescriptionRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
		List<Trip> trips = tripDescription.getTrips();
		return filterTripsByApproval(trips, 2);
	}

	@Override
	public List<Trip> findAll() {
		return tripRepository.findAll();
	}

	public List<TripDescription> findByNameStartingWith(String fragment) {
		return tripDescriptionRepository.findByNameStartingWith(fragment);
	}

	public Trip setApproved(long id, int value) {
		Trip trip = tripRepository.findAllById(id).get(0);
		trip.setIsApproved(value);
		return tripRepository.save(trip);
	}

	public Trip findById(long id) {
		return tripRepository.findAllById(id).get(0);
	}

	public TripDescription findDescriptionById(long id) {
		return tripDescriptionRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
	}

	public TripDescription createTrips(List<Trip> trips) {
		List<Trip> createdTrips = tripRepository.saveAll(trips);
		return createdTrips.get(0).getTripDescription();
	}

	public TripDescription saveDescription(TripDescription tripDescription) {
		return tripDescriptionRepository.save(tripDescription);
	}

	//region Private methods
	private List<Trip> filterTripsByRelevancy(List<Trip> trips) {
		trips.removeIf(t -> {
			String tripDateTo = t.getTripDescription().getDateTo();
			String formattedDate = DateHelper.formatDate(new Date());
			return DateHelper.isSooner(tripDateTo, formattedDate);
		});
		return trips;
	}

	private List<Trip> filterTripsByApproval(List<Trip> trips, int value) {
		trips.removeIf(t -> t.getIsApproved() != value);
		return trips;
	}
	//endregion
}
