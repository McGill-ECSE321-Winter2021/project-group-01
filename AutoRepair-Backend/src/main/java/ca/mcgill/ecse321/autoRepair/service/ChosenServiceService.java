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
		
		serviceNameIsValid(name);
		
		ChosenService chosenService = new ChosenService();
		chosenService.setName(name);
		chosenService.setDuration(duration);
		chosenService.setPayment(price);
		chosenServiceRepository.save(chosenService);

		return chosenService;
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

		ChosenService chosenService = getChosenService(name);
		if (chosenService==null) throw new IllegalArgumentException("Chosen Service invalid");

		chosenService.setDuration(duration);
		chosenService.setPayment(price);
		chosenServiceRepository.save(chosenService);

		return chosenService;
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
		ChosenService chosenService = getChosenService(name);
		if(chosenService!=null) {
		chosenServiceRepository.delete(chosenService);
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

	/**
	 * This method checks whether the name of the service already exists
	 * @param name
	 * @return
	 */
	private boolean serviceNameIsValid(String name) {
		if(chosenServiceRepository.findChosenServiceByName(name)==null) return true;
		else throw new IllegalArgumentException("Name is already taken");
	}

	/**
	 * This method checks if the string input contains character or is only white spaces
	 * @param input
	 * @return
	 */
	private static boolean containsCharacter(String input){
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