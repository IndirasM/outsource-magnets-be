package com.psk.demo.Entity;

import javax.persistence.*;

@Entity
@Table(name = "employee_trip")
public class Trip {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "employee_trip_id")
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "trip_id")
	private TripDescription tripDescription;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "employee_id")
	private Employee employee;

	@Column(name = "is_approved", nullable = true)
	private Boolean isApproved;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "hotel_id", nullable = true)
	private Hotel hotel;


	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "apartment_id", nullable = true)
	private Apartment apartment;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ticket_id", nullable = true)
	private Ticket ticket;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "transport_id", nullable = true)
	private Transport transport;

	//region getters

	public Long getId() {
		return this.id;
	}

	public TripDescription getTripDescription() {
		return this.tripDescription;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public Boolean getIsApproved() {
		return this.isApproved;
	}

	public Hotel getHotel() {
		return this.hotel;
	}

	public Apartment getApartment() {
		return this.apartment;
	}

	public Ticket getTicket() {
		return this.ticket;
	}

	public Transport getTransport() {
		return this.transport;
	}

	//endregion

	//region setters

	public void setId(Long id) {
		this.id = id;
	}

	public void setTripDescription(TripDescription tripDescription) {
		this.tripDescription = tripDescription;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public void setTransport(Transport transport) {
		this.transport = transport;
	}

	//endregion
}
