package com.psk.demo.Controller.Model;

import com.psk.demo.Entity.Limit;

public class LimitModel {
    public int yearLimit;
    public int monthLimit;
    public int rowLimit;

    public LimitModel() {}

    public LimitModel(Limit limit){
        this.yearLimit = limit.getDaysInYear();
        this.monthLimit = limit.getDaysInMonth();
        this.rowLimit = limit.getDaysInRow();
    }
}
