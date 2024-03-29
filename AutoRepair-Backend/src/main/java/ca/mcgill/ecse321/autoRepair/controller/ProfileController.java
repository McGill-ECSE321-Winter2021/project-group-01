package ca.mcgill.ecse321.autoRepair.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import ca.mcgill.ecse321.autoRepair.dto.ProfileDTO;
import ca.mcgill.ecse321.autoRepair.model.Profile;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.service.ProfileService;


@CrossOrigin(origins = "*")
@RestController
public class ProfileController {



	@Autowired
	private ProfileService profileService;

	/**
	 * @author Eric Chehata
	 * Edits a profile
	 * @param username
	 * @param firstName
	 * @param lastName
	 * @param phoneNumber
	 * @param email
	 * @param address
	 * @param zipCode
	 * @return customerDTO
	 */
	@PatchMapping(value = {"/edit_profile/{username}"})
	public ResponseEntity<?> editProfile (@PathVariable("username") String username, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String phoneNumber,
			@RequestParam String email, @RequestParam String address, @RequestParam String zipCode) {

		Customer customer = null;
		try {
			customer=profileService.updateProfile(username, firstName, lastName, address, zipCode, phoneNumber, email);
		}catch(IllegalArgumentException exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(Conversion.convertToDTO(customer), HttpStatus.OK);
	}
	
	/**
	 * @author Eric Chehata
	 * Gets a profile given an email
	 * @param email
	 * @return profileDTO
	 */
	@GetMapping(value = {"/profile","/profile/"})
	public ProfileDTO getProfile (@RequestParam String email) {
		return Conversion.convertToDTO(profileService.getProfile(email));
	}
	
	/**
	 * @author Eric Chehata
	 * Gets all profiles
	 * @return all profile DTOs
	 */
	@GetMapping(value = {"/profiles" , "/profiles/"})
	public List<ProfileDTO> getAllProfiles(){
		List<ProfileDTO> profiles = new ArrayList<ProfileDTO>();
		for(Profile profile : profileService.getAllProfiles()) {
			profiles.add(Conversion.convertToDTO(profile));
		}
		return profiles;
	}
}
