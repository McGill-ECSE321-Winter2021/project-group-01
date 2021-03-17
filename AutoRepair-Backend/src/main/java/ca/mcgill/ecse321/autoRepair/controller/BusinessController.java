package ca.mcgill.ecse321.autoRepair.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.autoRepair.dao.TimeSlotRepository;
import ca.mcgill.ecse321.autoRepair.dto.BusinessDTO;
import ca.mcgill.ecse321.autoRepair.dto.CustomerDTO;
import ca.mcgill.ecse321.autoRepair.dto.OperatingHourDTO;
import ca.mcgill.ecse321.autoRepair.dto.TimeSlotDTO;
import ca.mcgill.ecse321.autoRepair.model.Business;
import ca.mcgill.ecse321.autoRepair.model.OperatingHour;
import ca.mcgill.ecse321.autoRepair.model.OperatingHour.DayOfWeek;
import ca.mcgill.ecse321.autoRepair.model.TimeSlot;
import ca.mcgill.ecse321.autoRepair.service.BusinessService;

@CrossOrigin(origins = "*")
@RestController

public class BusinessController {
	
	@Autowired
	BusinessService businessService;
	
	@Autowired
	TimeSlotRepository timeSlotRepository;
	
	@PostMapping(value = {"/register_business"})
	public BusinessDTO registerBusiness(@RequestParam String name, @RequestParam String email, @RequestParam String address,
			@RequestParam String phoneNumber, @RequestParam String dayOfWeek, @RequestParam String startTimeBH, @RequestParam String endTimeBH, 
			@RequestParam String startTimeH, @RequestParam String endTimeH, @RequestParam String startDateH, @RequestParam String endDateH) {
		
		OperatingHour operatingHour = businessService.createOperatingHour(DayOfWeek.valueOf(dayOfWeek), Time.valueOf(startTimeBH+":00"), Time.valueOf(endTimeBH+":00"));
		List<OperatingHour> operatingHours = new ArrayList<OperatingHour>();
		operatingHours.add(operatingHour);
				
	
		TimeSlot holiday = new TimeSlot();
		holiday.setStartTime(Time.valueOf(startTimeH+":00"));
		holiday.setEndTime(Time.valueOf(endTimeH+":00"));
		holiday.setStartDate(Date.valueOf(startDateH));
		holiday.setEndDate(Date.valueOf(endDateH));
		timeSlotRepository.save(holiday);
		List<TimeSlot> holidays = new ArrayList<TimeSlot>();
		holidays.add(holiday);
		
		Business business = businessService.createBusiness(name, email, address, phoneNumber, operatingHours, holidays);

		return convertToDTO(business);

	}
	
	@PostMapping(value = {"/add_business_hours"})
	public OperatingHourDTO addBusinessHours(@RequestParam String dayOfWeek, @RequestParam String startTime, @RequestParam String endTime) {
		
		OperatingHour operatingHour = businessService.createOperatingHour(DayOfWeek.valueOf(dayOfWeek), Time.valueOf(startTime+":00"), Time.valueOf(endTime+":00"));
				
		return convertToDTO(operatingHour);

	}
	
	@PostMapping(value = {"/delete_business_hours"})
	public OperatingHourDTO deleteBusinessHours(@RequestParam String dayOfWeek) {
		
		OperatingHour operatingHour = businessService.deleteOperatingHour(DayOfWeek.valueOf(dayOfWeek));
				
		return convertToDTO(operatingHour);

	}
	
//	@PostMapping(value = {"/edit_business_hours"})
//	public OperatingHourDTO editBusinessHours(@RequestParam String dayOfWeek, @RequestParam String startTime, @RequestParam String endTime, @RequestParam String dayOfWeek1, @RequestParam String startTime1, @RequestParam String endTime1) {
//		
//		OperatingHour operatingHour = businessService.editOperatingHour(DayOfWeek.valueOf(dayOfWeek), Time.valueOf(startTime+":00"), Time.valueOf(endTime+":00"), DayOfWeek.valueOf(dayOfWeek1), Time.valueOf(startTime1+":00"), Time.valueOf(endTime1+":00"));
//				
//		return convertToDTO(operatingHour);
//
//	}
	
	private TimeSlotDTO convertToDTO(TimeSlot timeSlot) {
    	if(timeSlot==null) throw new IllegalArgumentException("Time slot not found.");
    	return new TimeSlotDTO(timeSlot.getStartTime(), timeSlot.getEndTime(), timeSlot.getStartDate(), timeSlot.getEndDate());
     }
	private OperatingHourDTO convertToDTO(OperatingHour operatingHour) {
 		if(operatingHour==null) throw new IllegalArgumentException("Operating hour not found.");
 		return new OperatingHourDTO(operatingHour.getDayOfWeek(), operatingHour.getStartTime(), operatingHour.getEndTime());
 	}
     private BusinessDTO convertToDTO(Business business) {
     	if(business==null) throw new IllegalArgumentException("Business not found.");
    	List<OperatingHourDTO> operatingHours = new ArrayList<OperatingHourDTO>();
    	for(int i=0; i<business.getBusinessHours().size(); i++) {
    		operatingHours.add(convertToDTO(business.getBusinessHours().get(i)));
    	}
    	List<TimeSlotDTO> holidays = new ArrayList<TimeSlotDTO>();
    	for(int i=0; i<business.getHolidays().size(); i++) {
    		holidays.add(convertToDTO(business.getHolidays().get(i)));
    	}
		return new BusinessDTO(business.getName(), business.getEmail(), business.getAddress(), business.getPhoneNumber(), operatingHours, holidays);
    }
}
