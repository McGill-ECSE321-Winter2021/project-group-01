
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.autoRepair.model;
import javax.persistence.*;
import java.util.*;
import java.sql.Time;
import java.sql.Date;

// line 3 "../../../../../AutoRepair.ump"
// line 152 "../../../../../AutoRepair.ump"
@Entity
@Table(name="autoRepairShopSystem")
public class AutoRepairShopSystem
{

	//------------------------
	// STATIC VARIABLES
	//------------------------

	private static Map<String, AutoRepairShopSystem> AutoRepairShopSystemsById = new HashMap<String, AutoRepairShopSystem>();

	//------------------------
	// MEMBER VARIABLES
	//------------------------

	//AutoRepairShopSystem Attributes
	private long id;

	//AutoRepairShopSystem Associations
	private Business business;
	private Owner owner;
	private Assistant assistant;
	private List<Customer> customers;
	private List<OperatingHour> operatingHours;
	private List<Appointment> appointments;
	private List<TimeSlot> timeSlots;
	private List<BookableService> bookableServices;
	private List<Reminder> reminders;
	private List<Review> reviews;

	//------------------------
	// CONSTRUCTOR
	//------------------------

//	public AutoRepairShopSystem(long aId)
//	{
////		if (!setId(aId))
////		{
////			throw new RuntimeException("Cannot create due to duplicate id. See http://manual.umple.org?RE003ViolationofUniqueness.html");
////		}
//		customers = new ArrayList<Customer>();
//		operatingHours = new ArrayList<OperatingHour>();
//		appointments = new ArrayList<Appointment>();
//		timeSlots = new ArrayList<TimeSlot>();
//		bookableServices = new ArrayList<BookableService>();
//		reminders = new ArrayList<Reminder>();
//		reviews = new ArrayList<Review>();
//	}
	
	public AutoRepairShopSystem() {
		customers = new ArrayList<Customer>();
		operatingHours = new ArrayList<OperatingHour>();
		appointments = new ArrayList<Appointment>();
		timeSlots = new ArrayList<TimeSlot>();
		bookableServices = new ArrayList<BookableService>();
		reminders = new ArrayList<Reminder>();
		reviews = new ArrayList<Review>();
	}

	//------------------------
	// INTERFACE
	//------------------------

//	public boolean setId(long aId)
//	{
//		boolean wasSet = false;
//		long anOldId = getId();
//		if (anOldId != null && anOldId.equals(aId)) {
//			return true;
//		}
//		if (hasWithId(aId)) {
//			return wasSet;
//		}
//		id = aId;
//		wasSet = true;
//		if (anOldId != null) {
//			AutoRepairShopSystemsById.remove(anOldId);
//		}
//		AutoRepairShopSystemsById.put(aId, this);
//		return wasSet;
//	}
	
