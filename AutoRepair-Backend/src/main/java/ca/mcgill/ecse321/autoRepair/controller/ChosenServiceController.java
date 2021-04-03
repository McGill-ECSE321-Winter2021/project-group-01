package ca.mcgill.ecse321.autoRepair.controller;

import ca.mcgill.ecse321.autoRepair.dto.ChosenServiceDTO;
import ca.mcgill.ecse321.autoRepair.model.*;
import ca.mcgill.ecse321.autoRepair.service.ChosenServiceService;
import ca.mcgill.ecse321.autoRepair.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
public class ChosenServiceController {

	
	@Autowired
	private ChosenServiceService chosenService;
	@Autowired
	private ReviewService reviewService;

	
	
	/**
	 * @author Robert Aprahamian
	 * Gets a list of all the chosen services
	 * @return list of all chosen service DTOs
	 */
	@GetMapping(value = { "/view_all_services", "/view_all_services/" })
	public List<ChosenServiceDTO> getAllServices() {
		return chosenService.getAllChosenService().stream().map(service -> convertToDTO(service)).collect(Collectors.toList());
	}
	
	/**
	 * @author Robert Aprahamian
	 * Gets a chosen service given a service name
	 * @param name
	 * @return chosenServiceDTO
	 */
	@GetMapping(value = { "/get_service","/get_service/" })
	public ChosenServiceDTO getService(@RequestParam String name) {
		return convertToDTO(chosenService.getChosenService(name));//.stream().map(service -> convertToDTO(service)).collect(Collectors.toList());
	}

	/**
	 * @author Robert Aprahamian
	 * Creates a chosen service
	 * @param duration
	 * @param price
	 * @return chosenServiceDTO
	 */
	@PostMapping(value = { "/create_service","/create_service/" })
	public ResponseEntity<?> createChosenService
	(@RequestParam String serviceName,@RequestParam String duration,@RequestParam String price) {
		if(serviceName == "")  return new ResponseEntity<>("The service name cannot be null", HttpStatus.INTERNAL_SERVER_ERROR);
		if(duration == "")  return new ResponseEntity<>("The service duration cannot be null", HttpStatus.INTERNAL_SERVER_ERROR);
		if(price == "")  return new ResponseEntity<>("The service price cannot be null", HttpStatus.INTERNAL_SERVER_ERROR);
		int duration2 =  Integer.parseInt(duration);
		Double d = Double.parseDouble(price);
		ChosenService service = null;
		try {
		service = chosenService.createChosenService(serviceName, duration2, d);
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(convertToDTO(service), HttpStatus.CREATED);
		//return convertToDTO(service);
	}

	/**
	 * @author Robert Aprahamian
	 * Updates a chosen service
	 * @param duration
	 * @param price
	 * @return chosenServiceDTO
	 */
	@PatchMapping(value = { "/update_service","/update_service/" })
	public ResponseEntity<?> updateChosenService
	(@RequestParam String serviceName,@RequestParam String duration,@RequestParam String price) {
		if(serviceName == "")  return new ResponseEntity<>("The service name cannot be null", HttpStatus.INTERNAL_SERVER_ERROR);
		if(duration == "")  return new ResponseEntity<>("The service duration cannot be null", HttpStatus.INTERNAL_SERVER_ERROR);
		if(price == "")  return new ResponseEntity<>("The service price cannot be null", HttpStatus.INTERNAL_SERVER_ERROR);
		int duration2 =  Integer.parseInt(duration);
		Double d = Double.parseDouble(price);
		ChosenService service = null;
		try {
		service = chosenService.editChosenService(serviceName, duration2, d);
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(convertToDTO(service), HttpStatus.OK);
	}

	/**
	 * @author Robert Aprahamian
	 * Deletes a chosen service given a service name
	 * @return true if chosen service is successfully deleted
	 */
	@DeleteMapping(value = { "/delete_service","/delete_service/" })
	public ResponseEntity<?> deleteChosenService
	(@RequestParam String serviceName) {
		if(serviceName == "")  return new ResponseEntity<>("The service name cannot be null", HttpStatus.INTERNAL_SERVER_ERROR);
		try {
			chosenService.deleteChosenService(serviceName);
			return new ResponseEntity<>(true, HttpStatus.OK);
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	private ChosenServiceDTO convertToDTO(ChosenService service) {
		if(service==null) throw new IllegalArgumentException("Service not found.");
		 Double avRating = null;
		try {
			avRating = reviewService.getAverageServiceReview(service.getName());
		}
		catch (Exception e){
			avRating = -1.0;
		}
	
		return new ChosenServiceDTO(service.getName(), service.getDuration(), service.getPayment(), avRating);
	}

}