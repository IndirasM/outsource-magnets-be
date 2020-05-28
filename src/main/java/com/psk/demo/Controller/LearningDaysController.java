package com.psk.demo.Controller;

import com.psk.demo.Controller.Model.EmployeeLearningDayModel;
import com.psk.demo.Controller.Model.LearningDayModel;
import com.psk.demo.Entity2.Employee;
import com.psk.demo.Entity2.LearningDay;
import com.psk.demo.Security.TokenUtil;
import com.psk.demo.Service.IEmployeeService;
import com.psk.demo.Service.ILearningDayService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/learningDays")
public class LearningDaysController
{
	@Autowired
	private IEmployeeService employeeService;

	@Autowired
	private ILearningDayService learningDayService;

	@Autowired
	private TokenUtil tokenUtil;

	@RequestMapping(method = RequestMethod.GET)
	public List<LearningDayModel> getUserLearningDays(HttpServletRequest request) throws Exception {
//		String token = request.getHeader("Authorization").replace(TOKEN_PREFIX, "");
//		Employee employee = employeeService.findByEmail(tokenUtil.getUsernameFromToken(token));
		long id = 1;
		Optional<Employee> employee = employeeService.findById(id);

		if (employee.isPresent())
		{
			List<LearningDay> learningDays = learningDayService.findByEmployee(employee.get());
			List<LearningDayModel> result = learningDays.stream()
					.map(LearningDayModel::new)
					.collect(Collectors.toList());

			return result;
		}
		return new ArrayList<LearningDayModel>();
	}

	@RequestMapping(value = "/staffers", method = RequestMethod.GET)
	public List<EmployeeLearningDayModel> getStaffersLearningDays(HttpServletRequest request) throws Exception {
//		String token = request.getHeader("Authorization").replace(TOKEN_PREFIX, "");
//		Employee employee = employeeService.findByEmail(tokenUtil.getUsernameFromToken(token));
		long id = 1;
		Optional<Employee> employee = employeeService.findById(id);

		if (employee.isPresent())
		{
			List<LearningDay> learningDays = learningDayService.findByManager(employee.get());
			List<EmployeeLearningDayModel> result = learningDays.stream()
					.map(EmployeeLearningDayModel::new)
					.collect(Collectors.toList());

			return result;
		}
		return new ArrayList<EmployeeLearningDayModel>();
	}
}