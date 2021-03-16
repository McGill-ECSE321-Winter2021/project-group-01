package ca.mcgill.ecse321.autoRepair.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

public class CustomerController {


	@Autowired
	private CustomerService customerService;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private CarService carService;


	@PostMapping(value = {"/register_customer"})
	public CustomerDTO registerCustomer(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String phoneNumber,
			@RequestParam String email, @RequestParam String address, @RequestParam String zipCode, @RequestParam String username, 
			@RequestParam String password, @RequestParam String model, @RequestParam String plateNumber, @RequestParam String carTransmission) {

		CarTransmission transmission = null;
		if(carTransmission=="Automatic") transmission = CarTransmission.Automatic;
		else if(carTransmission=="Manual") transmission = CarTransmission.Manual;
		else throw new IllegalArgumentException("Invalid car transmission");


		Profile profile = profileService.createProfile(firstName, lastName, address, zipCode, phoneNumber, email);
		Car car = carService.createCar(plateNumber, model, transmission);
		List<Car> cars = new ArrayList<Car>();
		cars.add(car);
		Customer customer = customerService.createCustomer(username, password, profile, cars);
		return convertToDTO(customer);

	}
	
	@PostMapping(value = {"/delete_customer/{username}"})
	public boolean deleteCustomer(@PathVariable("username") CustomerDTO customerDTO) {
		return customerService.deleteCustomer(customerDTO.getUsername());
	}
	
	@GetMapping(value = {"/view_customer/{username}"})
	public CustomerDTO viewCustomer(@PathVariable("username") CustomerDTO customerDTO) {
		return convertToDTO(customerService.getCustomer(customerDTO.getUsername()));
	}

	@GetMapping(value = {"/view_customers", "/view_customers/"})
	public List<CustomerDTO> viewCustomers(){
		List<CustomerDTO> customers = new ArrayList<CustomerDTO>();
		for(Customer c : customerService.getAllCustomers()) {
			customers.add(convertToDTO(c));
		}
		return customers;
	
	}
	
	@PostMapping(value = {"/change_passord/{username}"})
	public CustomerDTO changePassword(@PathVariable("username") CustomerDTO customerDTO, @RequestParam String password) {
		return convertToDTO(customerService.editCustomerPassword(customerDTO.getUsername(), password));
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
