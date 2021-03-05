package ca.mcgill.ecse321.autoRepair.dto;

import java.util.Collections;
import java.util.List;

import ca.mcgill.ecse321.autoRepair.model.Reminder;

public class AssistantDto {

	private String username;
	private String password;
	private List<ReminderDto> reminders;

	public AssistantDto() {
	}
	
	@SuppressWarnings("unchecked")
	public AssistantDto(String username) {
		this(username, Collections.EMPTY_LIST);
	}
	
	public AssistantDto(String username,String password) {
		this.username = username;
		this.password= password;
	}
	public AssistantDto(String username,List<ReminderDto> remindersList) {
		this.username = username;
		this.reminders= remindersList;
	}
	
	public AssistantDto(String username,String password,List<ReminderDto> remindersList) {
		this.username = username;
		this.password= password;
		this.reminders= remindersList;
	}
	
	
	
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}

	public List<ReminderDto> getReminders() {
		return reminders;
	}
	public void setReminders(List<ReminderDto> remindersList) {
		this.reminders = remindersList;
	}
	
	}
	