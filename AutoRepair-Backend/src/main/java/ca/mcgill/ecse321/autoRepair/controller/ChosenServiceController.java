package ca.mcgill.ecse321.autoRepair.controller;

import ca.mcgill.ecse321.autoRepair.dto.ChosenServiceDTO;
import ca.mcgill.ecse321.autoRepair.model.*;
import ca.mcgill.ecse321.autoRepair.service.ChosenServiceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
public class ChosenServiceController {

	@Autowired
	private ChosenServiceService chosenService;

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
	 * @param name
	 * @param duration
	 * @param price
	 * @return chosenServiceDTO
	 */
	@PostMapping(value = { "/create_service","/create_service/" })
	public ChosenServiceDTO createChosenService
	(@RequestParam String name,@RequestParam int duration,@RequestParam Double price) {
		ChosenService service = chosenService.createChosenService(name, duration, price);
		return convertToDTO(service);
	}

	/**
	 * @author Robert Aprahamian
	 * Updates a chosen service
	 * @param name
	 * @param duration
	 * @param price
	 * @return chosenServiceDTO
	 */
	@PostMapping(value = { "/update_service","/update_service/" })
	public ChosenServiceDTO updateChosenService
	(@RequestParam String name,@RequestParam int duration,@RequestParam Double price) {
		ChosenService service = chosenService.editChosenService(name, duration, price);
		return convertToDTO(service);
	}

	/**
	 * @author Robert Aprahamian
	 * Deletes a chosen service given a service name
	 * @param name
	 * @return true if chosen service is successfully deleted
	 */
	@PostMapping(value = { "/delete_service","/delete_service/" })
	public boolean deleteChosenService
	(@RequestParam String name) {
		return chosenService.deleteChosenService(name);
	}

	private ChosenServiceDTO convertToDTO(ChosenService service) {
		if(service==null) throw new IllegalArgumentException("Service not found.");
		return new ChosenServiceDTO(service.getName(), service.getDuration(), service.getPayment());
	}

}