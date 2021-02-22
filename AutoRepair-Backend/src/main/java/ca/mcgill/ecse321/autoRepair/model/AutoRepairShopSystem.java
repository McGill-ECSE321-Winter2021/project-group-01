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
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private Long id;

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

<<<<<<< Updated upstream
	public boolean hasReminders()
	{
		boolean has = reminders.size() > 0;
		return has;
	}

	public int indexOfReminder(Reminder aReminder)
	{
		int index = reminders.indexOf(aReminder);
		return index;
	}
	/* Code from template association_GetMany */
	public Review getReview(int index)
	{
		Review aReview = reviews.get(index);
		return aReview;
	}

	@OneToMany(cascade={CascadeType.ALL}, mappedBy = "autoRepairShopSystem")
	public List<Review> getReviews()
	{
		List<Review> newReviews = Collections.unmodifiableList(reviews);
		return newReviews;
	}
	
=======
>>>>>>> Stashed changes
	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

<<<<<<< Updated upstream
	public boolean removeBookableService(BookableService aBookableService)
	{
		boolean wasRemoved = false;
		//Unable to remove aBookableService, as it must always have a AutoRepairShopSystem
		if (!this.equals(aBookableService.getAutoRepairShopSystem()))
		{
			bookableServices.remove(aBookableService);
			wasRemoved = true;
		}
		return wasRemoved;
	}
	/* Code from template association_AddIndexControlFunctions */
	public boolean addBookableServiceAt(BookableService aBookableService, int index)
	{  
		boolean wasAdded = false;
		if(addBookableService(aBookableService))
		{
			if(index < 0 ) { index = 0; }
			if(index > numberOfBookableServices()) { index = numberOfBookableServices() - 1; }
			bookableServices.remove(aBookableService);
			bookableServices.add(index, aBookableService);
			wasAdded = true;
		}
		return wasAdded;
	}

	public boolean addOrMoveBookableServiceAt(BookableService aBookableService, int index)
	{
		boolean wasAdded = false;
		if(bookableServices.contains(aBookableService))
		{
			if(index < 0 ) { index = 0; }
			if(index > numberOfBookableServices()) { index = numberOfBookableServices() - 1; }
			bookableServices.remove(aBookableService);
			bookableServices.add(index, aBookableService);
			wasAdded = true;
		} 
		else 
		{
			wasAdded = addBookableServiceAt(aBookableService, index);
		}
		return wasAdded;
	}
	/* Code from template association_MinimumNumberOfMethod */
	public static int minimumNumberOfReminders()
	{
		return 0;
	}
	/* Code from template association_AddManyToOne */
