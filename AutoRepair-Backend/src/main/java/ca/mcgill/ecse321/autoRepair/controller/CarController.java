package ca.mcgill.ecse321.autoRepair.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.autoRepair.dto.CarDTO;
import ca.mcgill.ecse321.autoRepair.dto.CustomerDTO;
import ca.mcgill.ecse321.autoRepair.dto.ProfileDTO;
import ca.mcgill.ecse321.autoRepair.model.Car;
import ca.mcgill.ecse321.autoRepair.model.Car.CarTransmission;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.Profile;
import ca.mcgill.ecse321.autoRepair.service.CarService;
import ca.mcgill.ecse321.autoRepair.service.CustomerService;


@CrossOrigin(origins = "*")
@RestController
public class CarController {


	@Autowired
	private CustomerService customerService;


	@Autowired
	private CarService carService;


	/**
	 * @author Eric Chehata
	 * Given a username of a customer, method adds a car to the list of cars
	 * belonging to the customer
	 * @param username
	 * @param model
	 * @param plateNumber
	 * @param carTransmission
	 * @return customerDTO
	 */
	@PostMapping(value = {"/add_car/{username}"})
	public ResponseEntity<?> addCar (@PathVariable("username") String username, @RequestParam String model, 
			@RequestParam String plateNumber, @RequestParam String carTransmission) {

		CarTransmission transmission = null;
		if(carTransmission.equals("Automatic")) transmission = CarTransmission.Automatic;
		else if(carTransmission.equals("Manual")) transmission = CarTransmission.Manual;
		else return new ResponseEntity<>("Invalid car transmission", HttpStatus.INTERNAL_SERVER_ERROR);
		try {
			Car car = carService.createCar(plateNumber, model, transmission);
			carService.addCar(username, car);
		}catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(convertToDTO(customerService.getCustomer(username)), HttpStatus.CREATED);
	}

	/**
	 * @author Eric Chehata
	 * Removes a car from a customer's list of cars
	 * @param username
	 * @param plateNumber
	 * @return customerDTO
	 */
	@DeleteMapping(value = {"/remove_car/{username}"})
	public ResponseEntity<?> removeCar(@PathVariable("username") String username, @RequestParam String plateNumber) {
		try {
			carService.removeCar(username, plateNumber);
		}catch (IllegalArgumentException e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return new ResponseEntity<>(convertToDTO(customerService.getCustomer(username)), HttpStatus.OK);
	}

	/**
	 * @author Eric Chehata
	 * Gets the list of cars belonging to a specific customer
	 * @param username
	 * @return list of cars belonging to a specific customer
	 */
	@GetMapping(value = {"/cars/{username}"})
	public List<CarDTO> getCustomerCars(@PathVariable("username") String username){
		Customer customer = customerService.getCustomer(username);
		List<CarDTO> cars = new ArrayList<CarDTO>();
		for(Car car : customer.getCars()) {
			cars.add(convertToDTO(car));
		}
		return cars;
	}

	/**
	 * @author Eric Chehata
	 * Gets a car given the plate number
	 * @param plateNumber
	 * @return carDTO
	 */
	@GetMapping(value = {"/car","/car/"})
	public CarDTO getCar(@RequestParam String plateNumber) {
		return convertToDTO(carService.getCar(plateNumber));
	}

	/**
	 * @author Eric Chehata
	 * Gets a list of all the cars
	 * @return list of all the cars
	 */
	@GetMapping(value = {"/cars" , "/cars/"})
	public List<CarDTO> getAllCars(){
		List<CarDTO> cars = new ArrayList<CarDTO>();
		for(Car car : carService.getAllCars()) {
			cars.add(convertToDTO(car));
		}
		return cars;
	}

	/**
	 * This method converts a Customer object to a CustomerDTO
	 * @param customer
	 * @return
	 */
	private CustomerDTO convertToDTO(Customer customer) {
		if(customer==null) throw new IllegalArgumentException("Customer not found.");
		List<CarDTO> cars = new ArrayList<CarDTO>();

		for (Car car : customer.getCars()) {
			cars.add(convertToDTO(car));
		}

		return new CustomerDTO(customer.getUsername(), customer.getPassword(), cars, convertToDTO(customer.getProfile()));

	}

	/**
	 * This method converts a Car object to a CarDTO
	 * @param car
	 * @return
	 */
	private CarDTO convertToDTO(Car car) {
		if(car==null) throw new IllegalArgumentException("Car not found.");
		return new CarDTO(car.getModel(), car.getTransmission(), car.getPlateNumber());
	}

	/**
	 * This method converts a Profile object to a ProfileDTO
	 * @param profile
	 * @return
	 */
	private ProfileDTO convertToDTO(Profile profile) {
		if(profile == null) throw new IllegalArgumentException("Profile not found.");
		return new ProfileDTO(profile.getFirstName(), profile.getLastName(), profile.getAddress(), 
				profile.getZipCode(), profile.getPhoneNumber(), profile.getEmail());
	}

}
