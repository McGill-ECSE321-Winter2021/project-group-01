package ca.mcgill.ecse321.autoRepair.dao;

import java.sql.Date;
import java.sql.Time;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.autoRepair.model.TimeSlot;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeSlotRepository extends CrudRepository<TimeSlot, Long>{
	
	TimeSlot findTimeSlotByStartDateAndStartTime(Date startDate, Time startTime);
}
