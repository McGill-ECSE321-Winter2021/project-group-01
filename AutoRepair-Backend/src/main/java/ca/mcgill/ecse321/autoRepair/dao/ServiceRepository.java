package ca.mcgill.ecse321.autoRepair.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.autoRepair.model.Service;

public interface ServiceRepository extends CrudRepository<Service, String>{
	
	Service findServiceByName(String name);

}
