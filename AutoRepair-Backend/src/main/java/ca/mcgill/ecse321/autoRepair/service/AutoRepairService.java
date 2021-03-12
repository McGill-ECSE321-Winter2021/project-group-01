package ca.mcgill.ecse321.autoRepair.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import ca.mcgill.ecse321.autoRepair.dao.*;
import ca.mcgill.ecse321.autoRepair.model.*;

@Service
public class AutoRepairService {
	  
	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private AssistantRepository assistantRepository;
	@Autowired
	private OwnerRepository ownerRepository;
	@Autowired
	private ProfileRepository profileRepository;
	@Autowired
	private ReminderRepository reminderRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private CarRepository carRepository;
	@Autowired
	private OperatingHourRepository operatingHourRepository;
	@Autowired
	private ReviewRepository reviewRepository;
//	@Autowired
//	private ServiceComboRepository serviceComboRepository;
	@Autowired
	private ServiceRepository serviceRepository;
	@Autowired
	private TimeSlotRepository timeSlotRepository;
	@Autowired
	private BusinessRepository businessRepository;
//	@Autowired
//	private ComboItemRepository comboItemRepository;

//	@Transactional
//	public Customer createService(String name, int duration) {
//		ChosenService service = new ChosenService();
//		service.setName(name);
//		service.setDuration(duration);
//		chosenServiceRepository.save(service);
//		return service;
//	}
//	
//	@Transactional
//	public Customer getCustomer(String username) {
//		return chosenServiceRepository.findCustomerByUsername(username);
//	}
	
//		customer.setNoShow(0);
//		customer.setShow(0);
//		customer.setPassword(password);
//		customer.setUsername(username);
//		customer.setCars(new ArrayList<Car>());
	
	
//----------------------------------------------------------------------------------------------------------
//	@Autowired
//	CustomerRepository customerRepository;
//	@Autowired
//	CarRepository carRepository;
//	@Autowired
//	ProfileRepository profileRepository;
//
//	@Transactional
//	public Customer createCustomer(String username, String password) {
//		Customer customer = new Customer();
//		customer.setNoShow(0);
//		customer.setShow(0);
//		customer.setPassword(password);
//		customer.setUsername(username);
//		customer.setCars(new ArrayList<Car>());
//
//		customerRepository.save(customer);
//		return customer;
//	}
//
//	@Transactional
//	public Customer getCustomer(String username) {
//		return customerRepository.findCustomerByUsername(username);
//
//	}
//
//	@Transactional
//	public List<Customer> getAllCustomers(){
//		return toList(customerRepository.findAll());
//	}
//	
//	@Transactional
//	public Car createCar(String plateNumber, String model, Car.CarTransmission transmission) {
//		Car car = new Car();
//		car.setModel(model);
//		car.setPlateNumber(plateNumber);
//		car.setTransmission(transmission);
//		carRepository.save(car);
//		return car;
//	}
//	
//	@Transactional
//	public Car getCar(String plateNumber) {
//		return carRepository.findCarByPlateNumber(plateNumber);
//	}
//
//	
//	@Transactional
//	public List<Car> getAllCars(){
//		return toList(carRepository.findAll());
//	}
//	
//	@Transactional
//	public Profile createProfile(String firstName, String lastName, String address, String zipCode, String phoneNumber, String email) {
//		Profile profile = new Profile();
//		profile.setFirstName(firstName);
//		profile.setLastName(lastName);
//		profile.setAddress(address);
//		profile.setEmail(email);
//		profile.setPhoneNumber(phoneNumber);
//		profile.setZipCode(zipCode);
//		profileRepository.save(profile);
//		return profile;
//	}
//
//	@Transactional
//	public Profile getProfile(String firstName, String lastName) {
//		return profileRepository.findByFirstNameAndLastName(firstName, lastName);
//	}
//	
//
//	
//	@Transactional
//	public List<Profile> getAllProfiles(){
//		return toList(profileRepository.findAll());
//	}
//	
//	private <T> List<T> toList(Iterable<T> iterable){
//		List<T> resultList = new ArrayList<T>();
//		for (T t : iterable) {
//			resultList.add(t);
//		}
//		return resultList;
//
//	}
//-------------------------------------------------------------------------
//	@Transactional
//	public Person createPerson(String name) {
//		Person person = new Person();
//		person.setName(name);
//		personRepository.save(person);
//		return person;
//	}
//
//	@Transactional
//	public Person getPerson(String name) {
//		Person person = personRepository.findPersonByName(name);
//		return person;
//	}
//
//	@Transactional
//	public List<Person> getAllPersons() {
//		return toList(personRepository.findAll());
//	}
//
//	@Transactional
//	public Event createEvent(String name, Date date, Time startTime, Time endTime) {
//		Event event = new Event();
//		event.setName(name);
//		event.setDate(date);
//		event.setStartTime(startTime);
//		event.setEndTime(endTime);
//		eventRepository.save(event);
//		return event;
//	}
//
//	@Transactional
//	public Event getEvent(String name) {
//		Event event = eventRepository.findEventByName(name);
//		return event;
//	}
//
//	@Transactional
//	public List<Event> getAllEvents() {
//		return toList(eventRepository.findAll());
//	}
//
//	@Transactional
//	public Registration register(Person person, Event event) {
//		Registration registration = new Registration();
//		registration.setId(person.getName().hashCode() * event.getName().hashCode());
//		registration.setPerson(person);
//		registration.setEvent(event);
//
//		registrationRepository.save(registration);
//
//		return registration;
//	}
//
//	@Transactional
//	public List<Registration> getAllRegistrations(){
//		return toList(registrationRepository.findAll());
//	}
//
//	@Transactional
//	public List<Event> getEventsAttendedByPerson(Person person) {
//		List<Event> eventsAttendedByPerson = new ArrayList<>();
//		for (Registration r : registrationRepository.findByPerson(person)) {
//			eventsAttendedByPerson.add(r.getEvent());
//		}
//		return eventsAttendedByPerson;
//	}

	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

}