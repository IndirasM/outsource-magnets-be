package com.psk.demo.Controller;

import com.psk.demo.Controller.Model.SubjectModel;
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
	public List<SubjectModel> getAllSubject()
	{
		return subjectService.findAll().stream().map(SubjectModel::new).collect(Collectors.toList());
	}

	@RequestMapping(value = "/{id:[0-9]+}", method = RequestMethod.GET)
	public List<SubjectModel> getSubjectsById(@PathVariable String id)
	{
		return subjectService.findAll().stream().map(SubjectModel::new).collect(Collectors.toList());
	}
}