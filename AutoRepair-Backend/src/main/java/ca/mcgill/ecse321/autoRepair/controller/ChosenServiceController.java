package ca.mcgill.ecse321.autoRepair.controller;

import ca.mcgill.ecse321.autoRepair.dto.ChosenServiceDTO;
import ca.mcgill.ecse321.autoRepair.model.ChosenService;

public class ChosenServiceController {
	
	private ChosenServiceDTO convertToDTO(ChosenService service) {
		if(service == null) throw new IllegalArgumentException("Service not found");
		return new ChosenServiceDTO(service.getName(), service.getDuration());
	}

}
