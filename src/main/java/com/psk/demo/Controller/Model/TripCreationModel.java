package com.psk.demo.Controller.Model;

import java.util.List;

public class TripCreationModel {
	public String name;
	public String dateFrom;
	public String dateTo;
	public Long destination;
	public boolean needTicket;
	public boolean needAccommodation;
	public boolean needTransport;
	public List<Long> employees;
}
