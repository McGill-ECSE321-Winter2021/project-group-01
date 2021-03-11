package ca.mcgill.ecse321.autoRepair.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.autoRepair.model.Car;
import ca.mcgill.ecse321.autoRepair.model.ChosenService;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.Profile;
import ca.mcgill.ecse321.autoRepair.model.Reminder;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestReminderPersistence {
	
	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private ChosenServiceRepository chosenServiceRepository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private CarRepository carRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ReminderRepository reminderRepository;
	
	@AfterEach
	public void clearDatabase() {
		customerRepository.deleteAll();
	    profileRepository.deleteAll();
		reminderRepository.deleteAll();
		carRepository.deleteAll();
		chosenServiceRepository.deleteAll();
	}
	
	
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
		reminder.setChosenService(testService);
		reminder.setCustomer(testCustomer);
		
		chosenServiceRepository.save(testService);
		profileRepository.save(testProfile);
		carRepository.save(testCar);
		customerRepository.save(testCustomer);
		reminderRepository.save(reminder);
		

		reminder = null;

		reminder = reminderRepository.findByCustomerAndChosenService(testCustomer, testService);
		
		assertNotNull(reminder);
		assertEquals(testCustomer.getUsername(), reminder.getCustomer().getUsername());
		assertEquals(testCustomer.getPassword(), reminder.getCustomer().getPassword());
		assertEquals(testCustomer.getNoShow(), reminder.getCustomer().getNoShow());
		assertEquals(testCustomer.getShow(), reminder.getCustomer().getShow());
		assertEquals(testDate, reminder.getDate());
		assertEquals(testDescription, reminder.getDescription());
		assertEquals(testTime, reminder.getTime());
		assertEquals(testService.getName(), reminder.getChosenService().getName());
		
	}

}
