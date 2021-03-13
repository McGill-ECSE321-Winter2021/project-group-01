package ca.mcgill.ecse321.autoRepair.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.autoRepair.dao.ReminderRepository;
import ca.mcgill.ecse321.autoRepair.dao.ReviewRepository;
import ca.mcgill.ecse321.autoRepair.model.Appointment;
import ca.mcgill.ecse321.autoRepair.model.ChosenService;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.Reminder;
import ca.mcgill.ecse321.autoRepair.model.Review;

@Service
public class ReminderService {

	@Autowired
	ReminderRepository reminderRepository;

	@Transactional
	public Reminder createReminder(ChosenService service, Customer customer, Date date,
			String description, Time time) {
		
		Reminder reminder = new Reminder();
		reminder.setChosenService(service);
		reminder.setCustomer(customer);
		reminder.setDate(date);
		reminder.setDescription(description);
		reminder.setTime(time);
		reminderRepository.save(reminder);
		return reminder;
	}

	@Transactional
	public Reminder getReminder(Customer customer, ChosenService service) {
		return reminderRepository.findByCustomerAndChosenService(customer, service);
	}

	@Transactional
	public List<Reminder> getAllReminders(){
		return toList(reminderRepository.findAll());
	}

	@Transactional
	public List<Reminder> getCustomerReminders(Customer customer){
		return reminderRepository.findByCustomer(customer);
	}

	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

}
