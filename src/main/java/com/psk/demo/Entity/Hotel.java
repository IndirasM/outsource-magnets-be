package com.psk.demo.Entity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "hotel")
public class Hotel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hotel_id")
	private Long id;

	@Size(max = 200)
	private String address;

	private Float price;

	//region getters

	public Long getId() {
		return this.id;
	}

	public String getAddress() {
		return this.address;
	}

	public Float getPrice() {
		return this.price;
	}


	//endregion

	//region setters

	public void setId(Long id) {
		this.id = id;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	//endregion
}
