package com.psk.demo.Controller;

import com.psk.demo.Exception.ResourceNotFoundException;
import com.psk.demo.Model.Hello;
import com.psk.demo.Service.IEmployeeService;
import com.psk.demo.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController
{
	@Autowired
	private IEmployeeService employeeService;

	@RequestMapping("/employee/{id:[0-9]+}")
	public Employee getEmployee(@PathVariable String id)
	{
		Long parsedId = Long.parseLong(id, 10);
		return employeeService.findById(parsedId).orElseThrow(ResourceNotFoundException::new);
		// return new Hello();
	}
}