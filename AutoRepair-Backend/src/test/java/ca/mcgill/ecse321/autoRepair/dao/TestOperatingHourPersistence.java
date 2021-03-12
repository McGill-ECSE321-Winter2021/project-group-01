package ca.mcgill.ecse321.autoRepair.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Time;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.autoRepair.model.OperatingHour;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestOperatingHourPersistence {
	
	@Autowired
	EntityManager entityManager;
	@Autowired
	private OperatingHourRepository operatingHourRepository;
	
	@AfterEach
	public void clearDatabase(){
		operatingHourRepository.deleteAll();
	}
	
	@Test
	public void testPersistAndLoadOperatingHour() {
		OperatingHour testOpHour = new OperatingHour();
			
		testOpHour.setDayOfWeek(OperatingHour.DayOfWeek.Monday);
		
		Time startTime = Time.valueOf("08:30:00");	
		testOpHour.setStartTime(startTime);
		
		Time endTime = Time.valueOf("04:30:00");
		testOpHour.setEndTime(endTime);
		operatingHourRepository.save(testOpHour);
		
		testOpHour=null;
		
		testOpHour=operatingHourRepository.findByDayOfWeek(OperatingHour.DayOfWeek.Monday);
		
		assertNotNull(testOpHour);
		assertEquals(OperatingHour.DayOfWeek.Monday, testOpHour.getDayOfWeek());
		assertEquals(startTime, testOpHour.getStartTime());
		assertEquals(endTime,testOpHour.getEndTime());
		
	}

}
