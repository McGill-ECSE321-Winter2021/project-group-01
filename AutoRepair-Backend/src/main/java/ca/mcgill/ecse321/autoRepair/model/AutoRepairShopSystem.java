package ca.mcgill.ecse321.autoRepair.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="autoRepairShopSystem")
public class AutoRepairShopSystem{

	public AutoRepairShopSystem(){

	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	private long id;

	@OneToOne(mappedBy = "autoRepairShopSystem")
	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	private Business business;

	@OneToOne(mappedBy = "autoRepairShopSystem")
	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	@OneToOne(mappedBy = "autoRepairShopSystem")
	public Assistant getAssistant() {
		return assistant;
	}

	public void setAssistant(Assistant assistant) {
		this.assistant = assistant;
	}

	@OneToMany(mappedBy = "autoRepairShopSystem")
	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	@OneToMany(mappedBy = "autoRepairShopSystem")
	public List<OperatingHour> getOperatingHours() {
		return operatingHours;
	}

	public void setOperatingHours(List<OperatingHour> operatingHours) {
		this.operatingHours = operatingHours;
	}

	@OneToMany(mappedBy = "autoRepairShopSystem")
	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	@OneToMany(mappedBy = "autoRepairShopSystem")
	public List<TimeSlot> getTimeSlots() {
		return timeSlots;
	}

	public void setTimeSlots(List<TimeSlot> timeSlots) {
		this.timeSlots = timeSlots;
	}

	@OneToMany(mappedBy = "autoRepairShopSystem")
	public List<BookableService> getBookableServices() {
		return bookableServices;
	}

	public void setBookableServices(List<BookableService> bookableServices) {
		this.bookableServices = bookableServices;
	}

	@OneToMany(mappedBy = "autoRepairShopSystem")
	public List<Reminder> getReminders() {
		return reminders;
	}

	public void setReminders(List<Reminder> reminders) {
		this.reminders = reminders;
	}

	@OneToMany(mappedBy = "autoRepairShopSystem")
	public List<Review> getReviews() {
		return reviews;
	}
	
	public void setReviews(List<Review> reviews) {
		this.reviews=reviews;
	}


	private Owner owner;
	private Assistant assistant;
	private List<Customer> customers;
	private List<OperatingHour> operatingHours;
	private List<Appointment> appointments;
	private List<TimeSlot> timeSlots;
	private List<BookableService> bookableServices;
	private List<Reminder> reminders;
	private List<Review> reviews;

}