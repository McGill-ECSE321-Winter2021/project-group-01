package ca.mcgill.ecse321.autoRepair.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.autoRepair.dao.ChosenServiceRepository;
import ca.mcgill.ecse321.autoRepair.dao.CustomerRepository;
import ca.mcgill.ecse321.autoRepair.dao.ReminderRepository;
import ca.mcgill.ecse321.autoRepair.model.ChosenService;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.Reminder;
import ca.mcgill.ecse321.autoRepair.model.SystemTime;

@Service
public class ReminderService {

	@Autowired
	ReminderRepository reminderRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	ChosenServiceRepository chosenServiceRepository;
	
	/**
	 * @author Robert Aprahamian
	 * Creates a reminder
	 * @param serviceName
	 * @param customerName
	 * @param date
	 * @param description
	 * @param time
	 * @return reminder
	 */
	@Transactional
	public Reminder createReminder(String serviceName, String customerName, Date date,
			String description, Time time) {
		
		if(serviceName == null || serviceName.equals("") || containsCharacter(serviceName)==false) {
			throw new IllegalArgumentException("Service Invalid");
		}
		
		if(customerName == null || customerName.equals("") || containsCharacter(customerName)==false) {
			throw new IllegalArgumentException("Customer Invalid");
		}
		
		if (description==null|| description.equals("") || containsCharacter(description)==false) {
			throw new IllegalArgumentException("Description Invalid");
		}
		
		if(description.length()>50) {
			throw new IllegalArgumentException("Description Invalid, must be less than 50 characters");
		}
		
		if (time==null) {
			throw new IllegalArgumentException("Time Invalid");
		}
		
		if (date==null) {
			throw new IllegalArgumentException("Date Invalid");
		}
		else {
		LocalDate localDate = date.toLocalDate();
		Date now = SystemTime.getSystemDate();
		LocalDate now2 = now.toLocalDate();
		if(localDate.isBefore(now2)) {
			throw new IllegalArgumentException("Date has passed");
		}
		
	
		if(localDate.isEqual(now2)) {
			LocalTime localTime = time.toLocalTime();
			if(localTime.isBefore(SystemTime.getSystemTime().toLocalTime())) {
				throw new IllegalArgumentException("Time has passed");
			}
		}
		}
		
		Customer customer = customerRepository.findCustomerByUsername(customerName);
        if (customer == null) throw new IllegalArgumentException("The following user does not exist: " + customerName);
        ChosenService chosenService = chosenServiceRepository.findChosenServiceByName(serviceName);

        if (chosenService == null) throw new IllegalArgumentException("The following service does not exist: " + serviceName);
        Reminder r = reminderRepository.findByCustomerAndChosenService(customer, chosenService);
		if(r!=null) throw new IllegalArgumentException("This reminder is already created");
       
			Reminder reminder = new Reminder();
			reminder.setChosenService(chosenService);
			reminder.setCustomer(customer);
			reminder.setDate(date);
			reminder.setDescription(description);
			reminder.setTime(time);
			reminderRepository.save(reminder);
			return reminder;	
	
	}
	
