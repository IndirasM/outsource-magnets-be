package com.psk.demo.Entity2;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long teamId;

    @Size(max = 50)
    private String name;

    @ManyToOne
    @JoinColumn(name = "manager_employee_id")
    private Employee manager;

    public Long getId() {
        return this.teamId;
    }

    public String getName() {
        return this.name;
    }

    public Employee getManager() {
        return this.manager;
    }


    public void setId(Long id) {
        this.teamId = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }
}
