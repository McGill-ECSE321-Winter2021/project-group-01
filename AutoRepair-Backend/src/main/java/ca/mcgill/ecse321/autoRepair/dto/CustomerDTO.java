package ca.mcgill.ecse321.autoRepair.dto;

import java.util.List;


public class CustomerDTO extends UserDTO{


	private List<CarDTO> cars;
	private ProfileDTO profile;



	public CustomerDTO(String username, String password, List<CarDTO> cars, ProfileDTO profile) {
		
		super(username, password, "customer");
		this.cars=cars;
		this.profile=profile;
	
	}



	public List<CarDTO> getCars() {
		return cars;
	}
	
	public ProfileDTO getProfile() {
		return profile;
	}

}