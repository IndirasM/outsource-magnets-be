package com.psk.demo.Entity2;

import com.psk.demo.Entity.Office;
import com.psk.demo.Entity.Permission;
import com.psk.demo.Entity.Trip;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "team")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long userId;

    @Size(max = 50)
    private String name;

    @Size(max = 50)
    private String email;

    @Size(max = 50)
    private String password;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "limit_id")
    private Limit limit;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    //region getters

    public Long getId() {
        return this.userId;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public Team getTeam() {
        return this.team;
    }

    public Limit getLimit() {
        return this.limit;
    }

    public Role getRole() {
        return this.role;
    }

    //endregion

    //region setters

    public void setId(Long id) {
        this.userId = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setLimit(Limit limit) {
        this.limit = limit;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    //endregion
}
