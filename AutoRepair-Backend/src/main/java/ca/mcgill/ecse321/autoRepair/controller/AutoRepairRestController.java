package ca.mcgill.ecse321.autoRepair.controller;

import ca.mcgill.ecse321.autoRepair.service.AutoRepairService;
import org.springframework.beans.factory.annotation.Autowired;
//Had to import these ^
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;



@CrossOrigin(origins = "*")
@RestController
public class AutoRepairRestController {

	@Autowired
	private AutoRepairService service;

}