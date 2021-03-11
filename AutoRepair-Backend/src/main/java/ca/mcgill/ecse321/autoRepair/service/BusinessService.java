package ca.mcgill.ecse321.autoRepair.service;

import ca.mcgill.ecse321.autoRepair.dao.BusinessRepository;
import ca.mcgill.ecse321.autoRepair.model.Business;
import ca.mcgill.ecse321.autoRepair.model.OperatingHour;
import ca.mcgill.ecse321.autoRepair.model.TimeSlot;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BusinessService {

	@Autowired
	BusinessRepository businessRepository;

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
}