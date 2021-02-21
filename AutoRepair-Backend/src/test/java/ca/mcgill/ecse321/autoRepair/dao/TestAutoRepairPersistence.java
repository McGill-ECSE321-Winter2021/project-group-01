package ca.mcgill.ecse321.autoRepair.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.ComboItem;
import ca.mcgill.ecse321.autoRepair.model.OperatingHour;

@ExtendWith(SpringExtension.class)
@SpringBootTest

public class TestAutoRepairPersistence {

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private OperatingHourRepository operatingHourRepository;
	@Autowired
	private ComboItemRepository comboItemRepository;

	@AfterEach
	public void clearDatabase() {
		// Fisrt, we clear registrations to avoid exceptions due to inconsistencies
		customerRepository.deleteAll();
		// Then we can clear the other tables
		operatingHourRepository.deleteAll();
		comboItemRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadCustomer() {
		String name = "TestingCustomer"; 
		// First example for object save/load
		Customer customer = new Customer(null, null, 0, 0, null, null);
		// First example for attribute save/load
		customer.setUsername(name);
		customerRepository.save(customer);

		customer = null;

		customer = customerRepository.findCustomerByName(name);
		assertNotNull(customer);
		assertEquals(name, customer.getUsername());
	}

//	@Test
//	public void testPersistAndLoadEvent() {
//		String name = "ECSE321 Tutorial";
//		Date date = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
//		Time startTime = java.sql.Time.valueOf(LocalTime.of(11, 35));
//		Time endTime = java.sql.Time.valueOf(LocalTime.of(13, 25));
//		Event event = new Event();
//		event.setName(name);
//		event.setDate(date);
//		event.setStartTime(startTime);
//		event.setEndTime(endTime);
//		eventRepository.save(event);
//
//		event = null;
//
//		event = eventRepository.findEventByName(name);
//
//		assertNotNull(event);
//		assertEquals(name, event.getName());
//		assertEquals(date, event.getDate());
//		assertEquals(startTime, event.getStartTime());
//		assertEquals(endTime, event.getEndTime());
//	}
//
//	@Test
//	public void testPersistAndLoadRegistration() {
//		String personName = "TestPerson";
//		Person person = new Person();
//		person.setName(personName);
//		personRepository.save(person);
//
//		String eventName = "ECSE321 Tutorial";
//		Date date = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
//		Time startTime = java.sql.Time.valueOf(LocalTime.of(11, 35));
//		Time endTime = java.sql.Time.valueOf(LocalTime.of(13, 25));
//		Event event = new Event();
//		event.setName(eventName);
//		event.setDate(date);
//		event.setStartTime(startTime);
//		event.setEndTime(endTime);
//		eventRepository.save(event);
//
//		Registration reg = new Registration();
//		int regId = 1;
//		// First example for reference save/load
//		reg.setId(regId);
//		reg.setPerson(person);
//		reg.setEvent(event);
//		registrationRepository.save(reg);
//
//		reg = null;
//
//		reg = registrationRepository.findByPersonAndEvent(person, event);
//		assertNotNull(reg);
//		assertEquals(regId, reg.getId());
//		// Comparing by keys
//		assertEquals(person.getName(), reg.getPerson().getName());
//		assertEquals(event.getName(), reg.getEvent().getName());
//	}

}