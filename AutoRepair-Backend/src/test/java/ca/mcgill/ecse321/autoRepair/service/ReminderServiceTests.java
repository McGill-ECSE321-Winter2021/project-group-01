package ca.mcgill.ecse321.autoRepair.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.autoRepair.dao.CarRepository;
import ca.mcgill.ecse321.autoRepair.dao.ChosenServiceRepository;
import ca.mcgill.ecse321.autoRepair.dao.CustomerRepository;
import ca.mcgill.ecse321.autoRepair.dao.ProfileRepository;
import ca.mcgill.ecse321.autoRepair.dao.ReminderRepository;
import ca.mcgill.ecse321.autoRepair.model.Car;
import ca.mcgill.ecse321.autoRepair.model.Car.CarTransmission;
import ca.mcgill.ecse321.autoRepair.model.ChosenService;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.Profile;
import ca.mcgill.ecse321.autoRepair.model.Reminder;
import ca.mcgill.ecse321.autoRepair.model.SystemTime;

@ExtendWith(MockitoExtension.class)
public class ReminderServiceTests {

	@Mock
	private ReminderRepository reminderRepository;

	@Mock
	private CustomerRepository cusRepo;

	@Mock
	private ProfileRepository profileRepo;

	@Mock
	private ChosenServiceRepository csRepo;
	
	@Mock
	private CarRepository carRepo;
	
	@InjectMocks
	private CustomerService customerService;
	
	@InjectMocks
	private CarService carService;
	
	@InjectMocks
	private ProfileService profileService;
	
	@InjectMocks
	private ReminderService reminderService;
	
	
	
//	private Customer c;
//	private ChosenService csito;
//	private static final String Customer_Name = "TestCustomerName";
//	private static final String Service_Name = "TestService";
	
    private static final String PROFILE_EMAIL = "TestCustomer@mail.com";
    private static final String PROFILE_FIRSTNAME = "Bob";
    private static final String PROFILE_LASTNAME = "Fisher";
    private static final String PROFILE_ADDRESS = "1000, MEMORY LANE";
    private static final String PROFILE_PHONE = "5141234567";
    private static final String PROFILE_ZIP = "55555";
    
    private static final String CAR_MODEL = "BMW X6";
    private static final String CAR_PLATE = "123 ABC";
    private static final CarTransmission CAR_TRANSMISSION = CarTransmission.Automatic;
    
    private static final String CUSTOMER_USERNAME = "TestCustomer";
    private static final String CUSTOMER_PASSWORD = "TestPassword123";
	
	private static final String Reminder_Description = "TestDescription";
	private static final Date d = Date.valueOf("2021-12-12");
	private static final Time t = Time.valueOf("09:00:00");
	

	
	private static final String Reminder_Description2 = "TestDescription2";
	private static final Date d2 = Date.valueOf("2021-12-13");
	private static final Time t2 = Time.valueOf("10:00:00");
	
	private static final String CSName = "TestName";
	private static final int duration = 8;
	private static final Double thePrice = 321.1;
	
	private static final String CSName2 = "csName";
	private static final int duration2 = 10;
	private static final Double thePrice2 = 321.2;
	
	private static final String CUSTOMER2_USERNAME ="TestCustomer2";
	private static final String CUSTOMER2_PASSWORD ="TestPassword123";

	private static final String PROFILE2_EMAIL ="TestCustomer@mail.com";
	private static final String PROFILE2_FIRSTNAME ="Lionel";
	private static final String PROFILE2_LASTNAME ="Messi";
	private static final String PROFILE2_ADDRESS ="1000, MEMORY LANE";
	private static final String PROFILE2_PHONE ="5141234567";
	private static final String PROFILE2_ZIP ="55555";

	private static final String CAR2_MODEL ="BMW X7";
	private static final String CAR2_PLATE ="124 ABC";
	private static final CarTransmission CAR2_TRANSMISSION = CarTransmission.Automatic;
	
