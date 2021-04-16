package ca.mcgill.ecse321.autoRepair.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	 * Returns a list of all the assistants
	 * @return list of all assistants
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = { "/view_assistants"})
	public List<AssistantDTO> getAllAssitants() {
		return assisService.getAllAssistants().stream().map(assistant -> Conversion.convertToDTO(assistant)).collect(Collectors.toList());
	}

	/**
	 * @author Marc Saber
	 * Gets an assistant DTO given a username
	 * @param username
	 * @return assistantDTO
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = {"/view_assistant/{username}"})
	public AssistantDTO viewAssistant(@PathVariable("username") String username) {
		return Conversion.convertToDTO(assisService.getAssistant(username));
	}

	/**
	 * @author Marc Saber
	 * This method creates an Assistant
	 * @param username
	 * @param password
	 * @param authentificationCode
	 * @return assistantDTO
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = {"/create_assistant"})
	public ResponseEntity<?> createAssitant
	(@RequestParam("username") String username,@RequestParam("password") String password,
			@RequestParam("authentificationCode") String authentificationCode) {
		if (!authentificationCode.equals("5678")) {
			throw new IllegalArgumentException ("Wrong Authentification Code");
		}
		Assistant assistant =null;	
		try {
			assistant = assisService.createAssistant(username,password);
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(Conversion.convertToDTO(assistant), HttpStatus.CREATED);

	}


	/**
	 * @author Marc Saber
	 * Updates an assistant's password
	 * @param oldUsername
	 * @param newPassword
	 * @return assistantDTO
	 */
	@PatchMapping(value = { "/update_assistant/{oldUsername}" })
	public AssistantDTO updateAssistant(@PathVariable("oldUsername") String oldUsername,
			@RequestParam("newPassword") String newPassword) {
		Assistant assistant = assisService.updateAssistant(oldUsername,newPassword);
		return Conversion.convertToDTO(assistant);
	}

	/**
	 * @author Marc Saber
	 * Deletes an assistant
	 * @param username
	 * @return true when assistant is successfully deleted
	 */
	@DeleteMapping(value = { "/delete_assistant/{username}" })
	public boolean deleteAssistant(@PathVariable("username") String username){
		boolean assistant = assisService.deleteAssistant(username);
		return assistant;
	}
}