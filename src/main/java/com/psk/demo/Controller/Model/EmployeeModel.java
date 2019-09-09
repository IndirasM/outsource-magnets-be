package com.psk.demo.Controller.Model;

import com.psk.demo.Entity.Employee;

public class EmployeeModel {
	public Long id;
	public String name;
	public String email;
	public String office;

	public EmployeeModel(Employee employee){
		this.id = employee.getId();
		this.name = employee.getName();
		this.email = employee.getEmail();
		if (employee.getOffice() != null) this.office = employee.getOffice().getName();
	}
}
