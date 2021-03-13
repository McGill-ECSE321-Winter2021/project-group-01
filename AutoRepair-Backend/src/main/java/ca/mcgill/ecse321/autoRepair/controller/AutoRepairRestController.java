package ca.mcgill.ecse321.autoRepair.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.autoRepair.service.AutoRepairService;

@CrossOrigin(origins = "*")
@RestController
public class AutoRepairRestController {

	@Autowired
	private AutoRepairService service;

}