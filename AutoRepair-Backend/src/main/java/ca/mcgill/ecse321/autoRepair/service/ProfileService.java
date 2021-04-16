package ca.mcgill.ecse321.autoRepair.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.autoRepair.dao.CustomerRepository;
import ca.mcgill.ecse321.autoRepair.dao.ProfileRepository;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.Profile;

@Service
public class ProfileService {


	@Autowired
	ProfileRepository profileRepository;

	@Autowired
	CustomerRepository customerRepository;

	/**
	 * @author Eric Chehata
	 * Creates a profile
	 * @param firstName
	 * @param lastName
	 * @param address
	 * @param zipCode
	 * @param phoneNumber
	 * @param email
	 * @return profile
	 */
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
		
		if(profileRepository.findByEmail(email)!=null) {
			profileRepository.delete(profileRepository.findByEmail(email));
			throw new IllegalArgumentException("Customer with email entered already exists.");
		}
		
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

	/**
	 * @author Eric Chehata
	 * Gets a profile given an email
	 * @param email
	 * @return profile
	 */
	@Transactional
	public Profile getProfile(String email) {
		return profileRepository.findByEmail(email);
	}

	/**
	 * @author Eric Chehata
	 * Updates a profile
	 * @param username
	 * @param firstName
	 * @param lastName
	 * @param address
	 * @param zipCode
	 * @param phoneNumber
	 * @param email
	 * @return profile
	 */
	@Transactional
	public Customer updateProfile(String username, String firstName, String lastName, String address, String zipCode, String phoneNumber, String email) {
		Customer customer = customerRepository.findCustomerByUsername(username);
		
		if(customer==null)
			throw new IllegalArgumentException("Customer not found.");
		
		Profile profile = customer.getProfile();

		if(!profile.getEmail().equals(email) && email!=null && email!="") {
			if(!emailIsValid(email)) 
				throw new IllegalArgumentException("Invalid email.");

		}

		if(!profile.getPhoneNumber().equals(phoneNumber) && phoneNumber!=null && phoneNumber!="") {
			if(!isNumeric(phoneNumber))
				throw new IllegalArgumentException("Invalid phone number.");
		}

		
		if(!profile.getEmail().equals(email) && email != null && email !="") {
			if(getProfile(email)!=null)
				throw new IllegalArgumentException("Customer with email entered already exists.");

		}

		if(!profile.getFirstName().equals(firstName) && firstName!=null && firstName!="") {
			profile.setFirstName(firstName);
		}
		if(!profile.getLastName().equals(lastName) && lastName!=null && lastName!="") {
			profile.setLastName(lastName);
		}

		if(!profile.getEmail().equals(email) && email!=null && email!="") {
			profile.setEmail(email);
		}

		if(!profile.getPhoneNumber().equals(phoneNumber) && phoneNumber!=null && phoneNumber!="") {
			profile.setPhoneNumber(phoneNumber);
		}

		if(!profile.getZipCode().equals(zipCode) && zipCode!=null && zipCode!="") {
			profile.setZipCode(zipCode);
		}

		if(!profile.getAddress().equals(address) && address!=null && address!="") {
			profile.setAddress(address);
		}

		customer.setProfile(profile);
		customerRepository.save(customer);
		profileRepository.save(profile);

		return customer;
	}

	/**
	 * @author Eric Chehata
	 * This method deletes a profile
	 * @param email
	 * @return
	 */
	@Transactional
	public boolean deleteByEmail(String email) {
		Profile profile = profileRepository.findByEmail(email);
		if(profile!=null) { 
			profileRepository.delete(profile);
			return true;
		}
		return false;
	}


	/**
	 * @author Eric Chehata
	 * Returns a list of all the profiles
	 * @return list of all profiles
	 */
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

	/**
	 * This method checks whether the email is available or not since an email has to be unique
	 * @author Eric Chehata
	 * @param email
	 * @return
	 */
	private boolean emailIsValid(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
				"[a-zA-Z0-9_+&*-]+)*@" + 
				"(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
				"A-Z]{2,7}$"; 

		Pattern pat = Pattern.compile(emailRegex); 

		return pat.matcher(email).matches(); 
	}

	/**
	 * This method checks if the input phone number is only numeric. It does not accept non numeric values.
	 * @param phoneNumber
	 * @return
	 */
	private boolean isNumeric(String phoneNumber) {
		try {
			Integer.parseInt(phoneNumber);
		}catch(NumberFormatException nfe) {
			return false;
		}
		return true;
	}

}