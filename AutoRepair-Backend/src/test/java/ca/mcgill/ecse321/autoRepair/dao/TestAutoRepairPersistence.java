package ca.mcgill.ecse321.autoRepair.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import ca.mcgill.ecse321.autoRepair.model.*;
import ca.mcgill.ecse321.autoRepair.model.OperatingHour.DayOfWeek;

import org.junit.jupiter.api.AfterEach;
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
		String name = "TestingCustomer";
		AutoRepairShopSystem autoRepair = new AutoRepairShopSystem("1");
		// First example for object save/load
		Customer customer = new Customer(null, null, 0, 0, null, autoRepair);
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
		String aId = "TestingId";
		AutoRepairShopSystem autoRepair = new AutoRepairShopSystem("1");
		Service service = new Service("oil change", autoRepair, 30);
		ServiceCombo serviceCombo = new ServiceCombo("car maintenance", autoRepair);
		ComboItem comboItem = new ComboItem(null,false,service,serviceCombo);
		comboItem.setId(aId);
		comboItemRepository.save(comboItem);
		
		comboItem =null;
		
		comboItem = comboItemRepository.findComboItemById(aId);
		assertNotNull(comboItem);
		assertEquals(aId, comboItem.getId());
		
	}
	@Test
	public void testPersistAndOperatingHours() {
		AutoRepairShopSystem autoRepair = new AutoRepairShopSystem("1");
		String aId = "TestingId";
		OperatingHour operatingHour = new OperatingHour(null,null, null, null, autoRepair);
		operatingHour.setId(aId);
		//operatingHour.setDayOfWeek(DayOfWeek.Monday);
		operatingHourRepository.save(operatingHour);
		
		operatingHour=null;
	
		operatingHour= operatingHourRepository.findOperatingHourById(aId);
		assertNotNull(operatingHour);
		assertEquals(aId, operatingHour.getId());
	
	}

}
