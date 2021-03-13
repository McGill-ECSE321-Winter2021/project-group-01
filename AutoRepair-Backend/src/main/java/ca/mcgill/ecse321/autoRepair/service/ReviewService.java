package ca.mcgill.ecse321.autoRepair.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.autoRepair.dao.ReviewRepository;
import ca.mcgill.ecse321.autoRepair.model.Appointment;
import ca.mcgill.ecse321.autoRepair.model.ChosenService;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.Review;

public class ReviewService {

	@Autowired
	ReviewRepository reviewRepository;
	
	
	@Transactional
	public Review createReview(Appointment appointment, ChosenService service,
			Customer customer, String description, int serviceRating) {
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
