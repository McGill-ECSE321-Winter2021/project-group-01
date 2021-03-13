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
import ca.mcgill.ecse321.autoRepair.model.Car;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.Profile;

public class UserService {

	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	CarRepository carRepository;
	@Autowired
	ProfileRepository profileRepository;



	@Transactional
	public Customer createCustomer(String username, String password, Profile profile, List<Car> cars) {

		if(username==null || username=="") throw new IllegalArgumentException("Username cannot be blank");

		if(password==null || password=="") throw new IllegalArgumentException("Password cannot be blank");

		if (!usernameIsValid(username)) throw new IllegalArgumentException("Username already taken");

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

	private boolean usernameIsValid(String username) {
		for(Customer c: getAllCustomers()) {
			if(c.getUsername()==username) return false;
		}
		return true;
	}

	private boolean passwordIsValid(String password){
		String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
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


}

