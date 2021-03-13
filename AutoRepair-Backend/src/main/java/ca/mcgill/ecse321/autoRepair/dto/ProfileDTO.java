package ca.mcgill.ecse321.autoRepair.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class ProfileDTO {

	@NotBlank(message = "First name cannot be blank.")
	@NotEmpty(message = "First name cannot be blank.")
	private String firstName;
	
	@NotBlank(message = "Last name cannot be blank.")
	@NotEmpty(message = "Last name cannot be blank.")
	private String lastName;
	
	@NotBlank(message = "Address cannot be blank.")
	@NotEmpty(message = "Address cannot be blank.")
	private String address;
	
	@NotBlank(message = "Zip code cannot be blank.")
	@NotEmpty(message = "Zip code cannot be blank.")
	private String zipCode;
	
	@NotBlank(message = "Phone number cannot be blank.")
	@NotEmpty(message = "Phone number cannot be blank.")
	private String phoneNumber;
	
	@NotBlank(message = "Email cannot be blank.")
	@NotEmpty(message = "Email cannot be blank.")
	@Email(message = "Invalid Email.")
	private String email;


	public ProfileDTO(String aFirstName, String aLastName, String aAddress, String aZipCode, String aPhoneNumber, String aEmail)
	{
		firstName = aFirstName;
		lastName = aLastName;
		address = aAddress;
		zipCode = aZipCode;
		phoneNumber = aPhoneNumber;
		email = aEmail;

	}


	public String getFirstName() {
		return firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public String getAddress() {
		return address;
	}


	public String getZipCode() {
		return zipCode;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public String getEmail() {
		return email;
	}

}
