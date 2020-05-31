package com.psk.demo.Entity;

import javax.persistence.*;

@Entity
@Table(name = "learning_day")
public class LearningDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "learning_day_id")
    private Long learningDayId;

    @ManyToOne
    @JoinColumn(name="subject_id")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name="employee_id")
    private Employee employee;

    private String date;

    private String notes;

    private String created;

    public LearningDay() {}

    public LearningDay(Subject subject, Employee employee, String date, String notes, String created)
    {
        this.subject = subject;
        this.employee = employee;
        this.date = date;
        this.notes = notes;
        this.created = created;
    }

    public Long getId() {
        return this.learningDayId;
    }
    public Subject getSubject() {
        return this.subject;
    }
    public Employee getEmployee() {
        return this.employee;
    }
    public String getDate() {
        return this.date;
    }
    public String getNotes() {
        return this.notes;
    }
    public String getCreated() {
        return this.created;
    }

    public void setId(Long learningDayId) {
        this.learningDayId = learningDayId;
    }
    public void setSubject(Subject subject) {
        this.subject = subject;
    }
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public void setCreated(String created) {
        this.created = created;
    }
}
