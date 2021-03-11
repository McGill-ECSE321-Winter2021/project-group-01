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

import ca.mcgill.ecse321.autoRepair.model.Assistant;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestAssistantPersistence {
	
	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private AssistantRepository assistantRepository;
	
	@AfterEach
	public void clearDatabase() {
		assistantRepository.deleteAll();
	}
	
	@Test
	public void testPersistAndLoadAssitant() {
		String username = "testAssistant";
		String password = "testPassword";
		Assistant assistant = new Assistant();
		assistant.setUsername(username);
		assistant.setPassword(password);
		assistantRepository.save(assistant);


		assistant = null;

		assistant = assistantRepository.findAssistantByUsername(username);
		assertNotNull(assistant);
		assertEquals(username, assistant.getUsername());
	}

}
