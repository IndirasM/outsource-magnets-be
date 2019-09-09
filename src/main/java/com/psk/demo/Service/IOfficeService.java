package com.psk.demo.Service;

import com.psk.demo.Entity.Office;

import java.util.List;

public interface IOfficeService {
	List<Office> findAll();
	Office findById(Long id);
}
