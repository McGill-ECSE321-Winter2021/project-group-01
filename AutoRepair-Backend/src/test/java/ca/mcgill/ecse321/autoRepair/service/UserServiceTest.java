package ca.mcgill.ecse321.autoRepair.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.autoRepair.dao.AssistantRepository;
import ca.mcgill.ecse321.autoRepair.dao.CarRepository;
import ca.mcgill.ecse321.autoRepair.dao.CustomerRepository;
import ca.mcgill.ecse321.autoRepair.dao.OwnerRepository;
import ca.mcgill.ecse321.autoRepair.dao.ProfileRepository;
import ca.mcgill.ecse321.autoRepair.model.Assistant;
import ca.mcgill.ecse321.autoRepair.model.Car;
import ca.mcgill.ecse321.autoRepair.model.Car.CarTransmission;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.Owner;
import ca.mcgill.ecse321.autoRepair.model.Profile;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private CustomerRepository cusRepo;

	@Mock
	private ProfileRepository profileRepo;

	@Mock
	private CarRepository carRepo;
	
	@Mock 
	private OwnerRepository ownerRepo;
	
	@Mock 
	private AssistantRepository assisRepo;


	@InjectMocks
	private UserService service;

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
	
//----------------owner + assis-----------
	
	private static final String OWNER_USERNAME ="TestOwner";
	private static final String OWNER_PASSWORD ="TestPassword1";
	private static final String ASSISTANT_USERNAME ="TestAssistant";
	private static final String ASSISTANT_PASSWORD ="TestPassword2";

	@BeforeEach
	public void setMockOutput() {
			
		lenient().when(ownerRepo.findOwnerByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(OWNER_USERNAME)) {
				Owner owner = new Owner();
			    owner.setUsername(CUSTOMER_USERNAME);
				owner.setPassword(CUSTOMER_PASSWORD);
				return owner;
			}
		else {
			return null;
		}
	});
		
		lenient().when(assisRepo.findAssistantByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(ASSISTANT_USERNAME)) {
				Assistant assistant = new Assistant();
			    assistant.setUsername(CUSTOMER_USERNAME);
				assistant.setPassword(CUSTOMER_PASSWORD);
				return assistant;
			}
		else {
			return null;
		}
	});
		
//----------------------------------------//
		
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
				List<Car> cars = new ArrayList<Car>();


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

		lenient().when(profileRepo.findByFirstNameAndLastName(anyString(), anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(PROFILE_FIRSTNAME) && invocation.getArgument(1).equals(PROFILE_LASTNAME)) {

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
		
		lenient().when(ownerRepo.save(any(Owner.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(assisRepo.save(any(Assistant.class))).thenAnswer(returnParameterAsAnswer);
		
		lenient().when(cusRepo.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(profileRepo.save(any(Profile.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(carRepo.save(any(Car.class))).thenAnswer(returnParameterAsAnswer);
	}
	
	@Test
	public void testCreateProfile() {
		assertEquals(0, service.getAllProfiles().size());
		
		String firstName = "Gary";
		String lastName = "Jimmy";
		String email = "garyjimmy@mail.com";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String zip = "G79 DE4";
		Profile profile = null;
		try {
			profile = service.createProfile(firstName, lastName, address, zip, phoneNumber, email);
		}catch (IllegalArgumentException e) {
			fail();
		}
		
		assertNotNull(profile);
		assertEquals(firstName, profile.getFirstName());
		assertEquals(lastName, profile.getLastName());
		assertEquals(address, profile.getAddress());
		assertEquals(email, profile.getEmail());
		assertEquals(phoneNumber, profile.getPhoneNumber());
		assertEquals(zip, profile.getZipCode());
		
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
	public void testCreateCustomer() {
		assertEquals(0, service.getAllCustomers().size());
		
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
			car = service.createCar(plateNumber, model, carTransmition);
			List<Car> cars = new ArrayList<Car>();
			cars.add(car);
			profile = service.createProfile(firstName, lastName, address, zip, phoneNumber, email);
			customer = service.createCustomer(username, password, profile, cars);
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
	
//----------------------------------------//	
	
	@Test
	public void testCreateOwner() {
	assertEquals(0, service.getAllOwners().size());  
	String username = "nameTest";
	String password = "passwordTest1";
	Owner owner = null;
	try {
     owner = service.createOwner(username, password);
	}catch(IllegalArgumentException e) {
		fail();
	}
	assertNotNull(owner);
	assertEquals(username, owner.getUsername());
	assertEquals(password, owner.getPassword());
}

	
	@Test
	public void testCreateAssistant() {
	assertEquals(0, service.getAllAssistants().size());  
	String username = "nameTest";
	String password = "passwordTest1";
	Assistant assistant = null;
	try {
     assistant = service.createAssistant(username, password);
	}catch(IllegalArgumentException e) {
		fail();
	}
	assertNotNull(assistant);
	assertEquals(username, assistant.getUsername());
	assertEquals(password, assistant.getPassword());
}
	
	


}