//	public Reminder addReminder(Long aId, String aDescription, Date aDate, Time aTime, Customer aCustomer)
//	{
//		return new Reminder(aId, aDescription, aDate, aTime, this, aCustomer);
//	}

	public boolean addReminder(Reminder aReminder)
	{
		boolean wasAdded = false;
		if (reminders.contains(aReminder)) { return false; }
		AutoRepairShopSystem existingAutoRepairShopSystem = aReminder.getAutoRepairShopSystem();
		boolean isNewAutoRepairShopSystem = existingAutoRepairShopSystem != null && !this.equals(existingAutoRepairShopSystem);
		if (isNewAutoRepairShopSystem)
		{
			aReminder.setAutoRepairShopSystem(this);
		}
		else
		{
			reminders.add(aReminder);
		}
		wasAdded = true;
		return wasAdded;
	}

	public boolean removeReminder(Reminder aReminder)
	{
		boolean wasRemoved = false;
		//Unable to remove aReminder, as it must always have a AutoRepairShopSystem
		if (!this.equals(aReminder.getAutoRepairShopSystem()))
		{
			reminders.remove(aReminder);
			wasRemoved = true;
		}
		return wasRemoved;
	}
	/* Code from template association_AddIndexControlFunctions */
	public boolean addReminderAt(Reminder aReminder, int index)
	{  
		boolean wasAdded = false;
		if(addReminder(aReminder))
		{
			if(index < 0 ) { index = 0; }
			if(index > numberOfReminders()) { index = numberOfReminders() - 1; }
			reminders.remove(aReminder);
			reminders.add(index, aReminder);
			wasAdded = true;
		}
		return wasAdded;
	}

	public boolean addOrMoveReminderAt(Reminder aReminder, int index)
	{
		boolean wasAdded = false;
		if(reminders.contains(aReminder))
		{
			if(index < 0 ) { index = 0; }
			if(index > numberOfReminders()) { index = numberOfReminders() - 1; }
			reminders.remove(aReminder);
			reminders.add(index, aReminder);
			wasAdded = true;
		} 
		else 
		{
			wasAdded = addReminderAt(aReminder, index);
		}
		return wasAdded;
	}
	/* Code from template association_MinimumNumberOfMethod */
	public static int minimumNumberOfReviews()
	{
		return 0;
	}
	/* Code from template association_AddManyToOne */
	public Review addReview(String aId, String aDescription, int aServiceRating, Customer aCustomer, BookableService aBookableService)
	{
		return new Review(aId, aDescription, aServiceRating, this, aCustomer, aBookableService);
	}

	public boolean addReview(Review aReview)
	{
		boolean wasAdded = false;
		if (reviews.contains(aReview)) { return false; }
		AutoRepairShopSystem existingAutoRepairShopSystem = aReview.getAutoRepairShopSystem();
		boolean isNewAutoRepairShopSystem = existingAutoRepairShopSystem != null && !this.equals(existingAutoRepairShopSystem);
		if (isNewAutoRepairShopSystem)
		{
			aReview.setAutoRepairShopSystem(this);
		}
		else
		{
			reviews.add(aReview);
		}
		wasAdded = true;
		return wasAdded;
	}

	public boolean removeReview(Review aReview)
	{
		boolean wasRemoved = false;
		//Unable to remove aReview, as it must always have a AutoRepairShopSystem
		if (!this.equals(aReview.getAutoRepairShopSystem()))
		{
			reviews.remove(aReview);
			wasRemoved = true;
		}
		return wasRemoved;
	}
	/* Code from template association_AddIndexControlFunctions */
	public boolean addReviewAt(Review aReview, int index)
	{  
		boolean wasAdded = false;
		if(addReview(aReview))
		{
			if(index < 0 ) { index = 0; }
			if(index > numberOfReviews()) { index = numberOfReviews() - 1; }
			reviews.remove(aReview);
			reviews.add(index, aReview);
			wasAdded = true;
		}
		return wasAdded;
	}

	public boolean addOrMoveReviewAt(Review aReview, int index)
	{
		boolean wasAdded = false;
		if(reviews.contains(aReview))
		{
			if(index < 0 ) { index = 0; }
			if(index > numberOfReviews()) { index = numberOfReviews() - 1; }
			reviews.remove(aReview);
			reviews.add(index, aReview);
			wasAdded = true;
		} 
		else 
		{
			wasAdded = addReviewAt(aReview, index);
		}
		return wasAdded;
	}

	public void delete()
	{
		AutoRepairShopSystemsById.remove(getId());
		Business existingBusiness = business;
		business = null;
		if (existingBusiness != null)
		{
			existingBusiness.delete();
			existingBusiness.setAutoRepairShopSystem(null);
		}
		Owner existingOwner = owner;
		owner = null;
		if (existingOwner != null)
		{
			existingOwner.delete();
			existingOwner.setAutoRepairShopSystem(null);
		}
		Assistant existingAssistant = assistant;
		assistant = null;
		if (existingAssistant != null)
		{
			existingAssistant.delete();
			existingAssistant.setAutoRepairShopSystem(null);
		}
		while (customers.size() > 0)
		{
			Customer aCustomer = customers.get(customers.size() - 1);
			aCustomer.delete();
			customers.remove(aCustomer);
		}

		while (operatingHours.size() > 0)
		{
			OperatingHour aOperatingHour = operatingHours.get(operatingHours.size() - 1);
			aOperatingHour.delete();
			operatingHours.remove(aOperatingHour);
		}

		while (appointments.size() > 0)
		{
			Appointment aAppointment = appointments.get(appointments.size() - 1);
			aAppointment.delete();
			appointments.remove(aAppointment);
		}

		while (timeSlots.size() > 0)
		{
			TimeSlot aTimeSlot = timeSlots.get(timeSlots.size() - 1);
			aTimeSlot.delete();
			timeSlots.remove(aTimeSlot);
		}

		while (bookableServices.size() > 0)
		{
			BookableService aBookableService = bookableServices.get(bookableServices.size() - 1);
			aBookableService.delete();
			bookableServices.remove(aBookableService);
		}

		while (reminders.size() > 0)
		{
			Reminder aReminder = reminders.get(reminders.size() - 1);
			aReminder.delete();
			reminders.remove(aReminder);
		}

		while (reviews.size() > 0)
		{
			Review aReview = reviews.get(reviews.size() - 1);
			aReview.delete();
			reviews.remove(aReview);
		}

	}
=======
	private Owner owner;
	private Assistant assistant;
	private List<Customer> customers;
	private List<OperatingHour> operatingHours;
	private List<Appointment> appointments;
	private List<TimeSlot> timeSlots;
	private List<BookableService> bookableServices;
	private List<Reminder> reminders;
	private List<Review> reviews;
>>>>>>> Stashed changes


}