package com.psk.demo.Service;

import com.psk.demo.Entity.Employee;
import com.psk.demo.Entity.LearningDay;
import com.psk.demo.Entity.Subject;

import java.text.ParseException;
import java.util.List;

public interface ILearningDayService {
    List<LearningDay> findByEmployee(Employee employee);
    List<LearningDay> findByManager(Employee manager);
    void delete(Long id);
    Long Insert(Employee employee, Long subjectId, String date) throws Exception;
    void Save(LearningDay learningDay);
    LearningDay findById(Long id);
    List<LearningDay> findAllLearnedByTeamId(Long id);
    List<LearningDay> findAllToLearnByTeamId(Long id);
    boolean limitValid(Employee employee, String formattedDate) throws ParseException;
    List<LearningDay> findBySubject(Subject subject);
}