	/**
	 * @author Robert Aprahamian
	 * Edits a reminder
	 * @param oldServiceName
	 * @param newServiceName
	 * @param customerName
	 * @param newDate
	 * @param description
	 * @param newTime
	 * @return reminder
	 */
	@Transactional
	public Reminder editReminder(String oldServiceName, String newServiceName, String customerName, Date newDate,
			String description, Time newTime) {
		
				if(oldServiceName == null || oldServiceName.equals("") || containsCharacter(oldServiceName)==false) {
					throw new IllegalArgumentException("Old Service Invalid");
				}
				
				if(newServiceName == null || newServiceName.equals("") || containsCharacter(newServiceName)==false) {
					throw new IllegalArgumentException("New Service Invalid");
				}
				
				if(customerName == null || customerName.equals("") || containsCharacter(customerName)==false) {
					throw new IllegalArgumentException("Customer Invalid");
				}
				
				if (description==null|| description.equals("") || containsCharacter(description)==false) {
					throw new IllegalArgumentException("Description Invalid");
				}
				if(description.length()>50) {
					throw new IllegalArgumentException("Description Invalid, must be less than 50 characters");
				}
				
				if (newTime==null) {
					throw new IllegalArgumentException("Time Invalid");
				}
				
				if (newDate==null) {
					throw new IllegalArgumentException("Date Invalid");
				}
				else {
				LocalDate localDate = newDate.toLocalDate();
				Date now = SystemTime.getSystemDate();
				LocalDate now2 = now.toLocalDate();
				if(localDate.isBefore(now2)) {
					throw new IllegalArgumentException("Date has passed");
				}
				
			
				if(localDate.isEqual(now2)) {
					LocalTime localTime = newTime.toLocalTime();
					if(localTime.isBefore(SystemTime.getSystemTime().toLocalTime())) {
						throw new IllegalArgumentException("Time has passed");
					}
				}
				}
				Customer oldCustomer = customerRepository.findCustomerByUsername(customerName);
		        if (oldCustomer == null)
		            throw new IllegalArgumentException("The following user does not exist: " + customerName);

		        ChosenService oldChosenService = chosenServiceRepository.findChosenServiceByName(oldServiceName);
		        if (oldChosenService == null)
		            throw new IllegalArgumentException("The following service does not exist: " + oldServiceName);
		        
		        ChosenService newChosenService = chosenServiceRepository.findChosenServiceByName(newServiceName);
		        if (newChosenService == null)
		            throw new IllegalArgumentException("The following service does not exist: " + newServiceName);
		        Reminder r2 = reminderRepository.findByCustomerAndChosenService(oldCustomer, newChosenService);
		        if(r2!=null) 
		        	throw new IllegalArgumentException("Such a reminder is already exists.");
       
					Reminder reminder = getReminder(oldCustomer,oldChosenService);
					
					reminder.setChosenService(newChosenService);
					reminder.setCustomer(oldCustomer);
					reminder.setDate(newDate);
					reminder.setDescription(description);
					reminder.setTime(newTime);
					reminderRepository.save(reminder);
					return reminder;
	}
	

	/**
	 * @author Robert Aprahamian
	 * Deletes a reminder
	 * @param serviceName
	 * @param customerName
	 * @return true when reminder is successfully deleted
	 */
	@Transactional
	public boolean deleteReminder(String serviceName, String customerName) {

		String error = "";

		if(serviceName == null || serviceName.equals("") || containsCharacter(serviceName)==false) {
			error = error.concat("Service Invalid");
		}

		if(customerName == null || customerName.equals("") || containsCharacter(customerName)==false) {
			error = error.concat("Customer Invalid");
		}
		
		if (!(error.equals(""))) {
			throw new IllegalArgumentException(error);
		}
		
		Customer customer = customerRepository.findCustomerByUsername(customerName);
		if (customer == null)
			throw new IllegalArgumentException("The following user does not exist: " + customerName);
		ChosenService chosenService = chosenServiceRepository.findChosenServiceByName(serviceName);
		if (chosenService == null)
			throw new IllegalArgumentException("The following service does not exist: " + serviceName);

		Reminder reminder = getReminder(customer, chosenService);
		if(reminder!=null) {
			reminderRepository.delete(reminder);
			return true;
		}
		else throw new IllegalArgumentException("Reminder does not exist");
	}

	/**
	 * @author Robert Aprahamian
	 * Gets a reminder given a customer and a chosen service
	 * @param customer
	 * @param service
	 * @return reminder
	 */
	@Transactional
	public Reminder getReminder(Customer customer, ChosenService service) {
		return reminderRepository.findByCustomerAndChosenService(customer, service);
	}

	/**
	 * @author Robert Aprahamian
	 * Returns a list of all the reminders
	 * @return list of all reminders
	 */
	@Transactional
	public List<Reminder> getAllReminders(){
		return toList(reminderRepository.findAll());
	}
	
	/**
	 * @author Robert Aprahamian
	 * Returns a list of all the reminders associated to a given customer
	 * @param customer
	 * @return list of reminders associated to a given customer
	 */
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

	/**
	 * This method checks whether the input string contains any character or is only white spaces
	 * @param input
	 * @return
	 */
	public static boolean containsCharacter(String input){
	    if(input != null){
	        for(int i = 0; i < input.length(); i++){
	            if(!(Character.isWhitespace(input.charAt(i)))){
	                return true;
	            }
	        }
	    }
	    return false;
	}
	
}