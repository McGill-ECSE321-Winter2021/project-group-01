package ca.mcgill.ecse321.autoRepair.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.autoRepair.model.Assistant;


public interface AssistantRepository extends CrudRepository<Assistant, String>{
	
	Assistant findAssistantByUsername(String username);
}
