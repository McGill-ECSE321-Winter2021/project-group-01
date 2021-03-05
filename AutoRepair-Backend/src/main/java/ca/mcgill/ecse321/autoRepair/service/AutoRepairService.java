package ca.mcgill.ecse321.autoRepair.service;

import ca.mcgill.ecse321.autoRepair.dao.OwnerRepository;
import ca.mcgill.ecse321.autoRepair.dao.AssistantRepository;


import ca.mcgill.ecse321.autoRepair.model.Owner;
import ca.mcgill.ecse321.autoRepair.model.Reminder;
import ca.mcgill.ecse321.autoRepair.model.Assistant;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutoRepairService {
	
	@Autowired
	OwnerRepository ownerRepository;
	@Autowired
	AssistantRepository assistantRepository;
	
	
	@Transactional
	public Owner createOwner(String username,String password) {
		Owner owner = new Owner();
		owner.setUsername(username);
		owner.setPassword(password);
		
		ownerRepository.save(owner);
		return owner;
		
	}

	@Transactional
	public Owner getOwner(String name) {
		Owner owner = ownerRepository.findOwnerByUsername(name);
		return owner;
	}
	
	@Transactional
	public List<Owner> getAllOwners(){             //only one owner, no need for this.
		return toList(ownerRepository.findAll());
	}
	
		
	@Transactional
	public Assistant createAssistant(String username,String password) {
		Assistant assistant = new Assistant();
		assistant.setUsername(username);
		assistant.setPassword(password);
		assistant.setReminders(new ArrayList<Reminder>());
			
		assistantRepository.save(assistant);
		return assistant;
	}
	
	@Transactional
	public Assistant getAssistant(String name) {
		Assistant assistant = assistantRepository.findAssistantByUsername(name);
		return assistant;
	}
	@Transactional
	public List<Assistant> getAllAssistants(){           
		return toList(assistantRepository.findAll());
	}
	

	private <T> List<T> toList(Iterable<T> iterable){   //from tut notes
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
}
}
