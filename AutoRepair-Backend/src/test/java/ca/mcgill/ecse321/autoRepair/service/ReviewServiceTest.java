package ca.mcgill.ecse321.autoRepair.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.autoRepair.dao.CarRepository;
import ca.mcgill.ecse321.autoRepair.dao.CustomerRepository;
import ca.mcgill.ecse321.autoRepair.dao.ProfileRepository;
import ca.mcgill.ecse321.autoRepair.dao.ReviewRepository;
import ca.mcgill.ecse321.autoRepair.model.Appointment;
import ca.mcgill.ecse321.autoRepair.model.Car;
import ca.mcgill.ecse321.autoRepair.model.Car.CarTransmission;
import ca.mcgill.ecse321.autoRepair.model.ChosenService;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.Profile;
import ca.mcgill.ecse321.autoRepair.model.Review;
import ca.mcgill.ecse321.autoRepair.model.TimeSlot;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

	@Mock
	private ReviewRepository reviewRepo;

	@Mock
	private CustomerRepository cusRepo;

	@Mock
	private ProfileRepository profileRepo;

	@Mock
	private CarRepository carRepo;

	@InjectMocks
	private CustomerService customerService;

	@InjectMocks
	private ReviewService service;

	private static final String REVIEW_DESCRIPTION = "Good";
	private static final int REVIEW_RATING = 4;
	private static final String REVIEW_DESCRIPTION2 = "Excellent";
	private static final int REVIEW_RATING2 = 5;
	private static final Long APPOINTMENT_ID = 62784236L;
	private static final String CHOSENSERVICE_NAME = "Oil change";
	private static final int CHOSENSERVICE_DURATION = 30;
	private static final Date START_DATE = Date.valueOf("2021-03-14");
	private static final Date END_DATE = Date.valueOf("2021-03-14");
	private static final Time START_TIME = Time.valueOf("09:45:00");
	private static final Time END_TIME = Time.valueOf("10:15:00");

	private static final Date START_DATE2 = Date.valueOf("2021-02-14");
	private static final Date END_DATE2 = Date.valueOf("2021-02-14");
	private static final Time START_TIME2 = Time.valueOf("08:45:00");
	private static final Time END_TIME2 = Time.valueOf("09:15:00");

	private static final String CUSTOMER_USERNAME ="TestCustomer";
	private static final String CUSTOMER_PASSWORD ="TestPassword123";

	private static final String PROFILE_EMAIL ="TestCustomer@mail.com";
	private static final String PROFILE_FIRSTNAME ="Bob";
	private static final String PROFILE_LASTNAME ="Fisher";
	private static final String PROFILE_ADDRESS ="1000, MEMORY LANE";
	private static final String PROFILE_PHONE ="5141234567";
	private static final String PROFILE_ZIP ="55555";

	private static final String CAR_MODEL ="BMW X6";
	private static final String CAR_PLATE ="123 ABC";
	private static final CarTransmission CAR_TRANSMISSION = CarTransmission.Automatic;

	private static final String CUSTOMER2_USERNAME ="TestCustomer2";
	private static final String CUSTOMER2_PASSWORD ="TestPassword123";

	private static final String PROFILE2_EMAIL ="TestCustomer@mail.com";
	private static final String PROFILE2_FIRSTNAME ="Lionel";
	private static final String PROFILE2_LASTNAME ="Messi";
	private static final String PROFILE2_ADDRESS ="1000, MEMORY LANE";
	private static final String PROFILE2_PHONE ="5141234567";
	private static final String PROFILE2_ZIP ="55555";

	private static final String CAR2_MODEL ="BMW X6";
	private static final String CAR2_PLATE ="124 ABC";
	private static final CarTransmission CAR2_TRANSMISSION = CarTransmission.Automatic;


	@BeforeEach
	public void setMockOutput() {

		lenient().when(cusRepo.findCustomerByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(CUSTOMER_USERNAME)) {

				Profile profile = new Profile();
				profile.setFirstName(PROFILE_FIRSTNAME);
				profile.setLastName(PROFILE_LASTNAME);
				profile.setAddress(PROFILE_ADDRESS);
				profile.setEmail(PROFILE_EMAIL);
				profile.setPhoneNumber(PROFILE_PHONE);
				profile.setZipCode(PROFILE_ZIP);

				Car car = new Car();
				car.setModel(CAR_MODEL);
				car.setTransmission(CAR_TRANSMISSION);
				List<Car> cars = new ArrayList<Car>();


				Customer customer = new Customer();
				customer.setUsername(CUSTOMER_USERNAME);
				customer.setPassword(CUSTOMER_PASSWORD);
				customer.setProfile(profile);
				customer.setCars(cars);

				return customer;

			}else {
				return null;
			}
		});

		lenient().when(profileRepo.findByFirstNameAndLastName(anyString(), anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(PROFILE_FIRSTNAME) && invocation.getArgument(1).equals(PROFILE_LASTNAME)) {

				Profile profile = new Profile();
				profile.setFirstName(PROFILE_FIRSTNAME);
				profile.setLastName(PROFILE_LASTNAME);
				profile.setAddress(PROFILE_ADDRESS);
				profile.setEmail(PROFILE_EMAIL);
				profile.setPhoneNumber(PROFILE_PHONE);
				profile.setZipCode(PROFILE_ZIP);

				return profile;
			} else {
				return null;
			}
		});


		lenient().when(carRepo.findCarByPlateNumber(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(CAR_PLATE)) {	
				Car car = new Car();
				car.setModel(CAR_MODEL);
				car.setPlateNumber(CAR_PLATE);
				car.setTransmission(CAR_TRANSMISSION);

				return car;
			} else {
				return null;
			}

		});

		lenient().when(reviewRepo.findReviewByCustomer(any(Customer.class))).thenAnswer((InvocationOnMock invocation) -> {
			if(((Customer)invocation.getArgument(0)).getUsername().equals(CUSTOMER_USERNAME)) {

				Profile profile = new Profile();
				profile.setFirstName(PROFILE_FIRSTNAME);
				profile.setLastName(PROFILE_LASTNAME);
				profile.setAddress(PROFILE_ADDRESS);
				profile.setEmail(PROFILE_EMAIL);
				profile.setPhoneNumber(PROFILE_PHONE);
				profile.setZipCode(PROFILE_ZIP);

				Car car = new Car();
				car.setModel(CAR_MODEL);
				car.setTransmission(CAR_TRANSMISSION);
				List<Car> cars = new ArrayList<Car>();
				cars.add(car);

				Customer customer = new Customer();
				customer.setUsername(CUSTOMER_USERNAME);
				customer.setPassword(CUSTOMER_PASSWORD);
				customer.setProfile(profile);
				customer.setCars(cars);

				ChosenService service = new ChosenService();
				service.setName(CHOSENSERVICE_NAME);
				service.setDuration(CHOSENSERVICE_DURATION);

				TimeSlot timeSlot = new TimeSlot();
				timeSlot.setStartDate(START_DATE);
				timeSlot.setStartTime(START_TIME);
				timeSlot.setEndTime(END_TIME);
				timeSlot.setEndDate(END_DATE);

				Appointment appointment = new Appointment();
				appointment.setChosenService(service);
				appointment.setCustomer(customer);
				appointment.setTimeSlot(timeSlot);

				Review review = new Review();
				review.setAppointment(appointment);
				review.setChosenService(service);
				review.setCustomer(customer);
				review.setDescription(REVIEW_DESCRIPTION);
				review.setServiceRating(REVIEW_RATING);

				///////////////////////////////////////////

				Profile profile2 = new Profile();
				profile2.setFirstName(PROFILE2_FIRSTNAME);
				profile2.setLastName(PROFILE2_LASTNAME);
				profile2.setAddress(PROFILE2_ADDRESS);
				profile2.setEmail(PROFILE2_EMAIL);
				profile2.setPhoneNumber(PROFILE2_PHONE);
				profile2.setZipCode(PROFILE2_ZIP);

				Car car2 = new Car();
				car2.setModel(CAR2_MODEL);
				car2.setTransmission(CAR2_TRANSMISSION);
				List<Car> cars2 = new ArrayList<Car>();
				cars2.add(car2);

				Customer customer2 = new Customer();
				customer2.setUsername(CUSTOMER2_USERNAME);
				customer2.setPassword(CUSTOMER2_PASSWORD);
				customer2.setProfile(profile);
				customer2.setCars(cars);

				TimeSlot timeSlot2 = new TimeSlot();
				timeSlot2.setStartDate(START_DATE2);
				timeSlot2.setStartTime(START_TIME2);
				timeSlot2.setEndTime(END_TIME2);
				timeSlot2.setEndDate(END_DATE2);

				Appointment appointment2 = new Appointment();
				appointment2.setChosenService(service);
				appointment2.setCustomer(customer2);
				appointment2.setTimeSlot(timeSlot2);

				Review review2 = new Review();
				review2.setAppointment(appointment2);
				review2.setChosenService(service);
				review2.setCustomer(customer2);
				review2.setDescription(REVIEW_DESCRIPTION2);
				review2.setServiceRating(REVIEW_RATING2);

				List<Review> reviewList = new ArrayList<Review>();
				reviewList.add(review);
				reviewList.add(review2);

				//RETURN LIST
				return reviewList;

			}else {
				return null;
			}
		});

		lenient().when(reviewRepo.findReviewByAppointment(any(Appointment.class))).thenAnswer((InvocationOnMock invocation) -> {
			if(((Appointment)invocation.getArgument(0)).getId().equals(APPOINTMENT_ID)) {

				Profile profile = new Profile();
				profile.setFirstName(PROFILE_FIRSTNAME);
				profile.setLastName(PROFILE_LASTNAME);
				profile.setAddress(PROFILE_ADDRESS);
				profile.setEmail(PROFILE_EMAIL);
				profile.setPhoneNumber(PROFILE_PHONE);
				profile.setZipCode(PROFILE_ZIP);

				Car car = new Car();
				car.setModel(CAR_MODEL);
				car.setTransmission(CAR_TRANSMISSION);
				List<Car> cars = new ArrayList<Car>();

				Customer customer = new Customer();
				customer.setUsername(CUSTOMER_USERNAME);
				customer.setPassword(CUSTOMER_PASSWORD);
				customer.setProfile(profile);
				customer.setCars(cars);

				ChosenService service = new ChosenService();
				service.setName(CHOSENSERVICE_NAME);
				service.setDuration(CHOSENSERVICE_DURATION);

				TimeSlot timeSlot = new TimeSlot();
				timeSlot.setStartDate(START_DATE);
				timeSlot.setStartTime(START_TIME);
				timeSlot.setEndTime(END_TIME);
				timeSlot.setEndDate(END_DATE);

				Appointment appointment = new Appointment();
				appointment.setChosenService(service);
				appointment.setCustomer(customer);
				appointment.setTimeSlot(timeSlot);

				Review review = new Review();
				review.setAppointment(appointment);
				review.setChosenService(service);
				review.setCustomer(customer);
				review.setDescription(REVIEW_DESCRIPTION);
				review.setServiceRating(REVIEW_RATING);

				return review;

			} else {
				return null;
			}
		});


		lenient().when(reviewRepo.findReviewByChosenService(any(ChosenService.class))).thenAnswer((InvocationOnMock invocation) -> {
			if(((ChosenService)invocation.getArgument(0)).getName().equals(CHOSENSERVICE_NAME)) {

				Profile profile = new Profile();
				profile.setFirstName(PROFILE_FIRSTNAME);
				profile.setLastName(PROFILE_LASTNAME);
				profile.setAddress(PROFILE_ADDRESS);
				profile.setEmail(PROFILE_EMAIL);
				profile.setPhoneNumber(PROFILE_PHONE);
				profile.setZipCode(PROFILE_ZIP);

				Car car = new Car();
				car.setModel(CAR_MODEL);
				car.setTransmission(CAR_TRANSMISSION);
				List<Car> cars = new ArrayList<Car>();
				cars.add(car);

				Customer customer = new Customer();
				customer.setUsername(CUSTOMER_USERNAME);
				customer.setPassword(CUSTOMER_PASSWORD);
				customer.setProfile(profile);
				customer.setCars(cars);

				ChosenService service = new ChosenService();
				service.setName(CHOSENSERVICE_NAME);
				service.setDuration(CHOSENSERVICE_DURATION);

				TimeSlot timeSlot = new TimeSlot();
				timeSlot.setStartDate(START_DATE);
				timeSlot.setStartTime(START_TIME);
				timeSlot.setEndTime(END_TIME);
				timeSlot.setEndDate(END_DATE);

				Appointment appointment = new Appointment();
				appointment.setChosenService(service);
				appointment.setCustomer(customer);
				appointment.setTimeSlot(timeSlot);

				Review review = new Review();
				review.setAppointment(appointment);
				review.setChosenService(service);
				review.setCustomer(customer);
				review.setDescription(REVIEW_DESCRIPTION);
				review.setServiceRating(REVIEW_RATING);

				///////////////////////////////////////////

				Profile profile2 = new Profile();
				profile2.setFirstName(PROFILE2_FIRSTNAME);
				profile2.setLastName(PROFILE2_LASTNAME);
				profile2.setAddress(PROFILE2_ADDRESS);
				profile2.setEmail(PROFILE2_EMAIL);
				profile2.setPhoneNumber(PROFILE2_PHONE);
				profile2.setZipCode(PROFILE2_ZIP);

				Car car2 = new Car();
				car2.setModel(CAR2_MODEL);
				car2.setTransmission(CAR2_TRANSMISSION);
				List<Car> cars2 = new ArrayList<Car>();
				cars2.add(car2);

				Customer customer2 = new Customer();
				customer2.setUsername(CUSTOMER2_USERNAME);
				customer2.setPassword(CUSTOMER2_PASSWORD);
				customer2.setProfile(profile);
				customer2.setCars(cars);

				TimeSlot timeSlot2 = new TimeSlot();
				timeSlot2.setStartDate(START_DATE2);
				timeSlot2.setStartTime(START_TIME2);
				timeSlot2.setEndTime(END_TIME2);
				timeSlot2.setEndDate(END_DATE2);

				Appointment appointment2 = new Appointment();
				appointment2.setChosenService(service);
				appointment2.setCustomer(customer2);
				appointment2.setTimeSlot(timeSlot2);

				Review review2 = new Review();
				review2.setAppointment(appointment2);
				review2.setChosenService(service);
				review2.setCustomer(customer2);
				review2.setDescription(REVIEW_DESCRIPTION2);
				review2.setServiceRating(REVIEW_RATING2);

				List<Review> reviewList = new ArrayList<Review>();
				reviewList.add(review);
				reviewList.add(review2);

				//RETURN LIST
				return reviewList;

			} else {
				return null;
			}

		});

		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};

		lenient().when(reviewRepo.save(any(Review.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(cusRepo.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(profileRepo.save(any(Profile.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(carRepo.save(any(Car.class))).thenAnswer(returnParameterAsAnswer);
	}

	@Test
	public void testCreateReview() {
		assertEquals(0, service.getAllReviews().size());

		ChosenService chosenService = new ChosenService();
		chosenService.setDuration(30);
		chosenService.setName("Oil change");
		Customer customer = null;

		try {
			Car car = customerService.createCar("555-A7A", "Corolla", CarTransmission.Automatic);
			List<Car> cars = new ArrayList<Car>();
			Profile profile = customerService.createProfile("Robert", "Nafar", "1234 Cairo St.", "5555A7A", "12345678", "abc@mcgill.ca");
			customer = customerService.createCustomer("username", "Password1234", profile, cars);
		}catch (IllegalArgumentException e) {
			fail();
		}

		TimeSlot timeslot = new TimeSlot();
		timeslot.setStartDate(Date.valueOf("2021-07-07"));
		timeslot.setEndDate(Date.valueOf("2021-07-07"));
		timeslot.setStartTime(Time.valueOf("10:05:00"));
		timeslot.setEndTime(Time.valueOf("10:35:00"));

		Appointment appointment = new Appointment();
		appointment.setChosenService(chosenService);
		appointment.setCustomer(customer);
		appointment.setTimeSlot(timeslot);

		String description = "Great";
		int serviceRating = 5;
		Review review = null;

		try {
			review = service.createReview(appointment, chosenService, customer, description, serviceRating);
		}catch (IllegalArgumentException e) {
			fail();
		}

		assertNotNull(review);
		assertEquals(description, review.getDescription());
		assertEquals(appointment.getTimeSlot().getStartDate(), review.getAppointment().getTimeSlot().getStartDate());
		assertEquals(appointment.getTimeSlot().getEndDate(), review.getAppointment().getTimeSlot().getEndDate());
		assertEquals(appointment.getTimeSlot().getStartTime(), review.getAppointment().getTimeSlot().getStartTime());
		assertEquals(appointment.getTimeSlot().getEndTime(), review.getAppointment().getTimeSlot().getEndTime());
		assertEquals(chosenService.getName(), review.getChosenService().getName());
		assertEquals(chosenService.getDuration(), review.getChosenService().getDuration());
		assertEquals(customer.getUsername(), customer.getUsername());
		assertEquals(serviceRating, review.getServiceRating());

	}

	@Test
	public void testCreateReviewNullAppointment() {
		assertEquals(0, service.getAllReviews().size());

		ChosenService chosenService = new ChosenService();
		chosenService.setDuration(30);
		chosenService.setName("Oil change");
		Customer customer = null;

		try {
			Car car = customerService.createCar("555-A7A", "Corolla", CarTransmission.Automatic);
			List<Car> cars = new ArrayList<Car>();
			Profile profile = customerService.createProfile("Robert", "Nafar", "1234 Cairo St.", "5555A7A", "12345678", "abc@mcgill.ca");
			customer = customerService.createCustomer("username", "Password1234", profile, cars);
		}catch (IllegalArgumentException e) {
			fail();
		}

		TimeSlot timeslot = new TimeSlot();
		timeslot.setStartDate(Date.valueOf("2021-07-07"));
		timeslot.setEndDate(Date.valueOf("2021-07-07"));
		timeslot.setStartTime(Time.valueOf("10:05:00"));
		timeslot.setEndTime(Time.valueOf("10:35:00"));

		Appointment appointment = null;

		String description = "Great";
		int serviceRating = 5;
		Review review = null;
		String error = null;

		try {
			review = service.createReview(appointment, chosenService, customer, description, serviceRating);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(review);
		assertEquals(error, "Appointment not found");
	}

	@Test
	public void tesCreateReviewNullChosenService() {
		assertEquals(0, service.getAllReviews().size());

		ChosenService chosenService = null;

		Customer customer = null;

		try {
			Car car = customerService.createCar("555-A7A", "Corolla", CarTransmission.Automatic);
			List<Car> cars = new ArrayList<Car>();
			Profile profile = customerService.createProfile("Robert", "Nafar", "1234 Cairo St.", "5555A7A", "12345678", "abc@mcgill.ca");
			customer = customerService.createCustomer("username", "Password1234", profile, cars);
		}catch (IllegalArgumentException e) {
			fail();
		}

		TimeSlot timeslot = new TimeSlot();
		timeslot.setStartDate(Date.valueOf("2021-07-07"));
		timeslot.setEndDate(Date.valueOf("2021-07-07"));
		timeslot.setStartTime(Time.valueOf("10:05:00"));
		timeslot.setEndTime(Time.valueOf("10:35:00"));

		Appointment appointment = new Appointment();
		appointment.setChosenService(chosenService);
		appointment.setCustomer(customer);
		appointment.setTimeSlot(timeslot);

		String description = "Great";
		int serviceRating = 5;
		Review review = null;
		String error = null;

		try {
			review = service.createReview(appointment, chosenService, customer, description, serviceRating);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(review);
		assertEquals(error, "Service not found");
	}

	@Test
	public void testCreateReviewNullCustomer() {
		assertEquals(0, service.getAllReviews().size());

		ChosenService chosenService = new ChosenService();
		chosenService.setDuration(30);
		chosenService.setName("Oil change");
		Customer customer = null;

		try {
			Car car = customerService.createCar("555-A7A", "Corolla", CarTransmission.Automatic);
			List<Car> cars = new ArrayList<Car>();
			Profile profile = customerService.createProfile("Robert", "Nafar", "1234 Cairo St.", "5555A7A", "12345678", "abc@mcgill.ca");
		}catch (IllegalArgumentException e) {
			fail();
		}

		TimeSlot timeslot = new TimeSlot();
		timeslot.setStartDate(Date.valueOf("2021-07-07"));
		timeslot.setEndDate(Date.valueOf("2021-07-07"));
		timeslot.setStartTime(Time.valueOf("10:05:00"));
		timeslot.setEndTime(Time.valueOf("10:35:00"));

		Appointment appointment = new Appointment();
		appointment.setChosenService(chosenService);
		appointment.setCustomer(customer);
		appointment.setTimeSlot(timeslot);

		String description = "Great";
		int serviceRating = 5;
		Review review = null;
		String error = null;

		try {
			review = service.createReview(appointment, chosenService, customer, description, serviceRating);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(review);
		assertEquals(error, "Customer not found");
	}

	@Test
	public void createReviewInvalidServiceRating() {
		assertEquals(0, service.getAllReviews().size());

		ChosenService chosenService = new ChosenService();
		chosenService.setDuration(30);
		chosenService.setName("Oil change");
		Customer customer = null;

		try {
			Car car = customerService.createCar("555-A7A", "Corolla", CarTransmission.Automatic);
			List<Car> cars = new ArrayList<Car>();
			Profile profile = customerService.createProfile("Robert", "Nafar", "1234 Cairo St.", "5555A7A", "12345678", "abc@mcgill.ca");
			customer = customerService.createCustomer("username", "Password1234", profile, cars);
		}catch (IllegalArgumentException e) {
			fail();
		}

		TimeSlot timeslot = new TimeSlot();
		timeslot.setStartDate(Date.valueOf("2021-07-07"));
		timeslot.setEndDate(Date.valueOf("2021-07-07"));
		timeslot.setStartTime(Time.valueOf("10:05:00"));
		timeslot.setEndTime(Time.valueOf("10:35:00"));

		Appointment appointment = new Appointment();
		appointment.setChosenService(chosenService);
		appointment.setCustomer(customer);
		appointment.setTimeSlot(timeslot);

		String description = "Great";
		int serviceRating = 10;
		Review review = null;
		String error = null;

		try {
			review = service.createReview(appointment, chosenService, customer, description, serviceRating);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(review);
		assertEquals(error, "Service rating must be between 0 and 5 (inclusive)");
	}
	
	@Test
	public void testEditReview() {
		assertEquals(0, service.getAllReviews().size());

		ChosenService chosenService = new ChosenService();
		chosenService.setDuration(30);
		chosenService.setName("Oil change");
		Customer customer = null;

		try {
			Car car = customerService.createCar("555-A7A", "Corolla", CarTransmission.Automatic);
			List<Car> cars = new ArrayList<Car>();
			Profile profile = customerService.createProfile("Robert", "Nafar", "1234 Cairo St.", "5555A7A", "12345678", "abc@mcgill.ca");
			customer = customerService.createCustomer("username", "Password1234", profile, cars);
		}catch (IllegalArgumentException e) {
			fail();
		}

		TimeSlot timeslot = new TimeSlot();
		timeslot.setStartDate(Date.valueOf("2021-07-07"));
		timeslot.setEndDate(Date.valueOf("2021-07-07"));
		timeslot.setStartTime(Time.valueOf("10:05:00"));
		timeslot.setEndTime(Time.valueOf("10:35:00"));

		Appointment appointment = new Appointment();
		appointment.setChosenService(chosenService);
		appointment.setCustomer(customer);
		appointment.setTimeSlot(timeslot);

		String description = "Good";
		int serviceRating = 4;
		Review review = null;
		String error = null;

		try {
			review = service.editReview(review, "Excellent", 5);
			review = service.getReview(appointment);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNotNull(review);
		assertEquals("Excellent", review.getDescription());
		assertEquals(5, review.getServiceRating());
	}
}



//	@Test
//	public void testCreateCar() {
//		assertEquals(0, service.getAllCars().size());
//
//		String model = "Lambo";
//		String plateNumber = "Number 1";
//		CarTransmission carTransmition = CarTransmission.Automatic;
//		Car car = null;
//
//		try {
//			car = service.createCar(plateNumber, model, carTransmition);
//		}catch(IllegalArgumentException e) {
//			fail();
//		}
//
//		assertNotNull(car);
//		assertEquals(model, car.getModel());
//		assertEquals(plateNumber, car.getPlateNumber());
//		assertEquals(carTransmition, car.getTransmission());
//	}
//
//	@Test
//	public void testCreateCustomer() {
//		assertEquals(0, service.getAllCustomers().size());
//
//		String model = "Lambo";
//		String plateNumber = "Number 1";
//		CarTransmission carTransmition = CarTransmission.Automatic;
//		Car car = null;
//
//		String firstName = "Gary";
//		String lastName = "Jimmy";
//		String email = "garyjimmy@mail.com";
//		String phoneNumber = "012344567";
//		String address = "222, 5th Ave";
//		String zip = "G79 DE4";
//		Profile profile = null;
//
//		String username = "garyjimmy";
//		String password = "Password123";
//		Customer customer = null;
//
//		try {
//			car = service.createCar(plateNumber, model, carTransmition);
//			List<Car> cars = new ArrayList<Car>();
//			cars.add(car);
//			profile = service.createProfile(firstName, lastName, address, zip, phoneNumber, email);
//			customer = service.createCustomer(username, password, profile, cars);
//		}catch(IllegalArgumentException e) {
//			fail();
//		}
//
//		assertNotNull(customer);
//		assertEquals(username, customer.getUsername());
//		assertEquals(password, customer.getPassword());
//
//		assertNotNull(customer.getCars().get(0));
//		assertEquals(model, customer.getCars().get(0).getModel());
//		assertEquals(plateNumber, customer.getCars().get(0).getPlateNumber());
//		assertEquals(carTransmition, customer.getCars().get(0).getTransmission());
//
//		assertNotNull(customer.getProfile());
//		assertEquals(firstName, customer.getProfile().getFirstName());
//		assertEquals(lastName, customer.getProfile().getLastName());
//		assertEquals(address, customer.getProfile().getAddress());
//		assertEquals(email, customer.getProfile().getEmail());
//		assertEquals(phoneNumber, customer.getProfile().getPhoneNumber());
//		assertEquals(zip, customer.getProfile().getZipCode());
//
//	}
//
//	@Test
//	public void testCreateCustomerTakenUsername(){
//
//		assertEquals(0, service.getAllCustomers().size());
//
//		String model = "Lambo";
//		String plateNumber = "Number 1";
//		CarTransmission carTransmition = CarTransmission.Automatic;
//		Car car = null;
//
//		String firstName = "Gary";
//		String lastName = "Jimmy";
//		String email = "garyjimmy@mail.com";
//		String phoneNumber = "012344567";
//		String address = "222, 5th Ave";
//		String zip = "G79 DE4";
//		Profile profile = null;
//
//		String username = CUSTOMER_USERNAME;
//		String password = "Password123";
//		Customer customer = null;
//		String error = "";
//		try {
//			car = service.createCar(plateNumber, model, carTransmition);
//			List<Car> cars = new ArrayList<Car>();
//			cars.add(car);
//			profile = service.createProfile(firstName, lastName, address, zip, phoneNumber, email);
//			customer = service.createCustomer(username, password, profile, cars);
//		}catch(IllegalArgumentException e) {
//			error = e.getMessage();
//		}
//
//		assertNull(customer);
//		assertEquals(error, "Username is already taken");
//
//
//	}
