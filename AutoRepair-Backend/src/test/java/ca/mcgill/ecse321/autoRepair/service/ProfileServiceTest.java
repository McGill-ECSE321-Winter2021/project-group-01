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

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.autoRepair.dao.CustomerRepository;
import ca.mcgill.ecse321.autoRepair.dao.ProfileRepository;
import ca.mcgill.ecse321.autoRepair.model.Car;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.Profile;
import ca.mcgill.ecse321.autoRepair.model.Car.CarTransmission;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceTest {


	private static final String CUSTOMER_USERNAME ="TestCustomer";
	private static final String CUSTOMER_PASSWORD ="TestPassword123";

	private static final String PROFILE_EMAIL ="TestCustomer@mail.com";
	private static final String PROFILE_FIRSTNAME ="Bob";
	private static final String PROFILE_LASTNAME ="Fisher";
	private static final String PROFILE_ADDRESS ="1000, MEMORY LANE";
	private static final String PROFILE_PHONE ="5141234567";
	private static final String PROFILE_ZIP ="55555";

	private static final String PROFILE_EMAIL2 ="JohnSnow@mail.com";

	private static final String CAR_MODEL ="BMW X6";
	private static final String CAR_PLATE ="123 ABC";
	private static final CarTransmission CAR_TRANSMISSION = CarTransmission.Automatic;


	@Mock
	private ProfileRepository profileRepo;

	@Mock
	private CustomerRepository cusRepo;

	@InjectMocks
	private ProfileService service;

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
			if(invocation.getArgument(0).equals(PROFILE_EMAIL2)) {

				Profile profile = new Profile();
				profile.setFirstName(PROFILE_FIRSTNAME);
				profile.setLastName(PROFILE_LASTNAME);
				profile.setAddress(PROFILE_ADDRESS);
				profile.setEmail(PROFILE_EMAIL2);
				profile.setPhoneNumber(PROFILE_PHONE);
				profile.setZipCode(PROFILE_ZIP);

				return profile;
			} else {
				return null;
			}
		});


		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};

		lenient().when(cusRepo.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(profileRepo.save(any(Profile.class))).thenAnswer(returnParameterAsAnswer);
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
	public void testCreateProfileEmailAlreadyExists() {
		assertEquals(0, service.getAllProfiles().size());

		String firstName = PROFILE_FIRSTNAME;
		String lastName = PROFILE_LASTNAME;
		String email = PROFILE_EMAIL2;
		String phoneNumber = PROFILE_PHONE;
		String address = PROFILE_ADDRESS;
		String zip = PROFILE_ZIP;
		Profile profile = null;
		String error = null;
		try {
			profile = service.createProfile(firstName, lastName, address, zip, phoneNumber, email);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(profile);
		assertEquals(error, "Customer with email entered already exists.");

	}

	@Test
	public void testCreateProfileNullFirstName() {
		assertEquals(0, service.getAllProfiles().size());

		String firstName = null;
		String lastName = "Jimmy";
		String email = "garyjimmy@mail.com";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String zip = "G79 DE4";
		Profile profile = null;
		String error = null;
		try {
			profile = service.createProfile(firstName, lastName, address, zip, phoneNumber, email);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(profile);
		assertEquals(error, "First name cannot be blank.");

	}

	@Test
	public void testCreateProfileBlankFirstName() {
		assertEquals(0, service.getAllProfiles().size());

		String firstName = "";
		String lastName = "Jimmy";
		String email = "garyjimmy@mail.com";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String zip = "G79 DE4";
		Profile profile = null;
		String error = null;
		try {
			profile = service.createProfile(firstName, lastName, address, zip, phoneNumber, email);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(profile);
		assertEquals(error, "First name cannot be blank.");

	}

	@Test
	public void testCreateProfileNullLastName() {
		assertEquals(0, service.getAllProfiles().size());

		String firstName = "Gary";
		String lastName = null;
		String email = "garyjimmy@mail.com";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String zip = "G79 DE4";
		Profile profile = null;
		String error = null;
		try {
			profile = service.createProfile(firstName, lastName, address, zip, phoneNumber, email);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(profile);
		assertEquals(error, "Last name cannot be blank.");

	}

	@Test
	public void testCreateProfileBlankLastName() {
		assertEquals(0, service.getAllProfiles().size());

		String firstName = "Gary";
		String lastName = "";
		String email = "garyjimmy@mail.com";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String zip = "G79 DE4";
		Profile profile = null;
		String error = null;
		try {
			profile = service.createProfile(firstName, lastName, address, zip, phoneNumber, email);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(profile);
		assertEquals(error, "Last name cannot be blank.");

	}


	@Test
	public void testCreateProfileBlankEmail() {
		assertEquals(0, service.getAllProfiles().size());

		String firstName = "Gary";
		String lastName = "Jimmy";
		String email = "";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String zip = "G79 DE4";
		Profile profile = null;
		String error = null;
		try {
			profile = service.createProfile(firstName, lastName, address, zip, phoneNumber, email);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(profile);
		assertEquals(error, "Email cannot be blank.");
	}

	@Test
	public void testCreateProfileBlankPhone() {
		assertEquals(0, service.getAllProfiles().size());

		String firstName = "Gary";
		String lastName = "Jimmy";
		String email = "garyjimmy@mail.com";
		String phoneNumber = "";
		String address = "222, 5th Ave";
		String zip = "G79 DE4";
		Profile profile = null;
		String error = null;
		try {
			profile = service.createProfile(firstName, lastName, address, zip, phoneNumber, email);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(profile);
		assertEquals(error, "Phone number cannot be blank.");
	}

	@Test
	public void testCreateProfileBlankAddress() {
		assertEquals(0, service.getAllProfiles().size());

		String firstName = "Gary";
		String lastName = "Jimmy";
		String email = "garyjimmy@mail.com";
		String phoneNumber = "012344567";
		String address = "";
		String zip = "G79 DE4";
		Profile profile = null;
		String error = null;
		try {
			profile = service.createProfile(firstName, lastName, address, zip, phoneNumber, email);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(profile);
		assertEquals(error, "Address cannot be blank.");
	}


	@Test
	public void testCreateProfileBlankZip() {
		assertEquals(0, service.getAllProfiles().size());

		String firstName = "Gary";
		String lastName = "Jimmy";
		String email = "garyjimmy@mail.com";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String zip = "";
		Profile profile = null;
		String error = null;
		try {
			profile = service.createProfile(firstName, lastName, address, zip, phoneNumber, email);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(profile);
		assertEquals(error, "Zip code cannot be blank.");
	}


	@Test
	public void testUpdateProfile() {
		assertEquals(0, service.getAllProfiles().size());

		String firstName = "Gary";
		String lastName = "Jimmy";
		String email = "garyjimmy@mail.com";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String zip = "G79 DE4";
		Customer customer = null;
		try {
			customer = service.updateProfile(CUSTOMER_USERNAME, firstName, lastName, address, zip, phoneNumber, email);
		}catch (IllegalArgumentException e) {
			fail();
		}
		
		assertNotNull(customer);
		assertEquals(firstName, customer.getProfile().getFirstName());
		assertEquals(lastName, customer.getProfile().getLastName());
		assertEquals(address, customer.getProfile().getAddress());
		assertEquals(email, customer.getProfile().getEmail());
		assertEquals(phoneNumber, customer.getProfile().getPhoneNumber());
		assertEquals(zip, customer.getProfile().getZipCode());
	}

	@Test
	public void testUpdateProfileCustomerNotFound() {
		assertEquals(0, service.getAllProfiles().size());

		String firstName = "Gary";
		String lastName = "Jimmy";
		String email = "garyjimmy@mail.com";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String zip = "G79 DE4";
		Customer customer = null;
		String error = null;
		try {
			customer = service.updateProfile(firstName, firstName, lastName, address, zip, phoneNumber, email);
		}catch (IllegalArgumentException e) {
			error=e.getMessage();
		}

		assertNull(customer);
		assertEquals(error, "Customer not found.");
	}
	
	@Test
	public void testUpdateProfileInvalidEmail() {
		assertEquals(0, service.getAllProfiles().size());

		String firstName = "Gary";
		String lastName = "Jimmy";
		String email = "garyjimmymail.com";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String zip = "G79 DE4";
		Customer customer = null;
		String error = null;
		try {
			customer = service.updateProfile(CUSTOMER_USERNAME, firstName, lastName, address, zip, phoneNumber, email);
		}catch (IllegalArgumentException e) {
			error=e.getMessage();
		}

		assertNull(customer);
		assertEquals(error, "Invalid email.");
	}

	
	@Test
	public void testUpdateProfileInvalidPhone() {
		assertEquals(0, service.getAllProfiles().size());

		String firstName = "Gary";
		String lastName = "Jimmy";
		String email = "garyjimmy@mail.com";
		String phoneNumber = "abcdefgh";
		String address = "222, 5th Ave";
		String zip = "G79 DE4";
		Customer customer = null;
		String error = null;
		try {
			customer = service.updateProfile(CUSTOMER_USERNAME, firstName, lastName, address, zip, phoneNumber, email);
		}catch (IllegalArgumentException e) {
			error=e.getMessage();
		}

		assertNull(customer);
		assertEquals(error, "Invalid phone number.");
	}
	
	@Test
	public void testUpdateProfileEmailAlreadyExists() {
		assertEquals(0, service.getAllProfiles().size());

		String firstName = "Gary";
		String lastName = "Jimmy";
		String email = PROFILE_EMAIL2;
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String zip = "G79 DE4";
		Customer customer = null;
		String error=null;
		try {
			customer = service.updateProfile(CUSTOMER_USERNAME, firstName, lastName, address, zip, phoneNumber, email);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(customer);
		assertEquals(error, "Customer with email entered already exists.");
	}

	@Test
	public void testInvalidEmailMissingAtSign() {
		assertEquals(0, service.getAllProfiles().size());

		String firstName = "Gary";
		String lastName = "Jimmy";
		String email = "garyjimmymail.com";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String zip = "G79 DE4";
		Profile profile = null;
		String error = "";
		try {
			profile = service.createProfile(firstName, lastName, address, zip, phoneNumber, email);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(profile);
		assertEquals(error, "Invalid email.");

	}


	@Test
	public void testInvalidEmailMissingDot() {
		assertEquals(0, service.getAllProfiles().size());

		String firstName = "Gary";
		String lastName = "Jimmy";
		String email = "garyjimmy@mailcom";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String zip = "G79 DE4";
		Profile profile = null;
		String error = "";
		try {
			profile = service.createProfile(firstName, lastName, address, zip, phoneNumber, email);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(profile);
		assertEquals(error, "Invalid email.");

	}

	@Test
	public void testInvalidEmailDotBeforeAtSign() {
		assertEquals(0, service.getAllProfiles().size());

		String firstName = "Gary";
		String lastName = "Jimmy";
		String email = "garyjimmy.mail@com";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String zip = "G79 DE4";
		Profile profile = null;
		String error = "";
		try {
			profile = service.createProfile(firstName, lastName, address, zip, phoneNumber, email);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(profile);
		assertEquals(error, "Invalid email.");

	}

	@Test
	public void testInvalidEmailNoCharBeforeAt() {
		assertEquals(0, service.getAllProfiles().size());

		String firstName = "Gary";
		String lastName = "Jimmy";
		String email = "@mail.com";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String zip = "G79 DE4";
		Profile profile = null;
		String error = "";
		try {
			profile = service.createProfile(firstName, lastName, address, zip, phoneNumber, email);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(profile);
		assertEquals(error, "Invalid email.");

	}


	@Test
	public void testInvalidEmailNoCharAfterDot() {
		assertEquals(0, service.getAllProfiles().size());

		String firstName = "Gary";
		String lastName = "Jimmy";
		String email = "garyjimmy@mail.";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String zip = "G79 DE4";
		Profile profile = null;
		String error = "";
		try {
			profile = service.createProfile(firstName, lastName, address, zip, phoneNumber, email);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(profile);
		assertEquals(error, "Invalid email.");

	}

}
