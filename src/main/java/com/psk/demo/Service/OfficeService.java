package com.psk.demo.Service;

import com.psk.demo.Entity.Office;
import com.psk.demo.Exception.ResourceNotFoundException;
import com.psk.demo.Repository.IOfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficeService implements IOfficeService {
	@Autowired
	private IOfficeRepository officeRepository;

	@Override
	public List<Office> findAll() {
		return officeRepository.findAll();
	}

	@Override
	public Office findById(Long id) {
		return officeRepository.findAllById(id).get(0);
	}

	public Office save(Office office) {
		return officeRepository.save(office);
	}
}
