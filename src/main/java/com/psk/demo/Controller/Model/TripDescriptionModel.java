package com.psk.demo.Controller.Model;

import com.psk.demo.Entity.Trip;

public class TripDescriptionModel {
	public String name;
	public String createdByName;
	public String createdByEmail;
	public String dateFrom;
	public String dateTo;
	public String destination;

	public TripDescriptionModel(Trip trip) {
		this.name = trip.getTripDescription().getName();
		this.createdByName = trip.getTripDescription().getCreatedBy().getName();
		this.createdByEmail = trip.getTripDescription().getCreatedBy().getEmail();
		this.dateFrom = trip.getTripDescription().getDateFrom();
		this.dateTo = trip.getTripDescription().getDateTo();
		this.destination = trip.getTripDescription().getDestination().getName();
	}
}
