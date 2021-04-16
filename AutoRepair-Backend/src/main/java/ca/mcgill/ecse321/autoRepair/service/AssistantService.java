  
package ca.mcgill.ecse321.autoRepair.service;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ca.mcgill.ecse321.autoRepair.model.Assistant;

import ca.mcgill.ecse321.autoRepair.dao.AssistantRepository;

@Service
public class AssistantService {

	@Autowired
	AssistantRepository assistantRepository;

	/**
	 * @author Marc Saber
	 * Creates an Assistant
	 * @param username
	 * @param password
	 * @return assistant
	 */
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

	/**
	 * @author Marc Saber
	 * Updates an assistants passwords
	 * @param oldUsername
	 * @param newPassword
	 * @return updated assistant
	 */
	@Transactional
	public Assistant updateAssistant(String oldUsername,String newPassword) {
		Assistant oldAssistant = assistantRepository.findAssistantByUsername(oldUsername);
		if(oldAssistant.getPassword() != newPassword && passwordIsValid(newPassword)) {
			oldAssistant.setPassword(newPassword);
		}

		assistantRepository.save(oldAssistant);

		return oldAssistant;
	}

	/**
	 * @author Marc Saber
	 * Deletes an assistant given the assistant's username
	 * @param username
	 * @return true when the assistant is successfully deleted
	 */
	@Transactional
	public boolean deleteAssistant(String username) {
		Assistant assistant = assistantRepository.findAssistantByUsername(username);
		if (assistant==null) {
			throw new IllegalArgumentException("assistant with username " + username + " does not exist");
		}
		assistantRepository.delete(assistant);
		return true;

	}

	/**
	 * @author Marc Saber
	 * Gets an assistant given a name
	 * @param name
	 * @return assistant
	 */
	@Transactional
	public Assistant getAssistant(String name) {
		return assistantRepository.findAssistantByUsername(name);
	}

	/**
	 * @author Marc Saber
	 * returns a list of all the assistants 
	 * @return
	 */
	@Transactional
	public List<Assistant> getAllAssistants(){           
		return toList(assistantRepository.findAll());
	}
	private boolean usernameIsValidAssistant(String username) {
		if(assistantRepository.findAssistantByUsername(username)==null) return true;
		else throw new IllegalArgumentException("Username is already taken.");
	}

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

