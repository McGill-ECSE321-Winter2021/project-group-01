package ca.mcgill.ecse321.autoRepair.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;


import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.autoRepair.model.*;
import org.junit.jupiter.api.AfterEach;
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
	@Autowired
	private ComboItemRepository comboItemRepository;




	@AfterEach
	public void clearDatabase() {

//		profileRepository.deleteAll();
//		reminderRepository.deleteAll();
//		carRepository.deleteAll();
//		customerRepository.deleteAll();

		//		serviceComboRepository.deleteAll();
				comboItemRepository.deleteAll();
		//		assistantRepository.deleteAll();
		//		appointmentRepository.deleteAll();
			    serviceRepository.deleteAll();
		//		ownerRepository.deleteAll();
		//		bookableServiceRepository.deleteAll();
		//		businessRepository.deleteAll();
		//		operatingHourRepository.deleteAll();
		//		reviewRepository.deleteAll();
		//		timeSlotRepository.deleteAll();
		//		userRepository.deleteAll();

	}

	//@Test
	public void testPersistAndLoadReminder() {

		//Service
		String name = "service1";
		int duration = 30;
		Service testService = new Service();
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
		reminder.setBookableService(testService);
		reminder.setCustomer(testCustomer);


		profileRepository.save(testProfile);
		carRepository.save(testCar);
		customerRepository.save(testCustomer);
		reminderRepository.save(reminder);


		reminder = null;

		reminder = reminderRepository.findByCustomerAndBookableService(testCustomer, testService);

		assertNotNull(reminder);
		assertEquals(testCustomer.getUsername(), reminder.getCustomer().getUsername());
		assertEquals(testCustomer.getPassword(), reminder.getCustomer().getPassword());
		assertEquals(testCustomer.getNoShow(), reminder.getCustomer().getNoShow());
		assertEquals(testCustomer.getShow(), reminder.getCustomer().getShow());
		assertEquals(testDate, reminder.getDate());
		assertEquals(testDescription, reminder.getDescription());
		assertEquals(testTime, reminder.getTime());
		assertEquals(testService.getName(), reminder.getBookableService().getName());

	}

	@Test
	public void testPersistAndLoadComboItem() {

		String name = "service1";
		int duration = 30;
		Service testService = new Service();
		testService.setName(name);
		testService.setDuration(duration);
		
		ComboItem item = new ComboItem();
		item.setService(testService);
		
		serviceRepository.save(testService);
		comboItemRepository.save(item);
		
		item = null;
		
		List<ComboItem> items = new ArrayList<ComboItem>();
		items = comboItemRepository.findByService(testService);
		item = items.get(0);
		
		assertNotNull(item);
		assertEquals(item.getService().getName(), name);
		assertEquals(item.getService().getDuration(), duration);
	}

	//@Test
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




	//	
	//	//@Test
	//	public void testPersistAndLoadAppointment() {
	//		AutoRepairShopSystem autoRepair = new AutoRepairShopSystem("1");
	//		String username = "testCustomer";
	//		String password = "testPassword";
	//		Profile profile = new Profile("1", "Test", "Customer", "TestAddress", "55555", "+1514123456", "test@mail.ca");
	//		Customer testCustomer = new Customer(username, password, 0, 0, profile, autoRepair);
	//		TimeSlot testSlot = new TimeSlot("1", Date.valueOf(LocalDate.now()),Time.valueOf(LocalTime.now()), Date.valueOf(LocalDate.now()),Time.valueOf(LocalTime.now()), autoRepair);
	//		Service testService = new Service("testName", autoRepair, 0);
	//		String id = "11";
	//		Appointment testAppointment = new Appointment(id,testCustomer, testService, testSlot, autoRepair);
	//		appointmentRepository.save(testAppointment);
	//
	//		testAppointment = null;
	//
	//		testAppointment = appointmentRepository.findAppointmentById(id);
	//		assertNotNull(testAppointment);
	//		assertEquals(id, testAppointment.getId());
	//		
	//		testAppointment = null;
	//
	//		testAppointment = appointmentRepository.findAppointmentByCustomerAndBookableService(testCustomer, testService);
	//		assertNotNull(testAppointment);
	//		assertEquals(testCustomer, testAppointment.getCustomer());
	//		assertEquals(testService, testAppointment.getBookableService());
	//		
	//		testAppointment = null;
	//
	//		testAppointment = appointmentRepository.findAppointmentByTimeSlot(testSlot);
	//		assertNotNull(testAppointment);
	//		assertEquals(testSlot, testAppointment.getTimeSlot());
	//	}
	//
	//	//@Test
	//	public void testPersistAndLoadOwner() {
	//		AutoRepairShopSystem repairShopSystem = new AutoRepairShopSystem("1");
	//		String name = "TestOwner";
	//		// First example for object save/load
	//		Owner owner = new Owner(name,"12345", repairShopSystem);
	//		// First example for attribute save/load
	//		ownerRepository.save(owner);
	//
	//		owner= null;
	//
	//		owner = ownerRepository.findOwnerByUsername(name);
	//		assertNotNull(owner);
	//		assertEquals(name, owner.getUsername());
	//	}
	//
	//	//@Test
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
	//
	//	@Test
	//	public void testPersistAndLoadProfile() {
	//		
	//		Customer customer = new Customer();
	//		customer.setUsername(username);
	//		
	//		Profile testProfile = new Profile("profileId", "Test", "Profile", "Test Address", "Test zip", "4388661234",
	//				"Test email");
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


	//@Test
	public void testPersistAndLoadServiceCombo() {

		String name = "service";
		int duration = 30;
		Service testService1 = new Service();
		testService1.setName(name);
		testService1.setDuration(duration);

		String name2 = "service2";
		int duration2 = 30;
		Service testService2 = new Service();
		testService2.setName(name2);
		testService2.setDuration(duration2);


		String name3 = "service3";
		int duration3 = 30;
		Service testService3 = new Service();
		testService3.setName(name3);
		testService3.setDuration(duration3);

		String comboName = "testCombo";
		ServiceCombo testCombo = new ServiceCombo();

		ComboItem mainItem = new ComboItem();
		mainItem.setService(testService1);

		ComboItem mandatoryItem = new ComboItem();
		mandatoryItem.setService(testService2);

		ComboItem optionalItem = new ComboItem();
		optionalItem.setService(testService3);

		List<ComboItem> items = new ArrayList<ComboItem>();
		items.add(mainItem);
		items.add(mandatoryItem);
		items.add(optionalItem);

		mainItem.setServiceCombo(testCombo);
		mandatoryItem.setServiceCombo(testCombo);
		optionalItem.setServiceCombo(testCombo);

		testCombo.setMainService(mainItem);
		testCombo.setServices(items);
		testCombo.setName(comboName);


		//		comboItemRepository.save(mainItem);
		//		comboItemRepository.save(mandatoryItem);
		//		comboItemRepository.save(optionalItem);

		serviceRepository.save(testService1);
		serviceRepository.save(testService2);
		serviceRepository.save(testService3);


		serviceComboRepository.save(testCombo);

		testCombo=null;

		testCombo = serviceComboRepository.findServiceComboByName(comboName);

		assertNotNull(testCombo);
		assertEquals(comboName, testCombo.getName());

		for(int i=0; i<3; i++) {
			assertEquals(items.get(i).getService().getName(), testCombo.getServices().get(i).getService().getName());
			assertEquals(items.get(i).getService().getDuration(), testCombo.getServices().get(i).getService().getDuration());
		}
		assertEquals(mainItem.getService().getName(), testCombo.getMainService().getService().getName());	
	}


	//@Test
	public void testPersistAndLoadService() {
		String name = "service1";
		int duration = 30;
		Service testService = new Service();
		testService.setName(name);
		testService.setDuration(duration);

		serviceRepository.save(testService);

		testService = null;

		testService=serviceRepository.findServiceByName(name);

		assertNotNull(testService);
		assertEquals(name, testService.getName());
		assertEquals(duration, testService.getDuration());
	}

	//@Test
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

}
