package ca.mcgill.ecse321.autoRepair.controller;

import ca.mcgill.ecse321.autoRepair.dto.*;
import ca.mcgill.ecse321.autoRepair.model.*;
import ca.mcgill.ecse321.autoRepair.service.AppointmentService;
import ca.mcgill.ecse321.autoRepair.service.ChosenServiceService;
import ca.mcgill.ecse321.autoRepair.service.CustomerService;
import ca.mcgill.ecse321.autoRepair.service.ReviewService;
import ca.mcgill.ecse321.autoRepair.service.TimeSlotService;
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

@CrossOrigin(origins = "*")
@RestController
public class AppointmentController {
	@Autowired
	AppointmentService appointmentService;
	@Autowired
	ReviewService reviewService;

	@Autowired
	CustomerService customerService;

	@Autowired
	TimeSlotService timeSlotService;

	@Autowired
	ChosenServiceService chosenServiceService;

	/**
	 * @author Tamara Zard Aboujaoudeh
	 * 
	 * This method is to book an appointment for a certain service at a certain time slot.
	 * 
	 * @param username
	 * @param appointmentDate
	 * @param appointmentTime
	 * @param serviceName
	 * @return Response Entity
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = { "/make_appointment/" })
	public ResponseEntity<?> makeAppointment(@RequestParam String username, @RequestParam String appointmentDate,
			@RequestParam String appointmentTime,
			@RequestParam String serviceName) throws IllegalArgumentException {

		if(appointmentDate=="") {
			return new ResponseEntity<>("The date cannot be null", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(appointmentTime=="") {
			return new ResponseEntity<>("The time cannot be null", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		try {
			SystemTime.setSystemTime(Time.valueOf(LocalTime.now()));
			SystemTime.setSystemDate(Date.valueOf(LocalDate.now()));
			Date date = Date.valueOf(appointmentDate);
			Time startTime = Time.valueOf(appointmentTime + ":00");

			Appointment appointment = appointmentService.makeAppointment(username, serviceName, date, startTime);
			return new ResponseEntity<>(Conversion.convertToDTO(appointment), HttpStatus.CREATED);

		}catch (IllegalArgumentException e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * @author Tamara Zard Aboujaoudeh
	 * 
	 * This method updates an appointment. It can update the date, the time or the service seperately, or it can update the three
	 * at once (or any two).
	 * 
	 * @param username
	 * @param appointmentDate
	 * @param appointmentTime
	 * @param newAppointmentDate
	 * @param serviceName
	 * @param newAppointmentTime
	 * @param newServiceName
	 * @return Response Entity
	 * @throws IllegalArgumentException
	 */
	@PatchMapping(value = {"/update_appointment/"})
	public ResponseEntity<?> updateAppointment(@RequestParam String username, @RequestParam String appointmentDate, @RequestParam String appointmentTime,
			@RequestParam String newAppointmentDate, @RequestParam String serviceName, @RequestParam
			String newAppointmentTime, @RequestParam String newServiceName){

		if(appointmentTime == "") return new ResponseEntity<>("The old start time cannot be null", HttpStatus.INTERNAL_SERVER_ERROR) ;
		if(appointmentDate == "")  return new ResponseEntity<>("The old start date cannot be null", HttpStatus.INTERNAL_SERVER_ERROR);
		if(newAppointmentTime == "") return new ResponseEntity<>("The new start time cannot be null", HttpStatus.INTERNAL_SERVER_ERROR) ;
		if(newAppointmentDate == "")  return new ResponseEntity<>("The new start date cannot be null", HttpStatus.INTERNAL_SERVER_ERROR);

		SystemTime.setSystemTime(Time.valueOf(LocalTime.now()));
		SystemTime.setSystemDate(Date.valueOf(LocalDate.now()));

		Customer customer = customerService.getCustomer(username);
		Date oldDate = Date.valueOf(appointmentDate);
		Time oldTime = Time.valueOf(appointmentTime);
		ChosenService oldService = chosenServiceService.getChosenService(serviceName);

		TimeSlot timeSlot = timeSlotService.getTimeSlot(oldDate, oldTime);

		Appointment appointment = appointmentService.getAppointment(timeSlot);
		List<Appointment> appointmentLists = appointmentService.getAppointmentsOfCustomer(customer);

		boolean exists = false;

		for(int i=0; i<appointmentLists.size(); i++){
			if(appointment.equals(appointmentLists.get(i))) exists=true;
		}
		if(exists==false) return new ResponseEntity<>("The appointment does not exist for the customer", HttpStatus.INTERNAL_SERVER_ERROR);

		Date newDate = null;
		Time newStartTime = null;

		ChosenService newService = chosenServiceService.getChosenService(newServiceName);
		if(newAppointmentDate!=null && containsCharacter(newAppointmentDate)) {
			newDate = Date.valueOf(newAppointmentDate);
		}
		if(newAppointmentTime!=null && containsCharacter(newAppointmentTime)) {
			newStartTime = Time.valueOf(newAppointmentTime + ":00");
		}
		try {
			if (newService != null) {
				if (newDate == null && newStartTime == null) {
					appointmentService.updateAppointment(timeSlot.getStartDate(), timeSlot.getStartTime(), oldService.getName(), timeSlot.getStartDate(), timeSlot.getStartTime(), newService.getName());
				} else if (newDate != null && newStartTime == null) {
					appointmentService.updateAppointment(timeSlot.getStartDate(), timeSlot.getStartTime(), oldService.getName(), newDate, timeSlot.getStartTime(), newService.getName());
				} else if (newDate == null && newStartTime != null) {
					appointmentService.updateAppointment(timeSlot.getStartDate(), timeSlot.getStartTime(), oldService.getName(), timeSlot.getStartDate(), newStartTime, newService.getName());
				} else if (newDate != null && newStartTime != null) {
					appointmentService.updateAppointment(timeSlot.getStartDate(), timeSlot.getStartTime(), oldService.getName(), newDate, newStartTime, newService.getName());
				}
			} else {
				if (newDate != null && newStartTime == null) {
					appointmentService.updateAppointment(timeSlot.getStartDate(), timeSlot.getStartTime(), oldService.getName(), newDate, timeSlot.getStartTime(), oldService.getName());
				} else if (newDate == null && newStartTime != null) {
					appointmentService.updateAppointment(timeSlot.getStartDate(), timeSlot.getStartTime(), oldService.getName(), timeSlot.getStartDate(), newStartTime, oldService.getName());
				} else if (newDate != null && newStartTime != null) {
					appointmentService.updateAppointment(timeSlot.getStartDate(), timeSlot.getStartTime(), oldService.getName(), newDate, newStartTime, oldService.getName());
				}
			}
			return new ResponseEntity<>(Conversion.convertToDTO(appointment), HttpStatus.OK);

		}catch (IllegalArgumentException e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * @author Tamara Zard Aboujaoudeh
	 * 
	 * This method deletes an appointment
	 * 
	 * @param username
	 * @param appointmentDate
	 * @param appointmentTime
	 * @param serviceName
	 * @return true when successfully deleted
	 * @throws IllegalArgumentException
	 */
	@DeleteMapping(value = {"/cancel_appointment/"})
	public ResponseEntity<?> cancelAppointment(@RequestParam("username") String username, @RequestParam String appointmentDate, @RequestParam String appointmentTime, @RequestParam String serviceName){
		if(appointmentTime == "" || appointmentDate=="" || serviceName=="") return new ResponseEntity<>("Please choose an appointment", HttpStatus.INTERNAL_SERVER_ERROR) ;

		SystemTime.setSystemTime(Time.valueOf(LocalTime.now()));
		SystemTime.setSystemDate(Date.valueOf(LocalDate.now()));
		Date date = Date.valueOf(appointmentDate);
		Time startTime = Time.valueOf(appointmentTime);

		Customer customer = customerService.getCustomer(username);

		TimeSlot timeSlot = timeSlotService.getTimeSlot(date, startTime);

		Appointment appointment = appointmentService.getAppointment(timeSlot);
		List<Appointment> appointmentLists = appointmentService.getAppointmentsOfCustomer(customer);
		boolean exists = false;
		for(int i=0; i<appointmentLists.size(); i++){
			if(appointment.equals(appointmentLists.get(i))) exists=true;
		}
		if(exists==false) return new ResponseEntity<>("The appointment does not exist for the customer", HttpStatus.INTERNAL_SERVER_ERROR);

		try {
			appointmentService.cancelAppointment(serviceName, date, startTime);
			return new ResponseEntity<>(true, HttpStatus.OK);

		}catch (IllegalArgumentException e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	

	/**
	 * @author Tamara Zard Aboujaoudeh
	 * Gets a list of all the appointments
	 * @return list of all appointments 
	 */
	@GetMapping(value = { "/appointments" })
	public List<AppointmentDTO> getAllAppointments() {
		List<AppointmentDTO> appointmentDtos = new ArrayList<>();
		for (Appointment appointment: appointmentService.getAllAppointments()) {
			appointmentDtos.add(Conversion.convertToDTO(appointment));
		}
		return appointmentDtos;
	}
	
	/**
	 * @author Tamara Zard Aboujaoudeh
	 * Gets a list of all the upcoming appointments
	 * @param username
	 * @return list of all the upcoming appointments
	 */
	@GetMapping(value = {"/upcoming_appointments"})
	public ResponseEntity<?> getUpcomingAppointments() {
		try {
			List<Appointment> allAppointments = appointmentService.getAllAppointments();
			List<AppointmentDTO> appointments = new ArrayList<>();

			for (Appointment appointment : allAppointments) {
				if(appointment.getTimeSlot().getStartDate().toLocalDate().isAfter(LocalDate.now()) ||
						(appointment.getTimeSlot().getStartDate().toLocalDate().isEqual(LocalDate.now()) && 
								appointment.getTimeSlot().getEndTime().toLocalTime().isAfter(LocalTime.now()))) {
					appointments.add(Conversion.convertToDTO(appointment));

				}
			}
			return new ResponseEntity<>(appointments, HttpStatus.CREATED);

		}catch (IllegalArgumentException e){
			return new ResponseEntity<>("The customer does not exist", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	/**
	 * @author Tamara Zard Aboujaoudeh
	 * Gets a list of all the past appointments
	 * @param username
	 * @return list of all the past appointments
	 */
	@GetMapping(value = {"/past_appointments"})
	public ResponseEntity<?> getPastAppointments() {
		try {
			List<Appointment> allAppointments = appointmentService.getAllAppointments();
			List<AppointmentDTO> appointments = new ArrayList<>();

			for (Appointment appointment : allAppointments) {
				if(appointment.getTimeSlot().getStartDate().toLocalDate().isBefore(LocalDate.now()) ||
						(appointment.getTimeSlot().getStartDate().toLocalDate().isEqual(LocalDate.now()) && 
								appointment.getTimeSlot().getEndTime().toLocalTime().isBefore(LocalTime.now()))) {
					appointments.add(Conversion.convertToDTO(appointment));

				}
			}
			return new ResponseEntity<>(appointments, HttpStatus.CREATED);
		}catch (IllegalArgumentException e){
			return new ResponseEntity<>("The customer does not exist", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * @author Tamara Zard Aboujaoudeh
	 * Gets a list of all the appointments for a specific customer
	 * @param username
	 * @return list of all the appointments for a specific customer
	 */
	@GetMapping(value = {"/appointmentsOf/"})
	public ResponseEntity<?> getAppointmentsOfCustomer(@RequestParam("username") String username) {
		try {
			Customer customer = customerService.getCustomer(username);
			List<Appointment> appointmentsForCustomer = appointmentService.getAppointmentsOfCustomer(customer);
			List<AppointmentDTO> appointments = new ArrayList<>();
			for (Appointment appointment : appointmentsForCustomer) {
				appointments.add(Conversion.convertToDTO(appointment));
			}
			return new ResponseEntity<>(appointments, HttpStatus.CREATED);
		}catch (IllegalArgumentException e){
			return new ResponseEntity<>("The customer does not exist", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * @author Tamara Zard Aboujaoudeh
	 * Gets a list of all the upcoming appointments for a specific customer
	 * @param username
	 * @return list of all the upcoming appointments for a specific customer
	 */
	@GetMapping(value = {"/upcoming_appointmentsOf/"})
	public ResponseEntity<?> getUpcomingAppointmentsOfCustomer(@RequestParam("username") String username) {
		try {
			Customer customer = customerService.getCustomer(username);
			List<Appointment> appointmentsForCustomer = appointmentService.getAppointmentsOfCustomer(customer);
			List<AppointmentDTO> appointments = new ArrayList<>();
			for (Appointment appointment : appointmentsForCustomer) {
				if(appointment.getTimeSlot().getStartDate().toLocalDate().isAfter(LocalDate.now()) ||
						(appointment.getTimeSlot().getStartDate().toLocalDate().isEqual(LocalDate.now()) && 
								appointment.getTimeSlot().getEndTime().toLocalTime().isAfter(LocalTime.now()))) {
					appointments.add(Conversion.convertToDTO(appointment));

				}
			}
			return new ResponseEntity<>(appointments, HttpStatus.CREATED);
		}catch (IllegalArgumentException e){
			return new ResponseEntity<>("The customer does not exist", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	/**
	 * @author Tamara Zard Aboujaoudeh
	 * Gets a list of all the past appointments for a specific customer
	 * @param username
	 * @return list of all the past appointments for a specific customer
	 */
	@GetMapping(value = {"/past_appointmentsOf/"})
	public ResponseEntity<?> getPastAppointmentsOfCustomer(@RequestParam("username") String username) {
		try {
			Customer customer = customerService.getCustomer(username);
			List<Appointment> appointmentsForCustomer = appointmentService.getAppointmentsOfCustomer(customer);
			List<AppointmentDTO> appointments = new ArrayList<>();
			for (Appointment appointment : appointmentsForCustomer) {
				if(appointment.getTimeSlot().getStartDate().toLocalDate().isBefore(LocalDate.now()) ||
						(appointment.getTimeSlot().getStartDate().toLocalDate().isEqual(LocalDate.now()) && 
								appointment.getTimeSlot().getEndTime().toLocalTime().isBefore(LocalTime.now()))) {
					appointments.add(Conversion.convertToDTO(appointment));

				}
			}
			return new ResponseEntity<>(appointments, HttpStatus.CREATED);
		}catch (IllegalArgumentException e){
			return new ResponseEntity<>("The customer does not exist", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * @author Tamara Zard Aboujaoudeh
	 * Gets all the available time slots
	 * @param appointmentDate
	 * @return list containing all the available time slots
	 */
	@GetMapping(value = {"/availableTimeSlots/"})
	public ResponseEntity<?> getAvailableTimeSlotsForDay(@RequestParam("appointmentDate") String appointmentDate){
		Date date1 = Date.valueOf(appointmentDate);
		List<TimeSlot> timeSlotsList = timeSlotService.getAvailableTimeSlots(date1);
		List<TimeSlotDTO> availableTimeSlots = new ArrayList<>();
		for(TimeSlot timeSlot : timeSlotsList ){
			availableTimeSlots.add(Conversion.convertToDTO(timeSlot));
		}
		return new ResponseEntity<>(availableTimeSlots, HttpStatus.OK);
	}

	/**
	 * @author Tamara Zard Aboujaoudeh
	 * Gets a list of all the unavailable time slots
	 * @param stringDate
	 * @return list of all the unavailable time slots
	 */
	@GetMapping(value = {"/unavailableTimeSlots/{date}"})
	public ResponseEntity<?> getUnavailableTimeSlotsForDay(@PathVariable("date") String stringDate){
		Date date = Date.valueOf(stringDate);
		List<TimeSlot> timeSlotsList = timeSlotService.getUnavailableTimeSlots(date);
		List<TimeSlotDTO> unavailableTimeSlots = new ArrayList<>();
		for(TimeSlot timeSlot : timeSlotsList ){
			unavailableTimeSlots.add(Conversion.convertToDTO(timeSlot));
		}
		return new ResponseEntity<>(unavailableTimeSlots, HttpStatus.OK);
	}


	/**
	 * This method checks whether the input string is either empty or contains only spaces or it contains characters.
	 * @param input
	 * @return
	 */
	private static boolean containsCharacter(String input){
		for(int i = 0; i < input.length(); i++){
			if(!(Character.isWhitespace(input.charAt(i)))){
				return true;
			}
		}
		return false;
	}


//	private AppointmentDTO convertToDTO(Appointment appointment){
//		if(appointment==null)throw new IllegalArgumentException("There is no such appointment");
//		AppointmentDTO appointmentDTO= new AppointmentDTO();
//		appointmentDTO.setService(convertToDTO(appointment.getChosenService()));
//		appointmentDTO.setTimeSlot(convertToDTO(appointment.getTimeSlot()));
//		appointmentDTO.setCustomer(convertToDTO(appointment.getCustomer()));
//		return appointmentDTO;
//	}
//
//	private TimeSlotDTO convertToDTO(TimeSlot timeSlot){
//		if(timeSlot==null) throw new IllegalArgumentException("There is no such time slot");
//		TimeSlotDTO timeSlotDTO = new TimeSlotDTO(timeSlot.getStartTime(), timeSlot.getEndTime()
//				,timeSlot.getStartDate(), timeSlot.getEndDate());
//
//		return timeSlotDTO;
//	}
//
//	private ChosenServiceDTO convertToDTO(ChosenService service) {
//		if(service==null) throw new IllegalArgumentException("Service not found.");
//		Double avRating = null;
//		try {
//			avRating = reviewService.getAverageServiceReview(service.getName());
//		}
//		catch (Exception e){
//
//		}
//
//		return new ChosenServiceDTO(service.getName(), service.getDuration(), service.getPayment(), avRating);
//	}
//
//	private CustomerDTO convertToDTO(Customer customer) {
//		if(customer==null) throw new IllegalArgumentException("Customer not found.");
//		List<CarDTO> cars = new ArrayList<CarDTO>();
//
//		for (Car car : customer.getCars()) {
//			cars.add(convertToDTO(car));
//		}
//
//		return new CustomerDTO(customer.getUsername(), customer.getPassword(), customer.getNoShow(),
//				customer.getShow(), cars, convertToDTO(customer.getProfile()));
//
//	}
//
//	private CarDTO convertToDTO(Car car) {
//		if(car==null) throw new IllegalArgumentException("Car not found.");
//		return new CarDTO(car.getModel(), car.getTransmission(), car.getPlateNumber());
//	}
//
//	private ProfileDTO convertToDTO(Profile profile) {
//		if(profile == null) throw new IllegalArgumentException("Profile not found.");
//		return new ProfileDTO(profile.getFirstName(), profile.getLastName(), profile.getAddress(),
//				profile.getZipCode(), profile.getPhoneNumber(), profile.getEmail());
//	}


}