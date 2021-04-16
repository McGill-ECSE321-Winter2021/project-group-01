package ca.mcgill.ecse321.autoRepair.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.autoRepair.dao.ChosenServiceRepository;
import ca.mcgill.ecse321.autoRepair.dao.CustomerRepository;
import ca.mcgill.ecse321.autoRepair.dao.ReviewRepository;
import ca.mcgill.ecse321.autoRepair.model.Appointment;
import ca.mcgill.ecse321.autoRepair.model.ChosenService;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.Review;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ChosenServiceRepository serviceRepository;
	

	/**
	 * @author Mohammad Saeid Nafar
	 * Creates a review
	 * @param appointment
	 * @param serviceName
	 * @param customerName
	 * @param description
	 * @param serviceRating
	 * @return review
	 */
	@Transactional
	public Review createReview(Appointment appointment, String serviceName,
			String customerName, String description, int serviceRating) {

		if(appointment == null) {
			throw new IllegalArgumentException("Appointment not found");
		}

		if(serviceName == null || serviceName == "") {
			throw new IllegalArgumentException("Service not found");
		}

		if(customerName == null || customerName == "") {
			throw new IllegalArgumentException("Customer not found");
		}

		if(serviceRating < 0 || serviceRating > 5 ) {
			throw new IllegalArgumentException("Service rating must be between 0 and 5 (inclusive)");
		}

		if(description == null) {
			throw new IllegalArgumentException("No description");
		}

		if(description == "") {
			throw new IllegalArgumentException("Description must contain at least 1 character");
		}

		Customer customer =  customerRepository.findCustomerByUsername(customerName);
		if(customer == null) {
			throw new IllegalArgumentException("No customer found");
		}

		ChosenService service = serviceRepository.findChosenServiceByName(serviceName);
		if(service == null) {
			throw new IllegalArgumentException("No service found");
		}

		Review review = reviewRepository.findReviewByAppointment(appointment);
		if(review != null) {
			throw new IllegalArgumentException("Review already exists");
		}
		review = new Review();
		review.setAppointment(appointment);
		review.setChosenService(service);
		review.setCustomer(customer);
		review.setDescription(description);
		review.setServiceRating(serviceRating);
		reviewRepository.save(review);
		return review;
	}

	/**
	 * @author Mohammad Saeid Nafar
	 * Edits a review
	 * @param appointment
	 * @param newDescription
	 * @param newRating
	 * @return review
	 */
	@Transactional
	public Review editReview(Appointment appointment, String newDescription, int newRating) {

		if(appointment == null) {
			throw new IllegalArgumentException("Appointment not found");
		}

		Review review = reviewRepository.findReviewByAppointment(appointment);

		if(newRating < 0 || newRating > 5) {
			throw new IllegalArgumentException("Service rating must be between 0 and 5 (inclusive)");
		}

		if(newDescription == "") {
			throw new IllegalArgumentException("New description must contain at least 1 character");
		}

		if(newDescription == null) {
			throw new IllegalArgumentException("No new description");
		}

		review.setDescription(newDescription);
		review.setServiceRating(newRating);
		reviewRepository.save(review);
		return review;
	}

	/**
	 * @author Mohammad Saeid Nafar
	 * Deletes a review
	 * @param appointment
	 * @return true when review is successfully deleted
	 */
	@Transactional
	public boolean deleteReview(Appointment appointment) {
		if(appointment == null) {
			throw new IllegalArgumentException("Review not deleted. Appointment not found");
		}
		Review review = reviewRepository.findReviewByAppointment(appointment);
		reviewRepository.delete(review);
		return true;
	}

	/**
	 * @author Mohammad Saeid Nafar
	 * Returns a list of reviews associated to a given a chosen service
	 * @param service
	 * @return list of reviews associated to a given a chosen service
	 */
	@Transactional
	public List<Review> viewReviewsForService(ChosenService service) {
		return toList(reviewRepository.findReviewByChosenService(service));
	}

	/**
	 * @author Mohammad Saeid Nafar
	 * Returns a list of reviews associated to a given customer
	 * @param customer
	 * @return a list of reviews associated to a given customer
	 */
	@Transactional
	public List<Review> viewReviewsOfCustomer(Customer customer) {
		return toList(reviewRepository.findReviewByCustomer(customer));
	}

	/**
	 * @author Mohammad Saeid Nafar
	 * Gets the average service rating of a given chosen service
	 * @param serviceName
	 * @return double averageServiceRating
	 */
	@Transactional
	public double getAverageServiceReview(String serviceName) {
		ChosenService service = serviceRepository.findChosenServiceByName(serviceName);
		
		if(service == null) {
			throw new IllegalArgumentException("Service not found");
		}
		List<Review> reviewsForAService = viewReviewsForService(service);
		int totalServiceRating = 0;
		for(Review review : reviewsForAService) {
			totalServiceRating = totalServiceRating + review.getServiceRating();
		}
		double averageServiceRating = (double)totalServiceRating/(double)reviewsForAService.size();
		DecimalFormat numberFormat = new DecimalFormat("#.00");
		return Double.parseDouble(numberFormat.format(averageServiceRating));
	}

	/**
	 * @author Mohammad Saeid Nafar
	 * Gets a review associated to a given appointment
	 * @param appointment
	 * @return review
	 */
	@Transactional
	public Review getReview(Appointment appointment) {
		return reviewRepository.findReviewByAppointment(appointment);
	}

	/**
	 * @author Mohammad Saeid Nafar
	 * Gets all the reviews 
	 * @return a list containing all the reviews
	 */
	@Transactional
	public List<Review> getAllReviews(){
		return toList(reviewRepository.findAll());
	}

	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
