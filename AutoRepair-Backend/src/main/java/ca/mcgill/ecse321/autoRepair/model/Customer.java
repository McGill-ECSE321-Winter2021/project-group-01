/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.autoRepair.model;
import java.util.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import java.sql.Date;
import java.sql.Time;

// line 27 "../../../../../AutoRepair.ump"
// line 150 "../../../../../AutoRepair.ump"
@Entity
public class Customer extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Customer Attributes
  private int noShow;
  private int show;

  //Customer Associations
  private List<Car> cars;
  private List<Reminder> reminders;
  private List<Review> reviews;
  private Profile profile;
  private AutoRepairShopSytem autoRepairShopSytem;
  private List<Appointment> appointments;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Customer(String aUsername, String aPassword, int aNoShow, int aShow, Profile aProfile, AutoRepairShopSytem aAutoRepairShopSytem)
  {
    super(aUsername, aPassword);
    noShow = aNoShow;
    show = aShow;
    cars = new ArrayList<Car>();
    reminders = new ArrayList<Reminder>();
    reviews = new ArrayList<Review>();
    if (aProfile == null || aProfile.getCustomer() != null)
    {
      throw new RuntimeException("Unable to create Customer due to aProfile. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    profile = aProfile;
    boolean didAddAutoRepairShopSytem = setAutoRepairShopSytem(aAutoRepairShopSytem);
    if (!didAddAutoRepairShopSytem)
    {
      throw new RuntimeException("Unable to create customer due to autoRepairShopSytem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    appointments = new ArrayList<Appointment>();
  }

  public Customer(String aUsername, String aPassword, int aNoShow, int aShow, String aFirstNameForProfile, String aLastNameForProfile, String aAddressForProfile, String aZipCodeForProfile, String aPhoneNumberForProfile, String aEmailForProfile, AutoRepairShopSytem aAutoRepairShopSytem)
  {
    super(aUsername, aPassword);
    noShow = aNoShow;
    show = aShow;
    cars = new ArrayList<Car>();
    reminders = new ArrayList<Reminder>();
    reviews = new ArrayList<Review>();
    profile = new Profile(aFirstNameForProfile, aLastNameForProfile, aAddressForProfile, aZipCodeForProfile, aPhoneNumberForProfile, aEmailForProfile, this);
    boolean didAddAutoRepairShopSytem = setAutoRepairShopSytem(aAutoRepairShopSytem);
    if (!didAddAutoRepairShopSytem)
    {
      throw new RuntimeException("Unable to create customer due to autoRepairShopSytem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    appointments = new ArrayList<Appointment>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setNoShow(int aNoShow)
  {
    boolean wasSet = false;
    noShow = aNoShow;
    wasSet = true;
    return wasSet;
  }

  public boolean setShow(int aShow)
  {
    boolean wasSet = false;
    show = aShow;
    wasSet = true;
    return wasSet;
  }

  public int getNoShow()
  {
    return noShow;
  }

  public int getShow()
  {
    return show;
  }
  /* Code from template association_GetMany */
  public Car getCar(int index)
  {
    Car aCar = cars.get(index);
    return aCar;
  }
  
  @OneToMany
  public List<Car> getCars()
  {
    List<Car> newCars = Collections.unmodifiableList(cars);
    return newCars;
  }

  public int numberOfCars()
  {
    int number = cars.size();
    return number;
  }

  public boolean hasCars()
  {
    boolean has = cars.size() > 0;
    return has;
  }

  public int indexOfCar(Car aCar)
  {
    int index = cars.indexOf(aCar);
    return index;
  }
  /* Code from template association_GetMany */
  public Reminder getReminder(int index)
  {
    Reminder aReminder = reminders.get(index);
    return aReminder;
  }
  
  @OneToMany
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
  
  @OneToMany
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
  /* Code from template association_GetOne */
  @OneToOne
  public Profile getProfile()
  {
    return profile;
  }
  /* Code from template association_GetOne */
  public AutoRepairShopSytem getAutoRepairShopSytem()
  {
    return autoRepairShopSytem;
  }
  /* Code from template association_GetMany */
  public Appointment getAppointment(int index)
  {
    Appointment aAppointment = appointments.get(index);
    return aAppointment;
  }

  @OneToMany
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
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfCarsValid()
  {
    boolean isValid = numberOfCars() >= minimumNumberOfCars();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCars()
  {
    return 1;
  }
  /* Code from template association_AddMandatoryManyToOne */
  public Car addCar(String aModel, Car.CarTransmission aTransmission, String aPlateNumber)
  {
    Car aNewCar = new Car(aModel, aTransmission, aPlateNumber, this);
    return aNewCar;
  }

  public boolean addCar(Car aCar)
  {
    boolean wasAdded = false;
    if (cars.contains(aCar)) { return false; }
    Customer existingCustomer = aCar.getCustomer();
    boolean isNewCustomer = existingCustomer != null && !this.equals(existingCustomer);

    if (isNewCustomer && existingCustomer.numberOfCars() <= minimumNumberOfCars())
    {
      return wasAdded;
    }
    if (isNewCustomer)
    {
      aCar.setCustomer(this);
    }
    else
    {
      cars.add(aCar);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCar(Car aCar)
  {
    boolean wasRemoved = false;
    //Unable to remove aCar, as it must always have a customer
    if (this.equals(aCar.getCustomer()))
    {
      return wasRemoved;
    }

    //customer already at minimum (1)
    if (numberOfCars() <= minimumNumberOfCars())
    {
      return wasRemoved;
    }

    cars.remove(aCar);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCarAt(Car aCar, int index)
  {  
    boolean wasAdded = false;
    if(addCar(aCar))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCars()) { index = numberOfCars() - 1; }
      cars.remove(aCar);
      cars.add(index, aCar);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCarAt(Car aCar, int index)
  {
    boolean wasAdded = false;
    if(cars.contains(aCar))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCars()) { index = numberOfCars() - 1; }
      cars.remove(aCar);
      cars.add(index, aCar);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCarAt(aCar, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfReminders()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Reminder addReminder(String aDescription, Date aDate, Time aTime, AutoRepairShopSytem aAutoRepairShopSytem)
  {
    return new Reminder(aDescription, aDate, aTime, aAutoRepairShopSytem, this);
  }

  public boolean addReminder(Reminder aReminder)
  {
    boolean wasAdded = false;
    if (reminders.contains(aReminder)) { return false; }
    Customer existingCustomer = aReminder.getCustomer();
    boolean isNewCustomer = existingCustomer != null && !this.equals(existingCustomer);
    if (isNewCustomer)
    {
      aReminder.setCustomer(this);
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
    //Unable to remove aReminder, as it must always have a customer
    if (!this.equals(aReminder.getCustomer()))
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
  public Review addReview(String aDescription, int aServiceRating, AutoRepairShopSytem aAutoRepairShopSytem, BookableService aBookableService)
  {
    return new Review(aDescription, aServiceRating, aAutoRepairShopSytem, this, aBookableService);
  }

  public boolean addReview(Review aReview)
  {
    boolean wasAdded = false;
    if (reviews.contains(aReview)) { return false; }
    Customer existingCustomer = aReview.getCustomer();
    boolean isNewCustomer = existingCustomer != null && !this.equals(existingCustomer);
    if (isNewCustomer)
    {
      aReview.setCustomer(this);
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
    //Unable to remove aReview, as it must always have a customer
    if (!this.equals(aReview.getCustomer()))
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
  /* Code from template association_SetOneToMany */
  public boolean setAutoRepairShopSytem(AutoRepairShopSytem aAutoRepairShopSytem)
  {
    boolean wasSet = false;
    if (aAutoRepairShopSytem == null)
    {
      return wasSet;
    }

    AutoRepairShopSytem existingAutoRepairShopSytem = autoRepairShopSytem;
    autoRepairShopSytem = aAutoRepairShopSytem;
    if (existingAutoRepairShopSytem != null && !existingAutoRepairShopSytem.equals(aAutoRepairShopSytem))
    {
      existingAutoRepairShopSytem.removeCustomer(this);
    }
    autoRepairShopSytem.addCustomer(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAppointments()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Appointment addAppointment(BookableService aBookableService, TimeSlot aTimeSlot, AutoRepairShopSytem aAutoRepairShopSytem)
  {
    return new Appointment(this, aBookableService, aTimeSlot, aAutoRepairShopSytem);
  }

  public boolean addAppointment(Appointment aAppointment)
  {
    boolean wasAdded = false;
    if (appointments.contains(aAppointment)) { return false; }
    Customer existingCustomer = aAppointment.getCustomer();
    boolean isNewCustomer = existingCustomer != null && !this.equals(existingCustomer);
    if (isNewCustomer)
    {
      aAppointment.setCustomer(this);
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
    //Unable to remove aAppointment, as it must always have a customer
    if (!this.equals(aAppointment.getCustomer()))
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

  public void delete()
  {
    while (cars.size() > 0)
    {
      Car aCar = cars.get(cars.size() - 1);
      aCar.delete();
      cars.remove(aCar);
    }
    
    for(int i=reminders.size(); i > 0; i--)
    {
      Reminder aReminder = reminders.get(i - 1);
      aReminder.delete();
    }
    for(int i=reviews.size(); i > 0; i--)
    {
      Review aReview = reviews.get(i - 1);
      aReview.delete();
    }
    Profile existingProfile = profile;
    profile = null;
    if (existingProfile != null)
    {
      existingProfile.delete();
    }
    AutoRepairShopSytem placeholderAutoRepairShopSytem = autoRepairShopSytem;
    this.autoRepairShopSytem = null;
    if(placeholderAutoRepairShopSytem != null)
    {
      placeholderAutoRepairShopSytem.removeCustomer(this);
    }
    for(int i=appointments.size(); i > 0; i--)
    {
      Appointment aAppointment = appointments.get(i - 1);
      aAppointment.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "noShow" + ":" + getNoShow()+ "," +
            "show" + ":" + getShow()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "profile = "+(getProfile()!=null?Integer.toHexString(System.identityHashCode(getProfile())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "autoRepairShopSytem = "+(getAutoRepairShopSytem()!=null?Integer.toHexString(System.identityHashCode(getAutoRepairShopSytem())):"null");
  }
}