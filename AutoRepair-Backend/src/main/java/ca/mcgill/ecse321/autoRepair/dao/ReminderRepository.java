package ca.mcgill.ecse321.autoRepair.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.autoRepair.model.Reminder;

public interface ReminderRepository extends CrudRepository<Reminder, Long>{

}
