package com.psk.demo.Controller.Model;

import com.psk.demo.Entity.Employee;

public class StaffersLimits {
    public Long employeeId;
    public String employeeName;
    public Long limitId;
    public boolean isGlobal;
    public int yearLimit;
    public int monthLimit;
    public int rowLimit;

    public StaffersLimits(Employee employee){
        this.employeeId = employee.getId();
        this.employeeName = employee.getName();
        this.limitId = employee.getLimit().getId();
        this.isGlobal = employee.getLimit().getIsGlobal();
        this.yearLimit = employee.getLimit().getDaysInYear();
        this.monthLimit = employee.getLimit().getDaysInMonth();
        this.rowLimit = employee.getLimit().getDaysInRow();
    }
}
