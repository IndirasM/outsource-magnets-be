package com.psk.demo.Entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "employee")
public class Employee implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "employee_id")
	private Long id;

	@Size(max = 50)
	@Column(name = "full_name")
	private String name;

	@Size(max = 100)
	private String email;

	@Size(max = 50)
	private String password;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "main_office", nullable = false)
	private Office office;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "permission_employee",
			joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "employee_id"),
			inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "permission_id"))
	private List<Permission> permissions;

	//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
	private List<Trip> trips;

	//region getters

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getEmail() {
		return this.email;
	}

	public String getPassword() {
		return this.password;
	}

	public Office getOffice() {
		return this.office;
	}

	public List<Permission> getPermissions() {
		return this.permissions;
	}

	public List<Trip> getTrips() {
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

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public void setTrips(List<Trip> trips) {
		this.trips = trips;
	}

	//endregion

	//region UserDetails

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.permissions;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	//endregion
}