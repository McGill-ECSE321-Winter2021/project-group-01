package ca.mcgill.ecse321.autoRepair.dao;

import java.util.List;

import ca.mcgill.ecse321.autoRepair.model.Appointment;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.autoRepair.model.BookableService;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.TimeSlot;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Long>{
	
//	Appointment findAppointmentById(String id);
	Appointment findAppointmentByTimeSlot(TimeSlot slot);
//	Appointment findAppointmentByCustomerAndBookableService(Customer customer, BookableService service);
//	List<Appointment> findByCustomer(Customer customer);
	List<Appointment> findAppointmentByCustomer(Customer customer);
	boolean existsByCustomerAndBookableService(Customer customer, BookableService service);
}