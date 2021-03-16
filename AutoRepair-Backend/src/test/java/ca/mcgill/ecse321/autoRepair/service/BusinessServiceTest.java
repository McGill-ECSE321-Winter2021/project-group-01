package ca.mcgill.ecse321.autoRepair.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.autoRepair.dao.BusinessRepository;
import ca.mcgill.ecse321.autoRepair.dao.CustomerRepository;
import ca.mcgill.ecse321.autoRepair.dao.OperatingHourRepository;
import ca.mcgill.ecse321.autoRepair.dao.ProfileRepository;
import ca.mcgill.ecse321.autoRepair.model.Business;
import ca.mcgill.ecse321.autoRepair.model.Car;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.OperatingHour;
import ca.mcgill.ecse321.autoRepair.model.Profile;
import ca.mcgill.ecse321.autoRepair.model.TimeSlot;
import ca.mcgill.ecse321.autoRepair.model.OperatingHour.DayOfWeek;


@ExtendWith(MockitoExtension.class)
public class BusinessServiceTest {
	
	
	@Mock
	private BusinessRepository businessRepository;

	@Mock
	private OperatingHourRepository operatingHourRepository;
	
	@InjectMocks
	private BusinessService service;
	
	private static final String BUSINESS_NAME ="TestBusinessName";
	private static final String BUSINESS_EMAIL ="TestBusinessEmail";
	private static final String BUSINESS_ADDRESS ="TestBusinessAddress";
	private static final String BUSINESS_PHONENUMBER ="TestBusinessPhoneNumber";
	private static final List<OperatingHour> BUSINESS_HOURS = new ArrayList<OperatingHour>();
	private static final List<TimeSlot> BUSINESS_HOLIDAYS = new ArrayList<TimeSlot>();
	
	
	private static final DayOfWeek OPERATINGHOUR_DAYOFWEEK = DayOfWeek.Monday;
	private static final Time OPERATINGHOUR_STARTTIME = Time.valueOf("08:00:00");
	private static final Time OPERATINGHOUR_ENDTIME = Time.valueOf("15:00:00");

