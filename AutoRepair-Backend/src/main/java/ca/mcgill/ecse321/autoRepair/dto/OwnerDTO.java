package ca.mcgill.ecse321.autoRepair.dto;

public class OwnerDTO {

	private String username;
	private String password;

	public OwnerDTO() {
	}
	
	public OwnerDTO(String username) {
		this.username = username;
	}
	
	public OwnerDTO(String username,String password) {
		this.username = username;
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}

	
	
}
