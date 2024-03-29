package ca.mcgill.ecse321.autoRepair.service;

import ca.mcgill.ecse321.autoRepair.dao.BusinessRepository;
import ca.mcgill.ecse321.autoRepair.dao.OperatingHourRepository;
import ca.mcgill.ecse321.autoRepair.model.Business;
import ca.mcgill.ecse321.autoRepair.model.OperatingHour;
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

	/**
	 * @author Fadi Tawfik Beshay
	 * Creates the business information of a given business
	 * @param name
	 * @param email
	 * @param address
	 * @param phoneNumber
	 * @return business
	 */
	@Transactional
	public Business createBusiness(String name, String email, String address, String phoneNumber) {
		if(toList(businessRepository.findAll()).size()==1) {
			throw new IllegalArgumentException("Business already exists");
		}
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
		business.setBusinessHours(new ArrayList<>());
		business.setHolidays(new ArrayList<>());
		businessRepository.save(business);
		return business;
	}
	
	/**
	 * @author Fadi Tawfik Beshay
	 * Edits the business information of a given business
	 * @param name
	 * @param newName
	 * @param email
	 * @param address
	 * @param phoneNumber
	 * @return business
	 */
	@Transactional
	public Business editBusiness(String name, String newName, String email, String address, String phoneNumber) {
		if(name==null || name=="") throw new IllegalArgumentException("Name cannot be blank");
		if(email==null || email=="") throw new IllegalArgumentException("Email cannot be blank");
		if(address==null || address=="") throw new IllegalArgumentException("Address cannot be blank");
		if(phoneNumber==null || phoneNumber=="") throw new IllegalArgumentException("Phone number cannot be blank");
		if ((email.indexOf('@') == -1) || (email.indexOf('.') == -1) || (email.indexOf('.') < email.indexOf('@')) || (email.indexOf('@') == email.length()-1) || (email.indexOf('.') == email.length()-1)){
			throw new IllegalArgumentException("Invalid email");
		}	
		Business business = businessRepository.findBusinessByName(name);
		if(business==null) throw new IllegalArgumentException("Business not found");
		business.setName(newName);
		business.setEmail(email);
		business.setAddress(address);
		business.setPhoneNumber(phoneNumber);
		businessRepository.save(business);
		return business;
	}

	/**
	 * @author Fadi Tawfik Beshay
	 * Returns the business
	 * @return business
	 */
	@Transactional
	public Business getBusiness() {
		try {
			return toList(businessRepository.findAll()).get(0);
		}catch (IndexOutOfBoundsException e) {
			return null;
		}
		
	}

	/**
	 * @author Fadi Tawfik Beshay
	 * Creates an operating hour for the business
	 * @param businessName
	 * @param dayOfWeek
	 * @param startTime
	 * @param endTime
	 * @return operatingHour
	 */
	@Transactional
	public OperatingHour createOperatingHour(String businessName, DayOfWeek dayOfWeek, Time startTime, Time endTime) {
		if(dayOfWeek==null) throw new IllegalArgumentException("Day of week cannot be blank");
		if(startTime==null) throw new IllegalArgumentException("Start time cannot be blank");
		if(endTime==null) throw new IllegalArgumentException
		("End time cannot be blank");
		if(startTime.after(endTime)) throw new IllegalArgumentException
		("Start time cannot be after end time");
		if(operatingHourRepository.findByDayOfWeek(dayOfWeek)!=null) throw new IllegalArgumentException("Operating hour already exists");

		OperatingHour operatingHour = new OperatingHour();
		operatingHour.setDayOfWeek(dayOfWeek);
		operatingHour.setStartTime(startTime);
		operatingHour.setEndTime(endTime);
		Business business = businessRepository.findBusinessByName(businessName);
		business.getBusinessHours().add(operatingHour);
		operatingHourRepository.save(operatingHour);
		businessRepository.save(business);

		return operatingHour;
	}


	
	/**
	 * @author Fadi Tawfik Beshay
	 * edits the operating hour of a business
	 * @param dayOfWeek
	 * @param newDayOfWeek
	 * @param startTime
	 * @param endTime
	 * @return operatingHour
	 */
	@Transactional
	public OperatingHour editOperatingHour(DayOfWeek dayOfWeek, DayOfWeek newDayOfWeek, Time startTime, Time endTime) {
		
		if(dayOfWeek==null || newDayOfWeek==null) throw new IllegalArgumentException("Day of week cannot be blank");
		if(startTime==null) throw new IllegalArgumentException("Start time cannot be blank");
		if(endTime==null) throw new IllegalArgumentException("End time cannot be blank");
		if(startTime.after(endTime)) throw new IllegalArgumentException("Start time cannot be before end time");
		if(operatingHourRepository.findByDayOfWeek(dayOfWeek)==null) throw new IllegalArgumentException("Operating hour cannot be found");

		OperatingHour operatingHour = operatingHourRepository.findByDayOfWeek(dayOfWeek);
		operatingHour.setDayOfWeek(newDayOfWeek);
		operatingHour.setStartTime(startTime);
		operatingHour.setEndTime(endTime);
		operatingHourRepository.save(operatingHour);

		return operatingHour;
	}
	
	/**
	 * @author Fadi Tawfik Beshay
	 * Deletes an operating hour of a business
	 * @param businessName
	 * @param dayOfWeek
	 * @return true when operating hour is deleted
	 */
	@Transactional
	public boolean deleteOperatingHour(String businessName, DayOfWeek dayOfWeek) {
		if(dayOfWeek==null) throw new IllegalArgumentException("Day of week cannot be blank");

		OperatingHour operatingHour = operatingHourRepository.findByDayOfWeek(dayOfWeek);
		if(operatingHour==null) throw new IllegalArgumentException("Operating hour cannot be found");

		Business business = businessRepository.findBusinessByName(businessName);
		if(business==null) throw new IllegalArgumentException("Business not found");

		business.getBusinessHours().remove(operatingHour);
		businessRepository.save(business);
		operatingHourRepository.delete(operatingHourRepository.findByDayOfWeek(dayOfWeek));

		return true;
	}
	
	/**
	 * @author Fadi Tawfik Beshay
	 * Gets an operating hour given any day of the week
	 * @param dayOfWeek
	 * @return operatingHour
	 */
	@Transactional
	public OperatingHour getOperatingHour(DayOfWeek dayOfWeek) {
		return operatingHourRepository.findByDayOfWeek(dayOfWeek);
	}
	
	/**
	 * @author Fadi Tawfik Beshay
	 * Gets all operating hours of a business
	 * @return list of operating hours
	 */
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