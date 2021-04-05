package ca.mcgill.ecse321.autoRepair.controller;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.autoRepair.dto.OwnerDTO;
import ca.mcgill.ecse321.autoRepair.model.Owner;
import ca.mcgill.ecse321.autoRepair.service.OwnerService;

@CrossOrigin(origins = "*")
@RestController
public class OwnerController {
	@Autowired
	private OwnerService ownerService;
	
	/**
	 * @author Marc Saber
	 * Gets a list of all the owners
	 * @return
	 */
	@GetMapping(value = { "/view_owner" })
	public List<OwnerDTO> getAllOwners() {
		return ownerService.getAllOwners().stream().map(owner -> convertToDTO(owner)).collect(Collectors.toList());
	}
	
	/**
	 * @author Marc Saber
	 * Gets an owner given a username
	 * @param username
	 * @return owner DTO
	 */
	@GetMapping(value = {"/view_owner/{username}"})
	public OwnerDTO viewOwner(@PathVariable("username") String username) {
		return convertToDTO(ownerService.getOwner(username));
	}
	
	/**
	 * @author Marc Saber
	 * Creates an owner
	 * @param name
	 * @param password
	 * @param authentificationCode
	 * @return ownerDTO
	 */
	@PostMapping(value = {"/create_owner"})
	public ResponseEntity<?> createOwner(@RequestParam("name") String name,@RequestParam("password") String password
			,@RequestParam("authentification") String authentificationCode) {
		
		Owner owner =null;
		
		try {
		 owner = ownerService.createOwner(name,password,authentificationCode);
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(convertToDTO(owner), HttpStatus.CREATED);

	}
	
	/**
	 * @author Marc Saber
	 * Updates an owner's password
	 * @param oldUsername
	 * @param newPassword
	 * @return ownerDTO
	 */
	@PatchMapping(value = { "/update_owner/{oldUsername}"})
	public OwnerDTO updateOwner(@PathVariable("oldUsername") String oldUsername,
			@RequestParam("newPassword") String newPassword) {
        Owner owner = ownerService.updateOwner(oldUsername, newPassword);
		return convertToDTO(owner);
	}
	
	private OwnerDTO convertToDTO(Owner owner) {
		if(owner == null) throw new IllegalArgumentException("Owner not found.");
		return new OwnerDTO(owner.getUsername(),owner.getPassword());
	}


}
