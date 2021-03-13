package ca.mcgill.ecse321.autoRepair.dto;

import java.util.Collections;
import java.util.List;
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.NotEmpty;

import ca.mcgill.ecse321.autoRepair.model.Reminder;

public class AssistantDTO {

//	@NotBlank(message = "username cannot be blank.")
//	@NotEmpty(message = "username cannot be blank.")
	private String username;
//	@NotBlank(message = "Password cannot be blank.")
//	@NotEmpty(message = "Password cannot be blank.")
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
	