package ca.mcgill.ecse321.autoRepair.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.autoRepair.dto.CarDTO;
import ca.mcgill.ecse321.autoRepair.dto.CustomerDTO;
import ca.mcgill.ecse321.autoRepair.dto.ProfileDTO;
import ca.mcgill.ecse321.autoRepair.model.Car;
import ca.mcgill.ecse321.autoRepair.model.Profile;
import ca.mcgill.ecse321.autoRepair.model.Car.CarTransmission;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.service.CarService;
import ca.mcgill.ecse321.autoRepair.service.CustomerService;
import ca.mcgill.ecse321.autoRepair.service.ProfileService;

@CrossOrigin(origins = "*")
@RestController
public class CustomerController {


	@Autowired
	private CustomerService customerService;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private CarService carService;

	/**
	 * @author Eric Chehata
	 * Registers a customer
	 * @param firstName
	 * @param lastName
	 * @param phoneNumber
	 * @param email
	 * @param address
	 * @param zipCode
	 * @param username
	 * @param password
	 * @param model
	 * @param plateNumber
	 * @param carTransmission
	 * @return customerDTO
	 */
	@PostMapping(value = {"/register_customer/","/register_customer"})
	public ResponseEntity<?> registerCustomer(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String phoneNumber,
			@RequestParam String email, @RequestParam String address, @RequestParam String zipCode, @RequestParam String username, 
			@RequestParam String password, @RequestParam String model, @RequestParam String plateNumber, @RequestParam String carTransmission) {

		CarTransmission transmission = null;
		if(carTransmission.equals("Automatic")) transmission = CarTransmission.Automatic;
		else if(carTransmission.equals("Manual")) transmission = CarTransmission.Manual;
		else {
			//throw new IllegalArgumentException("Invalid car transmission");
			return new ResponseEntity<>("Invalid car transmission", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		Profile profile = null;
		try {
			profile = profileService.createProfile(firstName, lastName, address, zipCode, phoneNumber, email);
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		Car car = null;
		try {
			car = carService.createCar(plateNumber, model, transmission);
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		List<Car> cars = new ArrayList<Car>();
		cars.add(car);
		Customer customer = null;
		try {
			customer = customerService.createCustomer(username, password, profile, cars);
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return new ResponseEntity<>(convertToDTO(customer), HttpStatus.CREATED);

	}
	
	/**
	 * @author Eric Chehata
	 * Deletes a customer given a username
	 * @param username
	 * @return true if customer is successfully deleted
	 */
	@PostMapping(value = {"/delete_customer/{username}"})
	public boolean deleteCustomer(@PathVariable("username") String username) {
		return customerService.deleteCustomer(username);
	}
	
	/**
	 * @author Eric Chehata
	 * Gets a customer given a username
	 * @param username
	 * @return customerDTO
	 */
	@GetMapping(value = {"/view_customer/{username}"})
	public CustomerDTO viewCustomer(@PathVariable("username") String username) {
		return convertToDTO(customerService.getCustomer(username));
	}

	/**
	 * @author Eric Chehata
	 * Gets all customers
	 * @return list containing all customer DTOs
	 */
	@GetMapping(value = {"/view_customers", "/view_customers/"})
	public List<CustomerDTO> viewCustomers(){

		return customerService.getAllCustomers().stream().map(c ->
		convertToDTO(c)).collect(Collectors.toList());
	
	}
	
	/**
	 * @author Eric Chehata
	 * Edits the password of a customer
	 * @param username
	 * @param password
	 * @return customerDTO
	 */
	@PostMapping(value = {"/change_password/{username}"})
	public CustomerDTO changePassword(@PathVariable("username") String username, @RequestParam String password) {
		return convertToDTO(customerService.editCustomerPassword(username, password));
	}

	private CustomerDTO convertToDTO(Customer customer) {
		if(customer==null) throw new IllegalArgumentException("Customer not found.");
		List<CarDTO> cars = new ArrayList<CarDTO>();

		for (Car car : customer.getCars()) {
			cars.add(convertToDTO(car));
		}

		return new CustomerDTO(customer.getUsername(), customer.getPassword(), customer.getNoShow(), 
				customer.getShow(), cars, convertToDTO(customer.getProfile()));

	}

	private CarDTO convertToDTO(Car car) {
		if(car==null) throw new IllegalArgumentException("Car not found.");
		return new CarDTO(car.getModel(), car.getTransmission(), car.getPlateNumber());
	}

	private ProfileDTO convertToDTO(Profile profile) {
		if(profile == null) throw new IllegalArgumentException("Profile not found.");
		return new ProfileDTO(profile.getFirstName(), profile.getLastName(), profile.getAddress(), 
				profile.getZipCode(), profile.getPhoneNumber(), profile.getEmail());
	}



}
