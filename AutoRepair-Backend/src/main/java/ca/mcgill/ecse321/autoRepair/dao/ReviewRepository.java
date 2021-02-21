package ca.mcgill.ecse321.autoRepair.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.autoRepair.model.Review;

public interface ReviewRepository extends CrudRepository<Review, String>{
	Review findReviewByName(String name);

}
