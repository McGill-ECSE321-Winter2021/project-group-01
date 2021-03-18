package ca.mcgill.ecse321.autoRepair.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import ca.mcgill.ecse321.autoRepair.model.Owner;
import ca.mcgill.ecse321.autoRepair.dao.OwnerRepository;


@Service
public class OwnerService {

	@Autowired
	OwnerRepository ownerRepository;
	
	/**
	 * @author Marc Saber
	 * Creates an owner
	 * @param username
	 * @param password
	 * @param authentificationCode
	 * @return owner
	 */
	@Transactional
	public Owner createOwner(String username,String password,String authentificationCode) {
		
		if(username=="") throw new IllegalArgumentException("Username cannot be blank");
		if(password=="") throw new IllegalArgumentException("Password cannot be blank");
		if(!authentificationCode.equals("1234")) throw new IllegalArgumentException
		("wrong authentification code,please try again.");

		
		Owner owner = new Owner();
		owner.setUsername(username);  
		
		if (passwordIsValid(password)) {
			owner.setPassword(password);
		}
	 	ownerRepository.save(owner);
		return owner;
	}
	
	/**
	 * @author Marc Saber
	 * Updates an owner's password
	 * @param oldUsername
	 * @param newPassword
	 * @return owner
	 */
	@Transactional
	public Owner updateOwner(String oldUsername,String newPassword) {
		Owner oldOwner = ownerRepository.findOwnerByUsername(oldUsername);
//	 	if(oldOwner.getUsername() != newUsername && newUsername!="") {
//			oldOwner.setUsername(newUsername);
//		}
		if(oldOwner.getPassword() != newPassword && newPassword!="") {
			if (passwordIsValid(newPassword)) {
				oldOwner.setPassword(newPassword);		
				}
		ownerRepository.save(oldOwner);

		}
		return oldOwner;	
	}
	
	/**
	 * @author Marc Saber
	 * Gets an owner given a valid username
	 * @param name
	 * @return owner
	 */
	@Transactional
	public Owner getOwner(String name) {
		Owner owner = ownerRepository.findOwnerByUsername(name);
		return owner;
	}
	
	/**
	 * @author Marc Saber
	 * Returns a list of all the owners
	 * @return list of all owners
	 */
	@Transactional
	public List<Owner> getAllOwners(){             
		return toList(ownerRepository.findAll());
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
