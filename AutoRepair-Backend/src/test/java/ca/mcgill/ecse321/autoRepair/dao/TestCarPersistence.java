package ca.mcgill.ecse321.autoRepair.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.autoRepair.model.Car;
import ca.mcgill.ecse321.autoRepair.model.Car.CarTransmission;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestCarPersistence {
	
	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private CarRepository carRepository;
	
	@AfterEach
	public void clearDatabase() {
		carRepository.deleteAll();
	}
	
	@Test
	public void testPersistAndLoadCar() {
		String model = "Lambo";
		String plateNumber = "Number 1";
		Car car = new Car();
		CarTransmission carTransmition = CarTransmission.Automatic;
		car.setModel(model);
		car.setPlateNumber(plateNumber);
		car.setTransmission(carTransmition);
		carRepository.save(car);
		car = null;
		car = carRepository.findCarByPlateNumber(plateNumber);
		assertEquals(model, car.getModel());
		assertEquals(plateNumber, car.getPlateNumber());
		assertEquals(carTransmition, car.getTransmission());
	}

}
