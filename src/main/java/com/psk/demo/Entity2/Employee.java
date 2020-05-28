package com.psk.demo.Entity2;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Table(name = "employee")
public class Employee implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long employeeId;

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
        return this.employeeId;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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
        this.employeeId = id;
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
