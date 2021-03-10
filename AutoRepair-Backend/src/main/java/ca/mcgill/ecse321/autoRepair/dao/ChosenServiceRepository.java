package ca.mcgill.ecse321.autoRepair.dao;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

import ca.mcgill.ecse321.autoRepair.model.ChosenService;

@Repository
public interface ChosenServiceRepository extends CrudRepository<ChosenService, String>{
	
	ChosenService findChosenServiceByName(String name);

}
