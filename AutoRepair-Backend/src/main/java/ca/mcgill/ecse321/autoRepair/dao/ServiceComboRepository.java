package ca.mcgill.ecse321.autoRepair.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.autoRepair.model.ServiceCombo;

public interface ServiceComboRepository extends CrudRepository<ServiceCombo, String> {

	ServiceCombo findServiceComboByName(String name);
}
