package ca.mcgill.ecse321.autoRepair.dao;

import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.ChosenService;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.autoRepair.model.Reminder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReminderRepository extends CrudRepository<Reminder, Long>{
    Reminder findByCustomerAndService(Customer customer, ChosenService bookableService);
    List<Reminder> findByCustomer(Customer customer);
}