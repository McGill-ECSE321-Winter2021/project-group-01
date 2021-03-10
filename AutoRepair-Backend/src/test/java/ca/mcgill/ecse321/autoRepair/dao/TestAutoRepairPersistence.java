package ca.mcgill.ecse321.autoRepair.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.autoRepair.model.*;
import ca.mcgill.ecse321.autoRepair.model.Car.CarTransmission;

import org.junit.jupiter.api.AfterEach;
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
	private CarRepository carRepository;
	@Autowired
	private OperatingHourRepository operatingHourRepository;
	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
	private ChosenServiceRepository chosenServiceRepository;
	@Autowired
	private TimeSlotRepository timeSlotRepository;
	@Autowired
	private BusinessRepository businessRepository;
	
	@AfterEach
	public void clearDatabase() {
		reviewRepository.deleteAll();
		assistantRepository.deleteAll();
		appointmentRepository.deleteAll();
	    profileRepository.deleteAll();
		ownerRepository.deleteAll();
		reminderRepository.deleteAll();
		businessRepository.deleteAll();
		carRepository.deleteAll();
		customerRepository.deleteAll();
		operatingHourRepository.deleteAll();
		chosenServiceRepository.deleteAll();
		timeSlotRepository.deleteAll();
	}
	
	@Test
	public void testPersistAndLoadAssitant() {
		String username = "testAssistant";
		String password = "testPassword";
		Assistant assistant = new Assistant();
		assistant.setUsername(username);
		assistant.setPassword(password);
		assistantRepository.save(assistant);


		assistant = null;

		assistant = assistantRepository.findAssistantByUsername(username);
		assertNotNull(assistant);
		assertEquals(username, assistant.getUsername());
	}
	@Test
	public void testPersistAndLoadService() {
		String name = "service1";
		int duration = 30;
		ChosenService testService = new ChosenService();
		testService.setName(name);
		testService.setDuration(duration);

		chosenServiceRepository.save(testService);

		testService = null;

		testService= chosenServiceRepository.findChosenServiceByName(name);

		assertNotNull(testService);
		assertEquals(name, testService.getName());
		assertEquals(duration, testService.getDuration());
	}
	@Test
	public void testPersistAndLoadReview() {
		Car testCar = new Car();
		List<Car> carList = new ArrayList<>();
		testCar.setModel("testModel");
		testCar.setPlateNumber("123456");
		testCar.setTransmission(Car.CarTransmission.Automatic);

		Profile testProfile = new Profile();
		testProfile.setFirstName("TestName");
		testProfile.setAddress("Test Address");
		testProfile.setEmail("testemail@test.com");
		testProfile.setLastName("TestLastName");
		testProfile.setPhoneNumber("(123)456-7890");
		testProfile.setZipCode("H1V 3T2");

		String username = "testCustomer";
		String password = "testPassword";
		Customer testCustomer = new Customer();
		testProfile.setCustomer(testCustomer);
		testCar.setCustomer(testCustomer);
		carList.add(testCar);
		testCustomer.setUsername(username);
		testCustomer.setPassword(password);
		testCustomer.setCars(carList);
		testCustomer.setNoShow(0);
		testCustomer.setShow(0);
		testCustomer.setProfile(testProfile);

		TimeSlot testSlot = new TimeSlot();
		Date startDate = Date.valueOf("2021-02-22");
		Date endDate = startDate;
		Time startTime = Time.valueOf("12:00:00");
		Time endTime = Time.valueOf("14:00:00");
		testSlot.setEndDate(endDate);
		testSlot.setEndTime(endTime);
		testSlot.setStartTime(startTime);
		testSlot.setStartDate(startDate);

		String name = "service1";
		int duration = 30;
		ChosenService testService = new ChosenService();
		testService.setName(name);
		testService.setDuration(duration);

		Appointment testAppointment = new Appointment();
		testAppointment.setCustomer(testCustomer);
		testAppointment.setService(testService);
		testAppointment.setTimeSlot(testSlot);
		profileRepository.save(testProfile);
		carRepository.save(testCar);
		customerRepository.save(testCustomer);
		chosenServiceRepository.save(testService);
		timeSlotRepository.save(testSlot);
		appointmentRepository.save(testAppointment);

		
		String description = "The service was great!";
		int serviceRating = 5;
		Review review = new Review();
		review.setDescription(description);
		review.setServiceRating(serviceRating);
		review.setAppointment(testAppointment);
		review.setCustomer(testCustomer);
		review.setService(testService);
		reviewRepository.save(review);
		review = null;
		review = reviewRepository.findReviewByCustomerAndAppointment(testCustomer, testAppointment);
		assertEquals(description, review.getDescription());
		assertEquals(serviceRating, review.getServiceRating());
	}
	@Test
	public void testPersistAndLoadCar() {
		String model = "Lambo";
		String plateNumber = "Number 1";
		Car car = new Car();
		CarTransmission carTransmition = CarTransmission.Automatic;
		car.setModel(model);
		car.setPlateNumber(plateNumber);
		car.setTransmission(carTransmition);
		carRepository.save(car);
		car = null;
		car = carRepository.findCarByPlateNumber(plateNumber);
		assertEquals(model, car.getModel());
		assertEquals(plateNumber, car.getPlateNumber());
		assertEquals(carTransmition, car.getTransmission());
	}
	
	@Test
	public void testPersistAndLoadBusiness() {
		String name = "AutoRepair";
		String address = "Address";
		String phoneNumber = "514-000-9999";
		String email = "autorepair@mcgill.ca";
		Business business = new Business();
		business.setName(name);
		business.setAddress(address);
		business.setPhoneNumber(phoneNumber);
		business.setEmail(email);

		businessRepository.save(business);


		business = null;

		business = businessRepository.findBusinessByName(name);

		assertNotNull(business);
		assertEquals(name, business.getName());
		assertEquals(address, business.getAddress());
		assertEquals(phoneNumber, business.getPhoneNumber());
		assertEquals(email, business.getEmail());
	}

	@Test
	public void testPersistAndLoadAppointment() {
		Car testCar = new Car();
		List<Car> carList = new ArrayList<>();
		testCar.setModel("testModel");
		testCar.setPlateNumber("123456");
		testCar.setTransmission(Car.CarTransmission.Automatic);

		Profile testProfile = new Profile();
		testProfile.setFirstName("TestName");
		testProfile.setAddress("Test Address");
		testProfile.setEmail("testemail@test.com");
		testProfile.setLastName("TestLastName");
		testProfile.setPhoneNumber("(123)456-7890");
		testProfile.setZipCode("H1V 3T2");

		String username = "testCustomer";
		String password = "testPassword";
		Customer testCustomer = new Customer();
		testProfile.setCustomer(testCustomer);
		testCar.setCustomer(testCustomer);
		carList.add(testCar);
		testCustomer.setUsername(username);
		testCustomer.setPassword(password);
		testCustomer.setCars(carList);
		testCustomer.setNoShow(0);
		testCustomer.setShow(0);
		testCustomer.setProfile(testProfile);

		TimeSlot testSlot = new TimeSlot();
		Date startDate = Date.valueOf("2021-02-22");
		Date endDate = startDate;
		Time startTime = Time.valueOf("12:00:00");
		Time endTime = Time.valueOf("14:00:00");
		testSlot.setEndDate(endDate);
		testSlot.setEndTime(endTime);
		testSlot.setStartTime(startTime);
		testSlot.setStartDate(startDate);

		String name = "service1";
		int duration = 30;
		ChosenService testService = new ChosenService();
		testService.setName(name);
		testService.setDuration(duration);

		Appointment testAppointment = new Appointment();
		testAppointment.setCustomer(testCustomer);
		testAppointment.setService(testService);
		testAppointment.setTimeSlot(testSlot);
		profileRepository.save(testProfile);
		carRepository.save(testCar);
		customerRepository.save(testCustomer);
		chosenServiceRepository.save(testService);
		timeSlotRepository.save(testSlot);
		appointmentRepository.save(testAppointment);

		testAppointment = null;

		testAppointment = appointmentRepository.findAppointmentByTimeSlot(testSlot);
		assertNotNull(testAppointment);
		assertEquals(testCustomer.getUsername(), testAppointment.getCustomer().getUsername());
		assertEquals(testCustomer.getPassword(), testAppointment.getCustomer().getPassword());
		assertEquals(testCustomer.getNoShow(), testAppointment.getCustomer().getNoShow());
		assertEquals(testCustomer.getShow(), testAppointment.getCustomer().getShow());
		assertEquals(testService.getName(),testAppointment.getService().getName());
		ChosenService testService1 = testAppointment.getService();
		assertEquals(testService.getDuration(),testService1.getDuration());
		assertEquals(testSlot.getEndDate(), testAppointment.getTimeSlot().getEndDate());
		assertEquals(testSlot.getEndTime(),testAppointment.getTimeSlot().getEndTime());
		assertEquals(testSlot.getStartTime(),testAppointment.getTimeSlot().getStartTime());
		assertEquals(testSlot.getStartDate(),testAppointment.getTimeSlot().getStartDate());

	}

	@Test
	public void testPersistAndLoadOwner() {
		String name = "TestOwner";
		String password = "TestPassword";
		Owner testOwner= new Owner();
		testOwner.setPassword(password);
		testOwner.setUsername(name);
		ownerRepository.save(testOwner);

		testOwner= null;

		testOwner = ownerRepository.findOwnerByUsername(name);
		assertNotNull(testOwner);
		assertEquals(name, testOwner.getUsername());
		assertEquals(password,testOwner.getPassword());
	}

	@Test
	public void testPersistAndLoadCustomer(){
		Car testCar = new Car();
		List<Car> carList = new ArrayList<>();
		testCar.setModel("testModel");
		testCar.setPlateNumber("123456");
		testCar.setTransmission(Car.CarTransmission.Automatic);

		Profile testProfile = new Profile();
		testProfile.setFirstName("TestName");
		testProfile.setAddress("Test Address");
		testProfile.setEmail("testemail@test.com");
		testProfile.setLastName("TestLastName");
		testProfile.setPhoneNumber("(123)456-7890");
		testProfile.setZipCode("H1V 3T2");

		String username = "testCustomer";
		String password = "testPassword";
		Customer testCustomer = new Customer();
		testProfile.setCustomer(testCustomer);
		testCar.setCustomer(testCustomer);
		carList.add(testCar);
		testCustomer.setUsername(username);
		testCustomer.setPassword(password);
		testCustomer.setCars(carList);
		testCustomer.setNoShow(0);
		testCustomer.setShow(0);
		testCustomer.setProfile(testProfile);

		profileRepository.save(testProfile);
		carRepository.save(testCar);
		customerRepository.save(testCustomer);

		testCustomer=null;

		testCustomer= customerRepository.findCustomerByUsername(username);

		assertNotNull(testCustomer);
		assertEquals(username, testCustomer.getUsername());
		assertEquals(password,testCustomer.getPassword());
		assertEquals(password,testCustomer.getPassword());
		assertEquals(testProfile.getAddress(),testCustomer.getProfile().getAddress());
		assertEquals(testProfile.getEmail(),testCustomer.getProfile().getEmail());
		assertEquals(testProfile.getFirstName(),testCustomer.getProfile().getFirstName());
		assertEquals(testProfile.getLastName(),testCustomer.getProfile().getLastName());
		assertEquals(testProfile.getPhoneNumber(),testCustomer.getProfile().getPhoneNumber());
		assertEquals(testProfile.getZipCode(),testCustomer.getProfile().getZipCode());
		assertEquals(carList.get(0).getTransmission(),testCustomer.getCars().get(0).getTransmission());
		assertEquals(0,testCustomer.getNoShow());
		assertEquals(0,testCustomer.getShow());
	}
	
	@Test
	public void testPersistAndLoadProfile() {
		Car testCar = new Car();
		List<Car> carList = new ArrayList<>();
		testCar.setModel("testModel");
		testCar.setPlateNumber("123456");
		testCar.setTransmission(Car.CarTransmission.Automatic);

		Profile testProfile = new Profile();
		testProfile.setFirstName("TestName");
		testProfile.setAddress("Test Address");
		testProfile.setEmail("testemail@test.com");
		testProfile.setLastName("TestLastName");
		testProfile.setPhoneNumber("(123)456-7890");
		testProfile.setZipCode("H1V 3T2");

		String username = "testCustomer";
		String password = "testPassword";
		Customer testCustomer = new Customer();
		testProfile.setCustomer(testCustomer);
		testCar.setCustomer(testCustomer);
		carList.add(testCar);
		testCustomer.setUsername(username);
		testCustomer.setPassword(password);
		testCustomer.setCars(carList);
		testCustomer.setNoShow(0);
		testCustomer.setShow(0);
		testCustomer.setProfile(testProfile);

		profileRepository.save(testProfile);
		carRepository.save(testCar);
		customerRepository.save(testCustomer);

		testProfile = null;

		testProfile = profileRepository.findByCustomer(testCustomer);
		assertNotNull(testProfile);
		assertEquals("TestName", testProfile.getFirstName());
		assertEquals("TestLastName", testProfile.getLastName());
		assertEquals("Test Address", testProfile.getAddress());
		assertEquals("testemail@test.com",testProfile.getEmail());
		assertEquals("(123)456-7890",testProfile.getPhoneNumber());
		assertEquals("H1V 3T2",testProfile.getZipCode());
		assertEquals(username, testProfile.getCustomer().getUsername());
	}
	
	@Test
	public void testPersistAndLoadOperatingHour() {
		OperatingHour testOpHour = new OperatingHour();
			
		testOpHour.setDayOfWeek(OperatingHour.DayOfWeek.Monday);
		
		Time startTime = Time.valueOf("08:30:00");	
		testOpHour.setStartTime(startTime);
		
		Time endTime = Time.valueOf("04:30:00");
		testOpHour.setEndTime(endTime);
		operatingHourRepository.save(testOpHour);
		
		testOpHour=null;
		
		testOpHour=operatingHourRepository.findByDayOfWeek(OperatingHour.DayOfWeek.Monday);
		
		assertNotNull(testOpHour);
		assertEquals(OperatingHour.DayOfWeek.Monday, testOpHour.getDayOfWeek());
		assertEquals(startTime, testOpHour.getStartTime());
		assertEquals(endTime,testOpHour.getEndTime());
		
	}
	@Test
	public void testPersistAndLoadTimeSlot() {

			Date sD = Date.valueOf("2021-02-24");
			Date eD = Date.valueOf("2021-02-27");
			Time sT = Time.valueOf("10:10:00");
			Time eT = Time.valueOf("11:11:00");
			TimeSlot ts = new TimeSlot();
			
			ts.setStartDate(sD);
			ts.setEndDate(eD);
			ts.setStartTime(sT);
			ts.setEndTime(eT);

			timeSlotRepository.save(ts);

			ts = null;

			ts = timeSlotRepository.findTimeSlotByStartDateAndStartTime(sD, sT);

			assertNotNull(ts);
			assertEquals(sD, ts.getStartDate());
			assertEquals(eD, ts.getEndDate());
			assertEquals(sT, ts.getStartTime());
			assertEquals(eT, ts.getEndTime());
			
	}
	
