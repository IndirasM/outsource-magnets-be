package com.psk.demo.Controller.Model;

import java.io.Serializable;
import java.util.List;

public class PermissionResponse implements Serializable {
	private List<String> permissions;

	public PermissionResponse(List<String> permissions) {
		this.permissions = permissions;
	}

	public List<String> getPermissions() {
		return this.permissions;
	}
}