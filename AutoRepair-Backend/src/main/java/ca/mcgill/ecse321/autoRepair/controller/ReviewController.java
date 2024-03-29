package ca.mcgill.ecse321.autoRepair.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.autoRepair.dao.TimeSlotRepository;
import ca.mcgill.ecse321.autoRepair.dto.ReviewDTO;
import ca.mcgill.ecse321.autoRepair.model.Appointment;
import ca.mcgill.ecse321.autoRepair.model.ChosenService;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.Review;
import ca.mcgill.ecse321.autoRepair.model.TimeSlot;
import ca.mcgill.ecse321.autoRepair.dao.AppointmentRepository;
import ca.mcgill.ecse321.autoRepair.dao.ChosenServiceRepository;
import ca.mcgill.ecse321.autoRepair.dao.CustomerRepository;
import ca.mcgill.ecse321.autoRepair.service.AppointmentService;
import ca.mcgill.ecse321.autoRepair.service.ReviewService;
import ca.mcgill.ecse321.autoRepair.service.TimeSlotService;

@CrossOrigin(origins = "*")
@RestController
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private TimeSlotService timeSlotService;
	
	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private AppointmentRepository appointmentRepository; 

	@Autowired
	private TimeSlotRepository timeSlotRepoisoty;

	@Autowired
	private ChosenServiceRepository serviceRepository;

	@Autowired
	private CustomerRepository customerRepository;

	/**
	 * @author Mohammad Saeid Nafar
	 * Creates a review
	 * @param startDate
	 * @param startTime
	 * @param serviceName
	 * @param customerName
	 * @param description
	 * @param serviceRating
	 * @return reviewDTO
	 */
	@PostMapping(value = {"/create_review/"})
	public ResponseEntity<?> createReview(@RequestParam("startDate") String startDate, @RequestParam("startTime") String startTime,
			@RequestParam("description") String description, @RequestParam("serviceRating") int serviceRating) {

		Date date = Date.valueOf(startDate);
		Time time = Time.valueOf(startTime);
		if(date.toLocalDate().isAfter(LocalDate.now()) || 
			(date.toLocalDate().isEqual(LocalDate.now()) && 
					time.toLocalTime().isAfter(LocalTime.now()))) {
			return new ResponseEntity<>("Cannot create  a review for a future appointment.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		TimeSlot timeSlot = timeSlotService.getTimeSlot(date, time);
        Appointment appointment = appointmentService.getAppointment(timeSlot);
        
        Review review = null;
        try {
        	review = reviewService.createReview(appointment, appointment.getChosenService().getName(),
				appointment.getCustomer().getUsername(), description, serviceRating);
        }catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

		return new ResponseEntity<>(Conversion.convertToDTO(review, reviewService), HttpStatus.CREATED);
	}


	/**
	 * @author Mohammad Saeid Nafar
	 * Edits a review description and/or service rating
	 * @param startDate
	 * @param startTime
	 * @param newDescription
	 * @param newRating
	 * @return reviewDTO
	 */
	@PostMapping(value = {"/edit_review/"})
	public ResponseEntity<?> editReview(@RequestParam("startDate") String startDate, @RequestParam("startTime")
	String startTime, @RequestParam("newDescription") String newDescription,
	@RequestParam("newRating") int newRating) {

		Date date = Date.valueOf(startDate);
		Time time = Time.valueOf(startTime);
		TimeSlot timeSlot = timeSlotRepoisoty.findTimeSlotByStartDateAndStartTime(date, time);
		Appointment appointment = appointmentRepository.findAppointmentByTimeSlot(timeSlot);
		Review review = null;
		try {
			 review = reviewService.editReview(appointment, newDescription, newRating);
		}catch (IllegalArgumentException exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(Conversion.convertToDTO(review, reviewService), HttpStatus.OK);
	}

	/**
	 * @author Mohammad Saeid Nafar
	 * Deletes a review
	 * @param startDate
	 * @param startTime
	 * @return true if given review is successfully deleted
	 */
	@PostMapping(value = {"/delete_review/"})
	public boolean deleteReview(@RequestParam("startDate") String startDate, @RequestParam("startTime")
	String startTime) {

		Date date = Date.valueOf(startDate);
		Time time = Time.valueOf(startTime);
		TimeSlot timeSlot = timeSlotRepoisoty.findTimeSlotByStartDateAndStartTime(date, time);
		Appointment appointment = appointmentRepository.findAppointmentByTimeSlot(timeSlot);

		return reviewService.deleteReview(appointment);
	}

	/**
	 * @author Mohammad Saeid Nafar
	 * Gets all reviews
	 * @return list of review DTOs
	 */
	@GetMapping(value = {"/view_all_reviews"})
	public List<ReviewDTO> getAllReviews() {
		return reviewService.getAllReviews().stream().map(review -> 
		Conversion.convertToDTO(review, reviewService)).collect(Collectors.toList());
	}

	/**
	 * @author Mohammad Saeid Nafar
	 * Gets all reviews for a given chosen service
	 * @param serviceName
	 * @return list of review DTOs for a given chosen service
	 */
	@GetMapping(value = {"/view_reviews_for_service"})
	public List<ReviewDTO> viewReviewsForService(@RequestParam("serviceName") String serviceName) {
		ChosenService service = serviceRepository.findChosenServiceByName(serviceName);
		return reviewService.viewReviewsForService(service).stream().map(review -> 
		Conversion.convertToDTO(review, reviewService)).collect(Collectors.toList());
	}

	/**
	 * @author Mohammad Saeid Nafar
	 * Gets all reviews associated to a given customer
	 * @param username
	 * @return list of review DTOs associated to a given customer
	 */
	@GetMapping(value = {"/view_reviews_of_customer"})
	public List<ReviewDTO> viewReviewsOfCustomer(@RequestParam("username") String username) {
		Customer customer = customerRepository.findCustomerByUsername(username);
		return reviewService.viewReviewsOfCustomer(customer).stream().map(review -> 
		Conversion.convertToDTO(review, reviewService)).collect(Collectors.toList());
	}

	/**
	 * @author Mohammad Saeid Nafar
	 * Gets the average service review of a given chosen service
	 * @param serviceName
	 * @return double averageServiceReview
	 */
	@GetMapping(value = {"/get_average_service_review"})
	public double getAverageServiceReview(@RequestParam("serviceName") String serviceName) {
		return reviewService.getAverageServiceReview(serviceName);
	}

	/**
	 * @author Mohammad Saeid Nafar
	 * Gets a review given an appointment 
	 * @param startDate
	 * @param startTime
	 * @return reviewDTO associated to a given appointment
	 */
	@GetMapping(value = {"/get_review"})
	public ReviewDTO getReview(@RequestParam("startDate") String startDate, @RequestParam("startTime")
	String startTime) {
		Date date = Date.valueOf(startDate);
		Time time = Time.valueOf(startTime);
		TimeSlot timeSlot = timeSlotRepoisoty.findTimeSlotByStartDateAndStartTime(date, time);
		Appointment appointment = appointmentRepository.findAppointmentByTimeSlot(timeSlot);
		Review review = reviewService.getReview(appointment);
		return Conversion.convertToDTO(review, reviewService);
	}
}