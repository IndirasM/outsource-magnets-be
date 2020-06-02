package com.psk.demo.Controller.Model;

import com.psk.demo.Entity.Subject;

public class EmployeeSubjectModel {
    public Long subjectId;
    public String subjectName;

    public EmployeeSubjectModel(Subject subject) {
        this.subjectId = subject.getId();
        this.subjectName = subject.getName();
    }
}
