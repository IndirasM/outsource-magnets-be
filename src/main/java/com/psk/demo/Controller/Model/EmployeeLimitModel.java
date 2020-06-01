package com.psk.demo.Controller.Model;

import com.psk.demo.Entity.Limit;

public class EmployeeLimitModel {
    public Long employeeId;
    public Long limitId;
    public int yearLimit;
    public int monthLimit;
    public int rowLimit;
    public boolean isBoss;

    public EmployeeLimitModel(Limit limit){
        this.limitId = limit.getId();
        this.yearLimit = limit.getDaysInYear();
        this.monthLimit = limit.getDaysInMonth();
        this.rowLimit = limit.getDaysInRow();
    }
}
