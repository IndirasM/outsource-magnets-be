package com.psk.demo.Controller.Model;

import java.util.List;

public class EmployeeTripList {
	public List<EmployeeTripInfo> upcomingTrips;
	public List<EmployeeTripInfo> unapprovedTrips;
	public List<EmployeeTripInfo> declinedTrips;

	public EmployeeTripList(List<EmployeeTripInfo> upcomingTrips, List<EmployeeTripInfo> unapprovedTrips, List<EmployeeTripInfo> declinedTrips) {
		this.upcomingTrips = upcomingTrips;
		this.unapprovedTrips = unapprovedTrips;
		this.declinedTrips = declinedTrips;
	}
}
