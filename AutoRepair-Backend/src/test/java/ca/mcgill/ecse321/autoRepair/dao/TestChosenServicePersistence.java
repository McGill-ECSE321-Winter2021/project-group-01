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

import ca.mcgill.ecse321.autoRepair.model.ChosenService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestChosenServicePersistence {
	
	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private ChosenServiceRepository chosenServiceRepository;
	
	@AfterEach
	public void ClearDatabase(){
		chosenServiceRepository.deleteAll();
	}
	
	
	@Test
	public void testPersistAndLoadService() {
		String name = "service1";
		int duration = 30;
		ChosenService testService = new ChosenService();
		testService.setName(name);
		testService.setDuration(duration);

		chosenServiceRepository.save(testService);

		testService = null;

		testService= chosenServiceRepository.findChosenServiceByName(name);

		assertNotNull(testService);
		assertEquals(name, testService.getName());
		assertEquals(duration, testService.getDuration());
	}

}
