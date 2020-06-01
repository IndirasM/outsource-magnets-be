package com.psk.demo.Service;

import com.psk.demo.Entity.Employee;
import com.psk.demo.Entity.LearningDay;
import com.psk.demo.Entity.Subject;
import com.psk.demo.Entity.Team;
import com.psk.demo.Helper.DateHelper;
import com.psk.demo.Repository.IEmployeeRepository;
import com.psk.demo.Repository.ILearningDayRepository;
import com.psk.demo.Repository.ISubjectRepository;
import com.psk.demo.Repository.ITeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LearningDayService implements ILearningDayService {

    @Autowired
    private ILearningDayRepository learningDayRepository;
    @Autowired
    private IEmployeeRepository employeeRepository;
    @Autowired
    private ITeamRepository teamRepository;
    @Autowired
    private ISubjectRepository subjectRepository;

    public LearningDayService(ILearningDayRepository learningDayRepository, IEmployeeRepository employeeRepository, ITeamRepository teamRepository, ISubjectRepository subjectRepository) {
        this.learningDayRepository = learningDayRepository;
        this.employeeRepository = employeeRepository;
        this.teamRepository = teamRepository;
        this.subjectRepository = subjectRepository;
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

    @Override
    public Long Insert(Employee employee, Long subjectId, String date) throws Exception {
        Optional<Subject> subject = subjectRepository.findById(subjectId);
        String formattedCurrentDate = DateHelper.formatDate(new Date());
        LearningDay newLearningDay = new LearningDay(subject.get(), employee, date, null, formattedCurrentDate);
        LearningDay createdLearningDay = learningDayRepository.save(newLearningDay);
        return createdLearningDay.getId();
    }

    @Override
    public void Save(LearningDay learningDay) {
        learningDayRepository.save(learningDay);
    }

    @Override
    public LearningDay findById(Long id) {
        return learningDayRepository.findById(id).get();
    }

    @Override
    public List<LearningDay> findAllLearnedByTeamId(Long id) {
        Team team = teamRepository.findById(id).get();
        List<Employee> employees = employeeRepository.findByTeam(team);
        List<LearningDay> learningDays = learningDayRepository.findByEmployeeIn(employees);
        String formattedDate = DateHelper.formatDate(new Date());
        List<LearningDay> filteredDays = learningDays.stream()
                .filter(d -> formattedDate.compareTo(d.getDate()) > 0)
                .collect(Collectors.toList());
        return filteredDays;
    }

    @Override
    public List<LearningDay> findAllToLearnByTeamId(Long id) {
        Team team = teamRepository.findById(id).get();
        List<Employee> employees = employeeRepository.findByTeam(team);
        List<LearningDay> learningDays = learningDayRepository.findByEmployeeIn(employees);
        String formattedDate = DateHelper.formatDate(new Date());
        List<LearningDay> filteredDays = learningDays.stream()
                .filter(d -> formattedDate.compareTo(d.getDate()) <= 0)
                .collect(Collectors.toList());
        return filteredDays;
    }
}
