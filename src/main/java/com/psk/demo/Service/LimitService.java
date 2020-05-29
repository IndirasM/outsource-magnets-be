package com.psk.demo.Service;

import com.psk.demo.Entity.Employee;
import com.psk.demo.Entity.Limit;
import com.psk.demo.Entity.Team;
import com.psk.demo.Repository.ILimitRepository;
import com.psk.demo.Repository.ITeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LimitService implements ILimitService {
	@Autowired
	private ILimitRepository limitRepository;

	public LimitService(ILimitRepository limitRepository) {
		this.limitRepository = limitRepository;
	}

	@Override
	public Limit getGlobal() {
		return limitRepository.findByIsGlobalEquals(true);
	}
}
