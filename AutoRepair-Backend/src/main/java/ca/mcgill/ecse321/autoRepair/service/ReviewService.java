package ca.mcgill.ecse321.autoRepair.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.autoRepair.dao.ReviewRepository;
import ca.mcgill.ecse321.autoRepair.dto.ReviewDTO;
import ca.mcgill.ecse321.autoRepair.model.Appointment;
import ca.mcgill.ecse321.autoRepair.model.ChosenService;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.Review;

public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	@Transactional
	public Review createReview(Appointment appointment, ChosenService service,
			Customer customer, String description, int serviceRating) {

		if(appointment == null) {
			throw new IllegalArgumentException("Appointment not found");
		}

		if(service == null) {
			throw new IllegalArgumentException("Service not found");
		}

		if(customer == null) {
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

		Review review = new Review();
		review.setAppointment(appointment);
		review.setChosenService(service);
		review.setCustomer(customer);
		review.setDescription(description);
		review.setServiceRating(serviceRating);
		reviewRepository.save(review);
		return review;
	}

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

		if(newDescription != null && newDescription != "" && newDescription != review.getDescription()) {
			review.setDescription(newDescription);
		}
		review.setServiceRating(newRating);
		reviewRepository.save(review);
		return review;
	}

	@Transactional
	public boolean deleteReview(Appointment appointment) {
		if(appointment == null) {
			throw new IllegalArgumentException("Appointment not found");
		}
		Review review = reviewRepository.findReviewByAppointment(appointment);
		reviewRepository.delete(review);
		return true;
	}

	@Transactional 
	public List<Review> viewAllReviews() {
		return toList(reviewRepository.findAll());
	}

	@Transactional
	public List<Review> viewReviewsForService(ChosenService service) {
		return toList(reviewRepository.findReviewByChosenService(service));
	}

	@Transactional
	public List<Review> viewReviewsOfCustomer(Customer customer) {
		return toList(reviewRepository.findReviewByCustomer(customer));
	}

	@Transactional
	public double getAverageServiceReview(ChosenService service) {
		List<Review> reviewsForAService = viewReviewsForService(service);
		int totalServiceRating = 0;
		for(Review review : reviewsForAService) {
			totalServiceRating = totalServiceRating + review.getServiceRating();
		}
		double averageServiceRating = (double)totalServiceRating/(double)reviewsForAService.size();
		return averageServiceRating;
	}

	@Transactional
	public Review getReview(Appointment appointment) {
		return reviewRepository.findReviewByAppointment(appointment);
	}

	@Transactional
	public List<Review> getReview(Customer customer) {
		return reviewRepository.findReviewByCustomer(customer);
	}

	@Transactional
	public List<Review> getAllReviews(){
		return toList(reviewRepository.findAll());
	}

	@Transactional
	public List<Review> geServiceReviews(ChosenService service){
		return reviewRepository.findReviewByChosenService(service);
	}

	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
