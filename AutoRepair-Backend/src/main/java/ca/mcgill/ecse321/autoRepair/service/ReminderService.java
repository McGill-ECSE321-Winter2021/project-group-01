  
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
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	ChosenServiceRepository chosenServiceRepository;
	
	@Transactional
	public Reminder createReminder(String serviceName, String customerName, Date date,
			String description, Time time) {
		//String error = "";
		
		if(serviceName == null || serviceName.equals("") || containsCharacter(serviceName)==false) {
			throw new IllegalArgumentException("Service Invalid");
			//error = error.concat("Service not found. ");
		}
		
		if(customerName == null || customerName.equals("") || containsCharacter(customerName)==false) {
			//error = error.concat("Customer not found. ");
			throw new IllegalArgumentException("Customer Invalid");
		}
		
		if (description==null|| description.equals("") || containsCharacter(description)==false) {
			throw new IllegalArgumentException("Description Invalid");
			//error = error.concat("Invalid date. ");
		}
		
		if (time==null) {
			throw new IllegalArgumentException("Time Invalid");
		//error = error.concat("");
		}
		
		if (date==null) {
			throw new IllegalArgumentException("Date Invalid");
			//error = error.concat("Invalid date. ");
		}
		else {
		LocalDate localDate = date.toLocalDate();
		Date now = SystemTime.getSysDate();
		LocalDate now2 = now.toLocalDate();
		if(localDate.isBefore(now2)) {
			//error = error.concat("Date has passed. ");
			throw new IllegalArgumentException("Date has passed");
		}
		
	
		if(localDate.isEqual(now2)) {
			LocalTime localTime = time.toLocalTime();
			if(localTime.isBefore(LocalTime.now())) {
				//error = error.concat("Time has passed.");
				throw new IllegalArgumentException("Time has passed");
			}
		}
		}
		
		Customer customer = customerRepository.findCustomerByUsername(customerName);
        if (customer == null)
            throw new IllegalArgumentException("The following user does not exist: " + customerName);
        ChosenService chosenService = chosenServiceRepository.findChosenServiceByName(serviceName);
        if (chosenService == null)
            throw new IllegalArgumentException("The following service does not exist: " + serviceName);
//        Reminder r = reminderRepository.findByCustomerAndChosenService(customer, chosenService);
//		if(r!=null) throw new IllegalArgumentException("This reminder is already created");
        
			Reminder reminder = new Reminder();
			reminder.setChosenService(chosenService);
			reminder.setCustomer(customer);
			reminder.setDate(date);
			reminder.setDescription(description);
			reminder.setTime(time);
			reminderRepository.save(reminder);
			return reminder;	
	
	}
	
	@Transactional
	public Reminder editReminder(String oldServiceName, String newServiceName, String customerName, Date newDate,
			String description, Time newTime) { //String newCustomerName,
		//String error = "";
		
				if(oldServiceName == null || oldServiceName.equals("") || containsCharacter(oldServiceName)==false) {
					throw new IllegalArgumentException("Old Service Invalid");
					//error = error.concat("Service not found. ");
				}
				
				if(newServiceName == null || newServiceName.equals("") || containsCharacter(newServiceName)==false) {
					throw new IllegalArgumentException("New Service Invalid");
					//error = error.concat("Service not found. ");
				}
				
				if(customerName == null || customerName.equals("") || containsCharacter(customerName)==false) {
					//error = error.concat("Customer not found. ");
					throw new IllegalArgumentException("Customer Invalid");
				}
				
//				if(newCustomerName == null || newCustomerName.equals("") || containsCharacter(newCustomerName)==false) {
//					//error = error.concat("Customer not found. ");
//					throw new IllegalArgumentException("New Customer Invalid");
//				}
				
				
				//Maybe do a constraint for description
				if (description==null|| description.equals("") || containsCharacter(description)==false) {
					throw new IllegalArgumentException("Description Invalid");
					//error = error.concat("Invalid date. ");
				}
				
				if (newTime==null) {
					throw new IllegalArgumentException("Time Invalid");
				//error = error.concat("");
				}
				
				if (newDate==null) {
					throw new IllegalArgumentException("Date Invalid");
					//error = error.concat("Invalid date. ");
				}
				else {
				LocalDate localDate = newDate.toLocalDate();
				Date now = SystemTime.getSysDate();
				LocalDate now2 = now.toLocalDate();
				if(localDate.isBefore(now2)) {
					//error = error.concat("Date has passed. ");
					throw new IllegalArgumentException("Date has passed");
				}
				
			
				if(localDate.isEqual(now2)) {
					LocalTime localTime = newTime.toLocalTime();
					if(localTime.isBefore(LocalTime.now())) {
						//error = error.concat("Time has passed.");
						throw new IllegalArgumentException("Time has passed");
					}
				}
				}
				
				Customer oldCustomer = customerRepository.findCustomerByUsername(customerName);
		        if (oldCustomer == null)
		            throw new IllegalArgumentException("The following user does not exist: " + customerName);
		        
//		    	Customer newCustomer = customerRepository.findCustomerByUsername(newCustomerName);
//		        if (newCustomer == null)
//		            throw new IllegalArgumentException("The following user does not exist: " + newCustomerName);
//		        
		        ChosenService oldChosenService = chosenServiceRepository.findChosenServiceByName(oldServiceName);
		        if (oldChosenService == null)
		            throw new IllegalArgumentException("The following service does not exist: " + oldServiceName);
		        
		        ChosenService newChosenService = chosenServiceRepository.findChosenServiceByName(newServiceName);
		        if (newChosenService == null)
		            throw new IllegalArgumentException("The following service does not exist: " + newServiceName);
//		        Reminder r = reminderRepository.findByCustomerAndChosenService(customer, chosenService);
//				if(r!=null) throw new IllegalArgumentException("This reminder is already created");
		        
					Reminder reminder = getReminder(oldCustomer,oldChosenService);
					
					Date oldDate = reminder.getDate();
					LocalDate localOldDate = oldDate.toLocalDate();
					Time oldTime = reminder.getTime();
					LocalTime localOldTime = oldTime.toLocalTime();
					
					if(localOldDate.isBefore(newDate.toLocalDate()))
						throw new IllegalArgumentException("Reminder already sent, cannot be modified");
					
					if(localOldDate.isEqual(newDate.toLocalDate()) && newTime.toLocalTime().isAfter(localOldTime))
						throw new IllegalArgumentException("Reminder already sent today, cannot be modified");
					
					reminder.setChosenService(newChosenService);
					reminder.setCustomer(oldCustomer);
					reminder.setDate(newDate);
					reminder.setDescription(description);
					reminder.setTime(newTime);
					reminderRepository.save(reminder);
					return reminder;
//		Reminder r = getReminder(customer, service);
//		if(r!=null) {
//			if(service == null) {
//				throw new IllegalArgumentException("Service not found");
//			}
//			
//			if(customer == null) {
//				throw new IllegalArgumentException("Customer not found");
//			}
//			
//			LocalDate localDate = date.toLocalDate();
//			if(localDate.isBefore(LocalDate.now())) {
//				throw new IllegalArgumentException("Invalid date");
//			}
//			
//			if(localDate.isEqual(LocalDate.now())) {
//				LocalTime localTime = time.toLocalTime();
//				if(localTime.isBefore(LocalTime.now())) {
//					throw new IllegalArgumentException("Invalid time");
//				}
//			}
//			
//			r.setChosenService(service);
//			r.setCustomer(customer);
//			r.setDate(date);
//			r.setDescription(description);
//			r.setTime(time);
//			reminderRepository.save(r);
//			return r;
//		}
//		else throw new IllegalArgumentException("Reminder invalid");
		
		
	}
	
	@Transactional
	public Reminder deleteReminder(ChosenService service, Customer customer, Date date,
			String description, Time time) {
		
		Reminder r = getReminder(customer, service);
		if(r!=null) {
			r = null;
		reminderRepository.save(r); // ?
		return null;
		}
		else throw new IllegalArgumentException("Reminder invalid");
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