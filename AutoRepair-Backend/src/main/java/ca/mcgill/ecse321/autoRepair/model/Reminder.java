/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.autoRepair.model;
import java.sql.Date;
import java.sql.Time;
import java.util.*;

// line 114 "../../../../../AutoRepair.ump"
// line 212 "../../../../../AutoRepair.ump"
public class Reminder
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Reminder Attributes
  private String description;
  private Date date;
  private Time time;

  //Reminder Associations
  private AutoRepairShopSytem autoRepairShopSytem;
  private Customer customer;
  private List<BookableService> bookableServices;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Reminder(String aDescription, Date aDate, Time aTime, AutoRepairShopSytem aAutoRepairShopSytem, Customer aCustomer)
  {
    description = aDescription;
    date = aDate;
    time = aTime;
    boolean didAddAutoRepairShopSytem = setAutoRepairShopSytem(aAutoRepairShopSytem);
    if (!didAddAutoRepairShopSytem)
    {
      throw new RuntimeException("Unable to create reminder due to autoRepairShopSytem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddCustomer = setCustomer(aCustomer);
    if (!didAddCustomer)
    {
      throw new RuntimeException("Unable to create reminder due to customer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    bookableServices = new ArrayList<BookableService>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setTime(Time aTime)
  {
    boolean wasSet = false;
    time = aTime;
    wasSet = true;
    return wasSet;
  }

  public String getDescription()
  {
    return description;
  }

  public Date getDate()
  {
    return date;
  }

  public Time getTime()
  {
    return time;
  }
  /* Code from template association_GetOne */
  public AutoRepairShopSytem getAutoRepairShopSytem()
  {
    return autoRepairShopSytem;
  }
  /* Code from template association_GetOne */
  public Customer getCustomer()
  {
    return customer;
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
      existingAutoRepairShopSytem.removeReminder(this);
    }
    autoRepairShopSytem.addReminder(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCustomer(Customer aCustomer)
  {
    boolean wasSet = false;
    if (aCustomer == null)
    {
      return wasSet;
    }

    Customer existingCustomer = customer;
    customer = aCustomer;
    if (existingCustomer != null && !existingCustomer.equals(aCustomer))
    {
      existingCustomer.removeReminder(this);
    }
    customer.addReminder(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBookableServices()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addBookableService(BookableService aBookableService)
  {
    boolean wasAdded = false;
    if (bookableServices.contains(aBookableService)) { return false; }
    bookableServices.add(aBookableService);
    if (aBookableService.indexOfReminder(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aBookableService.addReminder(this);
      if (!wasAdded)
      {
        bookableServices.remove(aBookableService);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeBookableService(BookableService aBookableService)
  {
    boolean wasRemoved = false;
    if (!bookableServices.contains(aBookableService))
    {
      return wasRemoved;
    }

    int oldIndex = bookableServices.indexOf(aBookableService);
    bookableServices.remove(oldIndex);
    if (aBookableService.indexOfReminder(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aBookableService.removeReminder(this);
      if (!wasRemoved)
      {
        bookableServices.add(oldIndex,aBookableService);
      }
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

  public void delete()
  {
    AutoRepairShopSytem placeholderAutoRepairShopSytem = autoRepairShopSytem;
    this.autoRepairShopSytem = null;
    if(placeholderAutoRepairShopSytem != null)
    {
      placeholderAutoRepairShopSytem.removeReminder(this);
    }
    Customer placeholderCustomer = customer;
    this.customer = null;
    if(placeholderCustomer != null)
    {
      placeholderCustomer.removeReminder(this);
    }
    ArrayList<BookableService> copyOfBookableServices = new ArrayList<BookableService>(bookableServices);
    bookableServices.clear();
    for(BookableService aBookableService : copyOfBookableServices)
    {
      aBookableService.removeReminder(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "time" + "=" + (getTime() != null ? !getTime().equals(this)  ? getTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "autoRepairShopSytem = "+(getAutoRepairShopSytem()!=null?Integer.toHexString(System.identityHashCode(getAutoRepairShopSytem())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null");
  }
}