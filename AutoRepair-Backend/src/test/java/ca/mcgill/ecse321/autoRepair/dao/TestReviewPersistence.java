package ca.mcgill.ecse321.autoRepair.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.autoRepair.model.Appointment;
import ca.mcgill.ecse321.autoRepair.model.Car;
import ca.mcgill.ecse321.autoRepair.model.ChosenService;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.Profile;
import ca.mcgill.ecse321.autoRepair.model.Review;
import ca.mcgill.ecse321.autoRepair.model.TimeSlot;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestReviewPersistence {
	
	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private ProfileRepository profileRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private CarRepository carRepository;
	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
	private ChosenServiceRepository chosenServiceRepository;
	@Autowired
	private TimeSlotRepository timeSlotRepository;

	@AfterEach
	public void clearDatabase() {
		reviewRepository.deleteAll();
		appointmentRepository.deleteAll();
		customerRepository.deleteAll();
	    profileRepository.deleteAll();
		carRepository.deleteAll();
		chosenServiceRepository.deleteAll();
		timeSlotRepository.deleteAll();
	}
	
	
	@Test
	public void testPersistAndLoadReview() {
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
		testCustomer.setNoShow(0);
		testCustomer.setShow(0);
		testCustomer.setProfile(testProfile);

		TimeSlot testSlot = new TimeSlot();
		Date startDate = Date.valueOf("2021-02-22");
		Date endDate = startDate;
		Time startTime = Time.valueOf("12:00:00");
		Time endTime = Time.valueOf("14:00:00");
		testSlot.setEndDate(endDate);
		testSlot.setEndTime(endTime);
		testSlot.setStartTime(startTime);
		testSlot.setStartDate(startDate);

		String name = "service1";
		int duration = 30;
		ChosenService testService = new ChosenService();
		testService.setName(name);
		testService.setDuration(duration);

		Appointment testAppointment = new Appointment();
		testAppointment.setCustomer(testCustomer);
		testAppointment.setChosenService(testService);
		testAppointment.setTimeSlot(testSlot);
		profileRepository.save(testProfile);
		carRepository.save(testCar);
		customerRepository.save(testCustomer);
		chosenServiceRepository.save(testService);
		timeSlotRepository.save(testSlot);
		appointmentRepository.save(testAppointment);

		
		String description = "The service was great!";
		int serviceRating = 5;
		Review review = new Review();
		review.setDescription(description);
		review.setServiceRating(serviceRating);
		review.setAppointment(testAppointment);
		review.setCustomer(testCustomer);
		review.setChosenService(testService);
		reviewRepository.save(review);
		review = null;
		review = reviewRepository.findReviewByAppointment(testAppointment);
		assertEquals(description, review.getDescription());
		assertEquals(serviceRating, review.getServiceRating());
	}

}
