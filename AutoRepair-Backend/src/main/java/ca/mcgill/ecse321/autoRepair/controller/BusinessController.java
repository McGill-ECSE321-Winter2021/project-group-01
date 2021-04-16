package ca.mcgill.ecse321.autoRepair.controller;

import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.autoRepair.dao.TimeSlotRepository;
import ca.mcgill.ecse321.autoRepair.dto.BusinessDTO;
import ca.mcgill.ecse321.autoRepair.dto.OperatingHourDTO;
import ca.mcgill.ecse321.autoRepair.model.Business;
import ca.mcgill.ecse321.autoRepair.model.OperatingHour;
import ca.mcgill.ecse321.autoRepair.model.OperatingHour.DayOfWeek;
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
	public ResponseEntity<?> registerBusiness(@RequestParam String name, @RequestParam String email, @RequestParam String address,
			@RequestParam String phoneNumber) {
		
		Business business=null;
		try {
			business = businessService.createBusiness(name, email, address, phoneNumber);
		}
		catch(IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(Conversion.convertToDTO(business), HttpStatus.CREATED);

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
		return new ResponseEntity<>(Conversion.convertToDTO(business), HttpStatus.OK);

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
	public  ResponseEntity<?> addBusinessHours(@RequestParam String dayOfWeek, @RequestParam String startTime, @RequestParam String endTime) {
		String businessName = businessService.getBusiness().getName();
		OperatingHour opHour = null;
		try {
		opHour =businessService.createOperatingHour(businessName, DayOfWeek.valueOf(dayOfWeek), Time.valueOf(startTime+":00"), Time.valueOf(endTime+":00"));
		}		
			catch(IllegalArgumentException e) {
	return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

			}
		return new ResponseEntity<>(Conversion.convertToDTO(opHour), HttpStatus.CREATED);
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
	public ResponseEntity<?> editBusinessHours(@RequestParam String dayOfWeek,@RequestParam String startTime1, @RequestParam String endTime1) {
		String dayOfWeek1 = dayOfWeek;
		OperatingHour opHourToEdit = null;
		try{
			opHourToEdit =businessService.editOperatingHour(DayOfWeek.valueOf(dayOfWeek), DayOfWeek.valueOf(dayOfWeek1), Time.valueOf(startTime1+":00"), Time.valueOf(endTime1+":00"));
		}
		catch(IllegalArgumentException e) {
      	return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}

		return new ResponseEntity<>(Conversion.convertToDTO(opHourToEdit), HttpStatus.CREATED);
		

	}
	
	/**
	 * @author Fadi Tawfik Beshay
	 * Deletes the business hours of a given business
	 * @param businessName
	 * @param dayOfWeek
	 * @return true when successfully deleted
	 */
	@PostMapping(value = {"/delete_business_hours"})
	public boolean deleteBusinessHours( @RequestParam String dayOfWeek) {
		String businessName = businessService.getBusiness().getName();
	return  businessService.deleteOperatingHour(businessName, DayOfWeek.valueOf(dayOfWeek));

	}  

	
	/**
	 * @author Fadi Tawfik Beshay
	 * Returns the business information of a business given a business name
	 * @param businessName
	 * @return businessDTO
	 */
	@GetMapping(value = {"/view_business_info", "/view_business_info/"})
	public BusinessDTO viewBusinessInfo(){

		return Conversion.convertToDTO(businessService.getBusiness());
	
	}
	
	/**
	 * @author Fadi Tawfik Beshay
	 * Gets all the operating hours a business
	 * @return list containing all the operating hours of a business
	 */
	@GetMapping(value = {"/view_operating_hours", "/view_operating_hours/"})
	public List<OperatingHourDTO> viewOperatingHours(){

		return businessService.getAllOperatingHour().stream().map(c ->
		Conversion.convertToDTO(c)).collect(Collectors.toList());
	
	}
	
	/**
	 * @author Fadi Tawfik Beshay
	 * Returns an operating hour given any day of the week
	 * @param dayOfWeek
	 * @return operatingHourDTO
	 */
	@GetMapping(value = {"/view_operating_hour_by_day", "/view_operating_hour_by_day/"})
	public OperatingHourDTO viewOperatingHourByDay(@RequestParam String dayOfWeek){

		return Conversion.convertToDTO(businessService.getOperatingHour(DayOfWeek.valueOf(dayOfWeek)));
	
	}
}