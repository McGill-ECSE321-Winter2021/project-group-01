package ca.mcgill.ecse321.autoRepair.dao;

import ca.mcgill.ecse321.autoRepair.model.Appointment;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.ChosenService;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.autoRepair.model.Review;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long>{
	
	Review findReviewByCustomerAndAppointment(Customer customer, Appointment app);
	//List<Review> findReviewByChosenService(ChosenService chosenService);
}