//	@Test
//	public void testPersistAndLoadServiceCombo() {
//
//		String name = "service";
//		int duration = 30;
//		Service testService1 = new Service();
//		testService1.setName(name);
//		testService1.setDuration(duration);
//
//		String name2 = "service2";
//		int duration2 = 30;
//		Service testService2 = new Service();
//		testService2.setName(name2);
//		testService2.setDuration(duration2);
//
//
//		String name3 = "service3";
//		int duration3 = 30;
//		Service testService3 = new Service();
//		testService3.setName(name3);
//		testService3.setDuration(duration3);
//
//		String comboName = "testCombo";
//
//		ComboItem mainItem = new ComboItem();
//		mainItem.setService(testService1);
//
//		ComboItem mandatoryItem = new ComboItem();
//		mandatoryItem.setService(testService2);
//
//		ComboItem optionalItem = new ComboItem();
//		optionalItem.setService(testService3);
//
//		List<ComboItem> items = new ArrayList<ComboItem>();
//		items.add(mainItem);
//		items.add(mandatoryItem);
//		items.add(optionalItem);
//
//		mainItem.setServiceCombo(testCombo);
//		mandatoryItem.setServiceCombo(testCombo);
//		optionalItem.setServiceCombo(testCombo);
//
//		testCombo.setMainService(mainItem);
//		testCombo.setServices(items);
//		testCombo.setName(comboName);
//
//		serviceRepository.save(testService1);
//		serviceRepository.save(testService2);
//		serviceRepository.save(testService3);
//
//		serviceComboRepository.save(testCombo);
//
//		testCombo=null;
//
//		testCombo = serviceComboRepository.findServiceComboByName(comboName);
//
//		assertNotNull(testCombo);
//		assertEquals(comboName, testCombo.getName());
//
//		for(int i=0; i<3; i++) {
//			assertEquals(items.get(i).getService().getName(), testCombo.getServices().get(i).getService().getName());
//			assertEquals(items.get(i).getService().getDuration(), testCombo.getServices().get(i).getService().getDuration());
//		}
//		assertEquals(mainItem.getService().getName(), testCombo.getMainService().getService().getName());
//
//	}

	@Test
	public void testPersistAndLoadReminder() {

		//Service
		String name = "service1";
		int duration = 30;
		ChosenService testService = new ChosenService();
		testService.setName(name);
		testService.setDuration(duration);
		
		//Car
		Car testCar = new Car();
		List<Car> carList = new ArrayList<>();
		testCar.setModel("testModel");
		testCar.setPlateNumber("123456");
		testCar.setTransmission(Car.CarTransmission.Automatic);
		
		//Profile
		Profile testProfile = new Profile();
		testProfile.setFirstName("TestName");
		testProfile.setAddress("Test Address");
		testProfile.setEmail("testemail@test.com");
		testProfile.setLastName("TestLastName");
		testProfile.setPhoneNumber("(123)456-7890");
		testProfile.setZipCode("H1V 3T2");

		//Customer
		String username = "testCustomer";
		String password = "testPassword";
		Customer testCustomer = new Customer();
		testProfile.setCustomer(testCustomer);
		testCar.setCustomer(testCustomer);
		carList.add(testCar);
		testCustomer.setUsername(username);
		testCustomer.setPassword(password);
		testCustomer.setCars(carList);
		testCustomer.setNoShow(0);
		testCustomer.setShow(0);
		testCustomer.setProfile(testProfile);

		
		String testDescription = "testDescription";
		Date testDate = Date.valueOf("2021-02-22");
		Time testTime = Time.valueOf("12:00:00");
		Reminder reminder = new Reminder();
		reminder.setDate(testDate);
		reminder.setDescription(testDescription);
		reminder.setTime(testTime);
		reminder.setService(testService);
		reminder.setCustomer(testCustomer);
		
		chosenServiceRepository.save(testService);
		profileRepository.save(testProfile);
		carRepository.save(testCar);
		customerRepository.save(testCustomer);
		reminderRepository.save(reminder);
		

		reminder = null;

		reminder = reminderRepository.findByCustomerAndService(testCustomer, testService);
		
		assertNotNull(reminder);
		assertEquals(testCustomer.getUsername(), reminder.getCustomer().getUsername());
		assertEquals(testCustomer.getPassword(), reminder.getCustomer().getPassword());
		assertEquals(testCustomer.getNoShow(), reminder.getCustomer().getNoShow());
		assertEquals(testCustomer.getShow(), reminder.getCustomer().getShow());
		assertEquals(testDate, reminder.getDate());
		assertEquals(testDescription, reminder.getDescription());
		assertEquals(testTime, reminder.getTime());
		assertEquals(testService.getName(), reminder.getService().getName());
		
	}
//	@Test
//	public void testPersistAndLoadComboItem() {
//
//		String name = "service1";
//		int duration = 30;
//		ChosenService testService = new Service();
//		testService.setName(name);
//		testService.setDuration(duration);
//
//		ComboItem item = new ComboItem();
//		item.setService(testService);
//
//		serviceRepository.save(testService);
//		comboItemRepository.save(item);
//
//		item = null;
//
//		List<ComboItem> items = new ArrayList<ComboItem>();
//		items = comboItemRepository.findByService(testService);
//		item = items.get(0);
//
//		assertNotNull(item);
//		assertEquals(item.getService().getName(), name);
//		assertEquals(item.getService().getDuration(), duration);
//	}
}
