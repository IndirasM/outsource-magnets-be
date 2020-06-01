package com.psk.demo.Service;

import com.psk.demo.Entity.Employee;
import com.psk.demo.Entity.Limit;
import com.psk.demo.Entity.Team;
import com.psk.demo.Repository.IEmployeeRepository;
import com.psk.demo.Repository.ILimitRepository;
import com.psk.demo.Repository.ITeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LimitService implements ILimitService {
	@Autowired
	private ILimitRepository limitRepository;
	@Autowired
	private IEmployeeRepository employeeRepository;

	public LimitService(ILimitRepository limitRepository, IEmployeeRepository employeeRepository) {
		this.limitRepository = limitRepository;
		this.employeeRepository = employeeRepository;
	}

	@Override
	public Limit getGlobal() {
		return limitRepository.findByIsGlobalEquals(true);
	}

	@Override
	@Transactional
	public Limit setGlobal(Limit limit) {
		Limit newGlobalLimit = limitRepository.save(limit);
		List<Employee> employees = employeeRepository.findAll();
		for (Employee e : employees) {
			e.setLimit(newGlobalLimit);
		}
		employeeRepository.saveAll(employees);
		limitRepository.deleteByLimitIdNot(newGlobalLimit.getId());

		return newGlobalLimit;
	}

	@Override
	public Limit setLimit(Employee employee, int yearLimit, int monthLimit, int rowLimit) {
		Limit oldLimit = employee.getLimit();

		Limit limitToSet = new Limit(yearLimit, monthLimit, rowLimit);
		limitToSet.setIsGlobal(false);

		Limit newLimit = limitRepository.save(limitToSet);
		employee.setLimit(newLimit);
		employeeRepository.save(employee);

		List<Employee> employeesWithOldLimits = employeeRepository.findByLimit(oldLimit);
		if (employeesWithOldLimits.isEmpty()){
			limitRepository.delete(oldLimit);
		}

		return newLimit;
	}

	@Override
	public Limit setBulkLimit(List<Employee> employees, int yearLimit, int monthLimit, int rowLimit) {
		List<Limit> oldLimits = employees.stream().map(Employee::getLimit).collect(Collectors.toList());

		Limit limitToSet = new Limit(yearLimit, monthLimit, rowLimit);
		limitToSet.setIsGlobal(false);

		Limit newLimit = limitRepository.save(limitToSet);
		for (Employee e : employees) {
			e.setLimit(newLimit);
		}
		employeeRepository.saveAll(employees);

		List<Employee> employeesWithOldLimits = employeeRepository.findByLimitIn(oldLimits);
		List<Long> usedOldLimitIds = employeesWithOldLimits.stream().map(e -> e.getLimit().getId()).collect(Collectors.toList());
		usedOldLimitIds = new ArrayList<>(new HashSet<>(usedOldLimitIds));
		List<Long> finalUsedOldLimitIds = usedOldLimitIds;
		List<Limit> unusedLimits = oldLimits.stream().filter(l -> finalUsedOldLimitIds.indexOf(l.getId()) < 0).collect(Collectors.toList());
		limitRepository.deleteAll(unusedLimits);

		return newLimit;
	}
}
