package ca.mcgill.ecse321.autoRepair.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, String>{

    Customer findCustomerByUsername(String username);
    boolean existsCustomerByUsername(String username);
}