package com.psk.demo.Entity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "office")
public class Office {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "office_id")
	private Long id;

	@Size(max = 50)
	private String name;

	@Size(max = 200)
	private String address;

	//region getters

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getAddress() {
		return this.address;
	}

	//endregion

	//region setters

	public void setId(Long id) {
		this.id = id;
	}

	public void getName(String name) {
		this.name = name;
	}

	public void getAddress(String address) {
		this.address = address;
	}

	//endregion
}
