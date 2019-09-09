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

	@RequestMapping(value = "/overview", method = RequestMethod.GET)
	public ResponseEntity<List<OverviewTripListItemModel>> getTripDescriptionsForSuperviser(HttpServletRequest request) throws Exception {
		String token = request.getHeader("Authorization").replace(TOKEN_PREFIX, "");
		Employee supervisor = employeeService.findByEmail(tokenUtil.getUsernameFromToken(token));
		List<TripDescription> tripDescriptions = tripService.findDescriptionsByCreatedBy(supervisor);

		List<OverviewTripListItemModel> tripListModels = new ArrayList<>();
		tripDescriptions.forEach(td -> {
			OverviewTripListItemModel model = new OverviewTripListItemModel(td);
			List<OverviewEmployeeModel> employeeModels = new ArrayList<>();
			List<Trip> trips = tripService.findByTripDescription(td);
			trips.forEach(t -> {
				Employee employee = t.getEmployee();
				employeeModels.add(new OverviewEmployeeModel(employee.getId(), employee.getName(), employee.getEmail(), getApprovalStatus(t.getIsApproved())));
			});
			model.employees = employeeModels;

			tripListModels.add(model);
		});

		return ResponseEntity.ok(tripListModels);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<TripDescriptionInfoModel> getTripDescriptionsByIdModel(@PathVariable Long id) throws Exception {
		List<Trip> approvedTrips = tripService.getApprovedTripsByDescriptionId(id);
		List<Trip> unapprovedTrips = tripService.getUnapprovedTripsByDescriptionId(id);
		List<Trip> declinedTrips = tripService.getDeclinedTripsByDescriptionId(id);

		List<EmployeeTripInfo> approvedTripInfoList = new ArrayList<>();
		List<EmployeeTripInfo> unapprovedTripInfoList = new ArrayList<>();
		List<EmployeeTripInfo> declinedTripInfoList = new ArrayList<>();

		approvedTrips.forEach(t -> approvedTripInfoList.add(new EmployeeTripInfo(t)));
		unapprovedTrips.forEach(t -> unapprovedTripInfoList.add(new EmployeeTripInfo(t)));
		declinedTrips.forEach(t -> declinedTripInfoList.add(new EmployeeTripInfo(t)));

		TripDescriptionInfoModel infoModel = new TripDescriptionInfoModel(tripService.findDescriptionById(id));
		infoModel.employeeTrips = new EmployeeTripList(approvedTripInfoList, unapprovedTripInfoList, declinedTripInfoList);

		return ResponseEntity.ok(infoModel);
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
		if (!trip.getEmployee().getId().equals(owner.getId()) || trip.getIsApproved() != 0)
			throw new AuthorizationServiceException("Unauthorized");

		Trip resultTrip = tripService.setApproved(id, body.value ? 1 : 2);

		return ResponseEntity.ok(new TripDescriptionModel(resultTrip));
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity createTrip(HttpServletRequest request, @RequestBody TripCreationModel body) {
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

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity updateTrip(HttpServletRequest request, @RequestBody TripUpdateModel body, @PathVariable Long id) throws IllegalAccessException {
		if (hasNullProperties(body)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation failed");

		TripDescription tripDescription = tripService.findDescriptionById(id);
		tripDescription.setName(body.name);
		tripDescription.getChecklist().setNeedTransport(body.needTransport);
		tripDescription.getChecklist().setNeedAccommodation(body.needAccommodation);
		tripDescription.getChecklist().setNeedTicket(body.needTicket);

		tripService.saveDescription(tripDescription);

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

	private String getApprovalStatus(int isApproved) {
		String result = "";
		switch (isApproved) {
			case 0:
				result = "PENDING";
				break;
			case 1:
				result = "APPROVED";
				break;
			case 2:
				result = "DECLINED";
				break;
		}
		return result;
	}
	//endregion
}
