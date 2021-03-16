package ca.mcgill.ecse321.autoRepair.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.autoRepair.dao.OwnerRepository;
import ca.mcgill.ecse321.autoRepair.dto.AppointmentDTO;
import ca.mcgill.ecse321.autoRepair.dto.ChosenServiceDTO;
import ca.mcgill.ecse321.autoRepair.dto.CustomerDTO;
import ca.mcgill.ecse321.autoRepair.dto.OwnerDTO;
import ca.mcgill.ecse321.autoRepair.model.Appointment;
import ca.mcgill.ecse321.autoRepair.model.ChosenService;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.Owner;
import ca.mcgill.ecse321.autoRepair.model.TimeSlot;
import ca.mcgill.ecse321.autoRepair.service.OwnerService;

@CrossOrigin(origins = "*")
@RestController
public class OwnerController {
	@Autowired
	OwnerRepository ownerRepository;
	@Autowired
	private OwnerService ownerService;
	
	

	
	@GetMapping(value = { "/owner" })
	public List<OwnerDTO> getAllOwners() {
		return ownerService.getAllOwners().stream().map(owner -> convertToDTO(owner)).collect(Collectors.toList());
	}

	@PostMapping(value = { "/create owner/{name}/{password}/{authentificationCode}" })
	public OwnerDTO createOwner(@PathVariable("name") String name,@PathVariable("password") String password
			,@PathVariable("authentification") String authentificationCode) {
		
		Owner owner = ownerService.createOwner(name,password,authentificationCode);
		return convertToDTO(owner); 
	}
	
	@PostMapping(value = { "/update owner/{oldUsername}/{newUsername}/{newPassword}" })
	public OwnerDTO updateOwner(@PathVariable("oldUsername") String oldUsername,
			@PathVariable("newUsername") String newUsername,@PathVariable("newPassword") String newPassword) {
        Owner owner = ownerService.updateOwner(oldUsername, newUsername, newPassword);
		return convertToDTO(owner);
	}
	
	
	private OwnerDTO convertToDTO(Owner owner) {
		if(owner == null) throw new IllegalArgumentException("Owner not found.");
		return new OwnerDTO(owner.getUsername(),owner.getPassword());
	}

//	private Owner convertToDomainObject(OwnerDTO ownerDto) {      //unused.
//	List<Owner> allOwners = ownerService.getAllOwners();
//	for (Owner owner : allOwners) {
//		if (owner.getUsername().equals(ownerDto.getUsername())) {
//			return owner;
//		}
//	}
//	return null;
//	}

}
