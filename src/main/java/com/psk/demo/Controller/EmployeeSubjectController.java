package com.psk.demo.Controller;

import com.psk.demo.Controller.Model.AddSuggestedSubjectModel;
import com.psk.demo.Service.IEmployeeSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employeeSubject")
public class EmployeeSubjectController
{
	@Autowired
	private IEmployeeSubjectService employeeSubjectService;

	@RequestMapping(value = "/add/{id:[0-9]+}", method = RequestMethod.POST)
	public void addSuggestedSubject(@PathVariable String id, @RequestBody AddSuggestedSubjectModel model)
	{
		Long parsedId = Long.parseLong(id, 10);
		employeeSubjectService.addEmployeeSubject(parsedId, model.employeeIds);
	}
}