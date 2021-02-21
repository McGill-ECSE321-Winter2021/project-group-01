package ca.mcgill.ecse321.autoRepair.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.autoRepair.model.Business;

public interface BusinessRepository extends CrudRepository<Business, String> {

	Business findBusinessById(String id);
}
