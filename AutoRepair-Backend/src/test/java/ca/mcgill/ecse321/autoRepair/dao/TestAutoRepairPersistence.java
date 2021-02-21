package ca.mcgill.ecse321.autoRepair.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.autoRepair.model.Car;
import ca.mcgill.ecse321.autoRepair.model.Car.CarTransmission;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.Business;
import ca.mcgill.ecse321.autoRepair.model.AutoRepairShopSytem;
import ca.mcgill.ecse321.autoRepair.model.BookableService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestAutoRepairPersistence {

	@Autowired
	private BookableServiceRepository bookableServiceRepository;
	@Autowired
	private BusinessRepository businessRepository;
	@Autowired
	private CarRepository carRepository;

	@AfterEach
	public void clearDatabase() {
		// First, we clear registrations to avoid exceptions due to inconsistencies
		bookableServiceRepository.deleteAll();
		// Then we can clear the other tables
		businessRepository.deleteAll();
		carRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadCar() {
		String model = "modelTST";
		CarTransmission carTransmission = null;
		String plateNumber = "TST999";
		Customer customer = new Customer();
		Car car = new Car(model, carTransmission, plateNumber, customer);
		carRepository.save(car);

		car = null;

		car = carRepository.findCarByPlateNumber(plateNumber);
		assertNotNull(car);
		assertEquals(plateNumber, car.getPlateNumber());
	}

	@Test
	public void testPersistAndLoadBusiness() {
		AutoRepairShopSytem autoRepair = new AutoRepairShopSytem("TST");
		String id = "TST000";
		String name = "AutoRepair";
		String address = "Address";
		String phoneNumber = "514-000-9999";
		String email = "autorepair@mcgill.ca";
		Business business = new Business(id, name, address, phoneNumber, email,
				autoRepair);
		
		businessRepository.save(business);
		business = null;

		business = businessRepository.findBusinessById(id);

		assertNotNull(business);
		assertEquals(id, business.getId());
		assertEquals(name, business.getName());
		assertEquals(address, business.getAddress());
		assertEquals(phoneNumber, business.getPhoneNumber());
		assertEquals(email, business.getEmail());
	}

	@Test
	public void testPersistAndLoadBookableService() {
		AutoRepairShopSytem autoRepair = new AutoRepairShopSytem("TST");
		String name = "X";
	}	

}
