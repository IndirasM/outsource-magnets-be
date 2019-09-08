package com.psk.demo.Controller.Model;

import java.util.Collections;
import java.util.List;

public class TripDescriptionsByUserModel {
	public List<TripDescriptionModel> upcomingTrips;
	public List<TripDescriptionModel> unapprovedTrips;

	public TripDescriptionsByUserModel(List<TripDescriptionModel> upcomingTrips, List<TripDescriptionModel> unapprovedTrips) {
		this.upcomingTrips = upcomingTrips;
		this.unapprovedTrips = unapprovedTrips;
	}
}
