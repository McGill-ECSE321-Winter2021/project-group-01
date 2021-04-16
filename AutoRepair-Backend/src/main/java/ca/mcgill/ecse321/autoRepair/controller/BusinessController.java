
package ca.mcgill.ecse321.autoRepair.controller;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	public ResponseEntity<?> registerBusiness(@RequestParam String name, @RequestParam String email, @RequestParam String address,
			@RequestParam String phoneNumber) {
		
		Business business=null;
		try {
			business = businessService.createBusiness(name, email, address, phoneNumber);
		}
		catch(IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(convertToDTO(business), HttpStatus.CREATED);

	}
	
	/**
	 * @author Fadi Tawfik Beshay
	 * Edits the business information of a given business
	 * @param email
	 * @param address
	 * @param phoneNumber
	 * @return businessDTO
	 */
	@PatchMapping(value = {"/edit_business"})
	public ResponseEntity<?> editBusiness(@RequestParam String email, @RequestParam String address,
			@RequestParam String phoneNumber) {
		
		String businessName = businessService.getBusiness().getName();
		Business business=null;
		try {
			business = businessService.editBusiness(businessName, businessName, email, address, phoneNumber);
		}
		catch(IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(convertToDTO(business), HttpStatus.OK);

	}
	
	/**
	 * @author Fadi Tawfik Beshay
	 * Adds the business hours of a business
	 * @param dayOfWeek
	 * @param startTime
	 * @param endTime
	 * @return operatingHourDTO
	 */
	@PostMapping(value = {"/add_business_hours"})
	public  ResponseEntity<?> addBusinessHours(@RequestParam String dayOfWeek, @RequestParam String startTime, @RequestParam String endTime) {
		String businessName = businessService.getBusiness().getName();
		OperatingHour operatingHour = null;
		try {
		 operatingHour =businessService.createOperatingHour(businessName, DayOfWeek.valueOf(dayOfWeek), Time.valueOf(startTime+":00"), Time.valueOf(endTime+":00"));
		}		
			catch(IllegalArgumentException e) {
	return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

			}
		return new ResponseEntity<>(convertToDTO(operatingHour), HttpStatus.CREATED);
	}
	/**
	 * @author Fadi Tawfik Beshay
	 * Edits the business hours of a business
	 * @param dayOfWeek
	 * @param startTime
	 * @param endTime
	 * @return operatingHourDTO
	 */
	@PatchMapping(value = {"/edit_business_hours"})
	public ResponseEntity<?> editBusinessHours(@RequestParam String dayOfWeek,@RequestParam String startTime, @RequestParam String endTime) {
		OperatingHour operatingHourToEdit = null;
		try{
			operatingHourToEdit =businessService.editOperatingHour(DayOfWeek.valueOf(dayOfWeek), DayOfWeek.valueOf(dayOfWeek), Time.valueOf(startTime+":00"), Time.valueOf(endTime+":00"));
		}
		catch(IllegalArgumentException e) {
      	return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}

		return new ResponseEntity<>(convertToDTO(operatingHourToEdit), HttpStatus.CREATED);
		

	}
	
	/**
	 * @author Fadi Tawfik Beshay
	 * Deletes the business hours of a given business
	 * @param dayOfWeek
	 * @return true when successfully deleted
	 */
	@DeleteMapping(value = {"/delete_business_hours"})
	public boolean deleteBusinessHours( @RequestParam String dayOfWeek) {
		String businessName = businessService.getBusiness().getName();
	return  businessService.deleteOperatingHour(businessName, DayOfWeek.valueOf(dayOfWeek));

	}
	
	/**
	 * @author Fadi Tawfik Beshay
	 * Returns the business information of a business given a business name
	 * @return businessDTO
	 */
	@GetMapping(value = {"/view_business_info", "/view_business_info/"})
	public BusinessDTO viewBusinessInfo(){

		return convertToDTO(businessService.getBusiness());
	
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

	/**
	 * This method converts a TimeSlot object to a TimeSlotDTO
	 * @param timeSlot
	 * @return
	 */
	private TimeSlotDTO convertToDTO(TimeSlot timeSlot) {
    	if(timeSlot==null) throw new IllegalArgumentException("Time slot not found.");
    	return new TimeSlotDTO(timeSlot.getStartTime(), timeSlot.getEndTime(), timeSlot.getStartDate(), timeSlot.getEndDate());
     }

	/**
	 * This method converts an OperatingHour object to an OperatingHourDTO
	 * @param operatingHour
	 * @return
	 */
	private OperatingHourDTO convertToDTO(OperatingHour operatingHour) {
 		if(operatingHour==null) throw new IllegalArgumentException("Operating hour not found.");
 		return new OperatingHourDTO(operatingHour.getDayOfWeek(), operatingHour.getStartTime(), operatingHour.getEndTime());
 	}

	/**
	 * This method converts a Business object to a BusinessDTO
	 * @param business
	 * @return
	 */
     private BusinessDTO convertToDTO(Business business) {
     	if(business==null) return null;
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
