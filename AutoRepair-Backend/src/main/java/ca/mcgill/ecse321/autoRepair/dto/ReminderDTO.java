package ca.mcgill.ecse321.autoRepair.dto;

import java.sql.Date;
import java.sql.Time;

public class ReminderDTO {

	private String description;
	private Date date;
	private Time time;
	private CustomerDTO customer;
	private ChosenServiceDTO service;

	public ReminderDTO(CustomerDTO customer, ChosenServiceDTO service, Date date, Time time, String description) {
		this.description = description;
		this.date = date;
		this.time = time;
		this.customer = customer;
		this.service = service;
	}

	public String getDescription() {
		return description;
	}

	public Date getDate() {
		return date;
	}

	public Time getTime() {
		return time;
	}

	public CustomerDTO getCustomer() {
		return customer;
	}

	public ChosenServiceDTO getChosenService() {
		return service;
	}

}