package ca.mcgill.ecse321.autoRepair.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import ca.mcgill.ecse321.autoRepair.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestAutoRepairPersistence {
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private AssistantRepository assistantRepository;
	@Autowired
	private AutoRepairRepository autoRepairRepository;
	@Autowired
	private OwnerRepository ownerRepository;
	@Autowired
	private ProfileRepository profileRepository;
//	@Autowired
//	private ReminderRepository reminderRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private BusinessRepository businessRepository;
	
	
	
	@AfterEach
	public void clearDatabase() {

		autoRepairRepository.deleteAll();
		assistantRepository.deleteAll();
		appointmentRepository.deleteAll();
		profileRepository.deleteAll();
		ownerRepository.deleteAll();
//		reminderRepository.deleteAll();

	}
	
//	@Test
//	public void testPersistAndLoadAssitant() {
//		AutoRepairShopSystem autoRepair = new AutoRepairShopSystem();
//		String username = "testAssistant";
//		String password = "testPassword";
////		Assistant assistant = new Assistant();
////		assistant.setUsername(username);
////		assistant.setPassword(password);
////		assistant.setAutoRepairShopSystem(null);
//     	Assistant assistant = new Assistant(username, password, autoRepair);
//		assistantRepository.save(assistant);
//
//
//		assistant = null;
//
//		assistant = assistantRepository.findAssistantByUsername(username);
//		assertNotNull(assistant);
//		assertEquals(username, assistant.getUsername());
//	}
	
//	//@Test
//	public void testPersistAndLoadAutoRepair() {
//		String id = "1";
//		AutoRepairShopSystem autoRepair = new AutoRepairShopSystem(id);
//		autoRepairRepository.save(autoRepair);
//
//		autoRepair = null;
//
//		autoRepair = autoRepairRepository.findAutoRepairShopSystemById(id);
//		assertNotNull(autoRepair);
//		assertEquals(id, autoRepair.getId());
//	}
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
//	//@Test
//	public void testPersistAndLoadProfile() {
//		AutoRepairShopSystem repairShopSystem = new AutoRepairShopSystem("1");
//		Customer customer = new Customer("TestCustomer", "12345",0,0, null, repairShopSystem);
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

	@Test
	public void testPersistAndLoadBusiness() {
		AutoRepairShopSystem autoRepair = new AutoRepairShopSystem();
		String name = "AutoRepair";
		String address = "Address";
		String phoneNumber = "514-000-9999";
		String email = "autorepair@mcgill.ca";
		Business business = new Business();
		business.setName(name);
		business.setAddress(address);
		business.setPhoneNumber(phoneNumber);
		business.setEmail(email);
		business.setAutoRepairShopSystem(autoRepair);

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
