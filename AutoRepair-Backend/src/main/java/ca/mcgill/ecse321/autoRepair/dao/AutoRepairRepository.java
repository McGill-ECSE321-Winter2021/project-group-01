package ca.mcgill.ecse321.autoRepair.dao;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.autoRepair.model.AutoRepairShopSystem;

public interface AutoRepairRepository extends CrudRepository<AutoRepairShopSystem, Long>{
	AutoRepairShopSystem findAutoRepairShopSystemById(String id);
}


