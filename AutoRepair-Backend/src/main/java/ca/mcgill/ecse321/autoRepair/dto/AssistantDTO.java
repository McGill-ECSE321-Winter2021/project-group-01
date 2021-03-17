package ca.mcgill.ecse321.autoRepair.dto;

public class AssistantDTO {

	private String username;
	private String password;

	public AssistantDTO() {
	}

	
	public AssistantDTO(String username,String password) {
		this.username = username;
		this.password= password;
	}
	public AssistantDTO(String username) {
		this.username = username;
	
	}
	
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}

	}
	

