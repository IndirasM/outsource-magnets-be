package com.psk.demo.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "permission")
public class Permission {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "permission_id")
	private Long id;

	@Size(max = 50)
	private String name;

	//region getters

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	//endregion

	//region setters

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	//endregion
}
