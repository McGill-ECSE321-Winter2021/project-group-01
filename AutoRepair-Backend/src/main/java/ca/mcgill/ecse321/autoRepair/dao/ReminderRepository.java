package ca.mcgill.ecse321.autoRepair.dao;

<<<<<<< Updated upstream
public class ReminderRepository {

}
=======
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.autoRepair.model.Reminder;
import ca.mcgill.ecse321.autoRepair.model.BookableService;
import ca.mcgill.ecse321.autoRepair.model.Customer;

import java.util.List;

public interface ReminderRepository extends CrudRepository<Reminder, String>{

    List<Reminder> findByCustomerAndServiceName(Customer customer, BookableService service);
    boolean existsByCustomerAndService(Customer customer, BookableService service);
    List<Reminder> findByCustomer(Customer customer);
    List<Reminder> findByService(BookableService service);

}
>>>>>>> Stashed changes
