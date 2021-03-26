
package ca.mcgill.ecse321.autoRepair.controller;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.autoRepair.dao.TimeSlotRepository;
import ca.mcgill.ecse321.autoRepair.dto.BusinessDTO;
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
	
	/**
	 * @author Fadi Tawfik Beshay
	 * Registers a business given its business information
	 * @param name
	 * @param email
	 * @param address
	 * @param phoneNumber
	 * @return businessDTO
	 */
	@PostMapping(value = {"/register_business"})
	public BusinessDTO registerBusiness(@RequestParam String name, @RequestParam String email, @RequestParam String address,
			@RequestParam String phoneNumber) {
		
		Business business = businessService.createBusiness(name, email, address, phoneNumber);

		return convertToDTO(business);

	}
	
	/**
	 * @author Fadi Tawfik Beshay
	 * Edits the business information of a given business
	 * @param name
	 * @param name1
	 * @param email
	 * @param address
	 * @param phoneNumber
	 * @return businessDTO
	 */
	@PostMapping(value = {"/edit_business"})
	public BusinessDTO editBusiness(@RequestParam String name, @RequestParam String name1, @RequestParam String email, @RequestParam String address,
			@RequestParam String phoneNumber) {
		
		Business business = businessService.editBusiness(name, name1, email, address, phoneNumber);

		return convertToDTO(business);

	}
	
	/**
	 * @author Fadi Tawfik Beshay
	 * Adds the business hours of a business
	 * @param businessName
	 * @param dayOfWeek
	 * @param startTime
	 * @param endTime
	 * @return operatingHourDTO
	 */
	@PostMapping(value = {"/add_business_hours"})
	public OperatingHourDTO addBusinessHours(@RequestParam String businessName, @RequestParam String dayOfWeek, @RequestParam String startTime, @RequestParam String endTime) {
		
		return convertToDTO(businessService.createOperatingHour(businessName, DayOfWeek.valueOf(dayOfWeek), Time.valueOf(startTime+":00"), Time.valueOf(endTime+":00")));

	}
	
	/**
	 * @author Fadi Tawfik Beshay
	 * Edits the business hours of a business
	 * @param dayOfWeek
	 * @param dayOfWeek1
	 * @param startTime1
	 * @param endTime1
	 * @return operatingHourDTO
	 */
	@PostMapping(value = {"/edit_business_hours"})
	public OperatingHourDTO editBusinessHours(@RequestParam String dayOfWeek, @RequestParam String dayOfWeek1, @RequestParam String startTime1, @RequestParam String endTime1) {
		
		OperatingHour operatingHour = businessService.editOperatingHour(DayOfWeek.valueOf(dayOfWeek), DayOfWeek.valueOf(dayOfWeek1), Time.valueOf(startTime1+":00"), Time.valueOf(endTime1+":00"));

		return convertToDTO(operatingHour);

	}
	
	/**
	 * @author Fadi Tawfik Beshay
	 * Deletes the business hours of a given business
	 * @param businessName
	 * @param dayOfWeek
	 * @return true when successfully deleted
	 */
	@PostMapping(value = {"/delete_business_hours"})
	public boolean deleteBusinessHours(@RequestParam String businessName, @RequestParam String dayOfWeek) {
		
		return businessService.deleteOperatingHour(businessName, DayOfWeek.valueOf(dayOfWeek));

	}
	
	/**
	 * @author Fadi Tawfik Beshay
	 * Returns the business information of a business given a business name
	 * @param businessName
	 * @return businessDTO
	 */
	@GetMapping(value = {"/view_business_info", "/view_business_info/"})
	public BusinessDTO viewBusinessInfo(@RequestParam String businessName){

		return convertToDTO(businessService.getBusiness(businessName));
	
	}
	
	/**
	 * @author Fadi Tawfik Beshay
	 * Gets all the operating hours a business
	 * @return list containing all the operating hours of a business
	 */
	@GetMapping(value = {"/view_operating_hours", "/view_operating_hours/"})
	public List<OperatingHourDTO> viewOperatingHours(){

		return businessService.getAllOperatingHour().stream().map(c ->
		convertToDTO(c)).collect(Collectors.toList());
	
	}
	
	/**
	 * @author Fadi Tawfik Beshay
	 * Returns an operating hour given any day of the week
	 * @param dayOfWeek
	 * @return operatingHourDTO
	 */
	@GetMapping(value = {"/view_operating_hour_by_day", "/view_operating_hour_by_day/"})
	public OperatingHourDTO viewOperatingHourByDay(@RequestParam String dayOfWeek){

		return convertToDTO(businessService.getOperatingHour(DayOfWeek.valueOf(dayOfWeek)));
	
	}
	
	
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
