package com.psk.demo.Controller;

import com.psk.demo.Controller.Model.EmployeeInfo;
import com.psk.demo.Controller.Model.EmployeeModel;
import com.psk.demo.Exception.ResourceNotFoundException;
import com.psk.demo.Security.TokenUtil;
import com.psk.demo.Service.IEmployeeService;
import com.psk.demo.Entity2.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.psk.demo.Security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController
{
	@Autowired
	private IEmployeeService employeeService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private TokenUtil tokenUtil;

	@RequestMapping("/test/{string}")
	public String test(@PathVariable String string)
	{
		return passwordEncoder.encode(string);
	}

	@RequestMapping("/{id:[0-9]+}")
	public Employee getEmployee(@PathVariable String id)
	{
		Long parsedId = Long.parseLong(id, 10);
		return employeeService.findById(parsedId).orElseThrow(ResourceNotFoundException::new);
	}

	@RequestMapping("/by-email/{email}")
	public UserDetails getEmployeeByUsername(@PathVariable String email)
	{
		return employeeService.loadUserByUsername(email);
	}

	@RequestMapping(value = "/search/{fragment}", method = RequestMethod.GET)
	public List<EmployeeInfo> getEmployeesBySearch(@PathVariable String fragment)
	{
		List<Employee> employees = employeeService.findByNameStartingWith(fragment);
		List<EmployeeInfo> response = new ArrayList<>();
		employees.forEach(e -> {
			response.add(new EmployeeInfo(e.getId(), e.getName(), e.getEmail()));
		});

		return response;
	}

	@RequestMapping(method = RequestMethod.GET)
	public EmployeeModel getDetails(HttpServletRequest request) throws Exception {
		String token = request.getHeader("Authorization").replace(TOKEN_PREFIX, "");
		Employee employee = employeeService.findByEmail(tokenUtil.getUsernameFromToken(token));

		return new EmployeeModel(employee);
	}
}