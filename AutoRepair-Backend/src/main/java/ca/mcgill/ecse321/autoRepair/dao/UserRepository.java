package ca.mcgill.ecse321.autoRepair.dao;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.autoRepair.model.User;


public interface UserRepository extends CrudRepository<User, String>{

	void deleteAll();
	
}
