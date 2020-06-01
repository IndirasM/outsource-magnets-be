package com.psk.demo.Controller.Model;

import com.psk.demo.Entity.Team;

public class TeamModel {
    public Long teamId;
    public String name;
    public Long managerId;
    public String managerName;

    public TeamModel() {};

    public TeamModel(Team team) {
        this.teamId = team.getId();
        this.name = team.getName();
        this.managerId = team.getManager().getId();
        this.managerName = team.getManager().getName();
    }
}
