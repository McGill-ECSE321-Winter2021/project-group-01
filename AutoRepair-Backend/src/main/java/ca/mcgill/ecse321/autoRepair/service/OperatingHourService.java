package ca.mcgill.ecse321.autoRepair.service;

import ca.mcgill.ecse321.autoRepair.dao.OperatingHourRepository;
import ca.mcgill.ecse321.autoRepair.model.OperatingHour;
import ca.mcgill.ecse321.autoRepair.model.OperatingHour.DayOfWeek;
import java.sql.Time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OperatingHourService {

	@Autowired
	OperatingHourRepository operatingHourRepository;

	@Transactional
	public OperatingHour createOperatingHour(Long id, DayOfWeek dayOfWeek, Time startTime, Time endTime) {
		OperatingHour operatingHour = new OperatingHour();
		operatingHour.setId(id);
		operatingHour.setDayOfWeek(dayOfWeek);
		operatingHour.setStartTime(startTime);
		operatingHour.setEndTime(endTime);
		operatingHourRepository.save(operatingHour);
		return operatingHour;
	}
	
	@Transactional
	public OperatingHour getOperatingHour(DayOfWeek dayOfWeek) {
		return operatingHourRepository.findByDayOfWeek(dayOfWeek);
	}
	
	@Transactional
	public Iterable<OperatingHour> getAllOperatingHour() {
		return operatingHourRepository.findAll();
	}
	

	
}