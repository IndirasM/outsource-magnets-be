package com.psk.demo.Controller.Model;

import com.psk.demo.Entity2.Subject;

public class SubjectModel {
    public String name;
    public Long id;
    public Long parentId;

    public SubjectModel(Subject subject) {
        this.name = subject.getName();
        this.id = subject.getId();
        if(subject.getParentSubject() != null) {
            this.parentId = subject.getParentSubject().getId();
        }
    }
}
