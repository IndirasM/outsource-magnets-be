package com.psk.demo.Entity2;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Size(max = 50)
    private String name;

    public Long getId() {
        return this.roleId;
    }

    public String getName() {
        return this.name;
    }


    public void setId(Long id) {
        this.roleId = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
