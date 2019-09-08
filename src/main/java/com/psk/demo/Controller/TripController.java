package com.psk.demo.Controller;

import com.psk.demo.Controller.Model.*;
import com.psk.demo.Entity.Employee;
import com.psk.demo.Entity.Trip;
import com.psk.demo.Security.TokenUtil;
import com.psk.demo.Service.IEmployeeService;
import com.psk.demo.Service.ITripService;
import com.psk.demo.Entity.TripDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
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

//	@RequestMapping(value = "/", method = RequestMethod.POST)
//	public ResponseEntity<?> approveTrip(HttpServletRequest request, @RequestBody TripCreationModel body)
//	{
//		String token = request.getHeader("Authorization").replace(TOKEN_PREFIX, "");
//		String email = tokenUtil.getUsernameFromToken(token);
//		Employee owner = employeeService.findByEmail(email);
//
//		TripDescription tripdescription =
//	}
}
