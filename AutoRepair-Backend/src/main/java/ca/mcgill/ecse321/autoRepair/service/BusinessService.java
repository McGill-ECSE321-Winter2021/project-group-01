package ca.mcgill.ecse321.autoRepair.service;

import ca.mcgill.ecse321.autoRepair.dao.BusinessRepository;
import ca.mcgill.ecse321.autoRepair.dao.OperatingHourRepository;
import ca.mcgill.ecse321.autoRepair.model.Business;
import ca.mcgill.ecse321.autoRepair.model.OperatingHour;
import ca.mcgill.ecse321.autoRepair.model.TimeSlot;
import ca.mcgill.ecse321.autoRepair.model.OperatingHour.DayOfWeek;

import java.sql.Time;
import java.util.ArrayList;
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
		if(name==null || name=="") throw new IllegalArgumentException("Name cannot be blank");
		if(email==null || email=="") throw new IllegalArgumentException("Email cannot be blank");
		if(address==null || address=="") throw new IllegalArgumentException("Address cannot be blank");
		if(phoneNumber==null || phoneNumber=="") throw new IllegalArgumentException("Phone number cannot be blank");
		if ((email.indexOf('@') == -1) || (email.indexOf('.') == -1) || (email.indexOf('.') < email.indexOf('@')) || (email.indexOf('@') == email.length()-1) || (email.indexOf('.') == email.length()-1)){
			throw new IllegalArgumentException("Invalid email");
		}	
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
	public Business editBusiness(String name, String email, String address, String phoneNumber, List<OperatingHour> businessHours, List<TimeSlot> holidays) {
		if(name==null || name=="") throw new IllegalArgumentException("Name cannot be blank");
		if(email==null || email=="") throw new IllegalArgumentException("Email cannot be blank");
		if(address==null || address=="") throw new IllegalArgumentException("Address cannot be blank");
		if(phoneNumber==null || phoneNumber=="") throw new IllegalArgumentException("Phone number cannot be blank");
		if ((email.indexOf('@') == -1) || (email.indexOf('.') == -1) || (email.indexOf('.') < email.indexOf('@')) || (email.indexOf('@') == email.length()-1) || (email.indexOf('.') == email.length()-1)){
			throw new IllegalArgumentException("Invalid email");
		}	
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
	public OperatingHour createOperatingHour(DayOfWeek dayOfWeek, Time startTime, Time endTime) {
		if(dayOfWeek==null || dayOfWeek.equals("")) throw new IllegalArgumentException("Day of week cannot be blank");
		if(startTime==null || startTime.equals("")) throw new IllegalArgumentException("Start time cannot be blank");
		if(endTime==null || endTime.equals("")) throw new IllegalArgumentException("End time cannot be blank");
		if(startTime.after(endTime)) throw new IllegalArgumentException("Start time cannot be before end time");
		OperatingHour operatingHour = new OperatingHour();
		operatingHour.setDayOfWeek(dayOfWeek);
		operatingHour.setStartTime(startTime);
		operatingHour.setEndTime(endTime);
		operatingHourRepository.save(operatingHour);
		return operatingHour;
	}
	
	@Transactional
	public OperatingHour editOperatingHour(DayOfWeek dayOfWeek, Time startTime, Time endTime, DayOfWeek dayOfWeek1, Time startTime1, Time endTime1) {
		
		if(dayOfWeek==null || dayOfWeek1==null) throw new IllegalArgumentException("Day of week cannot be blank");
		if(startTime==null || startTime1==null) throw new IllegalArgumentException("Start time cannot be blank");
		if(endTime==null || endTime1==null) throw new IllegalArgumentException("End time cannot be blank");
		if(operatingHourRepository.findByDayOfWeek(dayOfWeek)==null) {
			throw new IllegalArgumentException("Operating hour cannot be found");
		}
		operatingHourRepository.delete(operatingHourRepository.findByDayOfWeek(dayOfWeek));
		OperatingHour operatingHour = new OperatingHour();
		operatingHour.setDayOfWeek(dayOfWeek1);
		operatingHour.setStartTime(startTime1);
		operatingHour.setEndTime(endTime1);
		operatingHourRepository.save(operatingHour);
		return operatingHour;
	}
	
	@Transactional
	public OperatingHour deleteOperatingHour(DayOfWeek dayOfWeek, Time startTime, Time endTime) {
		if(dayOfWeek==null) throw new IllegalArgumentException("Day of week cannot be blank");
		if(startTime==null) throw new IllegalArgumentException("Start time cannot be blank");
		if(endTime==null) throw new IllegalArgumentException("End time cannot be blank");
		if(startTime.after(endTime)) throw new IllegalArgumentException("Start time cannot be before end time");
		if(operatingHourRepository.findByDayOfWeek(dayOfWeek)==null) throw new IllegalArgumentException("Operating hour cannot be found");
		operatingHourRepository.delete(operatingHourRepository.findByDayOfWeek(dayOfWeek));
		return null;
	}
	
	
	
	@Transactional
	public OperatingHour getOperatingHour(DayOfWeek dayOfWeek) {
		return operatingHourRepository.findByDayOfWeek(dayOfWeek);
	}
	
	@Transactional
	public List<OperatingHour> getAllOperatingHour() {
		return toList(operatingHourRepository.findAll());
	}
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;

	}
}