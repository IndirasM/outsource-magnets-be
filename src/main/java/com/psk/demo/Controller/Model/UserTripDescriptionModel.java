package com.psk.demo.Controller.Model;

import com.psk.demo.Entity.Trip;
import com.psk.demo.Entity.TripDescription;

public class UserTripDescriptionModel extends TripDescriptionModel {
	public String ticketDescription;
	public String transportDescription;
	public String hotelAddress;
	public String apartmentAddress;

	public UserTripDescriptionModel(Trip trip) {
		super(trip);
		if (trip.getTicket() != null) this.ticketDescription = trip.getTicket().getDescription();
		if (trip.getTransport() != null) this.transportDescription = trip.getTransport().getDescription();
		if (trip.getHotel() != null) this.hotelAddress = trip.getHotel().getAddress();
		if (trip.getApartment() != null) this.apartmentAddress = trip.getApartment().getAddress();
	}
}
