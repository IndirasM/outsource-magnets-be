package com.psk.demo.Controller.Model;

import com.psk.demo.Entity.Limit;

public class LimitModel {
    public Long limitId;
    public boolean isGlobal;
    public int yearLimit;
    public int monthLimit;
    public int rowLimit;
    public boolean isBoss;

    public LimitModel(Limit limit){
        this.limitId = limit.getId();
        this.isGlobal = limit.getIsGlobal();
        this.yearLimit = limit.getDaysInYear();
        this.monthLimit = limit.getDaysInMonth();
        this.rowLimit = limit.getDaysInRow();
    }
}
