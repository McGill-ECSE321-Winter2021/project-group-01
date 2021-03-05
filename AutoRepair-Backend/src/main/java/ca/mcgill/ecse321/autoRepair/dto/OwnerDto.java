package ca.mcgill.ecse321.autoRepair.dto;

public class OwnerDto {

	private String username;
	private String password;

	public OwnerDto() {
	}
	
	
	public OwnerDto(String username,String password) {
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
