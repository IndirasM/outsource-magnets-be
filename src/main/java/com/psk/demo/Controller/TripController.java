package com.psk.demo.Controller;

import com.psk.demo.Controller.Model.*;
import com.psk.demo.Entity.*;
import com.psk.demo.Exception.ResourceNotFoundException;
import com.psk.demo.Helper.DateHelper;
import com.psk.demo.Security.TokenUtil;
import com.psk.demo.Service.IEmployeeService;
import com.psk.demo.Service.IOfficeService;
import com.psk.demo.Service.ITripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.psk.demo.Security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/trip")
public class TripController {
	@Autowired
	private TokenUtil tokenUtil;

	@Autowired
	private IEmployeeService employeeService;

	@Autowired
	private ITripService tripService;

	@Autowired
	private IOfficeService officeService;


	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<TripDescriptionsByUserModel> getTripDescriptionsByUserModel(HttpServletRequest request) throws Exception {
		String token = request.getHeader("Authorization").replace(TOKEN_PREFIX, "");

		List<Trip> approvedTrips = tripService.getApprovedTripsByUserName(tokenUtil.getUsernameFromToken(token));
		List<Trip> unapprovedTrips = tripService.getUnapprovedTripsByUserName(tokenUtil.getUsernameFromToken(token));

		List<TripDescriptionModel> approvedTripDescriptions = new ArrayList<>();
		List<TripDescriptionModel> unapprovedTripDescriptions = new ArrayList<>();

		approvedTrips.forEach(t -> {
			approvedTripDescriptions.add(new TripDescriptionModel(t));
		});
		unapprovedTrips.forEach(t -> {
			unapprovedTripDescriptions.add(new TripDescriptionModel(t));
		});

		return ResponseEntity.ok(new TripDescriptionsByUserModel(approvedTripDescriptions, unapprovedTripDescriptions));
	}

	@RequestMapping(value = "/search/{fragment}", method = RequestMethod.GET)
	public ResponseEntity<List<TripInfo>> getTripsBySearch(@PathVariable String fragment)
	{
		List<TripDescription> trips = tripService.findByNameStartingWith(fragment);
		List<TripInfo> response = new ArrayList<>();
		trips.forEach(t -> {
			response.add(new TripInfo(t.getId(), t.getName()));
		});

		return ResponseEntity.ok(response);
	}

	@RequestMapping(value = "/approve/{idString}", method = RequestMethod.POST)
	public ResponseEntity<?> approveTrip(HttpServletRequest request, @PathVariable String idString, @RequestBody ApproveModel body)
	{
		long id = Long.parseLong(idString);
		String token = request.getHeader("Authorization").replace(TOKEN_PREFIX, "");
		String email = tokenUtil.getUsernameFromToken(token);
		Employee owner = employeeService.findByEmail(email);
		Trip trip = tripService.findById(id);
		if (!trip.getEmployee().getId().equals(owner.getId()) || trip.getIsApproved() != null)
			throw new AuthorizationServiceException("Unauthorized");

		Trip resultTrip = tripService.setApproved(id, body.value);

		return ResponseEntity.ok(new TripDescriptionModel(resultTrip));
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity approveTrip(HttpServletRequest request, @RequestBody TripCreationModel body) {
		if (isTripCreationModelValid(body)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation failed");

		String token = request.getHeader("Authorization").replace(TOKEN_PREFIX, "");
		String email = tokenUtil.getUsernameFromToken(token);
		Employee owner = employeeService.findByEmail(email);
		Office destinationOffice = officeService.findById(body.destination);

		Checklist checklist = new Checklist();
		checklist.setNeedAccommodation(body.needAccommodation);
		checklist.setNeedTicket(body.needTicket);
		checklist.setNeedTransport(body.needTransport);

		TripDescription tripDescription = new TripDescription();
		tripDescription.setName(body.name);
		tripDescription.setCreatedBy(owner);
		tripDescription.setDateFrom(body.dateFrom);
		tripDescription.setDateTo(body.dateTo);
		tripDescription.setDestination(destinationOffice);
		tripDescription.setChecklist(checklist);

		List<Trip> trips = new ArrayList<Trip>();
		body.employees.forEach(e -> trips.add(createTrip(e, tripDescription)));

		tripService.createTrips(trips);

		return new ResponseEntity(HttpStatus.CREATED);
	}

	//region Private methods
	private Trip createTrip(Long id, TripDescription tripDescription) {
		Employee employee = employeeService.findById(id).orElseThrow(ResourceNotFoundException::new);
		Trip trip = new Trip();
		trip.setEmployee(employee);
		trip.setTripDescription(tripDescription);

		return trip;
	}

	private boolean isTripCreationModelValid(TripCreationModel tcm) {
		boolean isValid = true;
		try {
			isValid = hasNullProperties(tcm)
					&& DateHelper.validFormat(tcm.dateFrom)
					&& DateHelper.validFormat(tcm.dateTo)
					&& DateHelper.isSooner(tcm.dateFrom, tcm.dateTo)
					&& DateHelper.isLater(tcm.dateFrom, DateHelper.formatDate(new Date()))
					&& tcm.employees.size() > 0;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			isValid = false;
		}

		return isValid;
	}

	private boolean hasNullProperties(Object obj) throws IllegalAccessException {
		for (Field f : obj.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			if (f.get(obj) == null) {
				return true;
			}
		}
		return false;
	}
	//endregion
}
