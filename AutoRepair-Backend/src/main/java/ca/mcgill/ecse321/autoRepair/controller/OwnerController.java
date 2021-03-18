package ca.mcgill.ecse321.autoRepair.controller;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.autoRepair.dao.OwnerRepository;
import ca.mcgill.ecse321.autoRepair.dto.OwnerDTO;
import ca.mcgill.ecse321.autoRepair.model.Owner;
import ca.mcgill.ecse321.autoRepair.service.OwnerService;

@CrossOrigin(origins = "*")
@RestController
public class OwnerController {
	@Autowired
	OwnerRepository ownerRepository;
	@Autowired
	private OwnerService ownerService;
	
	

	
	@GetMapping(value = { "/view_owner" })
	public List<OwnerDTO> getAllOwners() {
		return ownerService.getAllOwners().stream().map(owner -> convertToDTO(owner)).collect(Collectors.toList());
	}
	
	@GetMapping(value = {"/view_owner/{username}"})
	public OwnerDTO viewOwner(@PathVariable("username") String username) {
		return convertToDTO(ownerService.getOwner(username));
	}
	
	

	@PostMapping(value = {"/create_owner"})
	public OwnerDTO createOwner(@RequestParam("name") String name,@RequestParam("password") String password
			,@RequestParam("authentification") String authentificationCode) {
		
		Owner owner = ownerService.createOwner(name,password,authentificationCode);
		return convertToDTO(owner); 
	}
	
	@PostMapping(value = { "/update_owner/{oldUsername}"})
	public OwnerDTO updateOwner(@PathVariable("oldUsername") String oldUsername,
			@RequestParam("newPassword") String newPassword) {
        Owner owner = ownerService.updateOwner(oldUsername, newPassword);
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
