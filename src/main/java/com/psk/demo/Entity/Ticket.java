package com.psk.demo.Entity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "ticket")
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ticket_id")
	private Long id;

	@Size(max = 500)
	private String description;

	private Float price;

	//region getters

	public Long getId() {
		return this.id;
	}

	public String getDescription() {
		return this.description;
	}


	public Float getPrice() {
		return this.price;
	}


	//endregion

	//region setters

	public void setId(Long id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	//endregion
}
