package com.psk.demo.Controller;

import com.psk.demo.Controller.Model.EmployeeSubjectModel;
import com.psk.demo.Controller.Model.NewSubjectModel;
import com.psk.demo.Controller.Model.SubjectModel;
import com.psk.demo.Controller.Model.SubjectsByTeamModel;
import com.psk.demo.Entity.Employee;
import com.psk.demo.Entity.LearningDay;
import com.psk.demo.Entity.Subject;
import com.psk.demo.Exception.ResourceNotFoundException;
import com.psk.demo.Repository.IEmployeeSubjectRepository;
import com.psk.demo.Security.TokenUtil;
import com.psk.demo.Service.IEmployeeService;
import com.psk.demo.Service.ILearningDayService;
import com.psk.demo.Service.ISubjectService;
import com.psk.demo.Service.IEmployeeSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.psk.demo.Security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/subject")
public class SubjectController
{
	@Autowired
	private ISubjectService subjectService;
	@Autowired
	private ILearningDayService learningDayService;
	@Autowired
	private IEmployeeSubjectService employeeSubjectService;
	@Autowired
	private IEmployeeService employeeService;
	@Autowired
	private TokenUtil tokenUtil;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<SubjectModel> getAllSubjects()
	{
		return subjectService.findAll().stream().map(SubjectModel::new).collect(Collectors.toList());
	}

	@RequestMapping(value = "/{id:[0-9]+}", method = RequestMethod.GET)
	public SubjectModel getSubjectById(@PathVariable String id)
	{
		Long parsedId = Long.parseLong(id, 10);
		Subject subject = subjectService.findById(parsedId).orElseThrow(ResourceNotFoundException::new);
		return new SubjectModel(subject);
	}

	@RequestMapping(value = "/learned/{id:[0-9]+}", method = RequestMethod.GET)
	public List<SubjectsByTeamModel> getLearned(@PathVariable String id)
	{
		Long parsedId = Long.parseLong(id, 10);
		List<LearningDay> learningDays = learningDayService.findAllLearnedByTeamId(parsedId);
		List<Subject> subjects = learningDays.stream().map(ld -> ld.getSubject()).collect(Collectors.toList());
		List<Subject> uniqueSubjects = new ArrayList<Subject>(new HashSet<Subject>(subjects));
		List<SubjectsByTeamModel> result = uniqueSubjects.stream().map(subject -> {
			List<LearningDay> lds = learningDays.stream().filter(ld -> ld.getSubject() == subject ).collect(Collectors.toList());
			List<String> employeeNames = lds.stream().map(ld -> ld.getEmployee().getName()).collect(Collectors.toList());
			employeeNames = new ArrayList<String>(new HashSet<String>(employeeNames));
			return new SubjectsByTeamModel(subject.getId(), subject.getName(), employeeNames);
		}).collect(Collectors.toList());
		return result;
	}

	@RequestMapping(value = "/toLearn/{id:[0-9]+}", method = RequestMethod.GET)
	public List<SubjectsByTeamModel> getToLearn(@PathVariable String id)
	{
		Long parsedId = Long.parseLong(id, 10);
		List<LearningDay> learningDays = learningDayService.findAllToLearnByTeamId(parsedId);
		List<Subject> subjects = learningDays.stream().map(ld -> ld.getSubject()).collect(Collectors.toList());
		List<Subject> uniqueSubjects = new ArrayList<Subject>(new HashSet<Subject>(subjects));
		List<SubjectsByTeamModel> result = uniqueSubjects.stream().map(subject -> {
			List<LearningDay> lds = learningDays.stream().filter(ld -> ld.getSubject() == subject ).collect(Collectors.toList());
			List<String> employeeNames = lds.stream().map(ld -> ld.getEmployee().getName()).collect(Collectors.toList());
			employeeNames = new ArrayList<String>(new HashSet<String>(employeeNames));
			return new SubjectsByTeamModel(subject.getId(), subject.getName(), employeeNames);
		}).collect(Collectors.toList());
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public void createSubject(@RequestBody NewSubjectModel subject) throws Exception {
		subjectService.Insert(subject);
	}

	@RequestMapping(value = "/allSuggestedSubjects", method = RequestMethod.GET)
	public List<EmployeeSubjectModel> getAllSuggestedSubjects(HttpServletRequest request)
	{
		String token = request.getHeader("Authorization").replace(TOKEN_PREFIX, "");
		Employee employee = employeeService.findByEmail(tokenUtil.getUsernameFromToken(token));
		return employeeSubjectService.findByEmployeeId(employee).stream()
				.map(es -> new EmployeeSubjectModel(es.getSubject())).collect(Collectors.toList());
	}
}