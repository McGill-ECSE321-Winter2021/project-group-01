
package ca.mcgill.ecse321.autoRepair.controller;

import ca.mcgill.ecse321.autoRepair.dao.ChosenServiceRepository;
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
	ChosenServiceRepository chosenServiceRepository;
	@Autowired

	private ChosenServiceService chosenService;
	
	
	@GetMapping(value = { "/service" })
	public List<ChosenServiceDTO> getAllServices() {
return chosenService.getAllChosenService().stream().map(service -> convertToDTO(service)).collect(Collectors.toList());
	}
	
	
	@PostMapping(value = { "/create chosen service/{name}/{duration}/{price}" })
	public ChosenServiceDTO createChosenService
	(@PathVariable("name") String name,@PathVariable("duration") int duration,@PathVariable("price") Double price) {
		ChosenService service = chosenService.createChosenService(name, duration, price);
		return convertToDTO(service);
	}
	
	@PostMapping(value = { "/update chosen service/{name}/{duration}/{price}" })
	public ChosenServiceDTO updateChosenService
	(@PathVariable("name") String name,@PathVariable("duration") int duration,@PathVariable("price") Double price) {
		ChosenService service = chosenService.editChosenService(name, duration, price);
		return convertToDTO(service);
	}
	
	@PostMapping(value = { "/delete chosen service/{name}" })
	public ChosenServiceDTO deleteChosenService
	(@PathVariable("name") String name) {
		ChosenService service = chosenService.deleteChosenService(name);
		return convertToDTO(service);
	}
	
	
	private ChosenServiceDTO convertToDTO(ChosenService service) {
		if(service==null) throw new IllegalArgumentException("Service not found.");
	 	return new ChosenServiceDTO(service.getName(), service.getDuration());
	}
 
}

