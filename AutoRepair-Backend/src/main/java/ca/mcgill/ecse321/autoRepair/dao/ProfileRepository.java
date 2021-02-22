package ca.mcgill.ecse321.autoRepair.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.autoRepair.model.Profile;
import ca.mcgill.ecse321.autoRepair.model.Customer;

public interface ProfileRepository extends CrudRepository<Profile, Long>{

	Profile findById(long id);
	
    Profile findByCustomer(Customer customer);

    boolean existsByCustomer(Customer person);

}