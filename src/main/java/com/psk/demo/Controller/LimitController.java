package com.psk.demo.Controller;

import com.psk.demo.Controller.Model.*;
import com.psk.demo.Entity.Employee;
import com.psk.demo.Entity.LearningDay;
import com.psk.demo.Entity.Limit;
import com.psk.demo.Exception.ResourceNotFoundException;
import com.psk.demo.Security.TokenUtil;
import com.psk.demo.Service.IEmployeeService;
import com.psk.demo.Service.ILimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.psk.demo.Security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/limit")
public class LimitController
{
	@Autowired
	private IEmployeeService employeeService;

	@Autowired
	private ILimitService limitService;

	@Autowired
	private TokenUtil tokenUtil;

	@RequestMapping(method = RequestMethod.GET)
	public LimitModel getLimit(HttpServletRequest request) throws Exception
	{
		//String token = request.getHeader("Authorization").replace(TOKEN_PREFIX, "");
		//Employee employee = employeeService.findByEmail(tokenUtil.getUsernameFromToken(token));
		long id = 1;
		Optional<Employee> employee = employeeService.findById(id);
		if(employee.isPresent()){
			LimitModel result = new LimitModel(employee.get().getLimit());
			result.employeeId = employee.get().getId();
			result.isBoss = employee.get().getTeam() == null;
			return result;
		}
		return null;
	}

	@RequestMapping(value = "/global", method = RequestMethod.GET)
	public GlobalLimitModel getGlobal() throws Exception
	{
		Limit globalLimit = limitService.getGlobal();
		return new GlobalLimitModel(globalLimit);
	}

	@RequestMapping(value = "/staffers", method = RequestMethod.GET)
	public List<StaffersLimits> getStaffersLimits(HttpServletRequest request) throws Exception {
//		String token = request.getHeader("Authorization").replace(TOKEN_PREFIX, "");
//		Employee employee = employeeService.findByEmail(tokenUtil.getUsernameFromToken(token));
		long id = 1;
		Optional<Employee> employee = employeeService.findById(id);

		if (employee.isPresent())
		{
			List<Employee> employees = employeeService.findByManager(employee.get());
			return employees.stream().map(StaffersLimits::new).collect(Collectors.toList());
		}
		return new ArrayList<StaffersLimits>();
	}
}