package ca.mcgill.ecse321.autoRepair.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.autoRepair.dao.CarRepository;
import ca.mcgill.ecse321.autoRepair.dao.CustomerRepository;
import ca.mcgill.ecse321.autoRepair.dao.ProfileRepository;
import ca.mcgill.ecse321.autoRepair.model.Assistant;
import ca.mcgill.ecse321.autoRepair.model.Car;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.Owner;
import ca.mcgill.ecse321.autoRepair.model.Profile;
import ca.mcgill.ecse321.autoRepair.dao.OwnerRepository;
import ca.mcgill.ecse321.autoRepair.dao.AssistantRepository;



public class UserService {

	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	OwnerRepository ownerRepository;
	@Autowired
	AssistantRepository assistantRepository;
	@Autowired
	CarRepository carRepository;
	@Autowired
	ProfileRepository profileRepository;
	

//--------------------------CUSTOMER------------------------------

	@Transactional
	public Customer createCustomer(String username, String password, Profile profile, List<Car> cars) {

		if(username==null || username=="") throw new IllegalArgumentException("Username cannot be blank");

		if(password==null || password=="") throw new IllegalArgumentException("Password cannot be blank");

		//if (!usernameIsValidAssistant(username)) throw new IllegalArgumentException("Username already taken");

		if (!passwordIsValid(password)) throw new IllegalArgumentException("Invalid Password. Password must have at least\r\n"
				+ " one numeric character\r\n" + 
				"one lowercase character\r\n" + 
				"one uppercase character\r\n" + 
				"And password length should be between 8 and 20");

		Customer customer = new Customer();
		customer.setNoShow(0);
		customer.setShow(0);
		customer.setUsername(username);
		customer.setPassword(password);
		customer.setCars(cars);
		customer.setProfile(profile);

		customerRepository.save(customer);

		return customer;
	}


	@Transactional
	public Customer getCustomer(String username) {
		return customerRepository.findCustomerByUsername(username);

	}

	@Transactional
	public List<Customer> getAllCustomers(){
		return toList(customerRepository.findAll());
	}

	@Transactional
	public Car createCar(String plateNumber, String model, Car.CarTransmission transmission) {
		if(plateNumber==null || plateNumber=="") {
			throw new IllegalArgumentException("Plate Number cannot be blank.");
		}

		if(model==null || model=="") {
			throw new IllegalArgumentException("Car model cannot be blank.");
		}

		if(transmission==null) {
			throw new IllegalArgumentException("Car transmission cannot be blank.");

		}

		Car car = new Car();
		car.setModel(model);
		car.setPlateNumber(plateNumber);
		car.setTransmission(transmission);
		carRepository.save(car);
		return car;
	}

	@Transactional
	public Car getCar(String plateNumber) {
		return carRepository.findCarByPlateNumber(plateNumber);
	}


	@Transactional
	public List<Car> getAllCars(){
		return toList(carRepository.findAll());
	}

