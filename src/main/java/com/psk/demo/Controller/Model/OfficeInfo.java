package com.psk.demo.Controller.Model;

import com.psk.demo.Entity.Office;

public class OfficeInfo {
	public Long id;
	public String name;
	public String address;

	public OfficeInfo(Office office) {
		this.id = office.getId();
		this.name = office.getName();
		this.address = office.getAddress();
	}
}
