package ca.mcgill.ecse321.autoRepair.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.autoRepair.model.Owner;

public interface ServiceComboRepository extends CrudRepository<Owner, String> {

}
