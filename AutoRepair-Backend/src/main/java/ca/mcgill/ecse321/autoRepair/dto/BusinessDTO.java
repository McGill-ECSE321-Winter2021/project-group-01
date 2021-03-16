package ca.mcgill.ecse321.autoRepair.dto;

import java.util.List;

public class BusinessDTO{

	private String name;
	private String address;
	private String phoneNumber;
	private String email;
	private List<OperatingHourDTO> businessHours;
	private List<TimeSlotDTO> holidays;


	public BusinessDTO(String name, String email, String address, String phoneNumber, List<OperatingHourDTO> businessHours, List<TimeSlotDTO> holidays){
		this.name=name;
		this.email=email;
		this.address=address;
		this.phoneNumber=phoneNumber;
		this.businessHours=businessHours;
		this.holidays=holidays;
	}


	public String getName() {
		return name;
	}


	public String getAddress() {
		return address;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public String getEmail() {
		return email;
	}


	public List<OperatingHourDTO> getBusinessHours() {
		return businessHours;
	}

	public List<TimeSlotDTO> getHolidays() {
		return holidays;
	}

}