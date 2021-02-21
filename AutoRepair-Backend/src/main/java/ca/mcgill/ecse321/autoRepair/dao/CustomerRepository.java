package ca.mcgill.ecse321.autoRepair.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.autoRepair.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, String>{
	
Customer findCustomerByName(String name);

}



