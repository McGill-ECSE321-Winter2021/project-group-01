package ca.mcgill.ecse321.autoRepair.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.autoRepair.model.BookableService;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.TimeSlot;


public interface AppointmentRepository extends CrudRepository<Appointment1, String>{
	
	Appointment1 findAppointmentById(String id);
	Appointment1 findAppointmentByTimeSlot(TimeSlot slot);
	Appointment1 findAppointmentByCustomerAndBookableService(Customer customer, BookableService service);
	List<Appointment1> findByCustomer(Customer customer);
	boolean existsByCustomerAndBookableService(Customer customer, BookableService service);
}