/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.autoRepair.model;
import javax.persistence.*;
import java.util.*;
import java.sql.Date;
import java.sql.Time;

// line 88 "../../../../../AutoRepair.ump"
// line 199 "../../../../../AutoRepair.ump"
@Entity
public class TimeSlot
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, TimeSlot> timeslotsById = new HashMap<String, TimeSlot>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TimeSlot Attributes
  private String id;
  private Date startDate;
  private Time startTime;
  private Date endDate;
  private Time endTime;

  //TimeSlot Associations
  private AutoRepairShopSystem AutoRepairShopSystem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TimeSlot(String aId, Date aStartDate, Time aStartTime, Date aEndDate, Time aEndTime, AutoRepairShopSystem aAutoRepairShopSystem)
  {
    startDate = aStartDate;
    startTime = aStartTime;
    endDate = aEndDate;
    endTime = aEndTime;
    if (!setId(aId))
    {
      throw new RuntimeException("Cannot create due to duplicate id. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddAutoRepairShopSystem = setAutoRepairShopSystem(aAutoRepairShopSystem);
    if (!didAddAutoRepairShopSystem)
    {
      throw new RuntimeException("Unable to create timeSlot due to AutoRepairShopSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
      timeslotsById.remove(anOldId);
    }
    timeslotsById.put(aId, this);
    return wasSet;
  }

  public boolean setStartDate(Date aStartDate)
  {
    boolean wasSet = false;
    startDate = aStartDate;
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

  public boolean setEndDate(Date aEndDate)
  {
    boolean wasSet = false;
    endDate = aEndDate;
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
  public String getId()
  {
    return id;
  }
  /* Code from template attribute_GetUnique */
  public static TimeSlot getWithId(String aId)
  {
    return timeslotsById.get(aId);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithId(String aId)
  {
    return getWithId(aId) != null;
  }

  public Date getStartDate()
  {
    return startDate;
  }

  public Time getStartTime()
  {
    return startTime;
  }

  public Date getEndDate()
  {
    return endDate;
  }

  public Time getEndTime()
  {
    return endTime;
  }
  /* Code from template association_GetOne */
  @ManyToOne
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
      existingAutoRepairShopSystem.removeTimeSlot(this);
    }
    AutoRepairShopSystem.addTimeSlot(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    timeslotsById.remove(getId());
    AutoRepairShopSystem placeholderAutoRepairShopSystem = AutoRepairShopSystem;
    this.AutoRepairShopSystem = null;
    if(placeholderAutoRepairShopSystem != null)
    {
      placeholderAutoRepairShopSystem.removeTimeSlot(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startDate" + "=" + (getStartDate() != null ? !getStartDate().equals(this)  ? getStartDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endDate" + "=" + (getEndDate() != null ? !getEndDate().equals(this)  ? getEndDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "AutoRepairShopSystem = "+(getAutoRepairShopSystem()!=null?Integer.toHexString(System.identityHashCode(getAutoRepairShopSystem())):"null");
  }
}