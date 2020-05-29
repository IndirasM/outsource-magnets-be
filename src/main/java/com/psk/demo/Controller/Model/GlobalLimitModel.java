package com.psk.demo.Controller.Model;

import com.psk.demo.Entity.Limit;

public class GlobalLimitModel {
    public Long limitId;
    public int yearLimit;
    public int monthLimit;
    public int rowLimit;

    public GlobalLimitModel(Limit limit){
        this.limitId = limit.getId();
        this.yearLimit = limit.getDaysInYear();
        this.monthLimit = limit.getDaysInMonth();
        this.rowLimit = limit.getDaysInRow();
    }
}
