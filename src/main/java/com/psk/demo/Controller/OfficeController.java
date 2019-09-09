package com.psk.demo.Controller;

import com.psk.demo.Controller.Model.OfficeInfo;
import com.psk.demo.Entity.Office;
import com.psk.demo.Service.IOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/office")
public class OfficeController {

	@Autowired
	private IOfficeService officeService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<OfficeInfo> getAllOffices() {
		List<Office> offices = officeService.findAll();
		List<OfficeInfo> response = new ArrayList<OfficeInfo>();
		offices.forEach(o -> {
			response.add(new OfficeInfo(o));
		});

		return response;
	}
}
