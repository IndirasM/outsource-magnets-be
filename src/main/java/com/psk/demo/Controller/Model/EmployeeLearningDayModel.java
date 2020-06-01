package com.psk.demo.Controller.Model;

import com.psk.demo.Entity.LearningDay;

public class EmployeeLearningDayModel {
    public Long employeeId;
    public String employeeName;
    public String title;
    public String date;
    public Long subjectId;
    public Long learningDayId;
    public String notes;

    public EmployeeLearningDayModel(LearningDay ld) {
        this.employeeId = ld.getEmployee().getId();
        this.employeeName = ld.getEmployee().getName();
        this.title = ld.getSubject().getName();
        this.date = ld.getDate();
        this.subjectId = ld.getSubject().getId();
        this.learningDayId = ld.getId();
        this.notes = ld.getNotes();
    }
}
