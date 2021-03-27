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
	private CustomerRepository cusRepo;

	@Autowired
	private OwnerRepository ownerRepo;

	@Autowired
	private AssistantRepository assistantRepo;

	@Transactional
	public User login(String username, String password) {

		if(!cusRepo.existsCustomerByUsername(username) && 
				!ownerRepo.existsOwnerByUsername(username) &&
				!assistantRepo.existsAssitantByUsername(username)) {
			throw new IllegalArgumentException("Invalid username");
		}

		Customer customer = cusRepo.findCustomerByUsername(username);
		if (customer!=null && customer.getPassword().equals(password))
			return customer;


		Owner owner = ownerRepo.findOwnerByUsername(username);
		if(owner!=null && owner.getPassword().equals(password))
			return owner;



		Assistant assistant = assistantRepo.findAssistantByUsername(username);
		if (assistant!=null && assistant.getPassword().equals(password))
			return assistant;

		throw new IllegalArgumentException("Incorrect password");

	}


}
