package ca.mcgill.ecse321.autoRepair.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import ca.mcgill.ecse321.autoRepair.service.ReviewService;

@CrossOrigin(origins = "*")
@RestController
public class ReviewController {

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private AppointmentRepository appointmentRepository; 

	@Autowired
	private TimeSlotRepository timeSlotRepoisoty;

	@Autowired
	private ChosenServiceRepository serviceRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@PostMapping(value = {"/create_review/"})
	public ReviewDTO createReview(@RequestParam("startDate") String startDate, @RequestParam("startTime") String startTime, @RequestParam("serviceName")
	String serviceName, @RequestParam("customerName") String customerName, @RequestParam("description")
	String description, @RequestParam("serviceRating") int serviceRating) {

		Date date = Date.valueOf(startDate);
		Time time = Time.valueOf(startTime);
		TimeSlot timeSlot = timeSlotRepoisoty.findTimeSlotByStartDateAndStartTime(date, time);
		Appointment appointment = appointmentRepository.findAppointmentByTimeSlot(timeSlot);

		Review review = reviewService.createReview(appointment, serviceName,
				customerName, description, serviceRating);

		return convertToDTO(review);
	}

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

	@PostMapping(value = {"/delete_review/"})
	public boolean deleteReview(@RequestParam("startDate") String startDate, @RequestParam("startTime")
	String startTime) {

		Date date = Date.valueOf(startDate);
		Time time = Time.valueOf(startTime);
		TimeSlot timeSlot = timeSlotRepoisoty.findTimeSlotByStartDateAndStartTime(date, time);
		Appointment appointment = appointmentRepository.findAppointmentByTimeSlot(timeSlot);

		return reviewService.deleteReview(appointment);
	}

	@GetMapping(value = {"/view_all_reviews"})
	public List<ReviewDTO> getAllReviews() {
		return reviewService.getAllReviews().stream().map(review -> 
		convertToDTO(review)).collect(Collectors.toList());
	}

	@GetMapping(value = {"/view_reviews_for_service"})
	public List<ReviewDTO> viewReviewsForService(@RequestParam("serviceName") String serviceName) {
		ChosenService service = serviceRepository.findChosenServiceByName(serviceName);
		return reviewService.viewReviewsForService(service).stream().map(review -> 
		convertToDTO(review)).collect(Collectors.toList());
	}

	@GetMapping(value = {"/view_reviews_of_customer"})
	public List<ReviewDTO> viewReviewsOfCustomer(@RequestParam("username") String username) {
		Customer customer = customerRepository.findCustomerByUsername(username);
		return reviewService.viewReviewsOfCustomer(customer).stream().map(review -> 
		convertToDTO(review)).collect(Collectors.toList());
	}

	@GetMapping(value = {"/get_average_service_review"})
	public double getAverageServiceReview(@RequestParam("serviceName") String serviceName) {
		return reviewService.getAverageServiceReview(serviceName);
	}

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
		if(service == null) throw new IllegalArgumentException("Service not found");
		return new ChosenServiceDTO(service.getName(), service.getDuration(), service.getPayment());
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