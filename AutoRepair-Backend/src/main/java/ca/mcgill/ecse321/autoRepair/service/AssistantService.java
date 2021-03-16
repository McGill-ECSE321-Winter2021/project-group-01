package ca.mcgill.ecse321.autoRepair.service;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ca.mcgill.ecse321.autoRepair.model.Assistant;

import ca.mcgill.ecse321.autoRepair.dao.AssistantRepository;


public class AssistantService {

	@Autowired
	AssistantRepository assistantRepository;

	@Transactional
	public Assistant createAssistant(String username,String password) {

		if(username==null || username=="") throw new IllegalArgumentException("Username cannot be blank");
		if(password==null || password=="") throw new IllegalArgumentException("Password cannot be blank");

		Assistant assistant = new Assistant();
		if (usernameIsValidAssistant(username)) {
			assistant.setUsername(username);
		}
		if (passwordIsValid(password)) {
			assistant.setPassword(password);
		}

		assistantRepository.save(assistant);
		return assistant;
	}

	@Transactional
	public Assistant updateAssistant(String oldUsername,String newUsername,String newPassword) {
		Assistant oldAssistant = assistantRepository.findAssistantByUsername(oldUsername);
		if(oldAssistant.getUsername() != newUsername && usernameIsValidAssistant(newUsername)) {
			oldAssistant.setUsername(newUsername);
		}
		if(oldAssistant.getPassword() != newPassword && passwordIsValid(newPassword)) {
			oldAssistant.setPassword(newPassword);
		}

		assistantRepository.save(oldAssistant);

		return oldAssistant;
	}

	@Transactional
	public boolean deleteAssistant(String username) {
		boolean deleted = false;
		Assistant assistant = assistantRepository.findAssistantByUsername(username);
		if (assistant==null) {
			deleted = false;
			throw new IllegalArgumentException("assistant with username " + username + " does not exist");
		}	
		else {
			assistantRepository.delete(assistant);
			deleted = true;
		}
		return deleted;

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
	private boolean usernameIsValidAssistant(String username) {
		if(assistantRepository.findAssistantByUsername(username)==null) return true;
		else throw new IllegalArgumentException("Username is already taken.");
	}

	@SuppressWarnings("unused")
	private boolean passwordIsValid(String password){
		if (password.length()<8) throw new IllegalArgumentException("Password must have at least 8 characters");
		if(password.length()>20) throw new IllegalArgumentException("Password must not have more than 20 characters");

		boolean upperCaseFlag = false;
		boolean lowerCaseFlag = false;
		boolean numberFlag = false;

		for(int i=0; i<password.length(); i++) {
			if(Character.isUpperCase(password.charAt(i))) upperCaseFlag = true;
			else if(Character.isLowerCase(password.charAt(i))) lowerCaseFlag = true;
			else if(Character.isDigit(password.charAt(i))) numberFlag = true;
		}

		if(upperCaseFlag == false) throw new IllegalArgumentException ("Password must contain at least one uppercase character");
		if(lowerCaseFlag == false) throw new IllegalArgumentException ("Password must contain at least one lowercase character");
		if(numberFlag == false) throw new IllegalArgumentException ("Password must contain at least one numeric character");

		return true;
	}


	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}



}
