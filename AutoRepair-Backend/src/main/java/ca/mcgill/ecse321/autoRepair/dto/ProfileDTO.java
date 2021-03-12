package ca.mcgill.ecse321.autoRepair.dto;

public class ProfileDTO {

	private String firstName;
	private String lastName;
	private String address;
	private String zipCode;
	private String phoneNumber;
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
