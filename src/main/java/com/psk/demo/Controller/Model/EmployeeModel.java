package com.psk.demo.Controller.Model;

import com.psk.demo.Entity.Employee;
import com.psk.demo.Entity.Office;
import com.psk.demo.Entity.Permission;

import java.util.Set;

public class EmployeeModel {
	private Long id;
	private String name;
	private String email;
	private String office;

	public EmployeeModel(Employee employee){
		this.id = employee.getId();
		this.name = employee.getName();
		this.email = employee.getEmail();
		if (employee.getOffice() != null) this.office = employee.getOffice().getName();
	}
}
