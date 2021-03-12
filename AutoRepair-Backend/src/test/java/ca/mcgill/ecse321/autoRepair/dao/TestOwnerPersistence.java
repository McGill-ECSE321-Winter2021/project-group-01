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

import ca.mcgill.ecse321.autoRepair.model.Owner;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestOwnerPersistence {
	
	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private OwnerRepository ownerRepository;
	
	@AfterEach
	public void clearDatabase() {
		ownerRepository.deleteAll();
	}
	
	@Test
	public void testPersistAndLoadOwner() {
		String name = "TestOwner";
		String password = "TestPassword";
		Owner testOwner= new Owner();
		testOwner.setPassword(password);
		testOwner.setUsername(name);
		ownerRepository.save(testOwner);

		testOwner= null;

		testOwner = ownerRepository.findOwnerByUsername(name);
		assertNotNull(testOwner);
		assertEquals(name, testOwner.getUsername());
		assertEquals(password,testOwner.getPassword());
	}

}
