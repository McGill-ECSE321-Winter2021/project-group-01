package ca.mcgill.ecse321.autoRepair.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.autoRepair.model.Car;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends CrudRepository<Car, String>{

    List<Car> findByCustomer(Customer customer);
    Car findCarByPlateNumber(String plateNumber);

}