package ca.mcgill.ecse321.autoRepair.controller;

import ca.mcgill.ecse321.autoRepair.dto.ReminderDTO;
import ca.mcgill.ecse321.autoRepair.model.*;
import ca.mcgill.ecse321.autoRepair.service.ChosenServiceService;
import ca.mcgill.ecse321.autoRepair.service.CustomerService;
import ca.mcgill.ecse321.autoRepair.service.ReminderService;
import ca.mcgill.ecse321.autoRepair.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
public class ReminderController {


	@Autowired
	ReminderService reminderService;
	@Autowired
	ReviewService reviewService;
	@Autowired
	CustomerService customerService;
	@Autowired
	ChosenServiceService chosenServiceService;
	
	/**
	 * @author Robert Aprahamian
	 * Gets all reminders associated with a given customer
	 * @param username
	 * @return list of reminder DTO
	 */
	@GetMapping(value = { "/view_reminders_for_customer","/view_reminders_for_customer/" })
	public ResponseEntity<?> getAllRemindersForCustomer(@RequestParam String username) {
		try {
			Customer customer = customerService.getCustomer(username);
			List<Reminder> remindersForCustomer = reminderService.getCustomerReminders(customer);
			List<ReminderDTO> reminders = new ArrayList<>();
			for (Reminder reminder : remindersForCustomer) {
				if(reminder.getDate().toLocalDate().isBefore(LocalDate.now()) ||
						(reminder.getDate().toLocalDate().isEqual(LocalDate.now()) && 
								reminder.getTime().toLocalTime().isBefore(LocalTime.now()))) {
					reminders.add(Conversion.convertToDTO(reminder, reviewService));

				}
			}
			return new ResponseEntity<>(reminders, HttpStatus.CREATED);
		}catch (IllegalArgumentException exception){
			return new ResponseEntity<>("The customer does not exist", HttpStatus.INTERNAL_SERVER_ERROR);
		}}
	
	/**
	 * @author Robert Aprahamian
	 * Gets all reminders associated with a given customer
	 * @param username
	 * @return list of reminder DTO
	 */
	@GetMapping(value = { "/view_reminders","/view_reminders/" })
	public List<ReminderDTO> getAllReminders() {
		return reminderService.getAllReminders().stream().map(reminder -> Conversion.convertToDTO(reminder, reviewService)).collect(Collectors.toList());
	}
	
	/**
	 * @author Robert Aprahamian
	 * Gets a reminder given a customer and a chosen service
	 * @param username
	 * @param serviceName
	 * @return reminderDTO
	 */
	@GetMapping(value = { "/get_reminder","/get_reminder/" })
	public ReminderDTO getReminder(@RequestParam String username, @RequestParam String serviceName) {
		Customer customer = customerService.getCustomer(username);
		if (customer == null)
			throw new IllegalArgumentException("The following user does not exist: " + username);
		ChosenService chosenService = chosenServiceService.getChosenService(serviceName);
		if (chosenService == null)
			throw new IllegalArgumentException("The following service does not exist: " + serviceName);
		return Conversion.convertToDTO(reminderService.getReminder(customer,chosenService), reviewService);
	}

	/**
	 * @author Robert Aprahamian
	 * Creates a reminder
	 * @param username
	 * @param serviceName
	 * @param datestring
	 * @param description
	 * @param timestring
	 * @return reminderDTO
	 */
	@PostMapping(value = { "/create_reminder/","/create_reminder" })
	public ResponseEntity<?> createReminder
	(@RequestParam String username,@RequestParam String serviceName,@RequestParam String datestring,@RequestParam String description, @RequestParam String timestring ) {
		if(username == "")  return new ResponseEntity<>("The customer name cannot be null", HttpStatus.INTERNAL_SERVER_ERROR);
		if(serviceName == "")  return new ResponseEntity<>("The service name cannot be null", HttpStatus.INTERNAL_SERVER_ERROR);
		if(datestring == "")  return new ResponseEntity<>("The date cannot be null", HttpStatus.INTERNAL_SERVER_ERROR);
		if(description == "")  return new ResponseEntity<>("The description cannot be null", HttpStatus.INTERNAL_SERVER_ERROR);
		if(timestring == "")  return new ResponseEntity<>("The time cannot be null", HttpStatus.INTERNAL_SERVER_ERROR);
		Date date = Date.valueOf(datestring);
		Time time = Time.valueOf(timestring + ":00");
		SystemTime.setSystemDate(Date.valueOf(LocalDate.now()));
		SystemTime.setSystemTime(Time.valueOf(LocalTime.now()));
		Reminder reminder = null;
		try {
			reminder = reminderService.createReminder(serviceName, username, date, description, time);			
		}catch (IllegalArgumentException e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(Conversion.convertToDTO(reminder, reviewService), HttpStatus.CREATED);
	}

	/**
	 * @author Robert Aprahamian
	 * Updates a reminder
	 * @param username
	 * @param oldServiceName
	 * @param newServiceName
	 * @param datestring
	 * @param description
	 * @param timestring
	 * @return reminderDTO
	 */
	@PostMapping(value = { "/update_reminder","/update_reminder/" })
	public ResponseEntity<?> updateReminder
	(@RequestParam String username,@RequestParam String oldServiceName, @RequestParam String newServiceName ,@RequestParam String datestring,@RequestParam String description, @RequestParam String timestring ) {
		if(username == "")  return new ResponseEntity<>("The customer name cannot be null", HttpStatus.INTERNAL_SERVER_ERROR);
		if(oldServiceName == "")  return new ResponseEntity<>("The old service name cannot be null", HttpStatus.INTERNAL_SERVER_ERROR);
		if(newServiceName == "")  return new ResponseEntity<>("The new service name cannot be null", HttpStatus.INTERNAL_SERVER_ERROR);
		if(datestring == "")  return new ResponseEntity<>("The date cannot be null", HttpStatus.INTERNAL_SERVER_ERROR);
		if(description == "")  return new ResponseEntity<>("The description cannot be null", HttpStatus.INTERNAL_SERVER_ERROR);
		if(timestring == "")  return new ResponseEntity<>("The time cannot be null", HttpStatus.INTERNAL_SERVER_ERROR);
		Date date = Date.valueOf(datestring);
		Time time = Time.valueOf(timestring + ":00");
		SystemTime.setSystemDate(Date.valueOf(LocalDate.now()));
		SystemTime.setSystemTime(Time.valueOf(LocalTime.now()));
		Reminder reminder = null;
		try {
			reminder = reminderService.editReminder(oldServiceName, newServiceName, username, date, description, time);			
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(Conversion.convertToDTO(reminder, reviewService), HttpStatus.OK);
	}

	/**
	 * @author Robert Aprahamian
	 * Deletes a reminder
	 * @param username
	 * @param serviceName
	 * @return true if reminder is successfully deleted
	 */
	@PostMapping(value = { "/delete_reminder","/delete_reminder/" })
	public ResponseEntity<?> deleteReminder
	(@RequestParam String username,@RequestParam String serviceName) {	
		if(username == "")  return new ResponseEntity<>("The customer name cannot be null", HttpStatus.INTERNAL_SERVER_ERROR);
		if(serviceName == "")  return new ResponseEntity<>("The service name cannot be null", HttpStatus.INTERNAL_SERVER_ERROR);
		try {
			reminderService.deleteReminder(serviceName, username);
			return new ResponseEntity<>(true, HttpStatus.OK);
		}catch(IllegalArgumentException exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}