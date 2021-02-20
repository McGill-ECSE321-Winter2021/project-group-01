/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.autoRepair.model;
import java.util.*;
import java.sql.Time;
import java.sql.Date;

// line 3 "../../../../../AutoRepair.ump"
// line 128 "../../../../../AutoRepair.ump"
public class AutoRepairShopSytem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //AutoRepairShopSytem Associations
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

  public AutoRepairShopSytem()
  {
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
  /* Code from template association_GetOne */
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

  public List<Customer> getCustomers()
  {
    List<Customer> newCustomers = Collections.unmodifiableList(customers);
    return newCustomers;
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

  public List<OperatingHour> getOperatingHours()
  {
    List<OperatingHour> newOperatingHours = Collections.unmodifiableList(operatingHours);
    return newOperatingHours;
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

  public List<Appointment> getAppointments()
  {
    List<Appointment> newAppointments = Collections.unmodifiableList(appointments);
    return newAppointments;
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

  public List<TimeSlot> getTimeSlots()
  {
    List<TimeSlot> newTimeSlots = Collections.unmodifiableList(timeSlots);
    return newTimeSlots;
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

  public List<BookableService> getBookableServices()
  {
    List<BookableService> newBookableServices = Collections.unmodifiableList(bookableServices);
    return newBookableServices;
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

  public List<Reminder> getReminders()
  {
    List<Reminder> newReminders = Collections.unmodifiableList(reminders);
    return newReminders;
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

  public List<Review> getReviews()
  {
    List<Review> newReviews = Collections.unmodifiableList(reviews);
    return newReviews;
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
    if (business != null && !business.equals(aNewBusiness) && equals(business.getAutoRepairShopSytem()))
    {
      //Unable to setBusiness, as existing business would become an orphan
      return wasSet;
    }

    business = aNewBusiness;
    AutoRepairShopSytem anOldAutoRepairShopSytem = aNewBusiness != null ? aNewBusiness.getAutoRepairShopSytem() : null;

    if (!this.equals(anOldAutoRepairShopSytem))
    {
      if (anOldAutoRepairShopSytem != null)
      {
        anOldAutoRepairShopSytem.business = null;
      }
      if (business != null)
      {
        business.setAutoRepairShopSytem(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setOwner(Owner aNewOwner)
  {
    boolean wasSet = false;
    if (owner != null && !owner.equals(aNewOwner) && equals(owner.getAutoRepairShopSytem()))
    {
      //Unable to setOwner, as existing owner would become an orphan
      return wasSet;
    }

    owner = aNewOwner;
    AutoRepairShopSytem anOldAutoRepairShopSytem = aNewOwner != null ? aNewOwner.getAutoRepairShopSytem() : null;

    if (!this.equals(anOldAutoRepairShopSytem))
    {
      if (anOldAutoRepairShopSytem != null)
      {
        anOldAutoRepairShopSytem.owner = null;
      }
      if (owner != null)
      {
        owner.setAutoRepairShopSytem(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setAssistant(Assistant aNewAssistant)
  {
    boolean wasSet = false;
    if (assistant != null && !assistant.equals(aNewAssistant) && equals(assistant.getAutoRepairShopSytem()))
    {
      //Unable to setAssistant, as existing assistant would become an orphan
      return wasSet;
    }

    assistant = aNewAssistant;
    AutoRepairShopSytem anOldAutoRepairShopSytem = aNewAssistant != null ? aNewAssistant.getAutoRepairShopSytem() : null;

    if (!this.equals(anOldAutoRepairShopSytem))
    {
      if (anOldAutoRepairShopSytem != null)
      {
        anOldAutoRepairShopSytem.assistant = null;
      }
      if (assistant != null)
      {
        assistant.setAutoRepairShopSytem(this);
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
    AutoRepairShopSytem existingAutoRepairShopSytem = aCustomer.getAutoRepairShopSytem();
    boolean isNewAutoRepairShopSytem = existingAutoRepairShopSytem != null && !this.equals(existingAutoRepairShopSytem);
    if (isNewAutoRepairShopSytem)
    {
      aCustomer.setAutoRepairShopSytem(this);
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
    //Unable to remove aCustomer, as it must always have a autoRepairShopSytem
    if (!this.equals(aCustomer.getAutoRepairShopSytem()))
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
  public OperatingHour addOperatingHour(OperatingHour.DayOfWeek aDayOfWeek, Time aStartTime, Time aEndTime)
  {
    return new OperatingHour(aDayOfWeek, aStartTime, aEndTime, this);
  }

  public boolean addOperatingHour(OperatingHour aOperatingHour)
  {
    boolean wasAdded = false;
    if (operatingHours.contains(aOperatingHour)) { return false; }
    AutoRepairShopSytem existingAutoRepairShopSytem = aOperatingHour.getAutoRepairShopSytem();
    boolean isNewAutoRepairShopSytem = existingAutoRepairShopSytem != null && !this.equals(existingAutoRepairShopSytem);
    if (isNewAutoRepairShopSytem)
    {
      aOperatingHour.setAutoRepairShopSytem(this);
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
    //Unable to remove aOperatingHour, as it must always have a autoRepairShopSytem
    if (!this.equals(aOperatingHour.getAutoRepairShopSytem()))
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
  public Appointment addAppointment(Customer aCustomer, BookableService aBookableService, TimeSlot aTimeSlot)
  {
    return new Appointment(aCustomer, aBookableService, aTimeSlot, this);
  }

  public boolean addAppointment(Appointment aAppointment)
  {
    boolean wasAdded = false;
    if (appointments.contains(aAppointment)) { return false; }
    AutoRepairShopSytem existingAutoRepairShopSytem = aAppointment.getAutoRepairShopSytem();
    boolean isNewAutoRepairShopSytem = existingAutoRepairShopSytem != null && !this.equals(existingAutoRepairShopSytem);
    if (isNewAutoRepairShopSytem)
    {
      aAppointment.setAutoRepairShopSytem(this);
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
    //Unable to remove aAppointment, as it must always have a autoRepairShopSytem
    if (!this.equals(aAppointment.getAutoRepairShopSytem()))
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
  public TimeSlot addTimeSlot(Date aStartDate, Time aStartTime, Date aEndDate, Time aEndTime)
  {
    return new TimeSlot(aStartDate, aStartTime, aEndDate, aEndTime, this);
  }

  public boolean addTimeSlot(TimeSlot aTimeSlot)
  {
    boolean wasAdded = false;
    if (timeSlots.contains(aTimeSlot)) { return false; }
    AutoRepairShopSytem existingAutoRepairShopSytem = aTimeSlot.getAutoRepairShopSytem();
    boolean isNewAutoRepairShopSytem = existingAutoRepairShopSytem != null && !this.equals(existingAutoRepairShopSytem);
    if (isNewAutoRepairShopSytem)
    {
      aTimeSlot.setAutoRepairShopSytem(this);
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
    //Unable to remove aTimeSlot, as it must always have a autoRepairShopSytem
    if (!this.equals(aTimeSlot.getAutoRepairShopSytem()))
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
    AutoRepairShopSytem existingAutoRepairShopSytem = aBookableService.getAutoRepairShopSytem();
    boolean isNewAutoRepairShopSytem = existingAutoRepairShopSytem != null && !this.equals(existingAutoRepairShopSytem);
    if (isNewAutoRepairShopSytem)
    {
      aBookableService.setAutoRepairShopSytem(this);
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
    //Unable to remove aBookableService, as it must always have a autoRepairShopSytem
    if (!this.equals(aBookableService.getAutoRepairShopSytem()))
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
  public Reminder addReminder(String aDescription, Date aDate, Time aTime, Customer aCustomer)
  {
    return new Reminder(aDescription, aDate, aTime, this, aCustomer);
  }

  public boolean addReminder(Reminder aReminder)
  {
    boolean wasAdded = false;
    if (reminders.contains(aReminder)) { return false; }
    AutoRepairShopSytem existingAutoRepairShopSytem = aReminder.getAutoRepairShopSytem();
    boolean isNewAutoRepairShopSytem = existingAutoRepairShopSytem != null && !this.equals(existingAutoRepairShopSytem);
    if (isNewAutoRepairShopSytem)
    {
      aReminder.setAutoRepairShopSytem(this);
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
    //Unable to remove aReminder, as it must always have a autoRepairShopSytem
    if (!this.equals(aReminder.getAutoRepairShopSytem()))
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
  public Review addReview(String aDescription, int aServiceRating, Customer aCustomer, BookableService aBookableService)
  {
    return new Review(aDescription, aServiceRating, this, aCustomer, aBookableService);
  }

  public boolean addReview(Review aReview)
  {
    boolean wasAdded = false;
    if (reviews.contains(aReview)) { return false; }
    AutoRepairShopSytem existingAutoRepairShopSytem = aReview.getAutoRepairShopSytem();
    boolean isNewAutoRepairShopSytem = existingAutoRepairShopSytem != null && !this.equals(existingAutoRepairShopSytem);
    if (isNewAutoRepairShopSytem)
    {
      aReview.setAutoRepairShopSytem(this);
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
    //Unable to remove aReview, as it must always have a autoRepairShopSytem
    if (!this.equals(aReview.getAutoRepairShopSytem()))
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
    Business existingBusiness = business;
    business = null;
    if (existingBusiness != null)
    {
      existingBusiness.delete();
      existingBusiness.setAutoRepairShopSytem(null);
    }
    Owner existingOwner = owner;
    owner = null;
    if (existingOwner != null)
    {
      existingOwner.delete();
      existingOwner.setAutoRepairShopSytem(null);
    }
    Assistant existingAssistant = assistant;
    assistant = null;
    if (existingAssistant != null)
    {
      existingAssistant.delete();
      existingAssistant.setAutoRepairShopSytem(null);
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

}