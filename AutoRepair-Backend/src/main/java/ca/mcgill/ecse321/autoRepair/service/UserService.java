package ca.mcgill.ecse321.autoRepair.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.autoRepair.dao.AssistantRepository;
import ca.mcgill.ecse321.autoRepair.dao.CustomerRepository;
import ca.mcgill.ecse321.autoRepair.dao.OwnerRepository;
import ca.mcgill.ecse321.autoRepair.model.Assistant;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.Owner;
import ca.mcgill.ecse321.autoRepair.model.User;

@Service
public class UserService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private OwnerRepository ownerRepository;

	@Autowired
	private AssistantRepository assistantRepository;

	/**
	 * This method is for logging in
	 * @author Eric Chehata
	 * @param username
	 * @param password
	 * @return
	 */
	@Transactional
	public User login(String username, String password) {

		if(!customerRepository.existsCustomerByUsername(username) &&
				!ownerRepository.existsOwnerByUsername(username) &&
				!assistantRepository.existsAssitantByUsername(username)) {
			throw new IllegalArgumentException("Invalid username");
		}

		Customer customer = customerRepository.findCustomerByUsername(username);
		if (customer!=null && customer.getPassword().equals(password))
			return customer;


		Owner owner = ownerRepository.findOwnerByUsername(username);
		if(owner!=null && owner.getPassword().equals(password))
			return owner;



		Assistant assistant = assistantRepository.findAssistantByUsername(username);
		if (assistant!=null && assistant.getPassword().equals(password))
			return assistant;

		throw new IllegalArgumentException("Incorrect password");

	}


}
