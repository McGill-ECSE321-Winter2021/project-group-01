package ca.mcgill.ecse321.autoRepair.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.autoRepair.dao.CarRepository;
import ca.mcgill.ecse321.autoRepair.dao.CustomerRepository;
import ca.mcgill.ecse321.autoRepair.dao.ProfileRepository;
import ca.mcgill.ecse321.autoRepair.model.Car;
import ca.mcgill.ecse321.autoRepair.model.Car.CarTransmission;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.Profile;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

	@Mock
	private CustomerRepository cusRepo;

	@Mock
	private ProfileRepository profileRepo;

	@Mock
	private CarRepository carRepo;

	@InjectMocks
	private CustomerService customerService;
	
	@InjectMocks
	private CarService carService;
	
	@InjectMocks
	private ProfileService profileService;

	private static final String CUSTOMER_USERNAME ="TestCustomer";
	private static final String CUSTOMER_PASSWORD ="TestPassword123";

	private static final String PROFILE_EMAIL ="TestCustomer@mail.com";
	private static final String PROFILE_FIRSTNAME ="Bob";
	private static final String PROFILE_LASTNAME ="Fisher";
	private static final String PROFILE_ADDRESS ="1000, MEMORY LANE";
	private static final String PROFILE_PHONE ="5141234567";
	private static final String PROFILE_ZIP ="55555";

	private static final String CAR_MODEL ="BMW X6";
	private static final String CAR_PLATE ="123 ABC";
	private static final CarTransmission CAR_TRANSMISSION = CarTransmission.Automatic;


	@BeforeEach
	public void setMockOutput() {

		lenient().when(cusRepo.findCustomerByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(CUSTOMER_USERNAME)) {

				Profile profile = new Profile();
				profile.setFirstName(PROFILE_FIRSTNAME);
				profile.setLastName(PROFILE_LASTNAME);
				profile.setAddress(PROFILE_ADDRESS);
				profile.setEmail(PROFILE_EMAIL);
				profile.setPhoneNumber(PROFILE_PHONE);
				profile.setZipCode(PROFILE_ZIP);

				Car car = new Car();
				car.setModel(CAR_MODEL);
				car.setTransmission(CAR_TRANSMISSION);
				car.setPlateNumber(CAR_PLATE);
				List<Car> cars = new ArrayList<Car>();
				cars.add(car);


				Customer customer = new Customer();
				customer.setUsername(CUSTOMER_USERNAME);
				customer.setPassword(CUSTOMER_PASSWORD);
				customer.setProfile(profile);
				customer.setCars(cars);

				return customer;

			}else {
				return null;
			}
		});
		
		lenient().when(profileRepo.findByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(PROFILE_EMAIL)) {

				Profile profile = new Profile();
				profile.setFirstName(PROFILE_FIRSTNAME);
				profile.setLastName(PROFILE_LASTNAME);
				profile.setAddress(PROFILE_ADDRESS);
				profile.setEmail(PROFILE_EMAIL);
				profile.setPhoneNumber(PROFILE_PHONE);
				profile.setZipCode(PROFILE_ZIP);

				return profile;
			} else {
				return null;
			}
		});


		lenient().when(carRepo.findCarByPlateNumber(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(CAR_PLATE)) {	
				Car car = new Car();
				car.setModel(CAR_MODEL);
				car.setPlateNumber(CAR_PLATE);
				car.setTransmission(CAR_TRANSMISSION);

				return car;
			} else {
				return null;
			}

		});
		
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};

		lenient().when(cusRepo.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(profileRepo.save(any(Profile.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(carRepo.save(any(Car.class))).thenAnswer(returnParameterAsAnswer);
	}

	
	


	@Test
	public void testCreateCustomer() {
		assertEquals(0, customerService.getAllCustomers().size());

		String model = "Lambo";
		String plateNumber = "Number 1";
		CarTransmission carTransmition = CarTransmission.Automatic;
		Car car = null;

		String firstName = "Gary";
		String lastName = "Jimmy";
		String email = "garyjimmy@mail.com";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String zip = "G79 DE4";
		Profile profile = null;

		String username = "garyjimmy";
		String password = "Password123";
		Customer customer = null;

		try {
			car = carService.createCar(plateNumber, model, carTransmition);
			List<Car> cars = new ArrayList<Car>();
			cars.add(car);
			profile = profileService.createProfile(firstName, lastName, address, zip, phoneNumber, email);
			customer = customerService.createCustomer(username, password, profile, cars);
		}catch(IllegalArgumentException e) {
			fail();
		}

		assertNotNull(customer);
		assertEquals(username, customer.getUsername());
		assertEquals(password, customer.getPassword());

		assertNotNull(customer.getCars().get(0));
		assertEquals(model, customer.getCars().get(0).getModel());
		assertEquals(plateNumber, customer.getCars().get(0).getPlateNumber());
		assertEquals(carTransmition, customer.getCars().get(0).getTransmission());

		assertNotNull(customer.getProfile());
		assertEquals(firstName, customer.getProfile().getFirstName());
		assertEquals(lastName, customer.getProfile().getLastName());
		assertEquals(address, customer.getProfile().getAddress());
		assertEquals(email, customer.getProfile().getEmail());
		assertEquals(phoneNumber, customer.getProfile().getPhoneNumber());
		assertEquals(zip, customer.getProfile().getZipCode());

	}
	
	@Test
	public void testCreateCustomerTakenUsername(){

		assertEquals(0, customerService.getAllCustomers().size());

		String model = "Lambo";
		String plateNumber = "Number 1";
		CarTransmission carTransmition = CarTransmission.Automatic;
		Car car = null;

		String firstName = "Gary";
		String lastName = "Jimmy";
		String email = "garyjimmy@mail.com";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String zip = "G79 DE4";
		Profile profile = null;

		String username = CUSTOMER_USERNAME;
		String password = "Password123";
		Customer customer = null;
		String error = "";
		try {
			car = carService.createCar(plateNumber, model, carTransmition);
			List<Car> cars = new ArrayList<Car>();
			cars.add(car);
			profile = profileService.createProfile(firstName, lastName, address, zip, phoneNumber, email);
			customer = customerService.createCustomer(username, password, profile, cars);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(customer);
		assertEquals(error, "Username is already taken");

	}
	
	@Test
	public void editCustomerPassword() {
		assertEquals(0, customerService.getAllCustomers().size());
		Customer customer = null;
		
		try {
			customer = customerService.editCustomerPassword(CUSTOMER_USERNAME, "Newpassword123");
		}catch(IllegalArgumentException e) {
			fail();
		}
		
		assertNotNull(customer);
		assertEquals("Newpassword123", customer.getPassword());
		assertEquals(CUSTOMER_USERNAME, customer.getUsername());
		
	}
	
	@Test
	public void editCustomerPasswordInvalidUpperCase() {
		assertEquals(0, customerService.getAllCustomers().size());
		Customer customer = null;
		String error = null;
		
		try {
			customer = customerService.editCustomerPassword(CUSTOMER_USERNAME, "newpassword123");
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(customer);
		assertEquals(error, "Password must contain at least one uppercase character");
		
	}
	
	@Test
	public void editCustomerPasswordNullPassword() {
		assertEquals(0, customerService.getAllCustomers().size());
		Customer customer = null;
		String error = null;
		
		try {
			customer = customerService.editCustomerPassword(CUSTOMER_USERNAME, null);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(customer);
		assertEquals(error, "New password cannot be blank.");
		
	}
	
	@Test
	public void deleteCustomerSuccess() {
		boolean success = false;
		try {
			success = customerService.deleteCustomer(CUSTOMER_USERNAME);
		}catch(IllegalArgumentException e) {
			fail();
		}
		assertTrue(success);
	}
	
	
	@Test
	public void deleteCustomerUserNotFound() {
		String error = null;
		try {
			customerService.deleteCustomer("Fake username");
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(error, "Customer not found.");
	}
	

	@Test
	public void invalidPasswordUpperCase(){

		assertEquals(0, customerService.getAllCustomers().size());

		String model = "Lambo";
		String plateNumber = "Number 1";
		CarTransmission carTransmition = CarTransmission.Automatic;
		Car car = null;

		String firstName = "Gary";
		String lastName = "Jimmy";
		String email = "garyjimmy@mail.com";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String zip = "G79 DE4";
		Profile profile = null;

		String username = "GaryJimmy";
		String password = "password123";
		Customer customer = null;
		String error = "";
		try {
			car = carService.createCar(plateNumber, model, carTransmition);
			List<Car> cars = new ArrayList<Car>();
			cars.add(car);
			profile = profileService.createProfile(firstName, lastName, address, zip, phoneNumber, email);
			customer = customerService.createCustomer(username, password, profile, cars);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(customer);
		assertEquals(error, "Password must contain at least one uppercase character");

	}


	@Test
	public void invalidPasswordLowerCase(){

		assertEquals(0, customerService.getAllCustomers().size());

		String model = "Lambo";
		String plateNumber = "Number 1";
		CarTransmission carTransmition = CarTransmission.Automatic;
		Car car = null;

		String firstName = "Gary";
		String lastName = "Jimmy";
		String email = "garyjimmy@mail.com";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String zip = "G79 DE4";
		Profile profile = null;

		String username = "GaryJimmy";
		String password = "PASSWORD123";
		Customer customer = null;
		String error = "";
		try {
			car = carService.createCar(plateNumber, model, carTransmition);
			List<Car> cars = new ArrayList<Car>();
			cars.add(car);
			profile = profileService.createProfile(firstName, lastName, address, zip, phoneNumber, email);
			customer = customerService.createCustomer(username, password, profile, cars);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(customer);
		assertEquals(error, "Password must contain at least one lowercase character");

	}

	@Test
	public void invalidPasswordNumeric(){

		assertEquals(0, customerService.getAllCustomers().size());

		String model = "Lambo";
		String plateNumber = "Number 1";
		CarTransmission carTransmition = CarTransmission.Automatic;
		Car car = null;

		String firstName = "Gary";
		String lastName = "Jimmy";
		String email = "garyjimmy@mail.com";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String zip = "G79 DE4";
		Profile profile = null;

		String username = "GaryJimmy";
		String password = "Password";
		Customer customer = null;
		String error = "";
		try {
			car = carService.createCar(plateNumber, model, carTransmition);
			List<Car> cars = new ArrayList<Car>();
			cars.add(car);
			profile = profileService.createProfile(firstName, lastName, address, zip, phoneNumber, email);
			customer = customerService.createCustomer(username, password, profile, cars);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(customer);
		assertEquals(error, "Password must contain at least one numeric character");

	}

	@Test
	public void invalidPasswordLengthUnder8(){

		assertEquals(0, customerService.getAllCustomers().size());

		String model = "Lambo";
		String plateNumber = "Number 1";
		CarTransmission carTransmition = CarTransmission.Automatic;
		Car car = null;

		String firstName = "Gary";
		String lastName = "Jimmy";
		String email = "garyjimmy@mail.com";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String zip = "G79 DE4";
		Profile profile = null;

		String username = "GaryJimmy";
		String password = "Pass1";
		Customer customer = null;
		String error = "";
		try {
			car = carService.createCar(plateNumber, model, carTransmition);
			List<Car> cars = new ArrayList<Car>();
			cars.add(car);
			profile = profileService.createProfile(firstName, lastName, address, zip, phoneNumber, email);
			customer = customerService.createCustomer(username, password, profile, cars);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(customer);
		assertEquals(error, "Password must have at least 8 characters");

	}


	@Test
	public void invalidPasswordLengthOver20(){

		assertEquals(0, customerService.getAllCustomers().size());

		String model = "Lambo";
		String plateNumber = "Number 1";
		CarTransmission carTransmition = CarTransmission.Automatic;
		Car car = null;

		String firstName = "Gary";
		String lastName = "Jimmy";
		String email = "garyjimmy@mail.com";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String zip = "G79 DE4";
		Profile profile = null;

		String username = "GaryJimmy";
		String password = "Password1234567890123456789";
		Customer customer = null;
		String error = "";
		try {
			car = carService.createCar(plateNumber, model, carTransmition);
			List<Car> cars = new ArrayList<Car>();
			cars.add(car);
			profile = profileService.createProfile(firstName, lastName, address, zip, phoneNumber, email);
			customer = customerService.createCustomer(username, password, profile, cars);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(customer);
		assertEquals(error, "Password must not have more than 20 characters");

	}

}
