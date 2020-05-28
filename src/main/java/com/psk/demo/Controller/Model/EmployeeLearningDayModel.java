package com.psk.demo.Controller.Model;

import com.psk.demo.Entity2.LearningDay;

public class EmployeeLearningDay {
    public long employeeId;
    public String employeeName;
    public String title;
    public String date;

    public EmployeeLearningDay(LearningDay ld) {
        this.employeeId = ld.getEmployee().getId();
        this.employeeName = ld.getEmployee().getName();
        this.title = ld.getSubject().getName();
        this.date = ld.getDate();
    }
}
