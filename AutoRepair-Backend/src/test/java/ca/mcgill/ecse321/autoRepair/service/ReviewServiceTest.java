package ca.mcgill.ecse321.autoRepair.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.autoRepair.dao.AppointmentRepository;
import ca.mcgill.ecse321.autoRepair.dao.CarRepository;
import ca.mcgill.ecse321.autoRepair.dao.ChosenServiceRepository;
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
	private ChosenServiceRepository serviceRepo;

	@Mock
	private CustomerRepository cusRepo;

	@Mock
	private ProfileRepository profileRepo;

	@Mock
	private CarRepository carRepo;
	
	@Mock
	private AppointmentRepository appRepo;
	
	@InjectMocks
	private AppointmentService appointmentService;

	@InjectMocks
	private CustomerService customerService;
	
	@InjectMocks
	private CarService carService;
	
	@InjectMocks
	private ProfileService profileService;

	@InjectMocks
	private ReviewService service;

	private static final String REVIEW_DESCRIPTION = "Good";
	private static final int REVIEW_RATING = 4;
	private static final String REVIEW_DESCRIPTION2 = "Excellent";
	private static final int REVIEW_RATING2 = 5;
	
	private static final Long APPOINTMENT_ID = 62784236L;
	private static final Long APPOINTMENT_ID2 = 48724639L;
	
	private static final String CHOSENSERVICE_NAME = "Oil change";
	private static final String CHOSENSERVICE_NAME2 = "Body repair";
	
	private static final int CHOSENSERVICE_DURATION = 30;
	
	private static final Date START_DATE = Date.valueOf("2021-07-14");
	private static final Date END_DATE = Date.valueOf("2021-07-14");
	private static final Time START_TIME = Time.valueOf("09:45:00");
	private static final Time END_TIME = Time.valueOf("10:15:00");
	private static final int DURATION = 30;

	private static final Date START_DATE2 = Date.valueOf("2021-07-21");
	private static final Date END_DATE2 = Date.valueOf("2021-07-21");
	private static final Time START_TIME2 = Time.valueOf("10:00:00");
	private static final Time END_TIME2 = Time.valueOf("10:30:00");

	private static final String CUSTOMER_USERNAME ="TestCustomer";
	private static final String CUSTOMER_PASSWORD ="TestPassword123";
	private static final String NULLCUSTOMER_USERNAME = "hamood";

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
		
		lenient().when(serviceRepo.findChosenServiceByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(CHOSENSERVICE_NAME)) {
                ChosenService chosenService = new ChosenService();
                
                    chosenService.setName(CHOSENSERVICE_NAME);
                    chosenService.setDuration(DURATION);
                    // SET PRICE
                
                return chosenService;
            } else {
                return null;
            }
        });

		lenient().when(profileRepo.findByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(PROFILE_EMAIL)) {

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
		
//		lenient().when(appRepo.findAppointmentByTimeSlot(any(TimeSlot.class))).thenAnswer((InvocationOnMock invocation) -> {
//			if(((Appointment)invocation.getArgument(0)).getTimeSlot().get.equals(CHOSENSERVICE_NAME)) {
//				
//				
//				
//			}
			
			
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
				appointment.setId(APPOINTMENT_ID);

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

		TimeSlot timeslot = new TimeSlot();
		timeslot.setStartDate(START_DATE);
		timeslot.setEndDate(END_DATE);
		timeslot.setStartTime(START_TIME);
		timeslot.setEndTime(END_TIME);

		Appointment appointment = new Appointment();
		appointment.setChosenService(serviceRepo.findChosenServiceByName(CHOSENSERVICE_NAME));
		appointment.setCustomer(cusRepo.findCustomerByUsername(CUSTOMER_USERNAME));
		appointment.setTimeSlot(timeslot);
		appointment.setId(APPOINTMENT_ID2);

		String description = "Great";
		int serviceRating = 5;
		Review review = null;

		try {
			review = service.createReview(appointment, CHOSENSERVICE_NAME, CUSTOMER_USERNAME,
					description, serviceRating);
		}catch (IllegalArgumentException e) {
			fail();
		}

		assertNotNull(review);
		assertEquals(description, review.getDescription());
		assertEquals(appointment.getTimeSlot().getStartDate(), review.getAppointment().getTimeSlot().getStartDate());
		assertEquals(appointment.getTimeSlot().getEndDate(), review.getAppointment().getTimeSlot().getEndDate());
		assertEquals(appointment.getTimeSlot().getStartTime(), review.getAppointment().getTimeSlot().getStartTime());
		assertEquals(appointment.getTimeSlot().getEndTime(), review.getAppointment().getTimeSlot().getEndTime());
		assertEquals(CHOSENSERVICE_NAME, review.getChosenService().getName());
		assertEquals(CUSTOMER_USERNAME, review.getCustomer().getUsername());
		assertEquals(serviceRating, review.getServiceRating());

	}

	@Test
	public void testCreateReviewNullAppointment() {
		assertEquals(0, service.getAllReviews().size());

		TimeSlot timeslot = new TimeSlot();
		timeslot.setStartDate(START_DATE);
		timeslot.setEndDate(END_DATE);
		timeslot.setStartTime(START_TIME);
		timeslot.setEndTime(END_TIME);

		Appointment appointment = null;

		String description = "Great";
		int serviceRating = 5;
		Review review = null;
		String error = null;

		try {
			review = service.createReview(appointment, CHOSENSERVICE_NAME, CUSTOMER_USERNAME, description, serviceRating);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(review);
		assertEquals(error, "Appointment not found");
	}

	@Test
	public void tesCreateReviewNullChosenService() {
		assertEquals(0, service.getAllReviews().size());

		TimeSlot timeslot = new TimeSlot();
		timeslot.setStartDate(START_DATE);
		timeslot.setEndDate(END_DATE);
		timeslot.setStartTime(START_TIME);
		timeslot.setEndTime(END_TIME);

		Appointment appointment = new Appointment();
		appointment.setChosenService(serviceRepo.findChosenServiceByName(null));
		appointment.setCustomer(cusRepo.findCustomerByUsername(CUSTOMER_USERNAME));
		appointment.setTimeSlot(timeslot);
		appointment.setId(APPOINTMENT_ID2);

		String description = "Great";
		int serviceRating = 5;
		Review review = null;
		String error = null;

		try {
			review = service.createReview(appointment, null, CUSTOMER_USERNAME, description, serviceRating);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(review);
		assertEquals(error, "Service not found");
	}
	
	@Test
	public void tesCreateReviewBlankChosenService() {
		assertEquals(0, service.getAllReviews().size());

		TimeSlot timeslot = new TimeSlot();
		timeslot.setStartDate(START_DATE);
		timeslot.setEndDate(END_DATE);
		timeslot.setStartTime(START_TIME);
		timeslot.setEndTime(END_TIME);;

		Appointment appointment = new Appointment();
		appointment.setChosenService(serviceRepo.findChosenServiceByName(""));
		appointment.setCustomer(cusRepo.findCustomerByUsername(CUSTOMER_USERNAME));
		appointment.setTimeSlot(timeslot);
		appointment.setId(APPOINTMENT_ID2);

		String description = "Great";
		int serviceRating = 5;
		Review review = null;
		String error = null;

		try {
			review = service.createReview(appointment, "", CUSTOMER_USERNAME, description, serviceRating);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(review);
		assertEquals(error, "Service not found");
	}

	@Test
	public void testCreateReviewNullCustomerName() {
		assertEquals(0, service.getAllReviews().size());	
		
		TimeSlot timeslot = new TimeSlot();
		timeslot.setStartDate(START_DATE);
		timeslot.setEndDate(END_DATE);
		timeslot.setStartTime(START_TIME);
		timeslot.setEndTime(END_TIME);

		Appointment appointment = new Appointment();
		appointment.setChosenService(serviceRepo.findChosenServiceByName(CHOSENSERVICE_NAME));
		appointment.setCustomer(cusRepo.findCustomerByUsername(null));
		appointment.setTimeSlot(timeslot);
		appointment.setId(APPOINTMENT_ID2);

		String description = "Great";
		int serviceRating = 5;
		Review review = null;
		String error = null;

		try {
			review = service.createReview(appointment, CHOSENSERVICE_NAME, null, description, serviceRating);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(review);
		assertEquals(error, "Customer not found");
	}
	
	@Test
	public void testCreateReviewBlankCustomerName() {
		assertEquals(0, service.getAllReviews().size());	
		
		TimeSlot timeslot = new TimeSlot();
		timeslot.setStartDate(START_DATE);
		timeslot.setEndDate(END_DATE);
		timeslot.setStartTime(START_TIME);
		timeslot.setEndTime(END_TIME);

		Appointment appointment = new Appointment();
		appointment.setChosenService(serviceRepo.findChosenServiceByName(CHOSENSERVICE_NAME));
		appointment.setCustomer(cusRepo.findCustomerByUsername(""));
		appointment.setTimeSlot(timeslot);
		appointment.setId(APPOINTMENT_ID2);

		String description = "Great";
		int serviceRating = 5;
		Review review = null;
		String error = null;

		try {
			review = service.createReview(appointment, CHOSENSERVICE_NAME, "", description, serviceRating);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(review);
		assertEquals(error, "Customer not found");
	}
	
	@Test
	public void createReviewInvalidServiceRating() {
		assertEquals(0, service.getAllReviews().size());

		TimeSlot timeslot = new TimeSlot();
		timeslot.setStartDate(START_DATE);
		timeslot.setEndDate(END_DATE);
		timeslot.setStartTime(START_TIME);
		timeslot.setEndTime(END_TIME);

		Appointment appointment = new Appointment();
		appointment.setChosenService(serviceRepo.findChosenServiceByName(CHOSENSERVICE_NAME));
		appointment.setCustomer(cusRepo.findCustomerByUsername(CUSTOMER_USERNAME));
		appointment.setTimeSlot(timeslot);
		appointment.setId(APPOINTMENT_ID2);

		String description = "Great";
		int serviceRating = 10;
		Review review = null;
		String error = null;

		try {
			review = service.createReview(appointment, CHOSENSERVICE_NAME, 
					CUSTOMER_USERNAME, description, serviceRating);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(review);
		assertEquals(error, "Service rating must be between 0 and 5 (inclusive)");
	}
	
	@Test
	public void createReviewInvalidServiceRating2() {
		assertEquals(0, service.getAllReviews().size());

		TimeSlot timeslot = new TimeSlot();
		timeslot.setStartDate(START_DATE);
		timeslot.setEndDate(END_DATE);
		timeslot.setStartTime(START_TIME);
		timeslot.setEndTime(END_TIME);

		Appointment appointment = new Appointment();
		appointment.setChosenService(serviceRepo.findChosenServiceByName(CHOSENSERVICE_NAME));
		appointment.setCustomer(cusRepo.findCustomerByUsername(CUSTOMER_USERNAME));
		appointment.setTimeSlot(timeslot);
		appointment.setId(APPOINTMENT_ID2);

		String description = "Very bad";
		int serviceRating = -26;
		Review review = null;
		String error = null;

		try {
			review = service.createReview(appointment, CHOSENSERVICE_NAME, 
					CUSTOMER_USERNAME, description, serviceRating);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(review);
		assertEquals(error, "Service rating must be between 0 and 5 (inclusive)");
	}
	
	@Test
	public void testCreateReviewNullDescription() {
		assertEquals(0, service.getAllReviews().size());

		TimeSlot timeslot = new TimeSlot();
		timeslot.setStartDate(START_DATE);
		timeslot.setEndDate(END_DATE);
		timeslot.setStartTime(START_TIME);
		timeslot.setEndTime(END_TIME);

		Appointment appointment = new Appointment();
		appointment.setChosenService(serviceRepo.findChosenServiceByName(CHOSENSERVICE_NAME));
		appointment.setCustomer(cusRepo.findCustomerByUsername(CUSTOMER_USERNAME));
		appointment.setTimeSlot(timeslot);
		appointment.setId(APPOINTMENT_ID2);

		String description = null;
		int serviceRating = 4;
		Review review = null;
		String error = null;

		try {
			review = service.createReview(appointment, CHOSENSERVICE_NAME,
					CUSTOMER_USERNAME, description, serviceRating);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(review);
		assertEquals(error, "No description");
	}
	
	@Test
	public void testCreatReviewBlankDescription(){
		assertEquals(0, service.getAllReviews().size());

		TimeSlot timeslot = new TimeSlot();
		timeslot.setStartDate(START_DATE);
		timeslot.setEndDate(END_DATE);
		timeslot.setStartTime(START_TIME);
		timeslot.setEndTime(END_TIME);

		Appointment appointment = new Appointment();
		appointment.setChosenService(serviceRepo.findChosenServiceByName(CHOSENSERVICE_NAME));
		appointment.setCustomer(cusRepo.findCustomerByUsername(CUSTOMER_USERNAME));
		appointment.setTimeSlot(timeslot);
		appointment.setId(APPOINTMENT_ID2);

		String description = "";
		int serviceRating = 4;
		Review review = null;
		String error = null;

		try {
			review = service.createReview(appointment, CHOSENSERVICE_NAME,
					CUSTOMER_USERNAME, description, serviceRating);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(review);
		assertEquals(error, "Description must contain at least 1 character");
	}
	
	@Test
	public void testCreatReviewNullCustomer(){
		assertEquals(0, service.getAllReviews().size());
		
		TimeSlot timeslot = new TimeSlot();
		timeslot.setStartDate(START_DATE);
		timeslot.setEndDate(END_DATE);
		timeslot.setStartTime(START_TIME);
		timeslot.setEndTime(END_TIME);

		Appointment appointment = new Appointment();
		appointment.setChosenService(serviceRepo.findChosenServiceByName(CHOSENSERVICE_NAME));
		appointment.setCustomer(cusRepo.findCustomerByUsername(NULLCUSTOMER_USERNAME));
		appointment.setTimeSlot(timeslot);
		appointment.setId(APPOINTMENT_ID2);

		String description = "Good";
		int serviceRating = 4;
		Review review = null;
		String error = null;

		try {
			review = service.createReview(appointment, CHOSENSERVICE_NAME,
					NULLCUSTOMER_USERNAME, description, serviceRating);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(review);
		assertEquals(error, "No customer found");
	}
	
	@Test
	public void testCreatReviewNullService(){
		assertEquals(0, service.getAllReviews().size());
		
		TimeSlot timeslot = new TimeSlot();
		timeslot.setStartDate(START_DATE);
		timeslot.setEndDate(END_DATE);
		timeslot.setStartTime(START_TIME);
		timeslot.setEndTime(END_TIME);

		Appointment appointment = new Appointment();
		appointment.setChosenService(serviceRepo.findChosenServiceByName(CHOSENSERVICE_NAME2));
		appointment.setCustomer(cusRepo.findCustomerByUsername(CUSTOMER_USERNAME));
		appointment.setTimeSlot(timeslot);
		appointment.setId(APPOINTMENT_ID2);

		String description = "Good";
		int serviceRating = 4;
		Review review = null;
		String error = null;

		try {
			review = service.createReview(appointment, CHOSENSERVICE_NAME2,
					CUSTOMER_USERNAME, description, serviceRating);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(review);
		assertEquals(error, "No service found");
	}
	
	@Test
	public void testCreatReviewNotNullReview(){
		assertEquals(0, service.getAllReviews().size());
		
		TimeSlot timeslot = new TimeSlot();
		timeslot.setStartDate(START_DATE);
		timeslot.setEndDate(END_DATE);
		timeslot.setStartTime(START_TIME);
		timeslot.setEndTime(END_TIME);

		Appointment appointment = new Appointment();
		appointment.setChosenService(serviceRepo.findChosenServiceByName(CHOSENSERVICE_NAME));
		appointment.setCustomer(cusRepo.findCustomerByUsername(CUSTOMER_USERNAME));
		appointment.setTimeSlot(timeslot);
		appointment.setId(APPOINTMENT_ID);

		String description = "Good";
		int serviceRating = 4;
		Review review = null;
		String error = null;

		try {
			review = service.createReview(appointment, CHOSENSERVICE_NAME,
					CUSTOMER_USERNAME, description, serviceRating);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(review);
		assertEquals(error, "Review already exists");
	}
	
	
	
	@Test
	public void testEditReview() {
		assertEquals(0, service.getAllReviews().size());

		TimeSlot timeslot = new TimeSlot();
		timeslot.setStartDate(START_DATE);
		timeslot.setEndDate(END_DATE);
		timeslot.setStartTime(START_TIME);
		timeslot.setEndTime(END_TIME);

		Appointment appointment = new Appointment();
		appointment.setChosenService(serviceRepo.findChosenServiceByName(CHOSENSERVICE_NAME));
		appointment.setCustomer(cusRepo.findCustomerByUsername(CUSTOMER_USERNAME));
		appointment.setTimeSlot(timeslot);
		appointment.setId(APPOINTMENT_ID);

		Review review = reviewRepo.findReviewByAppointment(appointment);

		try {
			review = service.editReview(appointment, "Awful", 0);
		}catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(review);
		assertEquals("Awful", review.getDescription());
		assertEquals(0, review.getServiceRating());
		assertEquals(appointment.getTimeSlot().getStartDate(), review.getAppointment().getTimeSlot().getStartDate());
		assertEquals(appointment.getTimeSlot().getEndDate(), review.getAppointment().getTimeSlot().getEndDate());
		assertEquals(appointment.getTimeSlot().getStartTime(), review.getAppointment().getTimeSlot().getStartTime());
		assertEquals(appointment.getTimeSlot().getEndTime(), review.getAppointment().getTimeSlot().getEndTime());
		assertEquals(CHOSENSERVICE_NAME, review.getChosenService().getName());
		assertEquals(CUSTOMER_USERNAME, review.getCustomer().getUsername());
	}
	
	@Test
	public void testEditReviewNullAppointment() {
		assertEquals(0, service.getAllReviews().size());

		TimeSlot timeslot = new TimeSlot();
		timeslot.setStartDate(Date.valueOf("2021-07-07"));
		timeslot.setEndDate(Date.valueOf("2021-07-07"));
		timeslot.setStartTime(Time.valueOf("10:05:00"));
		timeslot.setEndTime(Time.valueOf("10:35:00"));

		Appointment appointment = null;
		
		Review review = null;
		String error = null;

		try {
			review = service.editReview(appointment, "Excellent", 5);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(review);
		assertEquals(error, "Appointment not found");
	}
	
	@Test
	public void testEditReviewInvalidServiceRating() {
		assertEquals(0, service.getAllReviews().size());

		TimeSlot timeslot = new TimeSlot();
		timeslot.setStartDate(START_DATE);
		timeslot.setEndDate(END_DATE);
		timeslot.setStartTime(START_TIME);
		timeslot.setEndTime(END_TIME);
		
		Appointment appointment = new Appointment();
		appointment.setChosenService(serviceRepo.findChosenServiceByName(CHOSENSERVICE_NAME));
		appointment.setCustomer(cusRepo.findCustomerByUsername(CUSTOMER_USERNAME));
		appointment.setTimeSlot(timeslot);
		appointment.setId(APPOINTMENT_ID);

		Review review = reviewRepo.findReviewByAppointment(appointment);
		String error = null;

		try {
			review = service.editReview(appointment, "Excellent", -1);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNotNull(review);
		assertEquals(error, "Service rating must be between 0 and 5 (inclusive)");
		assertEquals(REVIEW_RATING, review.getServiceRating());
		assertEquals(REVIEW_DESCRIPTION, review.getDescription());
		assertEquals(appointment.getTimeSlot().getStartDate(), review.getAppointment().getTimeSlot().getStartDate());
		assertEquals(appointment.getTimeSlot().getEndDate(), review.getAppointment().getTimeSlot().getEndDate());
		assertEquals(appointment.getTimeSlot().getStartTime(), review.getAppointment().getTimeSlot().getStartTime());
		assertEquals(appointment.getTimeSlot().getEndTime(), review.getAppointment().getTimeSlot().getEndTime());
		assertEquals(CHOSENSERVICE_NAME, review.getChosenService().getName());
		assertEquals(CUSTOMER_USERNAME, review.getCustomer().getUsername());
	}
	
	@Test
	public void testEditReviewInvalidServiceRating2() {
		assertEquals(0, service.getAllReviews().size());

		TimeSlot timeslot = new TimeSlot();
		timeslot.setStartDate(START_DATE);
		timeslot.setEndDate(END_DATE);
		timeslot.setStartTime(START_TIME);
		timeslot.setEndTime(END_TIME);
		
		Appointment appointment = new Appointment();
		appointment.setChosenService(serviceRepo.findChosenServiceByName(CHOSENSERVICE_NAME));
		appointment.setCustomer(cusRepo.findCustomerByUsername(CUSTOMER_USERNAME));
		appointment.setTimeSlot(timeslot);
		appointment.setId(APPOINTMENT_ID);

		Review review = reviewRepo.findReviewByAppointment(appointment);
		String error = null;

		try {
			review = service.editReview(appointment, "Excellent", 25);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNotNull(review);
		assertEquals(error, "Service rating must be between 0 and 5 (inclusive)");
		assertEquals(REVIEW_RATING, review.getServiceRating());
		assertEquals(REVIEW_DESCRIPTION, review.getDescription());
		assertEquals(appointment.getTimeSlot().getStartDate(), review.getAppointment().getTimeSlot().getStartDate());
		assertEquals(appointment.getTimeSlot().getEndDate(), review.getAppointment().getTimeSlot().getEndDate());
		assertEquals(appointment.getTimeSlot().getStartTime(), review.getAppointment().getTimeSlot().getStartTime());
		assertEquals(appointment.getTimeSlot().getEndTime(), review.getAppointment().getTimeSlot().getEndTime());
		assertEquals(CHOSENSERVICE_NAME, review.getChosenService().getName());
		assertEquals(CUSTOMER_USERNAME, review.getCustomer().getUsername());
	}
	
	@Test
	public void testEditReviewNullNewDescription() {
		assertEquals(0, service.getAllReviews().size());

		TimeSlot timeslot = new TimeSlot();
		timeslot.setStartDate(START_DATE);
		timeslot.setEndDate(END_DATE);
		timeslot.setStartTime(START_TIME);
		timeslot.setEndTime(END_TIME);
		
		Appointment appointment = new Appointment();
		appointment.setChosenService(serviceRepo.findChosenServiceByName(CHOSENSERVICE_NAME));
		appointment.setCustomer(cusRepo.findCustomerByUsername(CUSTOMER_USERNAME));
		appointment.setTimeSlot(timeslot);
		appointment.setId(APPOINTMENT_ID);

		Review review = reviewRepo.findReviewByAppointment(appointment);
		String error = null;

		try {
			review = service.editReview(appointment, null, 4);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNotNull(review);
		assertEquals(error, "No new description");
		assertEquals(REVIEW_DESCRIPTION, review.getDescription());
		assertEquals(REVIEW_RATING, review.getServiceRating());
		assertEquals(appointment.getTimeSlot().getStartDate(), review.getAppointment().getTimeSlot().getStartDate());
		assertEquals(appointment.getTimeSlot().getEndDate(), review.getAppointment().getTimeSlot().getEndDate());
		assertEquals(appointment.getTimeSlot().getStartTime(), review.getAppointment().getTimeSlot().getStartTime());
		assertEquals(appointment.getTimeSlot().getEndTime(), review.getAppointment().getTimeSlot().getEndTime());
		assertEquals(CHOSENSERVICE_NAME, review.getChosenService().getName());
		assertEquals(CUSTOMER_USERNAME, review.getCustomer().getUsername());
	}
	
	@Test
	public void testEditReviewBlankNewDescription() {
		assertEquals(0, service.getAllReviews().size());

		TimeSlot timeslot = new TimeSlot();
		timeslot.setStartDate(START_DATE);
		timeslot.setEndDate(END_DATE);
		timeslot.setStartTime(START_TIME);
		timeslot.setEndTime(END_TIME);
		
		Appointment appointment = new Appointment();
		appointment.setChosenService(serviceRepo.findChosenServiceByName(CHOSENSERVICE_NAME));
		appointment.setCustomer(cusRepo.findCustomerByUsername(CUSTOMER_USERNAME));
		appointment.setTimeSlot(timeslot);
		appointment.setId(APPOINTMENT_ID);

		Review review = reviewRepo.findReviewByAppointment(appointment);
		String error = null;

		try {
			review = service.editReview(appointment, "", 4);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNotNull(review);
		assertEquals(error, "New description must contain at least 1 character");
		assertEquals(REVIEW_DESCRIPTION, review.getDescription());
		assertEquals(REVIEW_RATING, review.getServiceRating());
		assertEquals(appointment.getTimeSlot().getStartDate(), review.getAppointment().getTimeSlot().getStartDate());
		assertEquals(appointment.getTimeSlot().getEndDate(), review.getAppointment().getTimeSlot().getEndDate());
		assertEquals(appointment.getTimeSlot().getStartTime(), review.getAppointment().getTimeSlot().getStartTime());
		assertEquals(appointment.getTimeSlot().getEndTime(), review.getAppointment().getTimeSlot().getEndTime());
		assertEquals(CHOSENSERVICE_NAME, review.getChosenService().getName());
		assertEquals(CUSTOMER_USERNAME, review.getCustomer().getUsername());
	}
	
	@Test
	public void deleteReview() {
		assertEquals(0, service.getAllReviews().size());

		TimeSlot timeslot = new TimeSlot();
		timeslot.setStartDate(START_DATE);
		timeslot.setEndDate(END_DATE);
		timeslot.setStartTime(START_TIME);
		timeslot.setEndTime(END_TIME);
		
		Appointment appointment = new Appointment();
		appointment.setChosenService(serviceRepo.findChosenServiceByName(CHOSENSERVICE_NAME));
		appointment.setCustomer(cusRepo.findCustomerByUsername(CUSTOMER_USERNAME));
		appointment.setTimeSlot(timeslot);
		appointment.setId(APPOINTMENT_ID);

		
		boolean isDeleted = false;

		try {
			isDeleted = service.deleteReview(appointment);
		}catch (IllegalArgumentException e) {
			fail();
		}
		assertTrue(isDeleted);
	}
	
	@Test
	public void testDeletReviewNullAppointment() {
		assertEquals(0, service.getAllReviews().size());

		TimeSlot timeslot = new TimeSlot();
		timeslot.setStartDate(START_DATE);
		timeslot.setEndDate(END_DATE);
		timeslot.setStartTime(START_TIME);
		timeslot.setEndTime(END_TIME);
		
		Appointment appointment = new Appointment();
		appointment.setChosenService(serviceRepo.findChosenServiceByName(CHOSENSERVICE_NAME));
		appointment.setCustomer(cusRepo.findCustomerByUsername(CUSTOMER_USERNAME));
		appointment.setTimeSlot(timeslot);
		appointment.setId(APPOINTMENT_ID);

		String error = null;
		boolean isDeleted = false;

		try {
			isDeleted = service.deleteReview(null);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertFalse(isDeleted);
		assertEquals(error, "Review not deleted. Appointment not found");
	}

	@Test
	public void tesgetAverageServiceReview() {
		assertEquals(0, service.getAllReviews().size());

		double averageServiceRating = 0.0;

		try {
			averageServiceRating = service.getAverageServiceReview(CHOSENSERVICE_NAME);
		}catch (IllegalArgumentException e) {
			fail();
		}
		assertEquals(4.5, averageServiceRating);
	}
	
	@Test
	public void tesgetAverageServiceReviewNullService() {
		assertEquals(0, service.getAllReviews().size());

		String error = null;

		try {
			service.getAverageServiceReview("Tire change");
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals(error, "Service not found");
	}
	
}