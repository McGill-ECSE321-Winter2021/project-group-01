package ca.mcgill.ecse321.autoRepair.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.autoRepair.dao.TimeSlotRepository;
import ca.mcgill.ecse321.autoRepair.dto.AppointmentDTO;
import ca.mcgill.ecse321.autoRepair.dto.CarDTO;
import ca.mcgill.ecse321.autoRepair.dto.ChosenServiceDTO;
import ca.mcgill.ecse321.autoRepair.dto.CustomerDTO;
import ca.mcgill.ecse321.autoRepair.dto.ProfileDTO;
import ca.mcgill.ecse321.autoRepair.dto.ReviewDTO;
import ca.mcgill.ecse321.autoRepair.dto.TimeSlotDTO;
import ca.mcgill.ecse321.autoRepair.model.Appointment;
import ca.mcgill.ecse321.autoRepair.model.Car;
import ca.mcgill.ecse321.autoRepair.model.ChosenService;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.Profile;
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
	public ReviewDTO createReview(@RequestParam("startDate") String startDate, @RequestParam("startTime") String startTime,
			@RequestParam("description") String description, @RequestParam("serviceRating") int serviceRating) {

		Date date = Date.valueOf(startDate);
		Time time = Time.valueOf(startTime);
		TimeSlot timeSlot = timeSlotService.getTimeSlot(date, time);
        Appointment appointment = appointmentService.getAppointment(timeSlot);

		Review review = reviewService.createReview(appointment, appointment.getChosenService().getName(),
				appointment.getCustomer().getUsername(), description, serviceRating);

		return convertToDTO(review);
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
	public ReviewDTO editReview(@RequestParam("startDate") String startDate, @RequestParam("startTime")
	String startTime, @RequestParam("newDescription") String newDescription,
	@RequestParam("newRating") int newRating) {

		Date date = Date.valueOf(startDate);
		Time time = Time.valueOf(startTime);
		TimeSlot timeSlot = timeSlotRepoisoty.findTimeSlotByStartDateAndStartTime(date, time);
		Appointment appointment = appointmentRepository.findAppointmentByTimeSlot(timeSlot);

		Review review = reviewService.editReview(appointment, newDescription, newRating);

		return convertToDTO(review);
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
		convertToDTO(review)).collect(Collectors.toList());
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
		convertToDTO(review)).collect(Collectors.toList());
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
		convertToDTO(review)).collect(Collectors.toList());
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
		return convertToDTO(review);
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
		 Double avRating = null;
		try {
			avRating = reviewService.getAverageServiceReview(service.getName());
		}
		catch (Exception e){
			
		}
	
		return new ChosenServiceDTO(service.getName(), service.getDuration(), service.getPayment(), avRating);
	}


	private AppointmentDTO convertToDTO(Appointment appointment){
		if(appointment==null)throw new IllegalArgumentException("There is no such appointment");
		AppointmentDTO appointmentDTO= new AppointmentDTO();
		appointmentDTO.setService(convertToDTO(appointment.getChosenService()));
		appointmentDTO.setTimeSlot(convertToDTO(appointment.getTimeSlot()));
		appointmentDTO.setCustomer(convertToDTO(appointment.getCustomer()));
		return appointmentDTO;
	}

	private TimeSlotDTO convertToDTO(TimeSlot timeSlot){
		if(timeSlot==null) throw new IllegalArgumentException("There is no such time slot");
		TimeSlotDTO timeSlotDTO = new TimeSlotDTO(timeSlot.getStartTime(), timeSlot.getEndTime()
				,timeSlot.getStartDate(), timeSlot.getEndDate());

		return timeSlotDTO;
	}

	private ReviewDTO convertToDTO(Review review) {
		if(review==null) throw new IllegalArgumentException("Review not found.");
		return new ReviewDTO(review.getDescription(), review.getServiceRating(),
				convertToDTO(review.getCustomer()), convertToDTO(review.getAppointment()),
				convertToDTO(review.getChosenService()));
	}

}