	public void setId(long id) {
		this.id=id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public long getId()
	{
		return id;
	}
	/* Code from template attribute_GetUnique */
	public static AutoRepairShopSystem getWithId(long aId)
	{
		return AutoRepairShopSystemsById.get(aId);
	}
	/* Code from template attribute_HasUnique */
	public static boolean hasWithId(long aId)
	{
		return getWithId(aId) != null;
	}
	/* Code from template association_GetOne */
	@OneToOne(mappedBy = "autoRepairShopSystem")
	public Business getBusiness()
	{
		return business;
	}

	public boolean hasBusiness()
	{
		boolean has = business != null;
		return has;
	}
	/* Code from template association_GetOne */
	@OneToOne(mappedBy = "autoRepairShopSystem")
	public Owner getOwner()
	{
		return owner;
	}

	public boolean hasOwner()
	{
		boolean has = owner != null;
		return has;
	}
	/* Code from template association_GetOne */
	@OneToOne(mappedBy = "autoRepairShopSystem")
	public Assistant getAssistant()
	{
		return assistant;
	}

	public boolean hasAssistant()
	{
		boolean has = assistant != null;
		return has;
	}
	/* Code from template association_GetMany */
	public Customer getCustomer(int index)
	{
		Customer aCustomer = customers.get(index);
		return aCustomer;
	}

	@OneToMany(cascade={CascadeType.ALL}, mappedBy = "autoRepairShopSystem")
	public List<Customer> getCustomers()
	{
		List<Customer> newCustomers = Collections.unmodifiableList(customers);
		return newCustomers;
	}
	
	public void setCustomers(List<Customer> customers) {
		this.customers=customers;
	}

	public int numberOfCustomers()
	{
		int number = customers.size();
		return number;
	}

	public boolean hasCustomers()
	{
		boolean has = customers.size() > 0;
		return has;
	}

	public int indexOfCustomer(Customer aCustomer)
	{
		int index = customers.indexOf(aCustomer);
		return index;
	}
	/* Code from template association_GetMany */
	public OperatingHour getOperatingHour(int index)
	{
		OperatingHour aOperatingHour = operatingHours.get(index);
		return aOperatingHour;
	}

	@OneToMany(cascade={CascadeType.ALL}, mappedBy = "autoRepairShopSystem")
	public List<OperatingHour> getOperatingHours()
	{
		List<OperatingHour> newOperatingHours = Collections.unmodifiableList(operatingHours);
		return newOperatingHours;
	}
	
	public void setOperatingHours(List<OperatingHour> hours) {
		this.operatingHours=hours;
	}

	public int numberOfOperatingHours()
	{
		int number = operatingHours.size();
		return number;
	}

	public boolean hasOperatingHours()
	{
		boolean has = operatingHours.size() > 0;
		return has;
	}

	public int indexOfOperatingHour(OperatingHour aOperatingHour)
	{
		int index = operatingHours.indexOf(aOperatingHour);
		return index;
	}
	/* Code from template association_GetMany */
	public Appointment getAppointment(int index)
	{
		Appointment aAppointment = appointments.get(index);
		return aAppointment;
	}

	@OneToMany(cascade={CascadeType.ALL}, mappedBy = "autoRepairShopSystem")
	public List<Appointment> getAppointments()
	{
		List<Appointment> newAppointments = Collections.unmodifiableList(appointments);
		return newAppointments;
	}
	
	public void setAppointments(List<Appointment> appointments) {
		this.appointments=appointments;
	}

	public int numberOfAppointments()
	{
		int number = appointments.size();
		return number;
	}

	public boolean hasAppointments()
	{
		boolean has = appointments.size() > 0;
		return has;
	}

	public int indexOfAppointment(Appointment aAppointment)
	{
		int index = appointments.indexOf(aAppointment);
		return index;
	}
	/* Code from template association_GetMany */
	public TimeSlot getTimeSlot(int index)
	{
		TimeSlot aTimeSlot = timeSlots.get(index);
		return aTimeSlot;
	}

	@OneToMany(cascade={CascadeType.ALL}, mappedBy = "autoRepairShopSystem")
	public List<TimeSlot> getTimeSlots()
	{
		List<TimeSlot> newTimeSlots = Collections.unmodifiableList(timeSlots);
		return newTimeSlots;
	}

	public void setTimeSlots(List<TimeSlot> timeSlots) {
		this.timeSlots=timeSlots;
	}
	
	public int numberOfTimeSlots()
	{
		int number = timeSlots.size();
		return number;
	}

	public boolean hasTimeSlots()
	{
		boolean has = timeSlots.size() > 0;
		return has;
	}

	public int indexOfTimeSlot(TimeSlot aTimeSlot)
	{
		int index = timeSlots.indexOf(aTimeSlot);
		return index;
	}
	/* Code from template association_GetMany */
	public BookableService getBookableService(int index)
	{
		BookableService aBookableService = bookableServices.get(index);
		return aBookableService;
	}

	@OneToMany(cascade={CascadeType.ALL}, mappedBy = "autoRepairShopSystem")
	public List<BookableService> getBookableServices()
	{
		List<BookableService> newBookableServices = Collections.unmodifiableList(bookableServices);
		return newBookableServices;
	}

	public void setBookableServices(List<BookableService> services) {
		this.bookableServices=services;
	}
	
	public int numberOfBookableServices()
	{
		int number = bookableServices.size();
		return number;
	}

	public boolean hasBookableServices()
	{
		boolean has = bookableServices.size() > 0;
		return has;
	}

	public int indexOfBookableService(BookableService aBookableService)
	{
		int index = bookableServices.indexOf(aBookableService);
		return index;
	}
	/* Code from template association_GetMany */
	public Reminder getReminder(int index)
	{
		Reminder aReminder = reminders.get(index);
		return aReminder;
	}

	@OneToMany(cascade= {CascadeType.ALL}, mappedBy = "autoRepairShopSystem")
	public List<Reminder> getReminders()
	{
		List<Reminder> newReminders = Collections.unmodifiableList(reminders);
		return newReminders;
	}
	
	public void setReminders(List<Reminder> reminders) {
		this.reminders=reminders;
	}

	public int numberOfReminders()
	{
		int number = reminders.size();
		return number;
	}

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

	@OneToMany(cascade=(CascadeType.ALL), mappedBy = "autoRepairShopSystem")
	public List<Review> getReviews()
	{
		List<Review> newReviews = Collections.unmodifiableList(reviews);
		return newReviews;
	}
	
	public void setReviews(List<Review> reviews) {
		this.reviews=reviews;
	}

	public int numberOfReviews()
	{
		int number = reviews.size();
		return number;
	}

	public boolean hasReviews()
	{
		boolean has = reviews.size() > 0;
		return has;
	}

	public int indexOfReview(Review aReview)
	{
		int index = reviews.indexOf(aReview);
		return index;
	}
	/* Code from template association_SetOptionalOneToOne */
	public boolean setBusiness(Business aNewBusiness)
	{
		boolean wasSet = false;
		if (business != null && !business.equals(aNewBusiness) && equals(business.getAutoRepairShopSystem()))
		{
			//Unable to setBusiness, as existing business would become an orphan
			return wasSet;
		}

		business = aNewBusiness;
		AutoRepairShopSystem anOldAutoRepairShopSystem = aNewBusiness != null ? aNewBusiness.getAutoRepairShopSystem() : null;

		if (!this.equals(anOldAutoRepairShopSystem))
		{
			if (anOldAutoRepairShopSystem != null)
			{
				anOldAutoRepairShopSystem.business = null;
			}
			if (business != null)
			{
				business.setAutoRepairShopSystem(this);
			}
		}
		wasSet = true;
		return wasSet;
	}
	/* Code from template association_SetOptionalOneToOne */
	public boolean setOwner(Owner aNewOwner)
	{
		boolean wasSet = false;
		if (owner != null && !owner.equals(aNewOwner) && equals(owner.getAutoRepairShopSystem()))
		{
			//Unable to setOwner, as existing owner would become an orphan
			return wasSet;
		}

		owner = aNewOwner;
		AutoRepairShopSystem anOldAutoRepairShopSystem = aNewOwner != null ? aNewOwner.getAutoRepairShopSystem() : null;

		if (!this.equals(anOldAutoRepairShopSystem))
		{
			if (anOldAutoRepairShopSystem != null)
			{
				anOldAutoRepairShopSystem.owner = null;
			}
			if (owner != null)
			{
				owner.setAutoRepairShopSystem(this);
			}
		}
		wasSet = true;
		return wasSet;
	}
	/* Code from template association_SetOptionalOneToOne */
	public boolean setAssistant(Assistant aNewAssistant)
	{
		boolean wasSet = false;
		if (assistant != null && !assistant.equals(aNewAssistant) && equals(assistant.getAutoRepairShopSystem()))
		{
			//Unable to setAssistant, as existing assistant would become an orphan
			return wasSet;
		}

		assistant = aNewAssistant;
		AutoRepairShopSystem anOldAutoRepairShopSystem = aNewAssistant != null ? aNewAssistant.getAutoRepairShopSystem() : null;

		if (!this.equals(anOldAutoRepairShopSystem))
		{
			if (anOldAutoRepairShopSystem != null)
			{
				anOldAutoRepairShopSystem.assistant = null;
			}
			if (assistant != null)
			{
				assistant.setAutoRepairShopSystem(this);
			}
		}
		wasSet = true;
		return wasSet;
	}
	/* Code from template association_MinimumNumberOfMethod */
	public static int minimumNumberOfCustomers()
	{
		return 0;
	}
	/* Code from template association_AddManyToOne */
	public Customer addCustomer(String aUsername, String aPassword, int aNoShow, int aShow, Profile aProfile)
	{
		return new Customer(aUsername, aPassword, aNoShow, aShow, aProfile, this);
	}

	public boolean addCustomer(Customer aCustomer)
	{
		boolean wasAdded = false;
		if (customers.contains(aCustomer)) { return false; }
		AutoRepairShopSystem existingAutoRepairShopSystem = aCustomer.getAutoRepairShopSystem();
		boolean isNewAutoRepairShopSystem = existingAutoRepairShopSystem != null && !this.equals(existingAutoRepairShopSystem);
		if (isNewAutoRepairShopSystem)
		{
			aCustomer.setAutoRepairShopSystem(this);
		}
		else
		{
			customers.add(aCustomer);
		}
		wasAdded = true;
		return wasAdded;
	}

	public boolean removeCustomer(Customer aCustomer)
	{
		boolean wasRemoved = false;
		//Unable to remove aCustomer, as it must always have a AutoRepairShopSystem
		if (!this.equals(aCustomer.getAutoRepairShopSystem()))
		{
			customers.remove(aCustomer);
			wasRemoved = true;
		}
		return wasRemoved;
	}
	/* Code from template association_AddIndexControlFunctions */
	public boolean addCustomerAt(Customer aCustomer, int index)
	{  
		boolean wasAdded = false;
		if(addCustomer(aCustomer))
		{
			if(index < 0 ) { index = 0; }
			if(index > numberOfCustomers()) { index = numberOfCustomers() - 1; }
			customers.remove(aCustomer);
			customers.add(index, aCustomer);
			wasAdded = true;
		}
		return wasAdded;
	}

	public boolean addOrMoveCustomerAt(Customer aCustomer, int index)
	{
		boolean wasAdded = false;
		if(customers.contains(aCustomer))
		{
			if(index < 0 ) { index = 0; }
			if(index > numberOfCustomers()) { index = numberOfCustomers() - 1; }
			customers.remove(aCustomer);
			customers.add(index, aCustomer);
			wasAdded = true;
		} 
		else 
		{
			wasAdded = addCustomerAt(aCustomer, index);
		}
		return wasAdded;
	}
	/* Code from template association_MinimumNumberOfMethod */
	public static int minimumNumberOfOperatingHours()
	{
		return 0;
	}
	/* Code from template association_AddManyToOne */
	public OperatingHour addOperatingHour(String aId, OperatingHour.DayOfWeek aDayOfWeek, Time aStartTime, Time aEndTime)
	{
		return new OperatingHour(aId, aDayOfWeek, aStartTime, aEndTime, this);
	}

	public boolean addOperatingHour(OperatingHour aOperatingHour)
	{
		boolean wasAdded = false;
		if (operatingHours.contains(aOperatingHour)) { return false; }
		AutoRepairShopSystem existingAutoRepairShopSystem = aOperatingHour.getAutoRepairShopSystem();
		boolean isNewAutoRepairShopSystem = existingAutoRepairShopSystem != null && !this.equals(existingAutoRepairShopSystem);
		if (isNewAutoRepairShopSystem)
		{
			aOperatingHour.setAutoRepairShopSystem(this);
		}
		else
		{
			operatingHours.add(aOperatingHour);
		}
		wasAdded = true;
		return wasAdded;
	}

	public boolean removeOperatingHour(OperatingHour aOperatingHour)
	{
		boolean wasRemoved = false;
		//Unable to remove aOperatingHour, as it must always have a AutoRepairShopSystem
		if (!this.equals(aOperatingHour.getAutoRepairShopSystem()))
		{
			operatingHours.remove(aOperatingHour);
			wasRemoved = true;
		}
		return wasRemoved;
	}
	/* Code from template association_AddIndexControlFunctions */
	public boolean addOperatingHourAt(OperatingHour aOperatingHour, int index)
	{  
		boolean wasAdded = false;
		if(addOperatingHour(aOperatingHour))
		{
			if(index < 0 ) { index = 0; }
			if(index > numberOfOperatingHours()) { index = numberOfOperatingHours() - 1; }
			operatingHours.remove(aOperatingHour);
			operatingHours.add(index, aOperatingHour);
			wasAdded = true;
		}
		return wasAdded;
	}

	public boolean addOrMoveOperatingHourAt(OperatingHour aOperatingHour, int index)
	{
		boolean wasAdded = false;
		if(operatingHours.contains(aOperatingHour))
		{
			if(index < 0 ) { index = 0; }
			if(index > numberOfOperatingHours()) { index = numberOfOperatingHours() - 1; }
			operatingHours.remove(aOperatingHour);
			operatingHours.add(index, aOperatingHour);
			wasAdded = true;
		} 
		else 
		{
			wasAdded = addOperatingHourAt(aOperatingHour, index);
		}
		return wasAdded;
	}
	/* Code from template association_MinimumNumberOfMethod */
	public static int minimumNumberOfAppointments()
	{
		return 0;
	}
	/* Code from template association_AddManyToOne */
	public Appointment addAppointment(String aId, Customer aCustomer, BookableService aBookableService, TimeSlot aTimeSlot)
	{
		return new Appointment(aId, aCustomer, aBookableService, aTimeSlot, this);
	}

	public boolean addAppointment(Appointment aAppointment)
	{
		boolean wasAdded = false;
		if (appointments.contains(aAppointment)) { return false; }
		AutoRepairShopSystem existingAutoRepairShopSystem = aAppointment.getAutoRepairShopSystem();
		boolean isNewAutoRepairShopSystem = existingAutoRepairShopSystem != null && !this.equals(existingAutoRepairShopSystem);
		if (isNewAutoRepairShopSystem)
		{
			aAppointment.setAutoRepairShopSystem(this);
		}
		else
		{
			appointments.add(aAppointment);
		}
		wasAdded = true;
		return wasAdded;
	}

	public boolean removeAppointment(Appointment aAppointment)
	{
		boolean wasRemoved = false;
		//Unable to remove aAppointment, as it must always have a AutoRepairShopSystem
		if (!this.equals(aAppointment.getAutoRepairShopSystem()))
		{
			appointments.remove(aAppointment);
			wasRemoved = true;
		}
		return wasRemoved;
	}
	/* Code from template association_AddIndexControlFunctions */
	public boolean addAppointmentAt(Appointment aAppointment, int index)
	{  
		boolean wasAdded = false;
		if(addAppointment(aAppointment))
		{
			if(index < 0 ) { index = 0; }
			if(index > numberOfAppointments()) { index = numberOfAppointments() - 1; }
			appointments.remove(aAppointment);
			appointments.add(index, aAppointment);
			wasAdded = true;
		}
		return wasAdded;
	}

	public boolean addOrMoveAppointmentAt(Appointment aAppointment, int index)
	{
		boolean wasAdded = false;
		if(appointments.contains(aAppointment))
		{
			if(index < 0 ) { index = 0; }
			if(index > numberOfAppointments()) { index = numberOfAppointments() - 1; }
			appointments.remove(aAppointment);
			appointments.add(index, aAppointment);
			wasAdded = true;
		} 
		else 
		{
			wasAdded = addAppointmentAt(aAppointment, index);
		}
		return wasAdded;
	}
	/* Code from template association_MinimumNumberOfMethod */
	public static int minimumNumberOfTimeSlots()
	{
		return 0;
	}
	/* Code from template association_AddManyToOne */
	public TimeSlot addTimeSlot(String aId, Date aStartDate, Time aStartTime, Date aEndDate, Time aEndTime)
	{
		return new TimeSlot(aId, aStartDate, aStartTime, aEndDate, aEndTime, this);
	}

	public boolean addTimeSlot(TimeSlot aTimeSlot)
	{
		boolean wasAdded = false;
		if (timeSlots.contains(aTimeSlot)) { return false; }
		AutoRepairShopSystem existingAutoRepairShopSystem = aTimeSlot.getAutoRepairShopSystem();
		boolean isNewAutoRepairShopSystem = existingAutoRepairShopSystem != null && !this.equals(existingAutoRepairShopSystem);
		if (isNewAutoRepairShopSystem)
		{
			aTimeSlot.setAutoRepairShopSystem(this);
		}
		else
		{
			timeSlots.add(aTimeSlot);
		}
		wasAdded = true;
		return wasAdded;
	}

	public boolean removeTimeSlot(TimeSlot aTimeSlot)
	{
		boolean wasRemoved = false;
		//Unable to remove aTimeSlot, as it must always have a AutoRepairShopSystem
		if (!this.equals(aTimeSlot.getAutoRepairShopSystem()))
		{
			timeSlots.remove(aTimeSlot);
			wasRemoved = true;
		}
		return wasRemoved;
	}
	/* Code from template association_AddIndexControlFunctions */
	public boolean addTimeSlotAt(TimeSlot aTimeSlot, int index)
	{  
		boolean wasAdded = false;
		if(addTimeSlot(aTimeSlot))
		{
			if(index < 0 ) { index = 0; }
			if(index > numberOfTimeSlots()) { index = numberOfTimeSlots() - 1; }
			timeSlots.remove(aTimeSlot);
			timeSlots.add(index, aTimeSlot);
			wasAdded = true;
		}
		return wasAdded;
	}

	public boolean addOrMoveTimeSlotAt(TimeSlot aTimeSlot, int index)
	{
		boolean wasAdded = false;
		if(timeSlots.contains(aTimeSlot))
		{
			if(index < 0 ) { index = 0; }
			if(index > numberOfTimeSlots()) { index = numberOfTimeSlots() - 1; }
			timeSlots.remove(aTimeSlot);
			timeSlots.add(index, aTimeSlot);
			wasAdded = true;
		} 
		else 
		{
			wasAdded = addTimeSlotAt(aTimeSlot, index);
		}
		return wasAdded;
	}
	/* Code from template association_MinimumNumberOfMethod */
	public static int minimumNumberOfBookableServices()
	{
		return 0;
	}
	/* Code from template association_AddManyToOne */


	public boolean addBookableService(BookableService aBookableService)
	{
		boolean wasAdded = false;
		if (bookableServices.contains(aBookableService)) { return false; }
		AutoRepairShopSystem existingAutoRepairShopSystem = aBookableService.getAutoRepairShopSystem();
		boolean isNewAutoRepairShopSystem = existingAutoRepairShopSystem != null && !this.equals(existingAutoRepairShopSystem);
		if (isNewAutoRepairShopSystem)
		{
			aBookableService.setAutoRepairShopSystem(this);
		}
		else
		{
			bookableServices.add(aBookableService);
		}
		wasAdded = true;
		return wasAdded;
	}

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
	public Reminder addReminder(String aId, String aDescription, Date aDate, Time aTime, Customer aCustomer)
	{
		return new Reminder(aId, aDescription, aDate, aTime, this, aCustomer);
	}

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


	public String toString()
	{
		return super.toString() + "["+
				"id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
				"  " + "business = "+(getBusiness()!=null?Integer.toHexString(System.identityHashCode(getBusiness())):"null") + System.getProperties().getProperty("line.separator") +
				"  " + "owner = "+(getOwner()!=null?Integer.toHexString(System.identityHashCode(getOwner())):"null") + System.getProperties().getProperty("line.separator") +
				"  " + "assistant = "+(getAssistant()!=null?Integer.toHexString(System.identityHashCode(getAssistant())):"null");
	}
}
