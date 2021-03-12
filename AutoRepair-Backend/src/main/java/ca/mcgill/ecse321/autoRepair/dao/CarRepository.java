package ca.mcgill.ecse321.autoRepair.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.autoRepair.model.Car;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends CrudRepository<Car, String>{

    Car findCarByPlateNumber(String plateNumber);

}