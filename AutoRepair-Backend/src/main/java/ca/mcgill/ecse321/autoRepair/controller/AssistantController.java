package ca.mcgill.ecse321.autoRepair.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.autoRepair.dao.AssistantRepository;
import ca.mcgill.ecse321.autoRepair.dao.OwnerRepository;
import ca.mcgill.ecse321.autoRepair.dto.AssistantDTO;
import ca.mcgill.ecse321.autoRepair.dto.OwnerDTO;
import ca.mcgill.ecse321.autoRepair.model.Assistant;
import ca.mcgill.ecse321.autoRepair.model.Owner;
import ca.mcgill.ecse321.autoRepair.service.AssistantService;
import ca.mcgill.ecse321.autoRepair.service.OwnerService;


@CrossOrigin(origins = "*")
@RestController
public class AssistantController {
	@Autowired
	AssistantRepository assisRepository;
	@Autowired
	private AssistantService assisService;
	

	@GetMapping(value = { "/view_assistants"})
	public List<AssistantDTO> getAllAssitants() {
return assisService.getAllAssistants().stream().map(assistant -> convertToDTO(assistant)).collect(Collectors.toList());
	}
	
	
	@GetMapping(value = {"/view_assistant/{username}"})
	public AssistantDTO viewAssistant(@PathVariable("username") String username) {
		return convertToDTO(assisService.getAssistant(username));
	}
	
	@PostMapping(value = {"/create_assistant"})
	public AssistantDTO createAssitant(@RequestParam("username") String username,@RequestParam("password") String password) {
		Assistant assistant = assisService.createAssistant(username,password);
		return convertToDTO(assistant);
	}
	
	
	
	@PostMapping(value = { "/update_assistant}" })
	public AssistantDTO updateAssistant(@PathVariable("oldUsername") String oldUsername,
			@RequestParam("newUsername") String newUsername,@RequestParam("newPassword") String newPassword) {
        Assistant assistant = assisService.updateAssistant(oldUsername, newUsername, newPassword);
		return convertToDTO(assistant);
	}
	
	@PostMapping(value = { "/delete_assistant/{username}" })
	public boolean deleteAssistant(@PathVariable("username") String username){
        boolean assistant = assisService.deleteAssistant(username);
		return assistant;
	}
	
	private AssistantDTO convertToDTO(Assistant assistant) {
		if(assistant == null) throw new IllegalArgumentException("Assistant not found.");
		return new AssistantDTO(assistant.getUsername(),assistant.getPassword());
	}





}
