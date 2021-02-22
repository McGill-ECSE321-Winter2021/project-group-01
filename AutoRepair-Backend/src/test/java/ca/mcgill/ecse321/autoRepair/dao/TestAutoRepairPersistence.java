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
	private ReviewRepository reviewRepository;
	@Autowired
	private ServiceComboRepository serviceCombo;
	@Autowired
	private ServiceRepository serviceRepository;
	
	@Test
	public void testPersistAndLoadReview() {
		String id = "123";
		String description = "The service was great!";
		int serviceRating = 5;
		AutoRepairShopSytem repairShopSystem = new AutoRepairShopSytem("1");
		Customer customer = new Customer("TestCustomer", "12345",0,0, null, repairShopSystem);
		BookableService service = new Service("TestService", repairShopSystem,10);
		Review review = new Review(id, description, serviceRating, repairShopSystem, customer, service);
		assertEquals(id, review.getId());
		assertEquals(description, review.getDescription());
		assertEquals(serviceRating, review.getServiceRating());
	}
	@Test
	public void testPersistAndLoadService() {
		String name = "Oil Change";
		int duration = 30;
		AutoRepairShopSytem repairShopSystem = new AutoRepairShopSytem("1");
		Service testService = new Service(name, repairShopSystem, duration);
		assertEquals(duration, testService.getDuration());
		assertEquals(name, testService.getName());
	}
	@Test
	public void testPersistAndLoadServiceCombo() {
		String name = "Oil Change";
		AutoRepairShopSytem repairShopSystem = new AutoRepairShopSytem("1");
		ServiceCombo testService = new ServiceCombo(name, repairShopSystem);
		assertEquals(name, testService.getName());
	}
	
}

