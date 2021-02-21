package ca.mcgill.ecse321.autoRepair.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.autoRepair.model.Appointment;
import ca.mcgill.ecse321.autoRepair.model.Assistant;
import ca.mcgill.ecse321.autoRepair.model.AutoRepairShopSystem;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.Profile;
import ca.mcgill.ecse321.autoRepair.model.Service;
import ca.mcgill.ecse321.autoRepair.model.TimeSlot;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestAutoRepairPersistence {
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private AssistantRepository assistantRepository;
	@Autowired
	private AutoRepairRepository autoRepairRepository;
	
	
	@AfterEach
	public void clearDatabase() {

		autoRepairRepository.deleteAll();
		assistantRepository.deleteAll();
		appointmentRepository.deleteAll();
	}
	
	@Test
	public void testPersistAndLoadAssitant() {
		AutoRepairShopSystem autoRepair = new AutoRepairShopSystem("1");
		String username = "testAssistant";
		String password = "testPassword";
		Assistant assistant = new Assistant(username, password, autoRepair);
		assistantRepository.save(assistant);

		assistant = null;

		assistant = assistantRepository.findAssistantByUsername(username);
		assertNotNull(assistant);
		assertEquals(username, assistant.getUsername());
	}
	
	@Test
	public void testPersistAndLoadAutoRepair() {
		String id = "1";
		AutoRepairShopSystem autoRepair = new AutoRepairShopSystem(id);
		autoRepairRepository.save(autoRepair);

		autoRepair = null;

		autoRepair = autoRepairRepository.findAutoRepairShopSystemById(id);
		assertNotNull(autoRepair);
		assertEquals(id, autoRepair.getId());
	}
	
	@Test
	public void testPersistAndLoadAppointment() {
		AutoRepairShopSystem autoRepair = new AutoRepairShopSystem("1");
		String username = "testCustomer";
		String password = "testPassword";
		Profile profile = new Profile("1", "Test", "Customer", "TestAddress", "55555", "+1514123456", "test@mail.ca");
		Customer testCustomer = new Customer(username, password, 0, 0, profile, autoRepair);
		TimeSlot testSlot = new TimeSlot("1", Date.valueOf(LocalDate.now()),Time.valueOf(LocalTime.now()), Date.valueOf(LocalDate.now()),Time.valueOf(LocalTime.now()), autoRepair);
		Service testService = new Service("testName", autoRepair, 0);
		String id = "11";
		Appointment testAppointment = new Appointment(id,testCustomer, testService, testSlot, autoRepair);
		appointmentRepository.save(testAppointment);

		testAppointment = null;

		testAppointment = appointmentRepository.findAppointmentById(id);
		assertNotNull(testAppointment);
		assertEquals(id, testAppointment.getId());
		
		testAppointment = null;

		testAppointment = appointmentRepository.findAppointmentByCustomerAndBookableService(testCustomer, testService);
		assertNotNull(testAppointment);
		assertEquals(testCustomer, testAppointment.getCustomer());
		assertEquals(testService, testAppointment.getBookableService());
		
		testAppointment = null;

		testAppointment = appointmentRepository.findAppointmentByTimeSlot(testSlot);
		assertNotNull(testAppointment);
		assertEquals(testSlot, testAppointment.getTimeSlot());


	}

}
