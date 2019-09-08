package com.psk.demo.Controller;

import com.psk.demo.Exception.ResourceNotFoundException;
import com.psk.demo.Service.IEmployeeService;
import com.psk.demo.Entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController
{
	@Autowired
	private IEmployeeService employeeService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping("/test/{string}")
	public String test(@PathVariable String string)
	{
		return passwordEncoder.encode(string);
	}

	@RequestMapping("/employee/{id:[0-9]+}")
	public Employee getEmployee(@PathVariable String id)
	{
		Long parsedId = Long.parseLong(id, 10);
		return employeeService.findById(parsedId).orElseThrow(ResourceNotFoundException::new);
	}

	@RequestMapping("/employee-by-email/{email}")
	public UserDetails getEmployeeByUsername(@PathVariable String email)
	{
		return employeeService.loadUserByUsername(email);
	}
}