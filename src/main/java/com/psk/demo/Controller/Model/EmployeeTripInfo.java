package com.psk.demo.Controller.Model;

import com.psk.demo.Entity.Trip;

public class EmployeeTripInfo {
	public String name;
	public String email;
	public String ticket;
	public String hotel;
	public String apartment;
	public String transport;

	public EmployeeTripInfo(Trip trip) {
		if (trip.getEmployee() != null) this.name = trip.getEmployee().getName();
		if (trip.getEmployee() != null) this.email = trip.getEmployee().getEmail();
		if (trip.getTicket() != null) this.ticket = trip.getTicket().getDescription();
		if (trip.getTransport() != null) this.transport = trip.getTransport().getDescription();
		if (trip.getHotel() != null) this.hotel = trip.getHotel().getAddress();
		if (trip.getApartment() != null) this.apartment = trip.getApartment().getAddress();
	}
}
