package com.psk.demo.Entity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long subjectId;

    @ManyToOne
    @JoinColumn(name = "parent_subject_id", nullable = true)
    private Subject parentSubject;

    @Size(max = 50)
    private String date;

    @Column(name = "description")
    private String description;

    @Size(max = 50)
    private String name;

    public Long getId() {
        return this.subjectId;
    }
    public Subject getParentSubject() { return this.parentSubject; }
    public String getDate() { return this.date; }
    public String getDescription() { return this.description; }
    public String getName() { return this.name; }

    public void setId(Long id) { this.subjectId = id; }
    public void setParentSubject(Subject parentSubject) { this.parentSubject = parentSubject; }
    public void setDate(String date) { this.date = date; }
    public void setDescription(String description) { this.description = description; }
    public void setName(String name) { this.name = name; }
}
