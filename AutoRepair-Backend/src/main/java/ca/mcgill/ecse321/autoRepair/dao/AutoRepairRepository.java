package ca.mcgill.ecse321.autoRepair.dao;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.autoRepair.model.AutoRepairShopSytem;

public interface AutoRepairRepository extends CrudRepository<AutoRepairShopSytem, String>{
	
	AutoRepairShopSytem findAutoRepairShopSystemById(String id);
}


