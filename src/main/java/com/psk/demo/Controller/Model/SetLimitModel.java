package com.psk.demo.Controller.Model;

import com.psk.demo.Entity.Limit;

public class SetLimitModel {
    public Long employeeId;
    public int yearLimit;
    public int monthLimit;
    public int rowLimit;

    public SetLimitModel() {}

    public SetLimitModel(Limit limit){
        this.yearLimit = limit.getDaysInYear();
        this.monthLimit = limit.getDaysInMonth();
        this.rowLimit = limit.getDaysInRow();
    }
}