	@BeforeEach
	public void setMockOutput() {
		lenient().when(businessRepository.findBusinessByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {

			if(invocation.getArgument(0).equals(BUSINESS_NAME)) {
				Business business = new Business();
				business.setName(BUSINESS_NAME);
				business.setEmail(BUSINESS_EMAIL);
				business.setAddress(BUSINESS_ADDRESS);
				business.setPhoneNumber(BUSINESS_PHONENUMBER);
				business.setBusinessHours(BUSINESS_HOURS);
				business.setHolidays(BUSINESS_HOLIDAYS);
				return business;

			}else {
				return null;
			}
		});
	
		lenient().when(operatingHourRepository.findByDayOfWeek(any(DayOfWeek.class))).thenAnswer((InvocationOnMock invocation) -> {

			if(invocation.getArgument(0).equals(OPERATINGHOUR_DAYOFWEEK)) {
				OperatingHour operatingHour = new OperatingHour();
				operatingHour.setDayOfWeek(OPERATINGHOUR_DAYOFWEEK);
				operatingHour.setStartTime(OPERATINGHOUR_STARTTIME);
				operatingHour.setEndTime(OPERATINGHOUR_ENDTIME);				
				return operatingHour;

			}else {
				return null;
			}
		});

		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(businessRepository.save(any(Business.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(operatingHourRepository.save(any(OperatingHour.class))).thenAnswer(returnParameterAsAnswer);

	}
	
	@Test
	public void testCreateBusinessSuccessful() {

		String name = "Gary";
		String email = "garyjimmy@mail.com";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		OperatingHour operatingHour1 = new OperatingHour();
		operatingHour1.setDayOfWeek(DayOfWeek.Monday);
		operatingHour1.setStartTime(Time.valueOf("08:00:00"));
		operatingHour1.setEndTime(Time.valueOf("15:00:00"));
		OperatingHour operatingHour2 = new OperatingHour();
		operatingHour2.setDayOfWeek(DayOfWeek.Tuesday);
		operatingHour2.setStartTime(Time.valueOf("08:00:00"));
		operatingHour2.setEndTime(Time.valueOf("15:00:00"));
		List<OperatingHour> operatingHours = new ArrayList<OperatingHour>();
		operatingHours.add(operatingHour1);
		operatingHours.add(operatingHour2);
		TimeSlot timeSlot1 = new TimeSlot();
		timeSlot1.setStartTime(Time.valueOf("08:00:00"));
		timeSlot1.setEndTime(Time.valueOf("15:00:00"));
		timeSlot1.setStartDate(Date.valueOf("2021-01-21"));
		timeSlot1.setEndDate(Date.valueOf("2021-01-21"));
		TimeSlot timeSlot2 = new TimeSlot();
		timeSlot2.setStartTime(Time.valueOf("09:00:00"));
		timeSlot2.setEndTime(Time.valueOf("16:00:00"));
		timeSlot2.setStartDate(Date.valueOf("2021-02-21"));
		timeSlot2.setEndDate(Date.valueOf("2021-02-21"));
		List<TimeSlot> timeSlots = new ArrayList<TimeSlot>();
		timeSlots.add(timeSlot1);
		timeSlots.add(timeSlot2);
		Business business = null;
		try {
			business = service.createBusiness(name, email, address, phoneNumber, operatingHours, timeSlots);
		}catch (IllegalArgumentException e) {
			fail();
		}
		
		assertNotNull(business);
		assertEquals(name, business.getName());
		assertEquals(address, business.getAddress());
		assertEquals(email, business.getEmail());
		assertEquals(phoneNumber, business.getPhoneNumber());
		assertEquals(operatingHours, business.getBusinessHours());
		assertEquals(timeSlots, business.getHolidays());
		
	}
	
	@Test
	public void testCreateBusinessNullName() {

		String name = "";
		String email = "garyjimmy@mail.com";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String error = "";
		Business business = null;
		try {
			business = service.createBusiness(name, email, address, phoneNumber, null, null);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "Name cannot be blank");
		
	}
	
	@Test
	public void testCreateBusinessNullEmail() {

		String name = "Gary";
		String email = "";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String error = "";
		Business business = null;
		try {
			business = service.createBusiness(name, email, address, phoneNumber, null, null);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "Email cannot be blank");
		
	}
	
	@Test
	public void testCreateBusinessNullAddress() {

		String name = "Gary";
		String email = "garyjimmy@mail.com";
		String phoneNumber = "012344567";
		String address = "";
		String error = "";
		Business business = null;
		try {
			business = service.createBusiness(name, email, address, phoneNumber, null, null);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "Address cannot be blank");
		
	}
	
	@Test
	public void testCreateBusinessNullPhoneNumber() {

		String name = "Gary";
		String email = "garyjimmy@mail.com";
		String phoneNumber = "";
		String address = "222, 5th Ave";
		String error = "";
		Business business = null;
		try {
			business = service.createBusiness(name, email, address, phoneNumber, null, null);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "Phone number cannot be blank");
		
	}
	
	@Test
	public void testCreateBusinessInvalidEmail() {

		String name = "Gary";
		String email = "garyjimmymail.com";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String error = "";
		Business business = null;
		try {
			business = service.createBusiness(name, email, address, phoneNumber, null, null);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "Invalid email");
		
	}
	
	@Test
	public void testEditBusinessSuccessfull() {
		String name = "Gary";
		String email = "garyjimmy@mail.com";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		Business business = null;
		String name1 = "John";
		try {
			business = service.createBusiness(name, email, address, phoneNumber, null, null);
			business = service.editBusiness(name1, email, address, phoneNumber, null, null);
		}catch (IllegalArgumentException e) {
			fail();
		}
		
		assertNotNull(business);
		assertEquals(name1, business.getName());
		assertEquals(address, business.getAddress());
		assertEquals(email, business.getEmail());
		assertEquals(phoneNumber, business.getPhoneNumber());
		assertNull(business.getBusinessHours());
		assertNull(business.getHolidays());
	}
	
	@Test
	public void testEditBusinessNullName() {

		String name = "";
		String email = "garyjimmy@mail.com";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String error = "";
		Business business = null;
		try {
			business = service.editBusiness(name, email, address, phoneNumber, null, null);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "Name cannot be blank");
		
	}
	
	@Test
	public void testEditBusinessNullEmail() {

		String name = "Gary";
		String email = "";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String error = "";
		Business business = null;
		try {
			business = service.editBusiness(name, email, address, phoneNumber, null, null);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "Email cannot be blank");
		
	}
	
	@Test
	public void testEditBusinessNullAddress() {

		String name = "Gary";
		String email = "garyjimmy@mail.com";
		String phoneNumber = "012344567";
		String address = "";
		String error = "";
		Business business = null;
		try {
			business = service.editBusiness(name, email, address, phoneNumber, null, null);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "Address cannot be blank");
		
	}
	
	@Test
	public void testEditBusinessNullPhoneNumber() {

		String name = "Gary";
		String email = "garyjimmy@mail.com";
		String phoneNumber = "";
		String address = "222, 5th Ave";
		String error = "";
		Business business = null;
		try {
			business = service.editBusiness(name, email, address, phoneNumber, null, null);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "Phone number cannot be blank");
		
	}
	
	@Test
	public void testEditBusinessInvalidEmail() {

		String name = "Gary";
		String email = "garyjimmymail.com";
		String phoneNumber = "012344567";
		String address = "222, 5th Ave";
		String error = "";
		Business business = null;
		try {
			business = service.editBusiness(name, email, address, phoneNumber, null, null);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "Invalid email");
		
	}
	
	
	@Test
	public void testCreateOperatingHourSuccessfull() {

		assertEquals(0, service.getAllOperatingHour().size());
		
		DayOfWeek dayOfWeek = DayOfWeek.Monday;
		Time startTime = Time.valueOf("10:00:00");
		Time endTime = Time.valueOf("18:00:00");

		OperatingHour operatingHour = null;
		try {
			operatingHour = service.createOperatingHour(dayOfWeek, startTime, endTime);
		}catch (IllegalArgumentException e) {
			fail();
		}
		
//		assertEquals(1, service.getAllOperatingHour().size());
		assertNotNull(operatingHour);
		assertEquals(dayOfWeek, operatingHour.getDayOfWeek());
		assertEquals(startTime, operatingHour.getStartTime());
		assertEquals(endTime, operatingHour.getEndTime());
		
	}
	
	@Test
	public void testCreateOperatingHourNullDayOfWeek() {

		assertEquals(0, service.getAllOperatingHour().size());
		
		DayOfWeek dayOfWeek = null;
		Time startTime = Time.valueOf("10:00:00");
		Time endTime = Time.valueOf("18:00:00");
		String error = "";

		OperatingHour operatingHour = null;
		try {
			operatingHour = service.createOperatingHour(dayOfWeek, startTime, endTime);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "Day of week cannot be blank");
		
	}
	
	@Test
	public void testCreateOperatingHourNullStartTime() {

		assertEquals(0, service.getAllOperatingHour().size());
		
		DayOfWeek dayOfWeek = DayOfWeek.Monday;
		Time startTime = null;
		Time endTime = Time.valueOf("18:00:00");
		String error = "";

		OperatingHour operatingHour = null;
		try {
			operatingHour = service.createOperatingHour(dayOfWeek, startTime, endTime);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "Start time cannot be blank");
		
	}
	
	@Test
	public void testCreateOperatingHourNullEndTime() {

		assertEquals(0, service.getAllOperatingHour().size());
		
		DayOfWeek dayOfWeek = DayOfWeek.Monday;
		Time startTime = Time.valueOf("10:00:00");
		Time endTime = null;
		String error = "";

		OperatingHour operatingHour = null;
		try {
			operatingHour = service.createOperatingHour(dayOfWeek, startTime, endTime);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "End time cannot be blank");
		
	}
	
	@Test
	public void testCreateOperatingHourInvalidTime() {

		assertEquals(0, service.getAllOperatingHour().size());
		
		DayOfWeek dayOfWeek = DayOfWeek.Monday;
		Time startTime = Time.valueOf("12:00:00");
		Time endTime = Time.valueOf("11:00:00");
		String error = "";

		OperatingHour operatingHour = null;
		try {
			operatingHour = service.createOperatingHour(dayOfWeek, startTime, endTime);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "Start time cannot be before end time");
		
	}
	
	@Test
	public void testEditOperatingHourSuccessfull() {

		assertEquals(0, service.getAllOperatingHour().size());
		
		DayOfWeek dayOfWeek = DayOfWeek.Monday;
		Time startTime = Time.valueOf("10:00:00");
		Time endTime = Time.valueOf("18:00:00");
		DayOfWeek dayOfWeek1 = DayOfWeek.Tuesday;
		Time startTime1 = Time.valueOf("11:00:00");
		Time endTime1 = Time.valueOf("19:00:00");
		OperatingHour operatingHour = null;
		try {
			operatingHour = service.createOperatingHour(dayOfWeek, startTime, endTime);
			operatingHour = service.editOperatingHour(dayOfWeek, startTime, endTime, dayOfWeek1, startTime1, endTime1);
		}catch (IllegalArgumentException e) {
			fail();
		}
		
		assertNotNull(operatingHour);
		assertEquals(dayOfWeek1, operatingHour.getDayOfWeek());
		assertEquals(startTime1, operatingHour.getStartTime());
		assertEquals(endTime1, operatingHour.getEndTime());
		
	}
	
	@Test
	public void testEditOperatingHourNullDayOfWeek() {

		assertEquals(0, service.getAllOperatingHour().size());
		
		DayOfWeek dayOfWeek = DayOfWeek.Monday;
		Time startTime = Time.valueOf("10:00:00");
		Time endTime = Time.valueOf("18:00:00");
		DayOfWeek dayOfWeek1 = null;
		Time startTime1 = Time.valueOf("11:00:00");
		Time endTime1 = Time.valueOf("19:00:00");
		String error = "";

		OperatingHour operatingHour = null;
		try {
			operatingHour = service.editOperatingHour(dayOfWeek, startTime, endTime, dayOfWeek1, startTime1, endTime1);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "Day of week cannot be blank");
		
	}
	
	@Test
	public void testEditOperatingHourNullStartTime() {

		assertEquals(0, service.getAllOperatingHour().size());
		
		DayOfWeek dayOfWeek = DayOfWeek.Monday;
		Time startTime = Time.valueOf("10:00:00");
		Time endTime = Time.valueOf("18:00:00");
		DayOfWeek dayOfWeek1 = DayOfWeek.Tuesday;
		Time startTime1 = null;
		Time endTime1 = Time.valueOf("19:00:00");
		String error = "";

		OperatingHour operatingHour = null;
		try {
			operatingHour = service.editOperatingHour(dayOfWeek, startTime, endTime, dayOfWeek1, startTime1, endTime1);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "Start time cannot be blank");
		
	}
	
	@Test
	public void testEditOperatingHourNullEndTime() {

		assertEquals(0, service.getAllOperatingHour().size());
		
		DayOfWeek dayOfWeek = DayOfWeek.Monday;
		Time startTime = Time.valueOf("10:00:00");
		Time endTime = Time.valueOf("18:00:00");
		DayOfWeek dayOfWeek1 = DayOfWeek.Tuesday;
		Time startTime1 = Time.valueOf("11:00:00");
		Time endTime1 = null;
		String error = "";

		OperatingHour operatingHour = null;
		try {
			operatingHour = service.editOperatingHour(dayOfWeek, startTime, endTime, dayOfWeek1, startTime1, endTime1);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "End time cannot be blank");
		
	}
	
	@Test
	public void testEditOperatingHourNotFound() {

		assertEquals(0, service.getAllOperatingHour().size());
		
		DayOfWeek dayOfWeek = DayOfWeek.Monday;
		Time startTime = Time.valueOf("10:00:00");
		Time endTime = Time.valueOf("18:00:00");
		DayOfWeek dayOfWeek1 = DayOfWeek.Tuesday;
		Time startTime1 = Time.valueOf("11:00:00");
		Time endTime1 = Time.valueOf("15:00:00");
		String error = "";

		OperatingHour operatingHour = null;
		try {
			operatingHour = service.editOperatingHour(dayOfWeek1, startTime, endTime, dayOfWeek1, startTime1, endTime1);
		}catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "Operating hour cannot be found");
		
	}
	
	@Test
	public void testDeleteOperatingHourSuccessfull() {

		assertEquals(0, service.getAllOperatingHour().size());
		
		DayOfWeek dayOfWeek = DayOfWeek.Monday;
		Time startTime = Time.valueOf("10:00:00");
		Time endTime = Time.valueOf("18:00:00");

		OperatingHour operatingHour = null;
		try {
			operatingHour = service.createOperatingHour(dayOfWeek, startTime, endTime);
			assertNotNull(operatingHour);
			operatingHour = service.deleteOperatingHour(dayOfWeek, startTime, endTime);
		}catch (IllegalArgumentException e) {
			fail();
		}
		
		assertEquals(new ArrayList<OperatingHour>(), service.getAllOperatingHour());
		assertNull(operatingHour);
		
	}
	

	
	
	
		

}
