package ca.mcgill.ecse321.autoRepair.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.autoRepair.dao.CarRepository;
import ca.mcgill.ecse321.autoRepair.dao.CustomerRepository;
import ca.mcgill.ecse321.autoRepair.dao.ProfileRepository;
import ca.mcgill.ecse321.autoRepair.model.Car;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.Profile;
import ca.mcgill.ecse321.autoRepair.model.Car.CarTransmission;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {
	
	
	@Mock
	private CarRepository carRepo;
	
	@Mock
	private ProfileRepository profileRepo;

	@Mock
	private CustomerRepository cusRepo;

	@InjectMocks
	private CarService service;


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
	
	private static final String CAR_PLATE2 ="ABC 123";
	
	
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
				Car car2 = new Car();
				car2.setModel(CAR_MODEL);
				car2.setTransmission(CAR_TRANSMISSION);
				car2.setPlateNumber(CAR_PLATE2);
				List<Car> cars = new ArrayList<Car>();
				cars.add(car);
				cars.add(car2);



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
	public void testCreateCar() {
		assertEquals(0, service.getAllCars().size());

		String model = "Lambo";
		String plateNumber = "Number 1";
		CarTransmission carTransmition = CarTransmission.Automatic;
		Car car = null;

		try {
			car = service.createCar(plateNumber, model, carTransmition);
		}catch(IllegalArgumentException e) {
			fail();
		}

		assertNotNull(car);
		assertEquals(model, car.getModel());
		assertEquals(plateNumber, car.getPlateNumber());
		assertEquals(carTransmition, car.getTransmission());
	}
	
	@Test
	public void testCreateCarWithExistingPlate() {
		assertEquals(0, service.getAllCars().size());

		String model = "Lambo";
		String plateNumber = CAR_PLATE;
		CarTransmission carTransmition = CarTransmission.Automatic;
		Car car = null;
		String error = null;

		try {
			car = service.createCar(plateNumber, model, carTransmition);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(car);
		assertEquals(error, "A car with this plate number already exists in the system.");
	}

	@Test
	public void testCreateCarNullModel() {
		assertEquals(0, service.getAllCars().size());

		String model = null;
		String plateNumber = "Number 1";
		CarTransmission carTransmition = CarTransmission.Automatic;
		Car car = null;
		String error = null;

		try {
			car = service.createCar(plateNumber, model, carTransmition);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(car);
		assertEquals(error, "Car model cannot be blank.");
	}

	@Test
	public void testCreateCarBlankModel() {
		assertEquals(0, service.getAllCars().size());

		String model = "";
		String plateNumber = "Number 1";
		CarTransmission carTransmition = CarTransmission.Automatic;
		Car car = null;
		String error = null;

		try {
			car = service.createCar(plateNumber, model, carTransmition);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(car);
		assertEquals(error, "Car model cannot be blank.");
	}

	@Test
	public void testCreateCarNullPlateNumber() {
		assertEquals(0, service.getAllCars().size());

		String model = "BMW X6";
		String plateNumber = null;
		CarTransmission carTransmition = CarTransmission.Automatic;
		Car car = null;
		String error = null;

		try {
			car = service.createCar(plateNumber, model, carTransmition);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(car);
		assertEquals(error, "Car plate number cannot be blank.");
	}

	@Test
	public void testCreateCarBlankPlateNumber() {
		assertEquals(0, service.getAllCars().size());

		String model = "BMW X6";
		String plateNumber = "";
		CarTransmission carTransmition = CarTransmission.Automatic;
		Car car = null;
		String error = null;

		try {
			car = service.createCar(plateNumber, model, carTransmition);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(car);
		assertEquals(error, "Car plate number cannot be blank.");
	}

	@Test
	public void  testCreateCarNullTransmission() {
		assertEquals(0, service.getAllCars().size());

		String model = "Lambo";
		String plateNumber = "Number 1";
		CarTransmission carTransmition = null;
		Car car = null;
		String error = null;
		try {
			car = service.createCar(plateNumber, model, carTransmition);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(car);
		assertEquals(error, "Car transmission cannot be blank.");
	}
	
	@Test
	public void testAddCar() {
		String model = "Lambo";
		String plateNumber = "Number 1";
		CarTransmission carTransmission = CarTransmission.Automatic;
		Car car = new Car();
		car.setPlateNumber(plateNumber);
		car.setModel(model);
		car.setTransmission(carTransmission);
		boolean success = false;
		try {
			success = service.addCar(CUSTOMER_USERNAME, car);
		}catch(IllegalArgumentException e) {
			fail();
		}
		assertTrue(success);
	}
	
	@Test
	public void testAddCarNullCustomer() {
		String model = "Lambo";
		String plateNumber = "Number 1";
		CarTransmission carTransmission = CarTransmission.Automatic;
		Car car = new Car();
		car.setPlateNumber(plateNumber);
		car.setModel(model);
		car.setTransmission(carTransmission);
		boolean success = false;
		String error = null;
		try {
			service.addCar("Fake", car);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertFalse(success);
		assertEquals(error,"Customer not found.");
	}
	
	@Test
	public void testAddCarNullCar() {

		boolean success = false;
		String error = null;
		try {
			service.addCar(CUSTOMER_USERNAME, null);
		}catch(IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertFalse(success);
		assertEquals(error,"Car not found.");
	}
	
	@Test
	public void testRemoveCar() {

		boolean success = false;
		try {
			success = service.removeCar(CUSTOMER_USERNAME, CAR_PLATE);
		}catch(IllegalArgumentException e) {
			fail();
		}
		assertTrue(success);
	}
	
	@Test
	public void testRemoveCarNullCustomer() {

		boolean success = false;
		String error = null;
		try {
			success = service.removeCar(null, CAR_PLATE);
		}catch(IllegalArgumentException e) {
			error=e.getMessage();
		}
		assertFalse(success);
		assertEquals(error, "Customer not found.");
	}
	
	@Test
	public void testRemoveCarNullCar() {

		boolean success = false;
		String error = null;
		try {
			success = service.removeCar(CUSTOMER_USERNAME, "FAKE");
		}catch(IllegalArgumentException e) {
			error=e.getMessage();
		}
		assertFalse(success);
		assertEquals(error, "Car not found.");
	}

}