	@Transactional
	public Profile createProfile(String firstName, String lastName, String address, String zipCode, String phoneNumber, String email) {
		if(firstName ==null || firstName =="") {
			throw new IllegalArgumentException("First name cannot be blank.");
		}
		if(lastName ==null || lastName =="") {
			throw new IllegalArgumentException("Last name cannot be blank.");
		}

		if(address ==null || address =="") {
			throw new IllegalArgumentException("Address cannot be blank.");
		}

		if(zipCode ==null || zipCode =="") {
			throw new IllegalArgumentException("Zip code cannot be blank.");
		}

		if(phoneNumber ==null || phoneNumber =="") {
			throw new IllegalArgumentException("Phone number cannot be blank.");
		}

		if(email ==null || email =="") {
			throw new IllegalArgumentException("Email cannot be blank.");
		}

		if(!emailIsValid(email)) {
			throw new IllegalArgumentException("Invalid email.");

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

	@Transactional
	public Profile getProfile(String firstName, String lastName) {
		return profileRepository.findByFirstNameAndLastName(firstName, lastName);
	}

	@Transactional
	public void updateProfile(String username, String firstName, String lastName, String address, String zipCode, String phoneNumber, String email) {
		Customer customer = customerRepository.findCustomerByUsername(username);
		Profile profile = customer.getProfile();

		if(profile.getEmail()!=email && email!=null && email!="") {
			if(!emailIsValid(email)) 
				throw new IllegalArgumentException("Invalid email.");
		}

		if(profile.getPhoneNumber()!=phoneNumber && phoneNumber!=null && phoneNumber!="") {
			if(!isNumeric(phoneNumber))
				throw new IllegalArgumentException("Invalid phone number.");
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
	}


	private boolean emailIsValid(String email) {
		if((email.indexOf('@') == -1) || (email.indexOf('.') == -1) || (email.indexOf('.') < email.indexOf('@')) 
				|| (email.indexOf('@') == email.length()-1) || (email.indexOf('.') == email.length()-1)){
			return false;
		}
		return true;
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

	@Transactional
	public List<Profile> getAllProfiles(){
		return toList(profileRepository.findAll());
	}

	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;

	}
////--------------------------------OWNER-----------------------------
//	@Transactional
//	public Owner createOwner(String username,String password,String authentificationCode) {
//		
//		if(username==null || username=="") throw new IllegalArgumentException("Username cannot be blank");
//		if(password==null || password=="") throw new IllegalArgumentException("Password cannot be blank");
//		if(!authentificationCode.equals("1234")) throw new IllegalArgumentException
//		("wrong authentification code,please try again.");
//
//		
//		Owner owner = new Owner();
//		owner.setUsername(username);        //only one owner
//		
//		if (passwordIsValid(password)) {
//			owner.setPassword(password);
//		}
//		else throw new IllegalArgumentException("Invalid Password. Password must have at least\r\n"
//				+ " one numeric character\r\n" + 
//				"one lowercase character\r\n" + 
//				"one uppercase character\r\n" + 
//				"And password length should be between 8 and 20");
//				
//		ownerRepository.save(owner);
//		return owner;
//	}
//	
//	
//	@Transactional
//	public void updateOwner(String oldUsername,String newUsername,String newPassword) {
//		Owner oldOwner = ownerRepository.findOwnerByUsername(oldUsername);
//		if(oldOwner.getUsername() != newUsername && newUsername!=null && newUsername!="") {
//			oldOwner.setUsername(newUsername);
//		}
//		if(oldOwner.getPassword() != newPassword && newPassword!=null & newPassword!="") {
//			oldOwner.setPassword(newPassword);;
//		}
//
//	}
//	
//	@Transactional
//	public Owner getOwner(String name) {
//		Owner owner = ownerRepository.findOwnerByUsername(name);
//		return owner;
//	}
//	
//	@Transactional
//	public List<Owner> getAllOwners(){             
//		return toList(ownerRepository.findAll());
//	}	
	
////--------------------------------ASSISTANT-------------------------	
//	@Transactional
//	public Assistant createAssistant(String username,String password) {
//		
//		if(username==null || username=="") throw new IllegalArgumentException("Username cannot be blank");
//		if(password==null || password=="") throw new IllegalArgumentException("Password cannot be blank");
//		
//		Assistant assistant = new Assistant();
//		if (usernameIsValidAssistant(username)) {
//			assistant.setUsername(username);
//		}
//		if (passwordIsValid(password)) {
//			assistant.setPassword(password);
//		}
//		
//		assistantRepository.save(assistant);
//		return assistant;
//	}
//	
//	@Transactional
//	public void updateAssistant(String oldUsername,String newUsername,String newPassword,String authentification) {
//		Assistant oldAssistant = assistantRepository.findAssistantByUsername(oldUsername);
//		if(oldAssistant.getUsername() != newUsername && usernameIsValidAssistant(newUsername)) {
//			oldAssistant.setUsername(newUsername);
//		}
//		if(oldAssistant.getPassword() != newPassword && passwordIsValid(newPassword)) {
//			oldAssistant.setPassword(newPassword);
//		}
//	
//		
//		assistantRepository.save(oldAssistant);
//	}
//	
//	@Transactional
//	public void deleteAssistant(String username) {
//Assistant assistant = assistantRepository.findAssistantByUsername(username);
//if (assistant==null) throw new IllegalArgumentException
//("assistant with username" + username + "does not exist");
//assistantRepository.delete(assistant);
//
//	}
//	
//	
//	
//	@Transactional
//	public Assistant getAssistant(String name) {
//		Assistant assistant = assistantRepository.findAssistantByUsername(name);
//		return assistant;
//	}
//	
//	@Transactional
//	public List<Assistant> getAllAssistants(){           
//		return toList(assistantRepository.findAll());
//	}
//	private boolean usernameIsValidAssistant(String username) {
//		if(assistantRepository.findAssistantByUsername(username)==null) return true;
//		else throw new IllegalArgumentException("Username is already taken.");
	

	@SuppressWarnings("unused")
	private boolean passwordIsValid(String password){
		if (password.length()<8) throw new IllegalArgumentException("Password must have at least 8 characters");
		if(password.length()>20) throw new IllegalArgumentException("Password must not have more than 20 characters");
		
		boolean upperCaseFlag = false;
		boolean lowerCaseFlag = false;
		boolean numberFlag = false;

		for(int i=0; i<password.length(); i++) {
			if(Character.isUpperCase(password.charAt(i))) upperCaseFlag = true;
			else if(Character.isLowerCase(password.charAt(i))) lowerCaseFlag = true;
			else if(Character.isDigit(password.charAt(i))) numberFlag = true;
		}
		
		if(upperCaseFlag = false) throw new IllegalArgumentException ("Password must contain at least one uppercase character");
		if(lowerCaseFlag = false) throw new IllegalArgumentException ("Password must contain at least one lowercase character");
		if(numberFlag = false) throw new IllegalArgumentException ("Password must contain at least one numeric character");
		
		return true;
	}
	
}


