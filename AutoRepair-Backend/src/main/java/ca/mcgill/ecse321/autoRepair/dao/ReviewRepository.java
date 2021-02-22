package ca.mcgill.ecse321.autoRepair.dao;

import ca.mcgill.ecse321.autoRepair.model.Customer;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.autoRepair.model.Review;

public interface ReviewRepository extends CrudRepository<Review, Long>{
    Review findReviewByCustomer(Customer customer);

}