	@BeforeEach
	public void setMockOutput() {

		lenient().when(reminderRepository.findByCustomerAndChosenService(any(Customer.class),any(ChosenService.class))).thenAnswer((InvocationOnMock invocation) -> {
			if(((Customer) invocation.getArgument(0)).getUsername().equals(CUSTOMER_USERNAME) 
					&& ((ChosenService) invocation.getArgument(1)).getName().equals(CSName)) {

				ChosenService CS = new ChosenService();
				CS.setName(CSName);
				CS.setDuration(duration);
				CS.setPayment(thePrice);
				
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
				
				Reminder r = new Reminder();
				r.setDescription(Reminder_Description);
				r.setDate(d);
				r.setTime(t);
				r.setChosenService(CS);
				r.setCustomer(customer);
				
				return r;

			}else {
				return null;
			}
		});
		
		lenient().when(reminderRepository.findByCustomer(any(Customer.class))).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(CUSTOMER_USERNAME)) {

				ChosenService CS = new ChosenService();
				CS.setName(CSName);
				CS.setDuration(duration);
				CS.setPayment(thePrice);
				
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
				
				Reminder r = new Reminder();
				r.setDescription(Reminder_Description);
				r.setDate(d);
				r.setTime(t);
				r.setChosenService(CS);
				r.setCustomer(customer);
				
				///////////////////////////////////////////
				
				ChosenService CS2 = new ChosenService();
				CS2.setName(CSName2);
				CS2.setDuration(duration2);
				CS2.setPayment(thePrice2);
				
				Profile profile2 = new Profile();
				profile2.setFirstName(PROFILE2_FIRSTNAME);
				profile2.setLastName(PROFILE2_LASTNAME);
				profile2.setAddress(PROFILE2_ADDRESS);
				profile2.setEmail(PROFILE2_EMAIL);
				profile2.setPhoneNumber(PROFILE2_PHONE);
				profile2.setZipCode(PROFILE2_ZIP);

				Car car2 = new Car();
				car2.setModel(CAR2_MODEL);
				car2.setPlateNumber(CAR2_PLATE);
				car2.setTransmission(CAR2_TRANSMISSION);
				List<Car> cars2 = new ArrayList<Car>();
				cars2.add(car2);

				Customer customer2 = new Customer();
				customer2.setUsername(CUSTOMER2_USERNAME);
				customer2.setPassword(CUSTOMER2_PASSWORD);
				customer2.setProfile(profile);
				customer2.setCars(cars);
				
				Reminder r2 = new Reminder();
				r2.setDescription(Reminder_Description2);
				r2.setDate(d2);
				r2.setTime(t2);
				r2.setChosenService(CS2);
				r2.setCustomer(customer2);
				
				List<Reminder> reminderList = new ArrayList<Reminder>();
				reminderList.add(r);
				reminderList.add(r2);
				
				return reminderList;
			} else {
				return null;
			}
		});
		
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
		 lenient().when(csRepo.findChosenServiceByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
	            if (invocation.getArgument(0).equals(CSName) || invocation.getArgument(0).equals(CSName2)) {
	                ChosenService chosenService = new ChosenService();
	                if (invocation.getArgument(0).equals(CSName)) {
	                    chosenService.setName(CSName);
	                    chosenService.setDuration(duration);
	                    chosenService.setPayment(thePrice);
	                }
	                if (invocation.getArgument(0).equals(CSName2)) {
	                    chosenService.setName(CSName2);
	                    chosenService.setDuration(duration2);
	                    chosenService.setPayment(thePrice2);
	                }
	                return chosenService;
	            } else {
	                return null;
	            }
	        });
		
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(reminderRepository.save(any(Reminder.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(cusRepo.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(profileRepo.save(any(Profile.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(carRepo.save(any(Car.class))).thenAnswer(returnParameterAsAnswer);
	}
	
	@Test
	public void testCreateReminder() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = CUSTOMER_USERNAME;
		String chosenServiceName = CSName2;
	
		Time time = Time.valueOf("11:00:00");
		Date date = Date.valueOf("2021-12-22");
		Reminder r = null;
		String description = "Nice";
		
		try {
			r = reminderService.createReminder(chosenServiceName, customerName, date, description, time);
		}catch (IllegalArgumentException e) {
			fail();
		}
		
		assertNotNull(r);
		assertEquals(chosenServiceName, r.getChosenService().getName());
		assertEquals(customerName,r.getCustomer().getUsername());
		assertEquals(date,r.getDate());
		assertEquals(description,r.getDescription());
		assertEquals(time,r.getTime());
	}
	
	@Test
	public void testNullDescriptionCreateReminder() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = CUSTOMER_USERNAME;
		String chosenServiceName = CSName;
		
		Time time = Time.valueOf("11:00:00");
		Date date = Date.valueOf("2021-12-22");
		Reminder r = null;
		String description = null;
		String error = null;
		
		try {
			r = reminderService.createReminder(chosenServiceName, customerName, date, description, time);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("Description Invalid",error);
	}
	
	@Test
	public void testEmptyDescriptionCreateReminder() {
	assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = CUSTOMER_USERNAME;
		String chosenServiceName = CSName;
		
		Time time = Time.valueOf("11:00:00");
		Date date = Date.valueOf("2021-12-22");
		Reminder r = null;
		String description = "";
		String error = null;
		
		try {
			r = reminderService.createReminder(chosenServiceName, customerName, date, description, time);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("Description Invalid",error);
	}
	
	@Test
	public void testSpacesDescriptionCreateReminder() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = CUSTOMER_USERNAME;
		String chosenServiceName = CSName;
		
		Time time = Time.valueOf("11:00:00");
		Date date = Date.valueOf("2021-12-22");
		Reminder r = null;
		String description = "      ";
		String error = null;
		
		try {
			r = reminderService.createReminder(chosenServiceName, customerName, date, description, time);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("Description Invalid",error);
	}
	
	@Test
	public void testLongDescriptionCreateReminder() {
		assertEquals(0, reminderService.getAllReminders().size()); 
	
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = CUSTOMER_USERNAME;
		String chosenServiceName = CSName;
		
		Time time = Time.valueOf("11:00:00");
		Date date = Date.valueOf("2021-12-22");
		Reminder r = null;
		String description = "heeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee";
		String error = null;
		
		try {
			r = reminderService.createReminder(chosenServiceName, customerName, date, description, time);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("Description Invalid, must be less than 50 characters",error);
	}
	
	@Test
	public void testNullDateCreateReminder() {
		assertEquals(0, reminderService.getAllReminders().size());
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = CUSTOMER_USERNAME;
		String chosenServiceName = CSName;
		
		Time time = Time.valueOf("11:00:00");
		Date date = null;//Date.valueOf("2021-12-22");
		Reminder r = null;
		String description = "Nice";
		String error = null;
		
		try {
			r = reminderService.createReminder(chosenServiceName, customerName, date, description, time);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("Date Invalid",error);
	}
	
	@Test
	public void testNullTimeCreateReminder() {
		assertEquals(0, reminderService.getAllReminders().size());
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = CUSTOMER_USERNAME;
		String chosenServiceName = CSName;
		
		Time time = null;//Time.valueOf("11:00:00");
		Date date = Date.valueOf("2021-12-22");
		Reminder r = null;
		String description = "Nice";
		String error = null;
		
		try {
			r = reminderService.createReminder(chosenServiceName, customerName, date, description, time);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("Time Invalid",error);
	}
	
	@Test
	public void testDatePassedCreateReminder() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = CUSTOMER_USERNAME;
		String chosenServiceName = CSName;
		
		Time time = Time.valueOf("11:00:00");
		Date date = Date.valueOf("2021-03-24");
		Reminder r = null;
		String description = "Nice";
		String error = null;
		
		try {
			r = reminderService.createReminder(chosenServiceName, customerName, date, description, time);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("Date has passed",error);

	}
	
	@Test
	public void testTimePassedSameDayCreateReminder() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = CUSTOMER_USERNAME;
		String chosenServiceName = CSName;
		
		Time time = Time.valueOf("07:00:00");
		Date date = Date.valueOf("2021-05-31");
		Reminder r = null;
		String description = "Nice";
		String error = null;
		
		try {
			r = reminderService.createReminder(chosenServiceName, customerName, date, description, time);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("Time has passed",error);

	}
	
	@Test
	public void testNullCustomerCreateReminder() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = null;
		String chosenServiceName = CSName;
		
		Time time = Time.valueOf("11:00:00");
		Date date = Date.valueOf("2021-12-22");
		Reminder r = null;
		String description = "Nice";
		String error = null;
		
		try {
			r = reminderService.createReminder(chosenServiceName, customerName, date, description, time);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("Customer Invalid",error);
	}
	
	@Test
	public void testEmptyCustomerCreateReminder() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = "";
		String chosenServiceName = CSName;
		
		Time time = Time.valueOf("11:00:00");
		Date date = Date.valueOf("2021-12-22");
		Reminder r = null;
		String description = "Nice";
		String error = null;
		
		try {
			r = reminderService.createReminder(chosenServiceName, customerName, date, description, time);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("Customer Invalid",error);
	}
	
	@Test
	public void testSpacesCustomerCreateReminder() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = "          ";
		String chosenServiceName = CSName;
		
		Time time = Time.valueOf("11:00:00");
		Date date = Date.valueOf("2021-12-22");
		Reminder r = null;
		String description = "Nice";
		String error = null;
		
		try {
			r = reminderService.createReminder(chosenServiceName, customerName, date, description, time);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("Customer Invalid",error);
	}
	
	@Test
	public void testNullChosenServiceCreateReminder() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = CUSTOMER_USERNAME;
		String chosenServiceName = null;
		
		Time time = Time.valueOf("11:00:00");
		Date date = Date.valueOf("2021-12-22");
		Reminder r = null;
		String description = "Nice";
		String error = null;
		
		try {
			r = reminderService.createReminder(chosenServiceName, customerName, date, description, time);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("Service Invalid",error);
	}
	
	@Test
	public void testEmptyChosenServiceCreateReminder() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = CUSTOMER_USERNAME;
		String chosenServiceName = "";
		
		Time time = Time.valueOf("11:00:00");
		Date date = Date.valueOf("2021-12-22");
		Reminder r = null;
		String description = "Nice";
		String error = null;
		
		try {
			r = reminderService.createReminder(chosenServiceName, customerName, date, description, time);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("Service Invalid",error);
	}
	
	@Test
	public void testSpacesChosenServiceCreateReminder() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = CUSTOMER_USERNAME;
		String chosenServiceName = "  ";
		
		Time time = Time.valueOf("11:00:00");
		Date date = Date.valueOf("2021-12-22");
		Reminder r = null;
		String description = "Nice";
		String error = null;
		
		try {
			r = reminderService.createReminder(chosenServiceName, customerName, date, description, time);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("Service Invalid",error);
	}
	
	@Test
	public void testNotExistingCustomerCreateReminder() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = "Me";
		String chosenServiceName = CSName;
		
		Time time = Time.valueOf("11:00:00");
		Date date = Date.valueOf("2021-12-22");
		Reminder r = null;
		String description = "Nice";
		String error = null;
		
		try {
			r = reminderService.createReminder(chosenServiceName, customerName, date, description, time);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("The following user does not exist: " + customerName,error);
	}
	
	@Test
	public void testNotExistingChosenServiceCreateReminder() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = CUSTOMER_USERNAME;
		String chosenServiceName = "change tire";
		
		Time time = Time.valueOf("11:00:00");
		Date date = Date.valueOf("2021-12-22");
		Reminder r = null;
		String description = "Nice";
		String error = null;
		
		try {
			r = reminderService.createReminder(chosenServiceName, customerName, date, description, time);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("The following service does not exist: " + chosenServiceName,error);
	}
	
	
	@Test
	public void testCreateSameReminder() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = CUSTOMER_USERNAME;
		String chosenServiceName = CSName;
		
		Time time = Time.valueOf("11:00:00");
		Date date = Date.valueOf("2021-12-22");
		Reminder r = null;
		String description = "Nice";
		String error = null;
		
		try {
			r = reminderService.createReminder(chosenServiceName, customerName, date, description, time);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("This reminder is already created",error);
	}
		
	
	@Test
	public void testEditReminder() {//STILL
		assertEquals(0, reminderService.getAllReminders().size());
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = CUSTOMER_USERNAME;
		String oldServiceName = CSName;
		String chosenServiceName = CSName2;
	    String newDescription = "Hi";
		Date dBefore = Date.valueOf("2021-12-10");
		Time tBefore = Time.valueOf("08:00:00");
	    
		Reminder r = null;

		try {
			r = reminderService.editReminder(oldServiceName, chosenServiceName, customerName, dBefore, newDescription, tBefore);
		}catch (IllegalArgumentException e) {
			fail();
		}
		
		assertNotNull(r);
		assertEquals(chosenServiceName, r.getChosenService().getName());
		assertEquals(customerName,r.getCustomer().getUsername());
		assertEquals(dBefore,r.getDate());
		assertEquals(newDescription,r.getDescription());
		assertEquals(tBefore,r.getTime());
	
	}

	@Test
	public void testEditReminderLateDate() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = CUSTOMER_USERNAME;
		String oldServiceName = CSName;
		String chosenServiceName = CSName2;
	    String newDescription = "Hi";
		Date dBefore = Date.valueOf("2021-12-14");
		Time tBefore = Time.valueOf("08:00:00");
	    
		Reminder r = null;
		String error = null;

		try {
			r = reminderService.editReminder(oldServiceName, chosenServiceName, customerName, dBefore, newDescription, tBefore);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("Reminder already sent, cannot be modified",error);
	}
	
	@Test
	public void testEditReminderEarlyDate() {
		assertEquals(0, reminderService.getAllReminders().size());
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = CUSTOMER_USERNAME;
		String oldServiceName = CSName;
		String chosenServiceName = CSName2;
	    String newDescription = "Hi";
		Date dBefore = Date.valueOf("2021-04-14");
		Time tBefore = Time.valueOf("07:00:00");
	    
		Reminder r = null;
		String error = null;

		try {
			r = reminderService.editReminder(oldServiceName, chosenServiceName, customerName, dBefore, newDescription, tBefore);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("Date has passed",error);
	}
	
	@Test
	public void testEditReminderTodayEarlyTime() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = CUSTOMER_USERNAME;
		String oldServiceName = CSName;
		String chosenServiceName = CSName2;
	    String newDescription = "Hi";
		Date dBefore = Date.valueOf("2021-05-31");
		Time tBefore = Time.valueOf("07:00:00");
	    
		Reminder r = null;
		String error = null;

		try {
			r = reminderService.editReminder(oldServiceName, chosenServiceName, customerName, dBefore, newDescription, tBefore);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("Time has passed",error);
	}
	
	@Test
	public void testEditReminderSameDateLateTime() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = CUSTOMER_USERNAME;
		String oldServiceName = CSName;
		String chosenServiceName = CSName2;
	    String newDescription = "Hi";
		Date dBefore = Date.valueOf("2021-12-12");
		Time tBefore = Time.valueOf("10:00:00");
	    
		Reminder r = null;
		String error = null;
		
		try {
			r = reminderService.editReminder(oldServiceName, chosenServiceName, customerName, dBefore, newDescription, tBefore);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("Reminder already sent today, cannot be modified",error);
	}
	
	@Test
	public void testEditReminderNullCustomer() {
		assertEquals(0, reminderService.getAllReminders().size());
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = null;
		String oldServiceName = CSName;
		String chosenServiceName = CSName2;
	    String newDescription = "Hi";
		Date dBefore = Date.valueOf("2021-12-14");
		Time tBefore = Time.valueOf("08:00:00");
	    
		Reminder r = null;
		String error = null;

		try {
			r = reminderService.editReminder(oldServiceName, chosenServiceName, customerName, dBefore, newDescription, tBefore);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("Customer Invalid",error);
	}
	
	@Test
	public void testEditReminderEmptyCustomer() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = "";
		String oldServiceName = CSName;
		String chosenServiceName = CSName2;
	    String newDescription = "Hi";
		Date dBefore = Date.valueOf("2021-12-14");
		Time tBefore = Time.valueOf("08:00:00");
	    
		Reminder r = null;
		String error = null;

		try {
			r = reminderService.editReminder(oldServiceName, chosenServiceName, customerName, dBefore, newDescription, tBefore);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("Customer Invalid",error);
	}
	
	@Test
	public void testEditReminderSpacesCustomer() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = "       ";
		String oldServiceName = CSName;
		String chosenServiceName = CSName2;
	    String newDescription = "Hi";
		Date dBefore = Date.valueOf("2021-12-14");
		Time tBefore = Time.valueOf("08:00:00");
	    
		Reminder r = null;
		String error = null;

		try {
			r = reminderService.editReminder(oldServiceName, chosenServiceName, customerName, dBefore, newDescription, tBefore);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("Customer Invalid",error);
	}
	
	@Test
	public void testEditReminderNotExistCustomer() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = "theCustomer";
		String oldServiceName = CSName;
		String chosenServiceName = CSName2;
	    String newDescription = "Hi";
		Date dBefore = Date.valueOf("2021-12-14");
		Time tBefore = Time.valueOf("08:00:00");
	    
		Reminder r = null;
		String error = null;

		try {
			r = reminderService.editReminder(oldServiceName, chosenServiceName, customerName, dBefore, newDescription, tBefore);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("The following user does not exist: " + customerName,error);
	}
	
	
	
	
	@Test
	public void testEditReminderNullOldService() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = CUSTOMER_USERNAME;
		String oldServiceName = null;
		String chosenServiceName = CSName2;
	    String newDescription = "Hi";
		Date dBefore = Date.valueOf("2021-12-14");
		Time tBefore = Time.valueOf("08:00:00");
	    
		Reminder r = null;
		String error = null;

		try {
			r = reminderService.editReminder(oldServiceName, chosenServiceName, customerName, dBefore, newDescription, tBefore);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("Old Service Invalid",error);
	}
	
	@Test
	public void testEditReminderEmptyOldService() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = CUSTOMER_USERNAME;
		String oldServiceName = "";
		String chosenServiceName = CSName2;
	    String newDescription = "Hi";
		Date dBefore = Date.valueOf("2021-12-14");
		Time tBefore = Time.valueOf("08:00:00");
	    
		Reminder r = null;
		String error = null;

		try {
			r = reminderService.editReminder(oldServiceName, chosenServiceName, customerName, dBefore, newDescription, tBefore);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("Old Service Invalid",error);
	}
	
	@Test
	public void testEditReminderSpacesOldService() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = CUSTOMER_USERNAME;
		String oldServiceName = "    ";
		String chosenServiceName = CSName2;
	    String newDescription = "Hi";
		Date dBefore = Date.valueOf("2021-12-14");
		Time tBefore = Time.valueOf("08:00:00");
	    
		Reminder r = null;
		String error = null;

		try {
			r = reminderService.editReminder(oldServiceName, chosenServiceName, customerName, dBefore, newDescription, tBefore);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("Old Service Invalid",error);
	}
	
	
	@Test
	public void testEditReminderNotExistOldService() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = CUSTOMER_USERNAME;
		String oldServiceName = "maintenance";
		String chosenServiceName = CSName2;
	    String newDescription = "Hi";
		Date dBefore = Date.valueOf("2021-12-14");
		Time tBefore = Time.valueOf("08:00:00");
	    
		Reminder r = null;
		String error = null;

		try {
			r = reminderService.editReminder(oldServiceName, chosenServiceName, customerName, dBefore, newDescription, tBefore);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("The following service does not exist: " + oldServiceName,error);
	}
	
	@Test
	public void testEditReminderEmptyNullNewService() {
		assertEquals(0, reminderService.getAllReminders().size());
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = CUSTOMER_USERNAME;
		String oldServiceName = CSName;
		String chosenServiceName = null;
	    String newDescription = "Hi";
		Date dBefore = Date.valueOf("2021-12-14");
		Time tBefore = Time.valueOf("08:00:00");
	    
		Reminder r = null;
		String error = null;

		try {
			r = reminderService.editReminder(oldServiceName, chosenServiceName, customerName, dBefore, newDescription, tBefore);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("New Service Invalid",error);
	}
	
	@Test
	public void testEditReminderEmptyNewService() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = CUSTOMER_USERNAME;
		String oldServiceName = CSName;
		String chosenServiceName = "";
	    String newDescription = "Hi";
		Date dBefore = Date.valueOf("2021-12-14");
		Time tBefore = Time.valueOf("08:00:00");
	    
		Reminder r = null;
		String error = null;

		try {
			r = reminderService.editReminder(oldServiceName, chosenServiceName, customerName, dBefore, newDescription, tBefore);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("New Service Invalid",error);
	}
	
	
	@Test
	public void testEditReminderSpacesNewService() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = CUSTOMER_USERNAME;
		String oldServiceName = CSName;
		String chosenServiceName = "     ";
	    String newDescription = "Hi";
		Date dBefore = Date.valueOf("2021-12-14");
		Time tBefore = Time.valueOf("08:00:00");
	    
		Reminder r = null;
		String error = null;

		try {
			r = reminderService.editReminder(oldServiceName, chosenServiceName, customerName, dBefore, newDescription, tBefore);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("New Service Invalid",error);
	}
	
	@Test
	public void testEditReminderNotExistNewService() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = CUSTOMER_USERNAME;
		String oldServiceName = CSName;
		String chosenServiceName = "fix car";
	    String newDescription = "Hi";
		Date dBefore = Date.valueOf("2021-12-14");
		Time tBefore = Time.valueOf("08:00:00");
	    
		Reminder r = null;
		String error = null;

		try {
			r = reminderService.editReminder(oldServiceName, chosenServiceName, customerName, dBefore, newDescription, tBefore);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("The following service does not exist: " + chosenServiceName,error);
	}
	
	
	@Test
	public void testEditReminderNullDate() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = CUSTOMER_USERNAME;
		String oldServiceName = CSName;
		String chosenServiceName = CSName2;
	    String newDescription = "Hi";
		Date dBefore = null;//Date.valueOf("2021-12-14");
		Time tBefore = Time.valueOf("08:00:00");
	    
		Reminder r = null;
		String error = null;

		try {
			r = reminderService.editReminder(oldServiceName, chosenServiceName, customerName, dBefore, newDescription, tBefore);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("Date Invalid",error);
	}
	
	@Test
	public void testEditReminderNullTime() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = CUSTOMER_USERNAME;
		String oldServiceName = CSName;
		String chosenServiceName = CSName2;
	    String newDescription = "Hi";
		Date dBefore = Date.valueOf("2021-12-14");
		Time tBefore = null;
	    
		Reminder r = null;
		String error = null;

		try {
			r = reminderService.editReminder(oldServiceName, chosenServiceName, customerName, dBefore, newDescription, tBefore);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("Time Invalid",error);
	}
	
	@Test
	public void testEditReminderNullDescription() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = CUSTOMER_USERNAME;
		String oldServiceName = CSName;
		String chosenServiceName = CSName2;
	    String newDescription = null;
		Date dBefore = Date.valueOf("2021-12-14");
		Time tBefore = Time.valueOf("08:00:00");
	    
		Reminder r = null;
		String error = null;

		try {
			r = reminderService.editReminder(oldServiceName, chosenServiceName, customerName, dBefore, newDescription, tBefore);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("Description Invalid",error);
	}
	
	@Test
	public void testEditReminderLongDescription() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = CUSTOMER_USERNAME;
		String oldServiceName = CSName;
		String chosenServiceName = CSName2;
	    String newDescription = "hhheeeeelllloooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo";
		Date dBefore = Date.valueOf("2021-12-14");
		Time tBefore = Time.valueOf("08:00:00");
	    
		Reminder r = null;
		String error = null;

		try {
			r = reminderService.editReminder(oldServiceName, chosenServiceName, customerName, dBefore, newDescription, tBefore);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("Description Invalid, must be less than 50 characters",error);
	}
	
	@Test
	public void testEditReminderEmptyDescription() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = CUSTOMER_USERNAME;
		String oldServiceName = CSName;
		String chosenServiceName = CSName2;
	    String newDescription = "";
		Date dBefore = Date.valueOf("2021-12-14");
		Time tBefore = Time.valueOf("08:00:00");
	    
		Reminder r = null;
		String error = null;

		try {
			r = reminderService.editReminder(oldServiceName, chosenServiceName, customerName, dBefore, newDescription, tBefore);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("Description Invalid",error);
	}
	
	@Test
	public void testEditReminderSpacesDescription() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
		String customerName = CUSTOMER_USERNAME;
		String oldServiceName = CSName;
		String chosenServiceName = CSName2;
	    String newDescription = "      ";
		Date dBefore = Date.valueOf("2021-12-14");
		Time tBefore = Time.valueOf("08:00:00");
	    
		Reminder r = null;
		String error = null;

		try {
			r = reminderService.editReminder(oldServiceName, chosenServiceName, customerName, dBefore, newDescription, tBefore);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertNull(r);
		assertEquals("Description Invalid",error);
	}
	

	@Test
	public void testDeleteReminder() {//STILL
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
	    String customerName = CUSTOMER_USERNAME;
		String serviceName = CSName;
		boolean r = false;
		
		try {
			r = reminderService.deleteReminder(serviceName, customerName);
		}catch (IllegalArgumentException e) {
			fail();
		}
		
		assertTrue(r);
	}
	
	@Test
	public void testDeleteReminderNullCustomerName() {//STILL
		assertEquals(0, reminderService.getAllReminders().size());
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
	    String customerName = null;
		String serviceName = CSName;
		boolean r = false;
		String error = null;
		
		try {
			r = reminderService.deleteReminder(serviceName, customerName);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertFalse(r);
		assertEquals("Customer Invalid",error);
	}
	
	@Test
	public void testDeleteReminderEmptyCustomerName() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
	    String customerName = "";
		String serviceName = CSName;
		boolean r = false;
		String error = null;
		
		try {
			r = reminderService.deleteReminder(serviceName, customerName);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertFalse(r);
		assertEquals("Customer Invalid",error);
	}
	
	@Test
	public void testDeleteReminderSpacesCustomerName() {
		assertEquals(0, reminderService.getAllReminders().size());
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
	    String customerName = "          ";
		String serviceName = CSName;
		boolean r = false;
		String error = null;
		
		try {
			r = reminderService.deleteReminder(serviceName, customerName);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertFalse(r);
		assertEquals("Customer Invalid",error);
	}
	
	@Test
	public void testDeleteReminderCustomerNotExist() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
	    String customerName = "Messi";
		String serviceName = CSName;
		boolean r = false;
		String error = null;
		
		try {
			r = reminderService.deleteReminder(serviceName, customerName);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertFalse(r);
		assertEquals("The following user does not exist: " + customerName,error);
	}
	
	
	@Test
	public void testDeleteReminderNullServiceName() {
		assertEquals(0, reminderService.getAllReminders().size());
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
	    String customerName = CUSTOMER_USERNAME;
		String serviceName = null;
		boolean r = false;
		String error = null;
		
		try {
			r = reminderService.deleteReminder(serviceName, customerName);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertFalse(r);
		assertEquals("Service Invalid",error);
	}	
	
	@Test
	public void testDeleteReminderEmptyServiceName() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
	    String customerName = CUSTOMER_USERNAME;
		String serviceName = "";
		boolean r = false;
		String error = null;
		
		try {
			r = reminderService.deleteReminder(serviceName, customerName);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertFalse(r);
		assertEquals("Service Invalid",error);
	}
	
	@Test
	public void testDeleteReminderSpacesServiceName() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
	    String customerName = CUSTOMER_USERNAME;
		String serviceName = "      ";
		boolean r = false;
		String error = null;
		
		try {
			r = reminderService.deleteReminder(serviceName, customerName);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertFalse(r);
		assertEquals("Service Invalid",error);
	}
	
	@Test
	public void testDeleteReminderServiceNotExist() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
	    String customerName = CUSTOMER_USERNAME;
		String serviceName = "Change door";
		boolean r = false;
		String error = null;
		
		try {
			r = reminderService.deleteReminder(serviceName, customerName);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertFalse(r);
		assertEquals("The following service does not exist: " + serviceName,error);
	}
	
	@Test
	public void testDeleteReminderNotExist() {
		assertEquals(0, reminderService.getAllReminders().size()); 
		
		SystemTime.setSysTime(Time.valueOf("08:00:00"));
	    SystemTime.setSysDate(Date.valueOf("2021-05-31"));
		
	    String customerName = CUSTOMER_USERNAME;
		String serviceName = CSName2;
		boolean r = false;
		String error = null;
		
		try {
			r = reminderService.deleteReminder(serviceName, customerName);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertFalse(r);
		assertEquals("Reminder does not exist",error);
	}
	
}