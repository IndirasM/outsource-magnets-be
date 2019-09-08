package com.psk.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "trip")
public class TripDescription {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "trip_id")
	private Long id;

	@Size(max = 50)
	private String name;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "created_by", nullable = false)
	private Employee createdBy;

	@Size(max = 50)
	@Column(name = "date_from")
	private String dateFrom;

	@Size(max = 50)
	@Column(name = "date_to")
	private String dateTo;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "office_id", nullable = false)
	private Office destination;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "checklist_id", nullable = true)
	private Checklist checklist;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "employee_trip",
			joinColumns = @JoinColumn(name = "trip_id", referencedColumnName = "trip_id"),
			inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "employee_id"))
	private Set<Employee> employees;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@OneToMany(mappedBy = "tripDescription", fetch = FetchType.LAZY)
	private Set<Trip> trips;

	//region getters

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public Employee getCreatedBy() {
		return this.createdBy;
	}

	public String getDateFrom() {
		return this.dateFrom;
	}

	public String getDateTo() {
		return this.dateTo;
	}

	public Office getDestination() {
		return this.destination;
	}

	public Checklist getChecklist() {
		return this.checklist;
	}

	public Set<Employee> getEmployees() {
		return this.employees;
	}

	public Set<Trip> getTrips() {
		return this.trips;
	}

	//endregion

	//region setters

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCreatedBy(Employee createdBy) {
		this.createdBy = createdBy;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public void setDestination(Office destination) {
		this.destination = destination;
	}

	public void setChecklist(Checklist checklist) {
		this.checklist = checklist;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}

	public void setTrips(Set<Trip> trips) {
		this.trips = trips;
	}

	//endregion
}
