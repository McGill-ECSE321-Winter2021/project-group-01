package ca.mcgill.ecse321.autoRepair.service;

import ca.mcgill.ecse321.autoRepair.dao.CarRepository;
import ca.mcgill.ecse321.autoRepair.dao.CustomerRepository;
import ca.mcgill.ecse321.autoRepair.dao.ProfileRepository;
import ca.mcgill.ecse321.autoRepair.model.Appointment;
import ca.mcgill.ecse321.autoRepair.model.Car;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.Profile;
import ca.mcgill.ecse321.autoRepair.model.Reminder;
import ca.mcgill.ecse321.autoRepair.model.Review;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutoRepairService {

	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	CarRepository carRepository;
	@Autowired
	ProfileRepository profileRepository;

	@Transactional
	public Customer createCustomer(String username, String password) {
		Customer customer = new Customer();
		customer.setNoShow(0);
		customer.setShow(0);
		customer.setPassword(password);
		customer.setUsername(username);
		customer.setAppointments(new ArrayList<Appointment>());
		customer.setCars(new ArrayList<Car>());
		customer.setReminders(new ArrayList<Reminder>());
		customer.setReviews(new ArrayList<Review>());

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
	public Car createCar(String plateNumber, String model, Car.CarTransmission transmission, Customer customer) {
		Car car = new Car();
		//car.setCustomer(customer);
		car.setModel(model);
		car.setPlateNumber(plateNumber);
		car.setTransmission(transmission);
		customer.getCars().add(car);
		carRepository.save(car);
		return car;
	}
	
	@Transactional
	public Car getCar(String plateNumber) {
		return carRepository.findCarByPlateNumber(plateNumber);
	}
	
//	public List<Car> getCustomerCars(Customer customer) {
//		return carRepository.findByCustomer(customer);
//	}
	
	@Transactional
	public List<Car> getAllCars(){
		return toList(carRepository.findAll());
	}
	
	@Transactional
	public Profile createProfile(String firstName, String lastName, String address, String zipCode, String phoneNumber, String email, Customer customer) {
		Profile profile = new Profile();
		profile.setFirstName(firstName);
		profile.setLastName(lastName);
		profile.setAddress(address);
		profile.setEmail(email);
		profile.setPhoneNumber(phoneNumber);
		profile.setZipCode(zipCode);
		//profile.setCustomer(customer);
		customer.setProfile(profile);
		profileRepository.save(profile);
		return profile;
	}

	@Transactional
	public Profile getProfile(String firstName, String lastName) {
		return profileRepository.findByFirstNameAndLastName(firstName, lastName);
	}
	
//	@Transactional
//	public boolean profileExists(Customer customer) {
//		return profileRepository.existsByCustomer(customer);
//	}
	
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
