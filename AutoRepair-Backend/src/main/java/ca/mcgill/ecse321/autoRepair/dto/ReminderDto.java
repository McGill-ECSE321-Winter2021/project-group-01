package ca.mcgill.ecse321.autoRepair.dto;

import java.sql.Date;
import java.sql.Time;

public class ReminderDTO {
	
	private String description;
	private Date date;
	private Time time;
	
	public ReminderDTO(String description, Date date, Time time) {
		this.description = description;
		this.date = date;
		this.time = time;
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

}
