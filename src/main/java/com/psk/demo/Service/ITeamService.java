package com.psk.demo.Service;

import com.psk.demo.Entity2.Employee;
import com.psk.demo.Entity2.Team;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface ITeamService {
	List<Team> findByTeamManager(Employee manager);
}
