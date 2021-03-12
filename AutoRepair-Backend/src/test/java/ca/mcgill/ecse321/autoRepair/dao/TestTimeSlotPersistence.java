package ca.mcgill.ecse321.autoRepair.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.autoRepair.model.TimeSlot;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestTimeSlotPersistence {
	
	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private TimeSlotRepository timeSlotRepository;
	
	@AfterEach
	public void clearDatabase() {
		timeSlotRepository.deleteAll();
	}
	
	@Test
	public void testPersistAndLoadTimeSlot() {

			Date sD = Date.valueOf("2021-02-24");
			Date eD = Date.valueOf("2021-02-27");
			Time sT = Time.valueOf("10:10:00");
			Time eT = Time.valueOf("11:11:00");
			TimeSlot ts = new TimeSlot();
			
			ts.setStartDate(sD);
			ts.setEndDate(eD);
			ts.setStartTime(sT);
			ts.setEndTime(eT);

			timeSlotRepository.save(ts);

			ts = null;

			ts = timeSlotRepository.findTimeSlotByStartDateAndStartTime(sD, sT);

			assertNotNull(ts);
			assertEquals(sD, ts.getStartDate());
			assertEquals(eD, ts.getEndDate());
			assertEquals(sT, ts.getStartTime());
			assertEquals(eT, ts.getEndTime());
			
	}

}
