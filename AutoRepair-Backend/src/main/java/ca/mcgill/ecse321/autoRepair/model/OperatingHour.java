/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.autoRepair.model;
import javax.persistence.*;
import java.util.*;
import java.sql.Time;

// line 79 "../../../../../AutoRepair.ump"
// line 194 "../../../../../AutoRepair.ump"
@Entity
@Table(name = "opSlots")
public class OperatingHour
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum DayOfWeek { Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, OperatingHour> operatinghoursById = new HashMap<String, OperatingHour>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //OperatingHour Attributes
  private String id;
  private DayOfWeek dayOfWeek;
  private Time startTime;
  private Time endTime;

  //OperatingHour Associations
  private AutoRepairShopSystem AutoRepairShopSystem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public OperatingHour(String aId, DayOfWeek aDayOfWeek, Time aStartTime, Time aEndTime, AutoRepairShopSystem aAutoRepairShopSystem)
  {
    dayOfWeek = aDayOfWeek;
    startTime = aStartTime;
    endTime = aEndTime;
    if (!setId(aId))
    {
      throw new RuntimeException("Cannot create due to duplicate id. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddAutoRepairShopSystem = setAutoRepairShopSystem(aAutoRepairShopSystem);
    if (!didAddAutoRepairShopSystem)
    {
      throw new RuntimeException("Unable to create operatingHour due to AutoRepairShopSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
      operatinghoursById.remove(anOldId);
    }
    operatinghoursById.put(aId, this);
    return wasSet;
  }

  public boolean setDayOfWeek(DayOfWeek aDayOfWeek)
  {
    boolean wasSet = false;
    dayOfWeek = aDayOfWeek;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartTime(Time aStartTime)
  {
    boolean wasSet = false;
    startTime = aStartTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndTime(Time aEndTime)
  {
    boolean wasSet = false;
    endTime = aEndTime;
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
  public static OperatingHour getWithId(String aId)
  {
    return operatinghoursById.get(aId);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithId(String aId)
  {
    return getWithId(aId) != null;
  }

  public DayOfWeek getDayOfWeek()
  {
    return dayOfWeek;
  }

  public Time getStartTime()
  {
    return startTime;
  }

  public Time getEndTime()
  {
    return endTime;
  }
  /* Code from template association_GetOne */
  @ManyToOne(fetch = FetchType.LAZY)
  public AutoRepairShopSystem getAutoRepairShopSystem()
  {
    return AutoRepairShopSystem;
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
      existingAutoRepairShopSystem.removeOperatingHour(this);
    }
    AutoRepairShopSystem.addOperatingHour(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    operatinghoursById.remove(getId());
    AutoRepairShopSystem placeholderAutoRepairShopSystem = AutoRepairShopSystem;
    this.AutoRepairShopSystem = null;
    if(placeholderAutoRepairShopSystem != null)
    {
      placeholderAutoRepairShopSystem.removeOperatingHour(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "dayOfWeek" + "=" + (getDayOfWeek() != null ? !getDayOfWeek().equals(this)  ? getDayOfWeek().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "AutoRepairShopSystem = "+(getAutoRepairShopSystem()!=null?Integer.toHexString(System.identityHashCode(getAutoRepairShopSystem())):"null");
  }
}