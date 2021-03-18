package ca.mcgill.ecse321.autoRepair.controller;

import ca.mcgill.ecse321.autoRepair.dto.ReminderDTO;
import ca.mcgill.ecse321.autoRepair.model.Reminder;

public class ReminderController {

	
	private ReminderDTO convertTODTO(Reminder reminder) {
		if(reminder == null) {
			throw new IllegalArgumentException("Car not found.");
		}
		return null;
		
		
		//return new ReminderDTO(reminder.getDescription(), reminder.getDate(), reminder.getTime());
	}
}
