package com.psk.demo.Controller;

import com.psk.demo.Controller.Model.*;
import com.psk.demo.Entity.Employee;
import com.psk.demo.Entity.LearningDay;
import com.psk.demo.Entity.Subject;
import com.psk.demo.Security.TokenUtil;
import com.psk.demo.Service.IEmployeeService;
import com.psk.demo.Service.ILearningDayService;
import com.psk.demo.Service.ISubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;
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
	@Autowired
	private ISubjectService subjectService;


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
	public List<EmployeeLearningDayModel> getStaffersLearningDays(HttpServletRequest request) {
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
	public ResponseEntity<?> newLearningDay(HttpServletRequest request, @RequestBody NewLearningDayModel model) throws Exception {
		String token = request.getHeader("Authorization").replace(TOKEN_PREFIX, "");
		Employee employee = employeeService.findByEmail(tokenUtil.getUsernameFromToken(token));


		ResponseEntity result;
		if (learningDayService.limitValid(employee, model.date)) {
			Long id = learningDayService.Insert(employee, model.subjectId, model.date);
			result = new ResponseEntity(id, HttpStatus.OK);
		} else {
			result = new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		return result;
	}

	@RequestMapping(value = "/addNotes", method = RequestMethod.POST)
	public void addNotes(@RequestBody LearningDayNotesModel model) throws Exception {
		LearningDay ld = learningDayService.findById(model.learningDayId);
		ld.setNotes(model.notes);
		learningDayService.Save(ld);
	}

	@RequestMapping(value = "/bySubjectId/{id:[0-9]+}", method = RequestMethod.GET)
	public List<LearningDayEmployeeModel> getBySubjectId(@PathVariable String id) {
		Long parsedId = Long.parseLong(id, 10);
		Subject subject = subjectService.findById(parsedId).get();

		return learningDayService.findBySubject(subject).stream()
				.map(LearningDayEmployeeModel::new).collect(Collectors.toList());
	}

	@RequestMapping(value = "/edit/{id:[0-9]+}", method = RequestMethod.POST)
	public ResponseEntity<?> edit(@PathVariable String id, @RequestBody LearningDayEditModel model) throws ParseException {
		Long parsedId = Long.parseLong(id, 10);
		LearningDay ld = learningDayService.findById(parsedId);
		Subject subject = subjectService.findById(model.subjectId).get();
		ld.setSubject(subject);
		ld.setDate(model.date);

		ResponseEntity result;
		if (learningDayService.limitValid(ld.getEmployee(), model.date)) {
			learningDayService.Save(ld);
			result = new ResponseEntity(HttpStatus.OK);
		} else {
			result = new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		return result;
	}
}