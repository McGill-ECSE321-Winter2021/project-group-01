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

import ca.mcgill.ecse321.autoRepair.model.Profile;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestProfilePersistence {
	
	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@AfterEach
	public void clearDatabase(){
		profileRepository.deleteAll();
	}
	
	@Test
	public void testPersistAndLoadProfile() {
		
		Profile testProfile = new Profile();
		testProfile.setFirstName("TestName");
		testProfile.setAddress("Test Address");
		testProfile.setEmail("testemail@test.com");
		testProfile.setLastName("TestLastName");
		testProfile.setPhoneNumber("(123)456-7890");
		testProfile.setZipCode("H1V 3T2");

		profileRepository.save(testProfile);
		
		testProfile = null;

		testProfile = profileRepository.findByEmail("testemail@test.com");
		assertNotNull(testProfile);
		assertEquals("TestName", testProfile.getFirstName());
		assertEquals("TestLastName", testProfile.getLastName());
		assertEquals("Test Address", testProfile.getAddress());
		assertEquals("testemail@test.com",testProfile.getEmail());
		assertEquals("(123)456-7890",testProfile.getPhoneNumber());
		assertEquals("H1V 3T2",testProfile.getZipCode());
	}

}
