package ca.mcgill.ecse321.autoRepair.dao;

import java.util.List;

import ca.mcgill.ecse321.autoRepair.model.Appointment;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Long>{

	Appointment findAppointmentByStartDateAndStartTime(String startDate, String startTime);
	List<Appointment> findAppointmentsByCustomer(String username);
}