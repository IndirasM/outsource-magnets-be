package com.psk.demo.Entity;

import javax.persistence.*;

@Entity
@Table(name = "employee_subject")
public class EmployeeSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_subject_id")
    private Long employeeSubjectId;

    @ManyToOne
    @JoinColumn(name="subject_id")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name="employee_id")
    private Employee employee;

    private String created;

    public EmployeeSubject() {}

    public EmployeeSubject(Subject subject, Employee employee, String created) {
        this.subject = subject;
        this.employee = employee;
        this.created = created;
    }

    public Long getId() {
        return this.employeeSubjectId;
    }
    public Subject getSubject() { return this.subject; }
    public Employee getEmployee() { return this.employee; }
    public String getCreated() { return this.created; }


    public void setId(Long id) { this.employeeSubjectId = id; }
    public void setSubject(Subject subject) { this.subject = subject; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    public void setCreated(String created) { this.created = created; }
}
