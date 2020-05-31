package com.psk.demo.Controller;

import com.psk.demo.Controller.Model.EmployeeLearningDayModel;
import com.psk.demo.Controller.Model.LearningDayModel;
import com.psk.demo.Controller.Model.LearningDayNotesModel;
import com.psk.demo.Controller.Model.NewLearningDayModel;
import com.psk.demo.Entity.Employee;
import com.psk.demo.Entity.LearningDay;
import com.psk.demo.Entity.Subject;
import com.psk.demo.Security.TokenUtil;
import com.psk.demo.Service.IEmployeeService;
import com.psk.demo.Service.ILearningDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
		String token = request.getHeader("Authorization").replace(TOKEN_PREFIX, "");
		Employee employee = employeeService.findByEmail(tokenUtil.getUsernameFromToken(token));

		List<LearningDay> learningDays = learningDayService.findByEmployee(employee);
		List<LearningDayModel> result = learningDays.stream()
				.map(LearningDayModel::new)
				.collect(Collectors.toList());

		return result;
	}

	@RequestMapping(value = "/staffers", method = RequestMethod.GET)
	public List<EmployeeLearningDayModel> getStaffersLearningDays(HttpServletRequest request) throws Exception {
		String token = request.getHeader("Authorization").replace(TOKEN_PREFIX, "");
		Employee employee = employeeService.findByEmail(tokenUtil.getUsernameFromToken(token));

		List<LearningDay> learningDays = learningDayService.findByManager(employee);
		List<EmployeeLearningDayModel> result = learningDays.stream()
				.map(EmployeeLearningDayModel::new)
				.collect(Collectors.toList());

		return result;
	}


	@RequestMapping(value = "/{id:[0-9]+}", method = RequestMethod.DELETE)
	public void delete(@PathVariable String id)
	{
		Long parsedId = Long.parseLong(id, 10);
		learningDayService.delete(parsedId);
	}

	@RequestMapping(method = RequestMethod.POST)
	public Long newLearningDay(HttpServletRequest request, @RequestBody NewLearningDayModel model) throws Exception {
		String token = request.getHeader("Authorization").replace(TOKEN_PREFIX, "");
		Employee employee = employeeService.findByEmail(tokenUtil.getUsernameFromToken(token));
		return learningDayService.Insert(employee, model.subjectId, model.date);
	}

	@RequestMapping(value = "/addNotes", method = RequestMethod.POST)
	public void addNotes(@RequestBody LearningDayNotesModel model) throws Exception {
		LearningDay ld = learningDayService.findById(model.learningDayId);
		ld.setNotes(model.notes);
		learningDayService.Save(ld);
	}
}