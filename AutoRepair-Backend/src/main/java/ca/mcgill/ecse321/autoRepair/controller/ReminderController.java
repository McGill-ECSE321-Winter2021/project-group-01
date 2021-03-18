package ca.mcgill.ecse321.autoRepair.controller;

import ca.mcgill.ecse321.autoRepair.dao.ChosenServiceRepository;
import ca.mcgill.ecse321.autoRepair.dao.CustomerRepository;
import ca.mcgill.ecse321.autoRepair.dao.ReminderRepository;
import ca.mcgill.ecse321.autoRepair.dto.ReminderDTO;
import ca.mcgill.ecse321.autoRepair.dto.CarDTO;
import ca.mcgill.ecse321.autoRepair.dto.ChosenServiceDTO;
import ca.mcgill.ecse321.autoRepair.dto.CustomerDTO;
import ca.mcgill.ecse321.autoRepair.dto.ProfileDTO;
import ca.mcgill.ecse321.autoRepair.model.*;
import ca.mcgill.ecse321.autoRepair.service.ReminderService;

import org.springframework.beans.factory.annotation.Autowired;
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
	ChosenServiceRepository chosenServiceRepository;
	@Autowired
	ReminderRepository reminderRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	ReminderService reminderService;
	
	@GetMapping(value = { "/view_reminders_for_customer","/view_reminders_for_customer/" })
	public List<ReminderDTO> getAllRemindersForCustomer(@RequestParam String username) {
		Customer customer = customerRepository.findCustomerByUsername(username);
		if (customer == null)
			throw new IllegalArgumentException("The following user does not exist: " + username);
		return reminderService.getCustomerReminders(customer).stream().map(reminder -> convertToDTO(reminder)).collect(Collectors.toList());
	}
	
	@GetMapping(value = { "/get_reminder","/get_reminder/" })
	public ReminderDTO getReminder(@RequestParam String username, @RequestParam String serviceName) {
		Customer customer = customerRepository.findCustomerByUsername(username);
		if (customer == null)
			throw new IllegalArgumentException("The following user does not exist: " + username);
		ChosenService chosenService = chosenServiceRepository.findChosenServiceByName(serviceName);
		if (chosenService == null)
			throw new IllegalArgumentException("The following service does not exist: " + serviceName);
		return convertToDTO(reminderService.getReminder(customer,chosenService ));//.stream().map(service -> convertToDTO(service)).collect(Collectors.toList());
	}

	@PostMapping(value = { "/create_reminder/","/create_reminder" })
	public ReminderDTO createReminder
	(@RequestParam String username,@RequestParam String serviceName,@RequestParam String datestring,@RequestParam String description, @RequestParam String timestring ) {
		Date date = Date.valueOf(datestring);
		Time time = Time.valueOf(timestring);
		SystemTime.setSysDate(Date.valueOf(LocalDate.now()));
		SystemTime.setSysTime(Time.valueOf(LocalTime.now()));
		Reminder reminder = reminderService.createReminder(serviceName, username, date, description, time);
		return convertToDTO(reminder);
	}

	@PostMapping(value = { "/update_reminder","/update_reminder/" })
	public ReminderDTO updateReminder
	(@RequestParam String username,@RequestParam String oldServiceName, @RequestParam String newServiceName ,@RequestParam String datestring,@RequestParam String description, @RequestParam String timestring ) {
		Date date = Date.valueOf(datestring);
		Time time = Time.valueOf(timestring);
		SystemTime.setSysDate(Date.valueOf(LocalDate.now()));
		SystemTime.setSysTime(Time.valueOf(LocalTime.now()));
		Reminder reminder = reminderService.editReminder(oldServiceName, newServiceName, username, date, description, time);
		return convertToDTO(reminder);
	}

	@PostMapping(value = { "/delete_reminder","/delete_reminder/" })
	public boolean deleteReminder
	(@RequestParam String username,@RequestParam String serviceName) {
		return reminderService.deleteReminder(serviceName, username);
	}

	private ReminderDTO convertToDTO(Reminder reminder) {
		if(reminder==null) throw new IllegalArgumentException("Service not found.");
		return new ReminderDTO(convertToDTO(reminder.getCustomer()), convertToDTO(reminder.getChosenService()), reminder.getDate(), reminder.getTime(),reminder.getDescription());
	}
	
	private CustomerDTO convertToDTO(Customer customer) {
		if(customer==null) throw new IllegalArgumentException("Customer not found.");
		List<CarDTO> cars = new ArrayList<CarDTO>();

		for (Car car : customer.getCars()) {
			cars.add(convertToDTO(car));
		}

		return new CustomerDTO(customer.getUsername(), customer.getPassword(), customer.getNoShow(), 
				customer.getShow(), cars, convertToDTO(customer.getProfile()));

	}
	
	private CarDTO convertToDTO(Car car) {
		if(car==null) throw new IllegalArgumentException("Car not found.");
		return new CarDTO(car.getModel(), car.getTransmission(), car.getPlateNumber());
	}

	private ProfileDTO convertToDTO(Profile profile) {
		if(profile == null) throw new IllegalArgumentException("Profile not found.");
		return new ProfileDTO(profile.getFirstName(), profile.getLastName(), profile.getAddress(), 
				profile.getZipCode(), profile.getPhoneNumber(), profile.getEmail());
	}

	private ChosenServiceDTO convertToDTO(ChosenService service) {
		if(service==null) throw new IllegalArgumentException("Service not found.");
		return new ChosenServiceDTO(service.getName(), service.getDuration(), service.getPayment());
	}

}