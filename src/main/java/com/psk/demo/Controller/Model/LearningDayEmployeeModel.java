package com.psk.demo.Controller.Model;

import com.psk.demo.Entity.LearningDay;
import com.psk.demo.Entity.Team;

public class LearningDayEmployeeModel {
    public Long learningDayId;
    public String date;
    public String employeeName;
    public String notes;
    public Long teamId;

    public LearningDayEmployeeModel(LearningDay ld) {
        this.learningDayId = ld.getId();
        this.date = ld.getDate();
        this.employeeName = ld.getEmployee().getName();
        this.notes = ld.getNotes();
        Team team = ld.getEmployee().getTeam();
        if (team != null) {
            this.teamId = team.getId();
        }
    }
}
