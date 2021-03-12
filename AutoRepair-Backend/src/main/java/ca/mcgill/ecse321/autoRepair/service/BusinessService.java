package ca.mcgill.ecse321.autoRepair.service;

import ca.mcgill.ecse321.autoRepair.dao.BusinessRepository;
import ca.mcgill.ecse321.autoRepair.dao.OperatingHourRepository;
import ca.mcgill.ecse321.autoRepair.model.Business;
import ca.mcgill.ecse321.autoRepair.model.OperatingHour;
import ca.mcgill.ecse321.autoRepair.model.TimeSlot;
import ca.mcgill.ecse321.autoRepair.model.OperatingHour.DayOfWeek;

import java.sql.Time;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BusinessService {

	@Autowired
	BusinessRepository businessRepository;
	@Autowired
	OperatingHourRepository operatingHourRepository;

	@Transactional
	public Business createBusiness(String name, String email, String address, String phoneNumber, List<OperatingHour> businessHours, List<TimeSlot> holidays) {
		Business business = new Business();
		business.setName(name);
		business.setEmail(email);
		business.setAddress(address);
		business.setPhoneNumber(phoneNumber);
		business.setBusinessHours(businessHours);
		business.setHolidays(holidays);
		businessRepository.save(business);
		return business;
	}

	@Transactional
	public Business getBusiness(String name) {
		return businessRepository.findBusinessByName(name);
	}

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