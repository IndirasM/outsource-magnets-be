package com.psk.demo.Controller.Model;

public class OverviewEmployeeModel {
	public Long id;
	public String name;
	public String email;
	public String status;

	public OverviewEmployeeModel(Long id, String name, String email, String status) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.status = status;
	}
}
