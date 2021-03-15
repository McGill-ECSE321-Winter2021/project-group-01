package ca.mcgill.ecse321.autoRepair.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.autoRepair.dao.CarRepository;
import ca.mcgill.ecse321.autoRepair.dao.CustomerRepository;
import ca.mcgill.ecse321.autoRepair.model.Car;
import ca.mcgill.ecse321.autoRepair.model.Customer;

public class CarService {

	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	CarRepository carRepository;


	@Transactional
	public Car createCar(String plateNumber, String model, Car.CarTransmission transmission) {
		
		if(plateNumber==null || plateNumber=="") 
			throw new IllegalArgumentException("Car plate number cannot be blank.");
		

		if(model==null || model=="") 
			throw new IllegalArgumentException("Car model cannot be blank.");
		

		if(transmission==null) 
			throw new IllegalArgumentException("Car transmission cannot be blank.");

		
		
		if(getCar(plateNumber) != null)
			throw new IllegalArgumentException("A car with this plate number already exists in the system.");

		Car car = new Car();
		car.setModel(model);
		car.setPlateNumber(plateNumber);
		car.setTransmission(transmission);
		carRepository.save(car);
		return car;
	}

	@Transactional
	public boolean addCar(String username, Car car) {
		Customer customer = customerRepository.findCustomerByUsername(username);
		if(customer==null) throw new IllegalArgumentException("Customer not found.");
		if(car==null) throw new IllegalArgumentException("Car not found.");
		customer.getCars().add(car);
		customerRepository.save(customer);
		return true;
	}
	
	@Transactional
	public boolean removeCar(String username, String plateNumber) {
		Customer customer = customerRepository.findCustomerByUsername(username);
		Car car = getCar(plateNumber);
		
		
		if(customer==null) throw new IllegalArgumentException("Customer not found.");
		
		if(car==null) throw new IllegalArgumentException("Car not found.");
		
		boolean found = false;
		for(Car c : customer.getCars()) {
			if (c.getPlateNumber() == car.getPlateNumber()) found=true;
		}
		if(found==false) throw new IllegalArgumentException("Car not found.");
		
		if(customer.getCars().size()==1) throw new IllegalArgumentException("Customer must have at least one car.");
		
		customer.getCars().remove(getCar(plateNumber));
		carRepository.delete(car);
		customerRepository.save(customer);
		
		return true;
	}

	@Transactional
	public Car getCar(String plateNumber) {
		return carRepository.findCarByPlateNumber(plateNumber);
	}


	@Transactional
	public List<Car> getAllCars(){
		return toList(carRepository.findAll());
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

}

