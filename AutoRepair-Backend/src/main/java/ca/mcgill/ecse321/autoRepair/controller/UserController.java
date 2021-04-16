package ca.mcgill.ecse321.autoRepair.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.autoRepair.dto.AssistantDTO;
import ca.mcgill.ecse321.autoRepair.dto.CarDTO;
import ca.mcgill.ecse321.autoRepair.dto.CustomerDTO;
import ca.mcgill.ecse321.autoRepair.dto.OwnerDTO;
import ca.mcgill.ecse321.autoRepair.dto.ProfileDTO;
import ca.mcgill.ecse321.autoRepair.model.Assistant;
import ca.mcgill.ecse321.autoRepair.model.Car;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.Owner;
import ca.mcgill.ecse321.autoRepair.model.Profile;
import ca.mcgill.ecse321.autoRepair.model.User;
import ca.mcgill.ecse321.autoRepair.service.UserService;

@CrossOrigin(origins = "*")
@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping(value = {"/login", "/login/"})
	public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
		User user = null;
		try {
			user = userService.login(username, password);
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		
		}
		if(user instanceof Customer) {

			return new ResponseEntity<>(Conversion.convertToDTO((Customer) user), HttpStatus.OK);
		}
		
		if(user instanceof Assistant) {
			return new ResponseEntity<>(Conversion.convertToDTO((Assistant) user), HttpStatus.OK);
		}
		
		if(user instanceof Owner) {
			return new ResponseEntity<>(Conversion.convertToDTO((Owner) user), HttpStatus.OK);
		}
		return null;
	}
	


}
