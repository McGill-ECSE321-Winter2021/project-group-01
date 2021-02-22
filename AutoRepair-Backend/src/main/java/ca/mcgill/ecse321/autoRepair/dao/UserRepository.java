package ca.mcgill.ecse321.autoRepair.dao;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.autoRepair.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String>{

	User findUserByUsername(String name);
	void deleteAll();
	
}
