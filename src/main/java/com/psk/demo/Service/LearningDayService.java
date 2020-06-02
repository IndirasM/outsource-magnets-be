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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;
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

    @Override
    public List<LearningDay> findBySubject(Subject subject) {
        String formattedDate = DateHelper.formatDate(new Date());
        return learningDayRepository.findBySubjectAndDateLessThan(subject, formattedDate);
    }

    public boolean limitValid(Employee employee, String formattedDate) throws ParseException {
        boolean pastDayValid = pastDayValid(formattedDate);
        boolean sameDayValid = sameDayValid(employee, formattedDate);
        boolean rowValid = rowLimitValid(employee, formattedDate);
        boolean monthValid = monthLimitValid(employee, formattedDate);
        boolean yearValid = yearLimitValid(employee, formattedDate);

        return pastDayValid && sameDayValid && rowValid && monthValid && yearValid;
    }

    private boolean pastDayValid(String formattedDate) {
        String today = DateHelper.formatDate(new Date());
        return today.compareTo(formattedDate) < 0;
    }

    private boolean sameDayValid(Employee employee, String formattedDate) {
        List<LearningDay> lds = learningDayRepository.findByDateAndEmployee(formattedDate, employee);
        return lds.isEmpty();
    }

    private boolean rowLimitValid(Employee employee, String formattedDate) throws ParseException {
        int rowLimit = employee.getLimit().getDaysInRow();
        Date date = DateHelper.getDate(formattedDate);

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int i;
        for (i = 0; i < rowLimit; i++) {
            c.add(Calendar.DATE, -1);
            String d = DateHelper.formatDate(c.getTime());
            List<LearningDay> ld = learningDayRepository.findByDateAndEmployee(d, employee);
            if (ld.size() == 0){
                break;
            }
        }

        if (i == rowLimit)
            return false;

        c.setTime(date);
        for (; i < rowLimit; i++) {
            c.add(Calendar.DATE, 1);
            String d = DateHelper.formatDate(c.getTime());
            List<LearningDay> ld = learningDayRepository.findByDateAndEmployee(d, employee);
            if (ld.size() == 0){
                break;
            }
        }

        return i != rowLimit;
    }

    private boolean monthLimitValid(Employee employee, String formattedDate) throws ParseException {
        int monthLimit = employee.getLimit().getDaysInMonth();
        Date date = DateHelper.getDate(formattedDate);

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        String firstDayDate = DateHelper.formatDate(c.getTime());

        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        String lastDayDate = DateHelper.formatDate(c.getTime());

        List<LearningDay> lds = learningDayRepository
                .findByEmployeeAndDateGreaterThanEqualAndDateLessThan(employee, firstDayDate, lastDayDate);

        return lds.size() < monthLimit;
    }

    private boolean yearLimitValid(Employee employee, String formattedDate) throws ParseException {
        int yearLimit = employee.getLimit().getDaysInYear();
        Date date = DateHelper.getDate(formattedDate);

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);
        String firstDayDate = DateHelper.formatDate(c.getTime());

        c.add(Calendar.YEAR, 1);
        String lastDayDate = DateHelper.formatDate(c.getTime());

        List<LearningDay> lds = learningDayRepository
                .findByEmployeeAndDateGreaterThanEqualAndDateLessThan(employee, firstDayDate, lastDayDate);

        return lds.size() < yearLimit;
    }
}
