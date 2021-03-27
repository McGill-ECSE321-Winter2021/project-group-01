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

import ca.mcgill.ecse321.autoRepair.dto.AssistantDTO;
import ca.mcgill.ecse321.autoRepair.model.Assistant;
import ca.mcgill.ecse321.autoRepair.service.AssistantService;


@CrossOrigin(origins = "*")
@RestController
public class AssistantController {
	@Autowired
	private AssistantService assisService;

	/**
	 * @author Marc Saber
	 * returns a list of all the assistants 
	 * @return list of all assistants
	 */
	@GetMapping(value = { "/view_assistants"})
	public List<AssistantDTO> getAllAssitants() {
		return assisService.getAllAssistants().stream().map(assistant -> convertToDTO(assistant)).collect(Collectors.toList());
	}

	/**
	 * @author Marc Saber
	 * Gets an assistant DTO given a name
	 * @param username
	 * @return assistantDTO
	 */
	@GetMapping(value = {"/view_assistant/{username}"})
	public AssistantDTO viewAssistant(@PathVariable("username") String username) {
		return convertToDTO(assisService.getAssistant(username));
	}

	/**
	 * @author Marc Saber
	 * Creates an Assistant
	 * @param username
	 * @param password
	 * @return assistantDTO
	 */
	@PostMapping(value = {"/create_assistant"})
	public AssistantDTO createAssitant(@RequestParam("username") String username,@RequestParam("password") String password) {
		Assistant assistant = assisService.createAssistant(username,password);
		return convertToDTO(assistant);
	}

	/**
	 * @author Marc Saber
	 * Updates an assistant's password
	 * @param oldUsername
	 * @param newPassword
	 * @return assistantDTO
	 */
	@PostMapping(value = { "/update_assistant/{oldUsername}" })
	public AssistantDTO updateAssistant(@PathVariable("oldUsername") String oldUsername,
			@RequestParam("newPassword") String newPassword) {
		Assistant assistant = assisService.updateAssistant(oldUsername,newPassword);
		return convertToDTO(assistant);
	}

	/**
	 * @author Marc Saber
	 * Deletes an assistant
	 * @param username
	 * @return true when assistant is successfully deleted
	 */
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
