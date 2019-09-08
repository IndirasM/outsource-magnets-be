package com.psk.demo.Controller;

import com.psk.demo.Controller.Model.JwtResponse;
import com.psk.demo.Controller.Model.TripDescriptionModel;
import com.psk.demo.Controller.Model.TripDescriptionsByUserModel;
import com.psk.demo.Entity.Trip;
import com.psk.demo.Security.TokenUtil;
import com.psk.demo.Service.IEmployeeService;
import com.psk.demo.Service.ITripService;
import com.psk.demo.Entity.TripDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}