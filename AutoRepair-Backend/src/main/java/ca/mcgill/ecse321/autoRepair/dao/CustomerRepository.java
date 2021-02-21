package ca.mcgill.ecse321.autoRepair.dao;

<<<<<<< Updated upstream
public class CustomerRepository {

=======
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.autoRepair.model.Reminder;
import ca.mcgill.ecse321.autoRepair.model.BookableService;
import ca.mcgill.ecse321.autoRepair.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, String> {
>>>>>>> Stashed changes
}
