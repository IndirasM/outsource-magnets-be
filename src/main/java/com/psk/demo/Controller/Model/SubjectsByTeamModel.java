package com.psk.demo.Controller.Model;

import java.util.List;

public class SubjectsByTeamModel {
    public Long subjectId;
    public String subjectName;
    public List<String> employees;

    public SubjectsByTeamModel(Long subjectId, String subjectName, List<String> employees){
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.employees = employees;
    }
}
