package ca.mcgill.ecse321.autoRepair.dao;

import ca.mcgill.ecse321.autoRepair.model.BookableService;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.autoRepair.model.Reminder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ReminderRepository extends CrudRepository<Reminder, Long>{
    Reminder findByCustomerAndDate(Customer customer, Date date);
    Reminder findByCustomerAndBookableService(Customer customer, BookableService service);
    List<Reminder> findByCustomer(Customer customer);
}