package com.psk.demo.Controller.Model;

import com.psk.demo.Entity.LearningDay;

public class LearningDayModel {
    public Long learningDayId;
    public String title;
    public String date;
    public Long subjectId;
    public String notes;

    public LearningDayModel(LearningDay ld) {
        this.learningDayId = ld.getId();
        this.title = ld.getSubject().getName();
        this.date = ld.getDate();
        this.subjectId = ld.getSubject().getId();
        this.notes = ld.getNotes();
    }
}
