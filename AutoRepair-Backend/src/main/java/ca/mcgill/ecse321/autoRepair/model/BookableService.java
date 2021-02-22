package ca.mcgill.ecse321.autoRepair.model;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "services")
public abstract class BookableService{

	private String name;
	private List<Reminder> reminders;
	private List<Review> reviews;
	private AutoRepairShopSystem autoRepairShopSystem;
	
	public  BookableService(String name, AutoRepairShopSystem autoRepairShopSystem) {
		this.name=name;
		this.autoRepairShopSystem=autoRepairShopSystem;
		this.reviews=new ArrayList<Review>();
		this.reminders= new ArrayList<Reminder>();
	}

	public BookableService(){

	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany
	public List<Reminder> getReminders() {
		return reminders;
	}

	public void setReminders(List<Reminder> reminders) {
		this.reminders = reminders;
	}

	@OneToMany(mappedBy = "bookableService")
	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	@ManyToOne
	public AutoRepairShopSystem getAutoRepairShopSystem() {
		return autoRepairShopSystem;
	}

	public void setAutoRepairShopSystem(ca.mcgill.ecse321.autoRepair.model.AutoRepairShopSystem autoRepairShopSystem) {
		this.autoRepairShopSystem = autoRepairShopSystem;
	}

	@OneToMany(mappedBy = "bookableService")
	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	private List<Appointment> appointments;

}