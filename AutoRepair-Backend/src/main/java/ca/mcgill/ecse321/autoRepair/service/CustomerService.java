package ca.mcgill.ecse321.autoRepair.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.autoRepair.dao.CarRepository;
import ca.mcgill.ecse321.autoRepair.dao.CustomerRepository;
import ca.mcgill.ecse321.autoRepair.dao.ProfileRepository;
import ca.mcgill.ecse321.autoRepair.model.Car;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.Profile;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	CarRepository carRepository;
	@Autowired
	ProfileRepository profileRepository;

	/**
	 * @author Eric Chehata
	 * Creates a customer
	 * @param username
	 * @param password
	 * @param profile
	 * @param cars
	 * @return customer
	 */
	@Transactional
	public Customer createCustomer(String username, String password, Profile profile, List<Car> cars) {

		if(username==null || username=="") throw new IllegalArgumentException("Username cannot be blank");

		if(password==null || password=="") throw new IllegalArgumentException("Password cannot be blank");

		usernameIsValid(username);

		passwordIsValid(password);

		Customer customer = new Customer();
		customer.setUsername(username);
		customer.setPassword(password);
		customer.setCars(cars);
		customer.setProfile(profile);

		customerRepository.save(customer);

		return customer;
	}

	/**
	 * @author Eric Chehata
	 * Edits the password of a customer
	 * @param username
	 * @param password
	 * @return customer
	 */
	@Transactional
	public Customer editCustomerPassword(String username, String password) {
		Customer customer = customerRepository.findCustomerByUsername(username);
		if(customer==null) throw new IllegalArgumentException("Customer not found.");
		if(password==null) throw new IllegalArgumentException("New password cannot be blank.");
		if(passwordIsValid(password)) {
			customer.setPassword(password);
		}
		customerRepository.save(customer);
		return customer;
	}

	/**
	 * @author Eric Chehata
	 * Deletes a customer given a username
	 * @param username
	 * @return true
	 */
	@Transactional
	public boolean deleteCustomer(String username) {
		Customer customer = getCustomer(username);
		if(customer==null) throw new IllegalArgumentException("Customer not found.");
		profileRepository.delete(customer.getProfile());
		for (Car car : customer.getCars()) {
			carRepository.delete(car);
		}
		customerRepository.delete(customer);
		return true;
	}

	/**
	 * @author Eric Chehata
	 * Gets a customer given a username
	 * @param username
	 * @return customer
	 */
	@Transactional
	public Customer getCustomer(String username) {
		return customerRepository.findCustomerByUsername(username);

	}

	/**
	 * @author Eric Chehata
	 * Returns a list of all the customers
	 * @return
	 */
	@Transactional
	public List<Customer> getAllCustomers(){
		return toList(customerRepository.findAll());
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
	 * This method checks if a username is valid or taken since a username has to be unique
	 * @param username
	 * @return
	 */
	private boolean usernameIsValid(String username) {
		if(customerRepository.findCustomerByUsername(username)==null) return true;
		else throw new IllegalArgumentException("Username is already taken");
	}

	/**
	 * This method checks whether a password is valid or not
	 * @param password
	 * @return
	 */
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

		if(upperCaseFlag == false) throw new IllegalArgumentException ("Password must contain at least one uppercase character");
		if(lowerCaseFlag == false) throw new IllegalArgumentException ("Password must contain at least one lowercase character");
		if(numberFlag == false) throw new IllegalArgumentException ("Password must contain at least one numeric character");

		return true;
	}

}