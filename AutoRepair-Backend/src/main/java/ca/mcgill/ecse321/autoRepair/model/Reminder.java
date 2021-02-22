/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.autoRepair.model;
import javax.persistence.*;
import java.util.*;
import java.sql.Date;
import java.sql.Time;

// line 134 "../../../../../AutoRepair.ump"
// line 229 "../../../../../AutoRepair.ump"
@Entity
@Table(name="reminders")
public class Reminder
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Reminder> remindersById = new HashMap<String, Reminder>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Reminder Attributes
  private String id;
  private String description;
  private Date date;
  private Time time;

  //Reminder Associations
  private AutoRepairShopSystem AutoRepairShopSystem;
  private Customer customer;
  private List<BookableService> bookableServices;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Reminder(String aId, String aDescription, Date aDate, Time aTime, AutoRepairShopSystem aAutoRepairShopSystem, Customer aCustomer)
  {
    description = aDescription;
    date = aDate;
    time = aTime;
    if (!setId(aId))
    {
      throw new RuntimeException("Cannot create due to duplicate id. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddAutoRepairShopSystem = setAutoRepairShopSystem(aAutoRepairShopSystem);
    if (!didAddAutoRepairShopSystem)
    {
      throw new RuntimeException("Unable to create reminder due to AutoRepairShopSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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

  public boolean setId(String aId)
  {
    boolean wasSet = false;
    String anOldId = getId();
    if (anOldId != null && anOldId.equals(aId)) {
      return true;
    }
    if (hasWithId(aId)) {
      return wasSet;
    }
    id = aId;
    wasSet = true;
    if (anOldId != null) {
      remindersById.remove(anOldId);
    }
    remindersById.put(aId, this);
    return wasSet;
  }

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

  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE)
  public String getId()
  {
    return id;
  }
  /* Code from template attribute_GetUnique */
  public static Reminder getWithId(String aId)
  {
    return remindersById.get(aId);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithId(String aId)
  {
    return getWithId(aId) != null;
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
  @ManyToOne(fetch = FetchType.LAZY)
  public AutoRepairShopSystem getAutoRepairShopSystem()
  {
    return AutoRepairShopSystem;
  }

  /* Code from template association_GetOne */
  @ManyToOne(fetch = FetchType.LAZY)
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

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "reminders")
  public List<BookableService> getBookableServices()
  {
    List<BookableService> newBookableServices = Collections.unmodifiableList(bookableServices);
    return newBookableServices;
  }
  public void setBookableServices(List<BookableService> bookableServices) {
	  this.bookableServices=bookableServices;
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
  public boolean setAutoRepairShopSystem(AutoRepairShopSystem aAutoRepairShopSystem)
  {
    boolean wasSet = false;
    if (aAutoRepairShopSystem == null)
    {
      return wasSet;
    }

    AutoRepairShopSystem existingAutoRepairShopSystem = AutoRepairShopSystem;
    AutoRepairShopSystem = aAutoRepairShopSystem;
    if (existingAutoRepairShopSystem != null && !existingAutoRepairShopSystem.equals(aAutoRepairShopSystem))
    {
      existingAutoRepairShopSystem.removeReminder(this);
    }
    AutoRepairShopSystem.addReminder(this);
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
    remindersById.remove(getId());
    AutoRepairShopSystem placeholderAutoRepairShopSystem = AutoRepairShopSystem;
    this.AutoRepairShopSystem = null;
    if(placeholderAutoRepairShopSystem != null)
    {
      placeholderAutoRepairShopSystem.removeReminder(this);
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
            "id" + ":" + getId()+ "," +
            "description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "time" + "=" + (getTime() != null ? !getTime().equals(this)  ? getTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "AutoRepairShopSystem = "+(getAutoRepairShopSystem()!=null?Integer.toHexString(System.identityHashCode(getAutoRepairShopSystem())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null");
  }
}