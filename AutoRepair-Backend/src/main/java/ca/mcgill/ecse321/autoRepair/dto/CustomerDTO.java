package ca.mcgill.ecse321.autoRepair.dto;

import java.util.List;


public class CustomerDTO extends UserDTO{


	private int noShow;
	private int show;
	private List<CarDTO> cars;
	private ProfileDTO profile;



	public CustomerDTO(String username, String password, int noShow, int show, List<CarDTO> cars, ProfileDTO profile) {
		
		super(username, password, "customer");
		this.noShow=noShow;
		this.show = show;
		this.cars=cars;
		this.profile=profile;
	
	}


	public int getNoShow() {
		return noShow;
	}

	public int getShow() {
		return show;
	}

	public List<CarDTO> getCars() {
		return cars;
	}
	
	public ProfileDTO getProfile() {
		return profile;
	}

}