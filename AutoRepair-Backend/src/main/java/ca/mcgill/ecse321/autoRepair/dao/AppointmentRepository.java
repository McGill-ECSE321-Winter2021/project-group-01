package ca.mcgill.ecse321.autoRepair.dao;

import java.util.List;

import ca.mcgill.ecse321.autoRepair.model.Appointment;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.TimeSlot;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Long>{

	Appointment findAppointmentByTimeSlot(TimeSlot timeSlot);
	List<Appointment> findAppointmentsByCustomer(Customer customer);
}