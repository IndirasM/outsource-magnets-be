package com.psk.demo.Service;

import com.psk.demo.Entity.Employee;
import com.psk.demo.Entity.LearningDay;
import com.psk.demo.Entity.Team;
import com.psk.demo.Repository.IEmployeeRepository;
import com.psk.demo.Repository.ILearningDayRepository;
import com.psk.demo.Repository.ITeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LearningDayService implements ILearningDayService {

    @Autowired
    private ILearningDayRepository learningDayRepository;
    @Autowired
    private IEmployeeRepository employeeRepository;
    @Autowired
    private ITeamRepository teamRepository;

    public LearningDayService(ILearningDayRepository learningDayRepository, IEmployeeRepository employeeRepository, ITeamRepository teamRepository) {
        this.learningDayRepository = learningDayRepository;
        this.employeeRepository = employeeRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public List<LearningDay> findByEmployee(Employee employee) {
        return learningDayRepository.findByEmployee(employee);
    }

    @Override
    public List<LearningDay> findByManager(Employee manager) {
        List<Team> teams = teamRepository.findByManager(manager);
        List<Employee> employees = new ArrayList<>();
        for (Team team : teams) {
            employees.addAll(employeeRepository.findByTeam(team));
        }
        return learningDayRepository.findByEmployeeIn(employees);
    }

    @Override
    public void delete(Long id) {
        learningDayRepository.deleteById(id);
    }
}
