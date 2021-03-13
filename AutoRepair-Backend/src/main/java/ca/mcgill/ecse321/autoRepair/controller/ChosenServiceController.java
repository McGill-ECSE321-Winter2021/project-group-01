package ca.mcgill.ecse321.autoRepair.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ca.mcgill.ecse321.autoRepair.dto.AssistantDTO;
import ca.mcgill.ecse321.autoRepair.dto.ChosenServiceDTO;
import ca.mcgill.ecse321.autoRepair.dto.OwnerDTO;
import ca.mcgill.ecse321.autoRepair.model.Assistant;
import ca.mcgill.ecse321.autoRepair.model.ChosenService;
import ca.mcgill.ecse321.autoRepair.model.Owner;
import ca.mcgill.ecse321.autoRepair.model.Review;
import ca.mcgill.ecse321.autoRepair.dao.AssistantRepository;
import ca.mcgill.ecse321.autoRepair.dao.ChosenServiceRepository;
import ca.mcgill.ecse321.autoRepair.dao.OwnerRepository;

public class ChosenServiceController {

	@Autowired
	ChosenServiceRepository chosenServiceRepository;
	@Autowired
	AssistantRepository assistantRepository;
	@Autowired
    OwnerRepository ownerRepository;
	
	@PostMapping(value = { "/add service/{serviceName}/{serviceDuration}" })
	public ChosenServiceDTO addService
(@PathVariable ChosenService serviceName,@PathVariable int serviceDuration,
@PathVariable AssistantDTO assistantDTO, @PathVariable OwnerDTO ownerDTO) throws IllegalArgumentException {
		
ChosenService availableService = chosenServiceRepository.findChosenServiceByName(serviceName);
Assistant assistant = assistantRepository.findAssistantByUsername(assistantDTO.getUsername()); //returns null if owner in
Owner owner =  ownerRepository.findOwnerByUsername(ownerDTO.getUsername());  //returns null if assistant in


if (assistant==null && owner==null)throw new IllegalArgumentException
("Restricted access: can't add service");
if (serviceName==availableService)throw new IllegalArgumentException
("Service" + serviceName + "already exists");
if (serviceName==null)throw new IllegalArgumentException
("Please specify service name");
if (serviceDuration<0)throw new IllegalArgumentException
("Please specify service appropriate duration");

 ChosenService serviceToAdd = new ChosenService();
 serviceToAdd.setName(serviceName.getName());
 serviceToAdd.setDuration(serviceDuration);
 
	chosenServiceRepository.save(availableService);

	return convertToDTO(serviceToAdd);
	
	}


@PostMapping(value = { "/update service/{serviceName}/{serviceDuration}" })
public ChosenServiceDTO updateService
(@PathVariable ChosenService serviceName,@PathVariable int serviceDuration,
@PathVariable AssistantDTO assistantDTO, @PathVariable OwnerDTO ownerDTO) throws IllegalArgumentException {
	
	ChosenService availableService = chosenServiceRepository.findChosenServiceByName(serviceName);
	Assistant assistant = assistantRepository.findAssistantByUsername(assistantDTO.getUsername()); //returns null if owner in
	Owner owner =  ownerRepository.findOwnerByUsername(ownerDTO.getUsername());  //returns null if assistant in


	if (assistant==null && owner==null)throw new IllegalArgumentException
	("Restricted access: can't update service");
	if (serviceName!=availableService)throw new IllegalArgumentException
	("Service" + serviceName + "does not exist, kindly add service before updating it");
	if (serviceName==null)throw new IllegalArgumentException
	("Please specify service name to update");
	if (serviceDuration<0)throw new IllegalArgumentException
	("Please specify service appropriate duration to update");

	availableService.setName(serviceName.getName());
	availableService.setDuration(serviceDuration);
	
	chosenServiceRepository.save(availableService);
	
	return convertToDTO(availableService);
	
}

@PostMapping(value = { "/delete service/{serviceName}/"})
public void deleteService
(@PathVariable ChosenService serviceName,@PathVariable AssistantDTO assistantDTO, @PathVariable OwnerDTO ownerDTO) 
		throws IllegalArgumentException {
	
	
	ChosenService availableService = chosenServiceRepository.findChosenServiceByName(serviceName);
	Assistant assistant = assistantRepository.findAssistantByUsername(assistantDTO.getUsername()); //returns null if owner in
	Owner owner =  ownerRepository.findOwnerByUsername(ownerDTO.getUsername());  //returns null if assistant in


	if (assistant==null && owner==null)throw new IllegalArgumentException
	("Restricted access: can't delete service");
	if (serviceName!=availableService)throw new IllegalArgumentException
	("Service" + serviceName + "does not exist, kindly add service before deleting it");
	if (serviceName==null)throw new IllegalArgumentException
	("Please specify service name to delete");
	
	chosenServiceRepository.delete(serviceName);
	

}

@PostMapping(value = { "/review service/{serviceName}/"})
public Review reviewService
(@PathVariable String serviceName,@PathVariable AssistantDTO assistantDTO, @PathVariable OwnerDTO ownerDTO) 
		throws IllegalArgumentException {
	
	//if No Show, throw new exception: can't review a service customer didn't show up to.
    //if review provided, during or before service,throw new exception: can't review service that isn't done yet.
	//if rating not between 1 and 5, throw new exception : please provide a rating between 1 and 5 stars.
	
	//do total service rating 
	
	// availableService.getServiceRating();
	// servicePerformed.setServiceRating(providedReview);
	 return null;
			 //providedReview;
	
}


private ChosenServiceDTO convertToDTO(ChosenService availableService) {
	if(availableService == null) throw new IllegalArgumentException("service not found.");
	return new ChosenServiceDTO(availableService.getName());
}

}


	

