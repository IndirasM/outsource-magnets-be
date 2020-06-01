package com.psk.demo.Service;

import com.psk.demo.Entity.Employee;
import com.psk.demo.Entity.Limit;

import java.util.List;

public interface ILimitService {
    Limit getGlobal();
    Limit setGlobal(Limit limit);
    Limit setLimit(Employee employee, int yearLimit, int monthLimit, int rowLimit);
    Limit setBulkLimit(List<Employee> employees, int yearLimit, int monthLimit, int rowLimit);
}
