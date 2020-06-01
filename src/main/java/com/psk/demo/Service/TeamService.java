package com.psk.demo.Service;

import com.psk.demo.Entity.Employee;
import com.psk.demo.Entity.Team;
import com.psk.demo.Repository.ITeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService implements ITeamService {
	@Autowired
	private ITeamRepository teamRepository;

	public TeamService(ITeamRepository teamRepository) {
		this.teamRepository = teamRepository;
	}

	@Override
	public List<Team> findAll() {
		return teamRepository.findAll();
	}
}
