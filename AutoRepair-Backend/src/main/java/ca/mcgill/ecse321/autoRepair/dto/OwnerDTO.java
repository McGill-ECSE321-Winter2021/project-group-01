package ca.mcgill.ecse321.autoRepair.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class OwnerDTO {
	@NotBlank(message = "Username cannot be blank.")
	@NotEmpty(message = "Username cannot be blank.")
	private String username;
	@NotBlank(message = "Password cannot be blank.")
	@NotEmpty(message = "Password cannot be blank.")
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
