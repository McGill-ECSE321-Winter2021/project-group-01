package ca.mcgill.ecse321.autoRepair.controller;

import ca.mcgill.ecse321.autoRepair.dao.ChosenServiceRepository;
import ca.mcgill.ecse321.autoRepair.dto.ChosenServiceDTO;
import ca.mcgill.ecse321.autoRepair.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class ChosenServiceController {

	@Autowired
	ChosenServiceRepository chosenServiceRepository;

	private ChosenServiceDTO convertToDTO(ChosenService servicito) {
		if(servicito==null) throw new IllegalArgumentException("Service not found.");
		return new ChosenServiceDTO(servicito.getName(), servicito.getDuration());
	}
 
}


