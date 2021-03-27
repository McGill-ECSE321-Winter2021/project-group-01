package ca.mcgill.ecse321.autoRepair.dto;

public class ReviewDTO {
	
	private String description;
	private int serviceRating;
	private CustomerDTO customer;
	private AppointmentDTO appointment;
	private ChosenServiceDTO service;
	
	public ReviewDTO(String description, int serviceRating, CustomerDTO customer, 
			AppointmentDTO appointment, ChosenServiceDTO service) {
		this.description = description;
		this.serviceRating = serviceRating;
		this.appointment = appointment;
		this.customer = customer;
		this.service = service;
	}
	
	public String getDescription() {
		return description;
	}
	
	public int getServiceRating() {
		return serviceRating;
	}

	public CustomerDTO getCustomer() {
		return customer;
	}

	public AppointmentDTO getAppointment() {
		return appointment;
	}

	public ChosenServiceDTO getService() {
		return service;
	}

}