package ca.mcgill.ecse321.autoRepair.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.autoRepair.model.Assistant;
import org.springframework.stereotype.Repository;

@Repository
public interface AssistantRepository extends CrudRepository<Assistant, String>{
	
	Assistant findAssistantByUsername(String username);

	@Override
	void deleteAll();
}
