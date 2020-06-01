package com.psk.demo.Controller;

import com.psk.demo.Controller.Model.TeamModel;
import com.psk.demo.Service.IEmployeeSubjectService;
import com.psk.demo.Service.ITeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/team")
public class TeamController
{
	@Autowired
	private ITeamService teamService;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<TeamModel> getAllTeams()
	{
		return teamService.findAll().stream().map(TeamModel::new).collect(Collectors.toList());
	}
}