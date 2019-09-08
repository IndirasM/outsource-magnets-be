package com.psk.demo.Entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "permission")
public class Permission implements GrantedAuthority {
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

	//region GrantedAuthority

	@Override
	public String getAuthority() {
		return this.name;
	}

	//endregion
}
