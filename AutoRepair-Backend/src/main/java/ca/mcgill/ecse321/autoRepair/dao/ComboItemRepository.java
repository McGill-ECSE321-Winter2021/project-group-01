package ca.mcgill.ecse321.autoRepair.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.autoRepair.model.ComboItem;
import ca.mcgill.ecse321.autoRepair.model.Service;

public interface ComboItemRepository extends CrudRepository<ComboItem, Long> {
	List<ComboItem> findByService(Service service);
}