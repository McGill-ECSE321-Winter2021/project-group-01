package ca.mcgill.ecse321.autoRepair.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.autoRepair.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestAutoRepairPersistence {

	@Autowired
	EntityManager entityManager;
	
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
	private BookableServiceRepository bookableServiceRepository;
	@Autowired
	private CarRepository carRepository;
	@Autowired
	private OperatingHourRepository operatingHourRepository;
	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
	private ServiceComboRepository serviceComboRepository;
	@Autowired
	private ServiceRepository serviceRepository;
	@Autowired
	private TimeSlotRepository timeSlotRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BusinessRepository businessRepository;
	
	@AfterEach
	public void clearDatabase() {

		assistantRepository.deleteAll();
		appointmentRepository.deleteAll();
	    profileRepository.deleteAll();
		ownerRepository.deleteAll();
		reminderRepository.deleteAll();
	//	bookableServiceRepository.deleteAll();
		businessRepository.deleteAll();
		carRepository.deleteAll();
		customerRepository.deleteAll();
		operatingHourRepository.deleteAll();
		reviewRepository.deleteAll();
		serviceComboRepository.deleteAll();
		timeSlotRepository.deleteAll();
		userRepository.deleteAll();

	}
//	
//	@Test
//	public void testPersistAndLoadAssitant() {
//		String username = "testAssistant";
//		String password = "testPassword";
//		Assistant assistant = new Assistant();
//		assistant.setUsername(username);
//		assistant.setPassword(password);
//		assistantRepository.save(assistant);
//
//
//		assistant = null;
//
//		assistant = assistantRepository.findAssistantByUsername(username);
//		assertNotNull(assistant);
//		assertEquals(username, assistant.getUsername());
//	}
//	@Test
//	public void testPersistAndLoadService() {
//		String name = "service1";
//		int duration = 30;
//		Service testService = new Service();
//		testService.setName(name);
//		testService.setDuration(duration);
//
//		serviceRepository.save(testService);
//
//		testService = null;
//
//		testService=serviceRepository.findServiceByName("name");
//
//		assertNull(testService);
//		assertEquals(name, testService.getName());
//		assertEquals(duration, testService.getDuration());
//	}
//
//	@Test
//	public void testPersistAndLoadBusiness() {
//		String name = "AutoRepair";
//		String address = "Address";
//		String phoneNumber = "514-000-9999";
//		String email = "autorepair@mcgill.ca";
//		Business business = new Business();
//		business.setName(name);
//		business.setAddress(address);
//		business.setPhoneNumber(phoneNumber);
//		business.setEmail(email);
//
//		businessRepository.save(business);
//
//
//		business = null;
//
//		business = businessRepository.findBusinessByName(name);
//
//		assertNotNull(business);
//		assertEquals(name, business.getName());
//		assertEquals(address, business.getAddress());
//		assertEquals(phoneNumber, business.getPhoneNumber());
//		assertEquals(email, business.getEmail());
//	}
//
//	@Test
//	public void testPersistAndLoadAppointment() {
//		Car testCar = new Car();
//		List<Car> carList = new ArrayList<>();
//		testCar.setModel("testModel");
//		testCar.setPlateNumber("123456");
//		testCar.setTransmission(Car.CarTransmission.Automatic);
//
//		Profile testProfile = new Profile();
//		testProfile.setFirstName("TestName");
//		testProfile.setAddress("Test Address");
//		testProfile.setEmail("testemail@test.com");
//		testProfile.setLastName("TestLastName");
//		testProfile.setPhoneNumber("(123)456-7890");
//		testProfile.setZipCode("H1V 3T2");
//
//		String username = "testCustomer";
//		String password = "testPassword";
//		Customer testCustomer = new Customer();
//		testProfile.setCustomer(testCustomer);
//		testCar.setCustomer(testCustomer);
//		carList.add(testCar);
//		testCustomer.setUsername(username);
//		testCustomer.setPassword(password);
//		testCustomer.setCars(carList);
//		testCustomer.setNoShow(0);
//		testCustomer.setShow(0);
//		testCustomer.setProfile(testProfile);
//
//		TimeSlot testSlot = new TimeSlot();
//		Date startDate = Date.valueOf("2021-02-22");
//		Date endDate = startDate;
//		Time startTime = Time.valueOf("12:00:00");
//		Time endTime = Time.valueOf("14:00:00");
//		testSlot.setEndDate(endDate);
//		testSlot.setEndTime(endTime);
//		testSlot.setStartTime(startTime);
//		testSlot.setStartDate(startDate);
//
//		String name = "service1";
//		int duration = 30;
//		Service testService = new Service();
//		testService.setName(name);
//		testService.setDuration(duration);
//
//		Appointment testAppointment = new Appointment();
//		testAppointment.setCustomer(testCustomer);
//		testAppointment.setBookableService(testService);
//		testAppointment.setTimeSlot(testSlot);
//		testAppointment.setComboItems(null);
//		profileRepository.save(testProfile);
//		carRepository.save(testCar);
//		customerRepository.save(testCustomer);
//		serviceRepository.save(testService);
//		timeSlotRepository.save(testSlot);
//		appointmentRepository.save(testAppointment);
//
//		testAppointment = null;
//
//		testAppointment = appointmentRepository.findAppointmentByTimeSlot(testSlot);
//		assertNotNull(testAppointment);
//		assertEquals(testCustomer.getUsername(), testAppointment.getCustomer().getUsername());
//		assertEquals(testCustomer.getPassword(), testAppointment.getCustomer().getPassword());
//		assertEquals(testCustomer.getNoShow(), testAppointment.getCustomer().getNoShow());
//		assertEquals(testCustomer.getShow(), testAppointment.getCustomer().getShow());
//		assertEquals(testService.getName(),testAppointment.getBookableService().getName());
//		Service testService1 = (Service) testAppointment.getBookableService();
//		assertEquals(testService.getDuration(),testService1.getDuration());
//		assertEquals(testSlot.getEndDate(), testAppointment.getTimeSlot().getEndDate());
//		assertEquals(testSlot.getEndTime(),testAppointment.getTimeSlot().getEndTime());
//		assertEquals(testSlot.getStartTime(),testAppointment.getTimeSlot().getStartTime());
//		assertEquals(testSlot.getStartDate(),testAppointment.getTimeSlot().getStartDate());
//
//	}
//
//	@Test
//	public void testPersistAndLoadOwner() {
//		String name = "TestOwner";
//		String password = "TestPassword";
//		Owner testOwner= new Owner();
//		testOwner.setPassword(password);
//		testOwner.setUsername(name);
//		ownerRepository.save(testOwner);
//
//		testOwner= null;
//
//		testOwner = ownerRepository.findOwnerByUsername(name);
//		assertNotNull(testOwner);
//		assertEquals(name, testOwner.getUsername());
//		assertEquals(password,testOwner.getPassword());
//	}
//
//	@Test
//	public void testPersistAndLoadCustomer(){
//		Car testCar = new Car();
//		List<Car> carList = new ArrayList<>();
//		testCar.setModel("testModel");
//		testCar.setPlateNumber("123456");
//		testCar.setTransmission(Car.CarTransmission.Automatic);
//
//		Profile testProfile = new Profile();
//		testProfile.setFirstName("TestName");
//		testProfile.setAddress("Test Address");
//		testProfile.setEmail("testemail@test.com");
//		testProfile.setLastName("TestLastName");
//		testProfile.setPhoneNumber("(123)456-7890");
//		testProfile.setZipCode("H1V 3T2");
//
//		String username = "testCustomer";
//		String password = "testPassword";
//		Customer testCustomer = new Customer();
//		testProfile.setCustomer(testCustomer);
//		testCar.setCustomer(testCustomer);
//		carList.add(testCar);
//		testCustomer.setUsername(username);
//		testCustomer.setPassword(password);
//		testCustomer.setCars(carList);
//		testCustomer.setNoShow(0);
//		testCustomer.setShow(0);
//		testCustomer.setProfile(testProfile);
//
//		profileRepository.save(testProfile);
//		carRepository.save(testCar);
//		customerRepository.save(testCustomer);
//
//		testCustomer=null;
//
//		testCustomer= customerRepository.findCustomerByUsername(username);
//
//		assertNotNull(testCustomer);
//		assertEquals(username, testCustomer.getUsername());
//		assertEquals(password,testCustomer.getPassword());
//		assertEquals(password,testCustomer.getPassword());
//		assertEquals(testProfile.getAddress(),testCustomer.getProfile().getAddress());
//		assertEquals(testProfile.getEmail(),testCustomer.getProfile().getEmail());
//		assertEquals(testProfile.getFirstName(),testCustomer.getProfile().getFirstName());
//		assertEquals(testProfile.getLastName(),testCustomer.getProfile().getLastName());
//		assertEquals(testProfile.getPhoneNumber(),testCustomer.getProfile().getPhoneNumber());
//		assertEquals(testProfile.getZipCode(),testCustomer.getProfile().getZipCode());
//		assertEquals(carList,testCustomer.getCars());
//		assertEquals(0,testCustomer.getNoShow());
//		assertEquals(0,testCustomer.getShow());
////	}
//	@Test
//	public void testPersistAndLoadOperatingHour() {
//		OperatingHour testOpHour = new OperatingHour();
//			
//		testOpHour.setDayOfWeek(OperatingHour.DayOfWeek.Monday);
//		
//		Time startTime = Time.valueOf("08:30:00");	
//		testOpHour.setStartTime(startTime);
//		
//		Time endTime = Time.valueOf("04:30:00");
//		testOpHour.setEndTime(endTime);
//		operatingHourRepository.save(testOpHour);
//		
//		testOpHour=null;
//		
//		testOpHour=operatingHourRepository.findByDayOfWeek(OperatingHour.DayOfWeek.Monday);
//		
//		assertNotNull(testOpHour);
//		assertEquals(OperatingHour.DayOfWeek.Monday, testOpHour.getDayOfWeek());
//		assertEquals(startTime, testOpHour.getStartTime());
//		assertEquals(endTime,testOpHour.getEndTime());
//		
//	}
	
	
	

//	@Test
//	public void testPersistAndLoadReminder() {
//		AutoRepairShopSystem repairShopSystem = new AutoRepairShopSystem("1");
//		Customer customer = new Customer("TestCustomer", "12345",0,0, null, repairShopSystem);
//		BookableService service = new Service("TestService", repairShopSystem,10);
//
//		customerRepository.save(customer);
//		//Add to bookable service repository
//		Date date = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
//		Time time = java.sql.Time.valueOf(LocalTime.of(11, 35));
//		Reminder reminder = new Reminder("id1","TestReminder",date, time, repairShopSystem,customer);
//		reminderRepository.save(reminder);
//		String reminderId= "id1";
//		reminder =null;
//
//		reminder = reminderRepository.findByCustomerAndBookableService(customer,service);
//		assertNotNull(reminder);
//		assertEquals(reminderId, reminder.getId());
//		assertEquals(customer.getUsername(), reminder.getCustomer().getUsername());
//
//	}

//	@Test
//	public void testPersistAndLoadProfile() {
//		Car testCar = new Car();
//		List<Car> carList = new ArrayList<>();
//		testCar.setModel("testModel");
//		testCar.setPlateNumber("123456");
//		testCar.setTransmission(Car.CarTransmission.Automatic);
//		carList.add(testCar);
//
//		Profile testProfile = new Profile();
//		testProfile.setFirstName("TestName");
//		testProfile.setAddress("Test Address");
//		testProfile.setEmail("testemail@test.com");
//		testProfile.setLastName("TestLastName");
//		testProfile.setPhoneNumber("(123)456-7890");
//		testProfile.setZipCode("H1V 3T2");
//
//		String username = "testCustomer";
//		String password = "testPassword";
//		Customer testCustomer = new Customer();
//		testCustomer.setUsername(username);
//		testCustomer.setPassword(password);
//		testCustomer.setCars(carList);
//		testCustomer.setNoShow(0);
//		testCustomer.setShow(0);
//		testCustomer.setProfile(testProfile);
//
//		customerRepository.save(customer);
//		profileRepository.save(testProfile);
//		String profileId = "profileId";
//		testProfile = null;
//
//		testProfile = profileRepository.findByCustomer(customer);
//		assertNotNull(testProfile);
//		assertEquals(profileId, testProfile.getId());
//		assertEquals(customer.getUsername(), testProfile.getCustomer().getUsername());
//	}

}
