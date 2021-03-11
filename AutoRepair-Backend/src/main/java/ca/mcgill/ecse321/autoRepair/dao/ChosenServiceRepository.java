package ca.mcgill.ecse321.autoRepair.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.autoRepair.model.ChosenService;
import org.springframework.stereotype.Repository;

@Repository
public interface ChosenServiceRepository extends CrudRepository<ChosenService, String>{

	ChosenService findChosenServiceByName(String name);

}
