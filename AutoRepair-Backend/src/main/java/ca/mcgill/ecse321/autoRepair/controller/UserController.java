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
<<<<<<< HEAD
import ca.mcgill.ecse321.autoRepair.service.AssistantService;
import ca.mcgill.ecse321.autoRepair.service.OwnerService;
import ca.mcgill.ecse321.autoRepair.service.UserService;
=======
import ca.mcgill.ecse321.autoRepair.service.CustomerService;
>>>>>>> main



public class UserController {
	
<<<<<<< HEAD
	@Autowired
	private OwnerService owner1;
	@Autowired
	private AssistantService assistant1;

	@GetMapping(value = { "/owner", "/owner/" })
	public List<OwnerDTO> getAllOwners() {
		return owner1.getAllOwners().stream().map(owner -> convertToDTO(owner)).collect(Collectors.toList());
	}

	@PostMapping(value = { "/owner/{name}", "/owner/{name}/" })
	public OwnerDTO createOwner(@PathVariable("name") String name,@PathVariable("password") String password,
	@PathVariable("authentification") String authentificationCode)
			throws IllegalArgumentException {
		Owner owner = owner1.createOwner(name,password,authentificationCode);
		return convertToDTO(owner);
	}

	private OwnerDTO convertToDTO(Owner owner) {
		if(owner == null) throw new IllegalArgumentException("Owner not found.");
		return new OwnerDTO(owner.getUsername(),owner.getPassword());
	}
	
	
	@GetMapping(value = { "/assistants", "/assistants/" })
	public List<AssistantDTO> getAllAssitants() {
		return assistant1.getAllAssistants().stream().map(assistant -> convertToDTO(assistant)).collect(Collectors.toList());
	}
	
	@PostMapping(value = { "/assistant/{name}", "/assistant/{name}/" })
	public AssistantDTO createAssitant(@PathVariable("name") String name,
			@PathVariable("password") String password,@PathVariable ("authentification")String authentificationCode)
			throws IllegalArgumentException {
		Assistant assistant = assistant1.createAssistant(name,password);
		return convertToDTO(assistant);
	}
	

	private AssistantDTO convertToDTO(Assistant assistant) {
		if(assistant == null) throw new IllegalArgumentException("Assistant not found.");
		return new AssistantDTO(assistant.getUsername(),assistant.getPassword());
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
}
	
	
=======
//	@Autowired
//	private UserService user;
//
//	@GetMapping(value = { "/owner", "/owner/" })
//	public List<OwnerDTO> getAllOwners() {
//		return user.getAllOwners().stream().map(owner -> convertToDTO(owner)).collect(Collectors.toList());
//	}
//
//	@PostMapping(value = { "/owner/{name}", "/owner/{name}/" })
//	public OwnerDTO createOwner(@PathVariable("name") String name,@PathVariable("password") String password)
//			throws IllegalArgumentException {
//		Owner owner = user.createOwner(name,password);
//		return convertToDTO(owner);
//	}
//
//	private OwnerDTO convertToDTO(Owner owner) {
//		if(owner == null) throw new IllegalArgumentException("Owner not found.");
//		return new OwnerDTO(owner.getUsername());
//	}
//
////	private Owner convertToDomainObject(OwnerDTO ownerDto) {    not used!
////	List<Owner> allOwners = user.getAllOwners();
////	for (Owner owner : allOwners) {
////		if (owner.getUsername().equals(ownerDto.getUsername())) {
////			return owner;
////		}
////	}
////	return null;
////}
//
//	@GetMapping(value = { "/assistants", "/assistants/" })
//	public List<AssistantDTO> getAllAssitants() {
//		return user.getAllAssistants().stream().map(assistant -> convertToDTO(assistant)).collect(Collectors.toList());
//	}
//
//	@PostMapping(value = { "/assistant/{name}", "/assistant/{name}/" })
//	public AssistantDTO createAsssitant(@PathVariable("name") String name,@PathVariable("password") String password)
//			throws IllegalArgumentException {
//		Assistant assistant = user.createAssistant(name,password);
//		return convertToDTO(assistant);
//	}
//
//
//	private AssistantDTO convertToDTO(Assistant assistant) {
//		if(assistant == null) throw new IllegalArgumentException("Assistant not found.");
//		return new AssistantDTO(assistant.getUsername());
//	}
//
	
	
}
>>>>>>> main
