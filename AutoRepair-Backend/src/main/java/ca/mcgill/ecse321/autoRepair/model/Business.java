/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.autoRepair.model;
import javax.persistence.*;
import java.util.*;

// line 67 "../../../../../AutoRepair.ump"
// line 189 "../../../../../AutoRepair.ump"
@Entity
public class Business
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Business> businesssById = new HashMap<String, Business>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Business Attributes
  private String id;
  private String name;
  private String address;
  private String phoneNumber;
  private String email;

  //Business Associations
  private List<OperatingHour> businessHours;
  private List<TimeSlot> holidays;
  private AutoRepairShopSystem AutoRepairShopSystem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Business(String aId, String aName, String aAddress, String aPhoneNumber, String aEmail, AutoRepairShopSystem aAutoRepairShopSystem)
  {
    name = aName;
    address = aAddress;
    phoneNumber = aPhoneNumber;
    email = aEmail;
    if (!setId(aId))
    {
      throw new RuntimeException("Cannot create due to duplicate id. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    businessHours = new ArrayList<OperatingHour>();
    holidays = new ArrayList<TimeSlot>();
    boolean didAddAutoRepairShopSystem = setAutoRepairShopSystem(aAutoRepairShopSystem);
    if (!didAddAutoRepairShopSystem)
    {
      throw new RuntimeException("Unable to create business due to AutoRepairShopSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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
      businesssById.remove(anOldId);
    }
    businesssById.put(aId, this);
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setAddress(String aAddress)
  {
    boolean wasSet = false;
    address = aAddress;
    wasSet = true;
    return wasSet;
  }

  public boolean setPhoneNumber(String aPhoneNumber)
  {
    boolean wasSet = false;
    phoneNumber = aPhoneNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  @Id
  public String getId()
  {
    return id;
  }
  /* Code from template attribute_GetUnique */
  public static Business getWithId(String aId)
  {
    return businesssById.get(aId);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithId(String aId)
  {
    return getWithId(aId) != null;
  }

  public String getName()
  {
    return name;
  }

  public String getAddress()
  {
    return address;
  }

  public String getPhoneNumber()
  {
    return phoneNumber;
  }

  public String getEmail()
  {
    return email;
  }
  /* Code from template association_GetMany */
  public OperatingHour getBusinessHour(int index)
  {
    OperatingHour aBusinessHour = businessHours.get(index);
    return aBusinessHour;
  }

  @OneToMany(cascade = { CascadeType.ALL })
  public List<OperatingHour> getBusinessHours()
  {
    List<OperatingHour> newBusinessHours = Collections.unmodifiableList(businessHours);
    return newBusinessHours;
  }

  public int numberOfBusinessHours()
  {
    int number = businessHours.size();
    return number;
  }

  public boolean hasBusinessHours()
  {
    boolean has = businessHours.size() > 0;
    return has;
  }

  public int indexOfBusinessHour(OperatingHour aBusinessHour)
  {
    int index = businessHours.indexOf(aBusinessHour);
    return index;
  }
  /* Code from template association_GetMany */
  public TimeSlot getHoliday(int index)
  {
    TimeSlot aHoliday = holidays.get(index);
    return aHoliday;
  }

  @OneToMany(cascade = { CascadeType.ALL })
  public List<TimeSlot> getHolidays()
  {
    List<TimeSlot> newHolidays = Collections.unmodifiableList(holidays);
    return newHolidays;
  }

  public int numberOfHolidays()
  {
    int number = holidays.size();
    return number;
  }

  public boolean hasHolidays()
  {
    boolean has = holidays.size() > 0;
    return has;
  }

  public int indexOfHoliday(TimeSlot aHoliday)
  {
    int index = holidays.indexOf(aHoliday);
    return index;
  }
  /* Code from template association_GetOne */
  @OneToOne
  public AutoRepairShopSystem getAutoRepairShopSystem()
  {
    return AutoRepairShopSystem;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBusinessHours()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addBusinessHour(OperatingHour aBusinessHour)
  {
    boolean wasAdded = false;
    if (businessHours.contains(aBusinessHour)) { return false; }
    businessHours.add(aBusinessHour);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBusinessHour(OperatingHour aBusinessHour)
  {
    boolean wasRemoved = false;
    if (businessHours.contains(aBusinessHour))
    {
      businessHours.remove(aBusinessHour);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBusinessHourAt(OperatingHour aBusinessHour, int index)
  {  
    boolean wasAdded = false;
    if(addBusinessHour(aBusinessHour))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBusinessHours()) { index = numberOfBusinessHours() - 1; }
      businessHours.remove(aBusinessHour);
      businessHours.add(index, aBusinessHour);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBusinessHourAt(OperatingHour aBusinessHour, int index)
  {
    boolean wasAdded = false;
    if(businessHours.contains(aBusinessHour))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBusinessHours()) { index = numberOfBusinessHours() - 1; }
      businessHours.remove(aBusinessHour);
      businessHours.add(index, aBusinessHour);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBusinessHourAt(aBusinessHour, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfHolidays()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addHoliday(TimeSlot aHoliday)
  {
    boolean wasAdded = false;
    if (holidays.contains(aHoliday)) { return false; }
    holidays.add(aHoliday);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeHoliday(TimeSlot aHoliday)
  {
    boolean wasRemoved = false;
    if (holidays.contains(aHoliday))
    {
      holidays.remove(aHoliday);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addHolidayAt(TimeSlot aHoliday, int index)
  {  
    boolean wasAdded = false;
    if(addHoliday(aHoliday))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfHolidays()) { index = numberOfHolidays() - 1; }
      holidays.remove(aHoliday);
      holidays.add(index, aHoliday);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveHolidayAt(TimeSlot aHoliday, int index)
  {
    boolean wasAdded = false;
    if(holidays.contains(aHoliday))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfHolidays()) { index = numberOfHolidays() - 1; }
      holidays.remove(aHoliday);
      holidays.add(index, aHoliday);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addHolidayAt(aHoliday, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setAutoRepairShopSystem(AutoRepairShopSystem aNewAutoRepairShopSystem)
  {
    boolean wasSet = false;
    if (aNewAutoRepairShopSystem == null)
    {
      //Unable to setAutoRepairShopSystem to null, as business must always be associated to a AutoRepairShopSystem
      return wasSet;
    }
    
    Business existingBusiness = aNewAutoRepairShopSystem.getBusiness();
    if (existingBusiness != null && !equals(existingBusiness))
    {
      //Unable to setAutoRepairShopSystem, the current AutoRepairShopSystem already has a business, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    AutoRepairShopSystem anOldAutoRepairShopSystem = AutoRepairShopSystem;
    AutoRepairShopSystem = aNewAutoRepairShopSystem;
    AutoRepairShopSystem.setBusiness(this);

    if (anOldAutoRepairShopSystem != null)
    {
      anOldAutoRepairShopSystem.setBusiness(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    businesssById.remove(getId());
    businessHours.clear();
    holidays.clear();
    AutoRepairShopSystem existingAutoRepairShopSystem = AutoRepairShopSystem;
    AutoRepairShopSystem = null;
    if (existingAutoRepairShopSystem != null)
    {
      existingAutoRepairShopSystem.setBusiness(null);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "name" + ":" + getName()+ "," +
            "address" + ":" + getAddress()+ "," +
            "phoneNumber" + ":" + getPhoneNumber()+ "," +
            "email" + ":" + getEmail()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "AutoRepairShopSystem = "+(getAutoRepairShopSystem()!=null?Integer.toHexString(System.identityHashCode(getAutoRepairShopSystem())):"null");
  }
}