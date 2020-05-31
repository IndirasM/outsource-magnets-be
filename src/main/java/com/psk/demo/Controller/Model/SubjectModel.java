package com.psk.demo.Controller.Model;

import com.psk.demo.Entity.Subject;

public class SubjectModel {
    public String name;
    public Long id;
    public Long parentId;
    public String description;

    public SubjectModel(Subject subject) {
        this.name = subject.getName();
        this.id = subject.getId();
        this.description = subject.getDescription();
        if(subject.getParentSubject() != null) {
            this.parentId = subject.getParentSubject().getId();
        }
    }
}
