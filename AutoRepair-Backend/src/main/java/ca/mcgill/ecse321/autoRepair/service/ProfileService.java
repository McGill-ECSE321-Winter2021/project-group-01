package ca.mcgill.ecse321.autoRepair.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.autoRepair.dao.CustomerRepository;
import ca.mcgill.ecse321.autoRepair.dao.ProfileRepository;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.Profile;

public class ProfileService {


	@Autowired
	ProfileRepository profileRepository;

	@Autowired
	CustomerRepository customerRepository;


	@Transactional
	public Profile createProfile(String firstName, String lastName, String address, String zipCode, String phoneNumber, String email) {
		if(firstName ==null || firstName =="") 
			throw new IllegalArgumentException("First name cannot be blank.");

		if(lastName ==null || lastName =="") 
			throw new IllegalArgumentException("Last name cannot be blank.");

		if(address ==null || address =="") 
			throw new IllegalArgumentException("Address cannot be blank.");

		if(zipCode ==null || zipCode =="") 
			throw new IllegalArgumentException("Zip code cannot be blank.");

		if(phoneNumber ==null || phoneNumber =="") 
			throw new IllegalArgumentException("Phone number cannot be blank.");

		if(email ==null || email =="") 
			throw new IllegalArgumentException("Email cannot be blank.");

		if(!emailIsValid(email)) 
			throw new IllegalArgumentException("Invalid email.");
		
		if(profileRepository.findByEmail(email)!=null)
			throw new IllegalArgumentException("Customer with email entered already exists.");

		
		Profile profile = new Profile();
		profile.setFirstName(firstName);
		profile.setLastName(lastName);
		profile.setAddress(address);
		profile.setEmail(email);
		profile.setPhoneNumber(phoneNumber);
		profile.setZipCode(zipCode);

		profileRepository.save(profile);

		return profile;
	}

	@Transactional
	public Profile getProfile(String email) {
		return profileRepository.findByEmail(email);
	}

	@Transactional
	public Profile updateProfile(String username, String firstName, String lastName, String address, String zipCode, String phoneNumber, String email) {
		Customer customer = customerRepository.findCustomerByUsername(username);
		
		if(customer==null)
			throw new IllegalArgumentException("Customer not found.");
		
		Profile profile = customer.getProfile();

		if(profile.getEmail()!=email && email!=null && email!="") {
			if(!emailIsValid(email)) 
				throw new IllegalArgumentException("Invalid email.");

		}

		if(profile.getPhoneNumber()!=phoneNumber && phoneNumber!=null && phoneNumber!="") {
			if(!isNumeric(phoneNumber))
				throw new IllegalArgumentException("Invalid phone number.");
		}

		
		if(profile.getEmail()!=email && email != null && email !="") {
			if(getProfile(email)!=null)
				throw new IllegalArgumentException("Customer with email entered already exists.");

		}

		if(profile.getFirstName() != firstName && firstName!=null && firstName!="") {
			profile.setFirstName(firstName);
		}
		if(profile.getLastName() != lastName && lastName!=null && lastName!="") {
			profile.setLastName(lastName);
		}

		if(profile.getEmail()!=email && email!=null && email!="") {
			profile.setEmail(email);
		}

		if(profile.getPhoneNumber()!=phoneNumber && phoneNumber!=null && phoneNumber!="") {
			profile.setPhoneNumber(phoneNumber);
		}

		if(profile.getZipCode()!=zipCode && zipCode!=null && zipCode!="") {
			profile.setZipCode(zipCode);
		}

		if(profile.getAddress()!=address && address!=null && address!="") {
			profile.setAddress(address);
		}

		profileRepository.save(profile);

		return profile;
	}




	@Transactional
	public List<Profile> getAllProfiles(){
		return toList(profileRepository.findAll());
	}

	//----------------------------------------------------------------------------------------
	//---------------------------------------HELPER METHODS-----------------------------------
	//----------------------------------------------------------------------------------------

	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;

	}


	private boolean emailIsValid(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
				"[a-zA-Z0-9_+&*-]+)*@" + 
				"(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
				"A-Z]{2,7}$"; 

		Pattern pat = Pattern.compile(emailRegex); 

		return pat.matcher(email).matches(); 
	}

	private boolean isNumeric(String phoneNumber) {
		try {
			@SuppressWarnings("unused")
			int d = Integer.parseInt(phoneNumber);
		}catch(NumberFormatException nfe) {
			return false;
		}
		return true;
	}

}