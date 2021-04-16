package ca.mcgill.ecse321.autoRepair.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.autoRepair.model.Car;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.Profile;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestCustomerPersistence {
	
	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired 
	private CarRepository carRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@AfterEach
	public void clearDatabase() {
		customerRepository.deleteAll();
		carRepository.deleteAll();
		profileRepository.deleteAll();
	}
	
	@Test
	public void testPersistAndLoadCustomer(){
		Car testCar = new Car();
		List<Car> carList = new ArrayList<>();
		testCar.setModel("testModel");
		testCar.setPlateNumber("123456");
		testCar.setTransmission(Car.CarTransmission.Automatic);

		Profile testProfile = new Profile();
		testProfile.setFirstName("TestName");
		testProfile.setAddress("Test Address");
		testProfile.setEmail("testemail@test.com");
		testProfile.setLastName("TestLastName");
		testProfile.setPhoneNumber("(123)456-7890");
		testProfile.setZipCode("H1V 3T2");

		String username = "testCustomer";
		String password = "testPassword";
		Customer testCustomer = new Customer();

		carList.add(testCar);
		testCustomer.setUsername(username);
		testCustomer.setPassword(password);
		testCustomer.setCars(carList);
		testCustomer.setProfile(testProfile);

		profileRepository.save(testProfile);
		carRepository.save(testCar);
		customerRepository.save(testCustomer);

		testCustomer=null;

		boolean exists = customerRepository.existsCustomerByUsername(username);
		testCustomer= customerRepository.findCustomerByUsername(username);

		assertNotNull(testCustomer);
		assertEquals(exists, true);
		assertEquals(username, testCustomer.getUsername());
		assertEquals(password,testCustomer.getPassword());
		assertEquals(password,testCustomer.getPassword());
		assertEquals(testProfile.getAddress(),testCustomer.getProfile().getAddress());
		assertEquals(testProfile.getEmail(),testCustomer.getProfile().getEmail());
		assertEquals(testProfile.getFirstName(),testCustomer.getProfile().getFirstName());
		assertEquals(testProfile.getLastName(),testCustomer.getProfile().getLastName());
		assertEquals(testProfile.getPhoneNumber(),testCustomer.getProfile().getPhoneNumber());
		assertEquals(testProfile.getZipCode(),testCustomer.getProfile().getZipCode());
		assertEquals(carList.get(0).getTransmission(),testCustomer.getCars().get(0).getTransmission());
	}

}
