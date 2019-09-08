package com.psk.demo.Entity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "apartment")
public class Apartment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "apartment_id")
	private Long id;

	@Size(max = 200)
	private String address;

	private int capacity;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "office_id")
	private Office office;

//	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//	@OneToMany(mappedBy = "apartmentAccommodation", fetch = FetchType.LAZY)
//	private Set<Trip> trips;

	//region getters

	public Long getId() {
		return this.id;
	}

	public String getAddress() {
		return this.address;
	}

	public int getCapacity() {
		return this.capacity;
	}

	public Office getOffice() {
		return this.office;
	}


	//endregion

	//region setters

	public void setId(Long id) {
		this.id = id;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	//endregion
}
