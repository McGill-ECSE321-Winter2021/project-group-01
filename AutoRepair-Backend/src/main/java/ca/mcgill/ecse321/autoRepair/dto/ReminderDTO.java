package ca.mcgill.ecse321.autoRepair.dto;

import java.sql.Date;
import java.sql.Time;

public class ReminderDTO {
	
	private String description;
	private Date date;
	private Time time;
	private CustomerDTO customer;
	private AppointmentDTO appointment;
	
	public ReminderDTO(String description, Date date, Time time, CustomerDTO customer,
			AppointmentDTO appointment) {
		this.description = description;
		this.date = date;
		this.time = time;
		this.appointment = appointment;
		this.customer = customer;
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

	public AppointmentDTO getAppointment() {
		return appointment;
	}

}