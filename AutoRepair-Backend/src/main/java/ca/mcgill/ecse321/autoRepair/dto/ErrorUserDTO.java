package ca.mcgill.ecse321.autoRepair.dto;

public class ErrorUserDTO extends UserDTO{
	String error;

	public ErrorUserDTO(String username, String password, String userType, String error) {
		super(username, password, userType);
		this.error=error;
	}
	
	public String getError() {
		return error;
	}

}
