package ca.mcgill.ecse321.autoRepair.dao;

<<<<<<< Updated upstream
public class ProfileRepository {

}
=======
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.autoRepair.model.Profile;
import ca.mcgill.ecse321.autoRepair.model.Customer;

public interface ProfileRepository extends CrudRepository<Profile, String>{

    Profile findByCustomer(Customer customer);

    boolean existsByCustomer(Customer person);

}
>>>>>>> Stashed changes
