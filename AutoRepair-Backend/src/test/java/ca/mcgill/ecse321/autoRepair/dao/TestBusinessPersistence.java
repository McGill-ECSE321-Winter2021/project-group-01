package ca.mcgill.ecse321.autoRepair.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.autoRepair.model.Business;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestBusinessPersistence {
	
	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private BusinessRepository businessRepository;
	
	@AfterEach
	public void clearDatabase() {
		businessRepository.deleteAll();
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

}
