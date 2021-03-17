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
import ca.mcgill.ecse321.autoRepair.model.Car;
import ca.mcgill.ecse321.autoRepair.model.Car.CarTransmission;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.service.CarService;
import ca.mcgill.ecse321.autoRepair.service.CustomerService;




public class CarController {



	@Autowired
	private CustomerService customerService;


	@Autowired
	private CarService carService;



	@PostMapping(value = {"/add_car/{username}"})
	public boolean addCar (@PathVariable("username") CustomerDTO customerDTO, @RequestParam String model, 
			@RequestParam String plateNumber, @RequestParam String carTransmission) {

		CarTransmission transmission = null;
		if(carTransmission=="Automatic") transmission = CarTransmission.Automatic;
		else if(carTransmission=="Manual") transmission = CarTransmission.Manual;
		else throw new IllegalArgumentException("Invalid car transmission");
		Car car = carService.createCar(plateNumber, model, transmission);
		return carService.addCar(customerDTO.getUsername(), car);
	}

	@PostMapping(value = {"/remove_car/{username"})
	public boolean removeCar(@PathVariable("username") CustomerDTO customerDTO, @RequestParam String plateNumber) {
		return carService.removeCar(customerDTO.getUsername(), plateNumber);
	}

	@GetMapping(value = {"/cars/{username}"})
	public List<CarDTO> getCustomerCars(@PathVariable("username") CustomerDTO customerDTO){
		Customer customer = customerService.getCustomer(customerDTO.getUsername());
		List<CarDTO> cars = new ArrayList<CarDTO>();
		for(Car c : customer.getCars()) {
			cars.add(convertToDTO(c));
		}
		return cars;
	}

	@GetMapping(value = {"/car","/car/"})
	public CarDTO getCar(@RequestParam String plateNumber) {
		return convertToDTO(carService.getCar(plateNumber));
	}


	@GetMapping(value = {"/cars" , "/cars/"})
	public List<CarDTO> getAllCars(){
		List<CarDTO> cars = new ArrayList<CarDTO>();
		for(Car c : carService.getAllCars()) {
			cars.add(convertToDTO(c));
		}
		return cars;
	}


	private CarDTO convertToDTO(Car car) {
		if(car==null) throw new IllegalArgumentException("Car not found.");
		return new CarDTO(car.getModel(), car.getTransmission(), car.getPlateNumber());
	}

}
