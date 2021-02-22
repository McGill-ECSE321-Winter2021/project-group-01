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
	private CustomerRepository customerRepository;
	private ComboItemRepository comboItemRepository;
	private OperatingHourRepository operatingHourRepository;
	
	@AfterEach
	public void clearDatabase() {
		customerRepository.deleteAll();
		comboItemRepository.deleteAll();
		operatingHourRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadCustomer() {
		long name;
		AutoRepairShopSystem autoRepair = new AutoRepairShopSystem();
		// First example for object save/load
		Customer customer = new Customer();
		// First example for attribute save/load
		customer.setUsername(name);
		customerRepository.save(customer);

		customer = null;

		customer = customerRepository.findCustomerByUsername(name);
		assertNotNull(customer);
		assertEquals(name, customer.getUsername());
	}
	@Test
	 public void testPersistAndLoadComboItem() {
		long aId = "testingId";
		AutoRepairShopSystem autoRepair = new AutoRepairShopSystem();
		Service service = new Service("oil change", autoRepair, 30);
		ServiceCombo serviceCombo = new ServiceCombo("car maintenance", autoRepair);
		ComboItem comboItem = new ComboItem();
		comboItem.setId(aId);
		comboItemRepository.save(comboItem);
		
		comboItem =null;
		
		comboItem = comboItemRepository.findComboItemById(aId);
		assertNotNull(comboItem);
		assertEquals(aId, comboItem.getId());
		
	}
	@Test
	public void testPersistAndOperatingHours() {
		AutoRepairShopSystem autoRepair = new AutoRepairShopSystem();
		long aId = "TestingId";
		OperatingHour operatingHour = new OperatingHour();
		operatingHour.setId(aId);
		//operatingHour.setDayOfWeek(DayOfWeek.Monday);
		operatingHourRepository.save(operatingHour);
		
		operatingHour=null;
	
		operatingHour= operatingHourRepository.findOperatingHourById(aId);
		assertNotNull(operatingHour);
		assertEquals(aId, operatingHour.getId());
	
	}


	
	

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

		//businessRepository.save(business);

		business = null;

		//business = businessRepository.findBusinessByName(name);

		assertNotNull(business);
		assertEquals(name, business.getName());
		assertEquals(address, business.getAddress());
		assertEquals(phoneNumber, business.getPhoneNumber());
		assertEquals(email, business.getEmail());
	}
	
}
