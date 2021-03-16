package ca.mcgill.ecse321.autoRepair.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ca.mcgill.ecse321.autoRepair.dao.AssistantRepository;
import ca.mcgill.ecse321.autoRepair.dao.OwnerRepository;
import ca.mcgill.ecse321.autoRepair.dto.AssistantDTO;
import ca.mcgill.ecse321.autoRepair.dto.OwnerDTO;
import ca.mcgill.ecse321.autoRepair.model.Assistant;
import ca.mcgill.ecse321.autoRepair.model.Owner;
import ca.mcgill.ecse321.autoRepair.service.AssistantService;
import ca.mcgill.ecse321.autoRepair.service.OwnerService;

public class AssistantController {
	@Autowired
	AssistantRepository assisRepository;
	@Autowired
	private AssistantService assisService;
	

	@GetMapping(value = { "/assistants", "/assistants/" })
	public List<AssistantDTO> getAllAssitants() {
return assisService.getAllAssistants().stream().map(assistant -> convertToDTO(assistant)).collect(Collectors.toList());
	}

	
	@PostMapping(value = { "/create assistant/{name}/{password}" })
	public AssistantDTO createAsssitant(@PathVariable("name") String name,@PathVariable("password") String password) {
		Assistant assistant = assisService.createAssistant(name,password);
		return convertToDTO(assistant);
	}
	
	@PostMapping(value = { "/update assistant/{oldUsername}/{newUsername}/{newPassword}" })
	public AssistantDTO updateAssistant(@PathVariable("oldUsername") String oldUsername,
			@PathVariable("newUsername") String newUsername,@PathVariable("newPassword") String newPassword) {
        Assistant assistant = assisService.updateAssistant(oldUsername, newUsername, newPassword);
		return convertToDTO(assistant);
	}
	
	@PostMapping(value = { "/delete assistant/{username}" })
	public boolean deleteAssistant(@PathVariable("username") String username){
        boolean assistant = assisService.deleteAssistant(username);
		return assistant;
	}
	
	private AssistantDTO convertToDTO(Assistant assistant) {
		if(assistant == null) throw new IllegalArgumentException("Assistant not found.");
		return new AssistantDTO(assistant.getUsername(),assistant.getPassword());
	}





}
