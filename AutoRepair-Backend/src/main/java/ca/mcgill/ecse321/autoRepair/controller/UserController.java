package ca.mcgill.ecse321.autoRepair.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ca.mcgill.ecse321.autoRepair.dto.AssistantDTO;
import ca.mcgill.ecse321.autoRepair.dto.OwnerDTO;
import ca.mcgill.ecse321.autoRepair.model.Assistant;
import ca.mcgill.ecse321.autoRepair.model.Owner;
import ca.mcgill.ecse321.autoRepair.service.UserService;



public class UserController {
	
	@Autowired
	private UserService user;

	@GetMapping(value = { "/owner", "/owner/" })
	public List<OwnerDTO> getAllOwners() {
		return user.getAllOwners().stream().map(owner -> convertToDTO(owner)).collect(Collectors.toList());
	}

	@PostMapping(value = { "/owner/{name}", "/owner/{name}/" })
	public OwnerDTO createOwner(@PathVariable("name") String name,@PathVariable("password") String password)
			throws IllegalArgumentException {
		Owner owner = user.createOwner(name,password);
		return convertToDTO(owner);
	}

	private OwnerDTO convertToDTO(Owner owner) {
		if(owner == null) throw new IllegalArgumentException("Owner not found.");
		return new OwnerDTO(owner.getUsername());
	}
	
//	private Owner convertToDomainObject(OwnerDTO ownerDto) {    not used!
//	List<Owner> allOwners = user.getAllOwners();
//	for (Owner owner : allOwners) {
//		if (owner.getUsername().equals(ownerDto.getUsername())) {
//			return owner;
//		}
//	}
//	return null;
//}
	
	@GetMapping(value = { "/assistants", "/assistants/" })
	public List<AssistantDTO> getAllAssitants() {
		return user.getAllAssistants().stream().map(assistant -> convertToDTO(assistant)).collect(Collectors.toList());
	}
	
	@PostMapping(value = { "/assistant/{name}", "/assistant/{name}/" })
	public AssistantDTO createAsssitant(@PathVariable("name") String name,@PathVariable("password") String password)
			throws IllegalArgumentException {
		Assistant assistant = user.createAssistant(name,password);
		return convertToDTO(assistant);
	}
	

	private AssistantDTO convertToDTO(Assistant assistant) {
		if(assistant == null) throw new IllegalArgumentException("Assistant not found.");
		return new AssistantDTO(assistant.getUsername());
	}
	
	
	
}
