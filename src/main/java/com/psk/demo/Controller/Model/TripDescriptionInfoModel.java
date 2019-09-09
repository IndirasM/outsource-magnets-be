package com.psk.demo.Controller.Model;

import com.psk.demo.Entity.Trip;
import com.psk.demo.Entity.TripDescription;

import java.util.List;

public class TripDescriptionInfoModel {
	public Long id;
	public String name;
	public String dateFrom;
	public String dateTo;
	public String destination;
	public boolean needTicket;
	public boolean needAccommodation;
	public boolean needTransport;
	public EmployeeTripList employeeTrips;

	public TripDescriptionInfoModel(TripDescription tripDescription) {
		this.id = tripDescription.getId();
		this.name = tripDescription.getName();
		this.dateFrom = tripDescription.getDateFrom();
		this.dateTo = tripDescription.getDateTo();
		if (tripDescription.getDestination() != null) this.destination = tripDescription.getDestination().getName();
		this.needTicket = tripDescription.getChecklist().getNeedTicket();
		this.needAccommodation = tripDescription.getChecklist().getNeedAccommodation();
		this.needTransport = tripDescription.getChecklist().getNeedTransport();
}
}
