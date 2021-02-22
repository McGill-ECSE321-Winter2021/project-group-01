package ca.mcgill.ecse321.autoRepair.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Assistant extends User {
	public Assistant(){
		super();
	}

	@OneToMany
	public List<Reminder> getReminders() {
		return reminders;
	}

	public void setReminders(List<Reminder> reminders) {
		this.reminders = reminders;
	}

	private List<Reminder> reminders;

}