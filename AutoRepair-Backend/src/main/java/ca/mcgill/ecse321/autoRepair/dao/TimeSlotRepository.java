package ca.mcgill.ecse321.autoRepair.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.autoRepair.model.TimeSlot;

public interface TimeSlotRepository extends CrudRepository<TimeSlot, Long>{

}
