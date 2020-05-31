package com.psk.demo.Controller;

import com.psk.demo.Controller.Model.SubjectModel;
import com.psk.demo.Entity.Employee;
import com.psk.demo.Entity.Subject;
import com.psk.demo.Exception.ResourceNotFoundException;
import com.psk.demo.Service.ISubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/subject")
public class SubjectController
{
	@Autowired
	private ISubjectService subjectService;

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
}