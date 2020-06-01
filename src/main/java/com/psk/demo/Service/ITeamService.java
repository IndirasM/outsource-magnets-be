package com.psk.demo.Service;

import com.psk.demo.Entity.Employee;
import com.psk.demo.Entity.Team;

import java.util.List;
import java.util.Optional;

public interface ITeamService {
	List<Team> findByTeamManager(Employee manager);
	Optional<Team> findById(Long id);
}
