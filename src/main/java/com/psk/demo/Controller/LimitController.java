package com.psk.demo.Controller;

import com.psk.demo.Controller.Model.*;
import com.psk.demo.Entity.Employee;
import com.psk.demo.Entity.Limit;
import com.psk.demo.Security.TokenUtil;
import com.psk.demo.Service.IEmployeeService;
import com.psk.demo.Service.ILimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
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
	public EmployeeLimitModel getLimit(HttpServletRequest request) throws Exception
	{
		String token = request.getHeader("Authorization").replace(TOKEN_PREFIX, "");
		Employee employee = employeeService.findByEmail(tokenUtil.getUsernameFromToken(token));

		EmployeeLimitModel result = new EmployeeLimitModel(employee.getLimit());
		result.employeeId = employee.getId();
		result.isBoss = employee.getTeam() == null;
		return result;
	}

	@RequestMapping(value = "/global", method = RequestMethod.GET)
	public GlobalLimitModel getGlobal() throws Exception
	{
		Limit globalLimit = limitService.getGlobal();
		return new GlobalLimitModel(globalLimit);
	}

	@RequestMapping(value = "/staffers", method = RequestMethod.GET)
	public List<StaffersLimits> getStaffersLimits(HttpServletRequest request) throws Exception {
		String token = request.getHeader("Authorization").replace(TOKEN_PREFIX, "");
		Employee employee = employeeService.findByEmail(tokenUtil.getUsernameFromToken(token));

		List<Employee> employees = employeeService.findByManager(employee);
		return employees.stream().map(StaffersLimits::new).collect(Collectors.toList());
	}

	@RequestMapping(value = "/setGlobal", method = RequestMethod.POST)
	public ResponseEntity<?> setGlobalLimit(HttpServletRequest request, @RequestBody LimitModel model)
	{
		String token = request.getHeader("Authorization").replace(TOKEN_PREFIX, "");
		Employee employee = employeeService.findByEmail(tokenUtil.getUsernameFromToken(token));
		if (employee.getTeam() != null)
		{
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		}

		Limit newGlobalLimit = new Limit(model.yearLimit, model.monthLimit, model.rowLimit);
		newGlobalLimit.setIsGlobal(true);
		Limit globalLimit = limitService.setGlobal(newGlobalLimit);
		LimitModel returnModel = new LimitModel(globalLimit);
		return new ResponseEntity<>(returnModel, HttpStatus.OK);
	}

	@RequestMapping(value = "/set", method = RequestMethod.POST)
	public ResponseEntity<?> setLimit(HttpServletRequest request, @RequestBody SetLimitModel model)
	{
		String token = request.getHeader("Authorization").replace(TOKEN_PREFIX, "");
		Employee manager = employeeService.findByEmail(tokenUtil.getUsernameFromToken(token));
		Employee employee = employeeService.findById(model.employeeId).get();

		boolean bossSettingOwn = manager.getTeam() == null && manager.getId() == model.employeeId;
		boolean settingByManager =  employee.getTeam().getManager().getId() == manager.getId();

		if (!(bossSettingOwn || settingByManager))
		{
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		}

		Limit limit = limitService.setLimit(employee, model.yearLimit, model.monthLimit, model.rowLimit);
		LimitModel returnModel = new LimitModel(limit);
		return new ResponseEntity<>(returnModel, HttpStatus.OK);
	}

	@RequestMapping(value = "/setBulk", method = RequestMethod.POST)
	public ResponseEntity<?> setBulkLimit(HttpServletRequest request, @RequestBody LimitModel model)
	{
		String token = request.getHeader("Authorization").replace(TOKEN_PREFIX, "");
		Employee manager = employeeService.findByEmail(tokenUtil.getUsernameFromToken(token));
		List<Employee> employees = employeeService.findByManager(manager);

		Limit limit = limitService.setBulkLimit(employees, model.yearLimit, model.monthLimit, model.rowLimit);
		LimitModel returnModel = new LimitModel(limit);
		return new ResponseEntity<>(returnModel, HttpStatus.OK);
	}
}