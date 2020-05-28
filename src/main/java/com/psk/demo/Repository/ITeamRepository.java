package com.psk.demo.Repository;

import com.psk.demo.Entity2.Employee;
import com.psk.demo.Entity2.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITeamRepository extends JpaRepository<Team, Long> {
	List<Team> findByManager(Employee manager);
}
