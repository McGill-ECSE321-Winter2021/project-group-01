package ca.mcgill.ecse321.autoRepair.service;

import ca.mcgill.ecse321.autoRepair.dao.OwnerRepository;
import ca.mcgill.ecse321.autoRepair.dao.AssistantRepository;


import ca.mcgill.ecse321.autoRepair.model.Owner;
import ca.mcgill.ecse321.autoRepair.model.Profile;
import ca.mcgill.ecse321.autoRepair.model.Assistant;
import ca.mcgill.ecse321.autoRepair.model.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
	
	@Autowired
	OwnerRepository ownerRepository;
	@Autowired
	AssistantRepository assistantRepository;
	
	
	@Transactional
	public Owner createOwner(String username,String password) {
		
		if(username==null || username=="") throw new IllegalArgumentException("Username cannot be blank");
		if(password==null || password=="") throw new IllegalArgumentException("Password cannot be blank");
		
		Owner owner = new Owner();
		owner.setUsername(username);        //only one owner
		
		if (passwordIsValid(password)) {
			owner.setPassword(password);
		}
		else throw new IllegalArgumentException("Invalid Password. Password must have at least\r\n"
				+ " one numeric character\r\n" + 
				"one lowercase character\r\n" + 
				"one uppercase character\r\n" + 
				"And password length should be between 8 and 20");
				
		ownerRepository.save(owner);
		return owner;
	}

	@Transactional
	public Owner getOwner(String name) {
		Owner owner = ownerRepository.findOwnerByUsername(name);
		return owner;
	}
	
	@Transactional
	public List<Owner> getAllOwners(){             
		return toList(ownerRepository.findAll());
	}
	
		
	@Transactional
	public Assistant createAssistant(String username,String password) {
		
		Assistant assistant = new Assistant();
		if (usernameIsValid(username)) {
			assistant.setUsername(username);
		}
		else throw new IllegalArgumentException("Username already taken");
		if (passwordIsValid(password)) {
			assistant.setPassword(password);
		}			
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
	

	private <T> List<T> toList(Iterable<T> iterable){ 
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
}
	
	//---------------------------rico helpers for username password validity-------------------------------
	
	
	private boolean usernameIsValid(String username) {    
		for(Assistant a: getAllAssistants()) {
			if(a.getUsername()==username) return false;
		}
		return true;
	}
	
	private boolean passwordIsValid(String password){    
		String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
	
	
}