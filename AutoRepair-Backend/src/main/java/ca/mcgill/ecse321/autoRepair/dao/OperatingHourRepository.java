
package ca.mcgill.ecse321.autoRepair.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.autoRepair.model.OperatingHour;

public interface OperatingHourRepository extends CrudRepository<OperatingHour, Long>{

}