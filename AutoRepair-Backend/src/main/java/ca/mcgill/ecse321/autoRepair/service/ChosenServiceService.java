package ca.mcgill.ecse321.autoRepair.service;

import ca.mcgill.ecse321.autoRepair.dao.ChosenServiceRepository;
import ca.mcgill.ecse321.autoRepair.model.ChosenService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChosenServiceService {

	@Autowired
	ChosenServiceRepository chosenServiceRepository;
	
	/**
	 * @author Robert Aprahamian
	 * Creates a chosen service
	 * @param name
	 * @param duration
	 * @param price
	 * @return cService
	 */
	@Transactional
	public ChosenService createChosenService(String name, int duration, Double price) { //Add service
		
		if(name == null || name.equals("") || containsCharacter(name)==false) {
			throw new IllegalArgumentException("Invalid name");
		}
		
		if(duration == 0) {
			throw new IllegalArgumentException("Invalid duration");
		}
		
		if(price == null) {
			throw new IllegalArgumentException("Invalid Price");
		}
		
		usernameIsValid(name);
		
		ChosenService cService = new ChosenService();
		cService.setName(name);
		cService.setDuration(duration);
		cService.setPayment(price);
		chosenServiceRepository.save(cService);
		return cService;
	}
	
	/**
	 * @author Robert Aprahamian
	 * Edits a chosen service
	 * @param name
	 * @param duration
	 * @param price
	 * @return cService
	 */
	@Transactional
	public ChosenService editChosenService(String name, int duration,Double price) { 
		
		if(name == null || name.equals("") || containsCharacter(name)==false) {
			throw new IllegalArgumentException("Invalid name");
		}
		if(duration == 0) { 
			throw new IllegalArgumentException("Invalid duration");
		}
		if(price == null) {
			throw new IllegalArgumentException("Invalid Price");
		}
		ChosenService cService = getChosenService(name);
		if (cService==null) 
			throw new IllegalArgumentException("Chosen Service invalid");
		cService.setDuration(duration);
		cService.setPayment(price);
		chosenServiceRepository.save(cService);
		return cService;
	}
	
	/**
	 * @author Robert Aprahamian
	 * Deletes a chosen service given a service name
	 * @param name
	 * @return true
	 */
	@Transactional
	public boolean deleteChosenService(String name) {
		
		if(name == null || name.equals("") || containsCharacter(name)==false) {
			throw new IllegalArgumentException("Invalid name");
		}
		ChosenService cs = getChosenService(name);
		if(cs!=null) {
		chosenServiceRepository.delete(cs);
		return true;
		}
		else throw new IllegalArgumentException("Chosen Service invalid");
	}
	
	/**
	 * @author Robert Aprahamian
	 * Returns a chosen service given a service name
	 * @param name
	 * @return chosenService
	 */
	@Transactional
	public ChosenService getChosenService(String name) {
		return chosenServiceRepository.findChosenServiceByName(name);
	}
	
	/**
	 * @author Robert Aprahamian
	 * Returns a list of all the chosen services
	 * @return list of all chosen services
	 */
	@Transactional
	public List<ChosenService> getAllChosenService(){
		return toList(chosenServiceRepository.findAll());
	}

	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
	
	private boolean usernameIsValid(String name) {
		if(chosenServiceRepository.findChosenServiceByName(name)==null) return true;
		else throw new IllegalArgumentException("Name is already taken");
	}
	
	public static boolean containsCharacter(String input){
	    if(input != null){
	        for(int i = 0; i < input.length(); i++){
	            if(!(Character.isWhitespace(input.charAt(i)))){
	                return true;
	            }
	        }
	    }
	    return false;
	}
	
}