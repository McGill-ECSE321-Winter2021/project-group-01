/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.autoRepair.model;
import java.sql.Time;

// line 70 "../../../../../AutoRepair.ump"
// line 176 "../../../../../AutoRepair.ump"
public class OperatingHour
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum DayOfWeek { Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //OperatingHour Attributes
  private DayOfWeek dayOfWeek;
  private Time startTime;
  private Time endTime;

  //OperatingHour Associations
  private AutoRepairShopSytem autoRepairShopSytem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public OperatingHour(DayOfWeek aDayOfWeek, Time aStartTime, Time aEndTime, AutoRepairShopSytem aAutoRepairShopSytem)
  {
    dayOfWeek = aDayOfWeek;
    startTime = aStartTime;
    endTime = aEndTime;
    boolean didAddAutoRepairShopSytem = setAutoRepairShopSytem(aAutoRepairShopSytem);
    if (!didAddAutoRepairShopSytem)
    {
      throw new RuntimeException("Unable to create operatingHour due to autoRepairShopSytem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

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
  public AutoRepairShopSytem getAutoRepairShopSytem()
  {
    return autoRepairShopSytem;
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
      existingAutoRepairShopSytem.removeOperatingHour(this);
    }
    autoRepairShopSytem.addOperatingHour(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    AutoRepairShopSytem placeholderAutoRepairShopSytem = autoRepairShopSytem;
    this.autoRepairShopSytem = null;
    if(placeholderAutoRepairShopSytem != null)
    {
      placeholderAutoRepairShopSytem.removeOperatingHour(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "dayOfWeek" + "=" + (getDayOfWeek() != null ? !getDayOfWeek().equals(this)  ? getDayOfWeek().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "autoRepairShopSytem = "+(getAutoRepairShopSytem()!=null?Integer.toHexString(System.identityHashCode(getAutoRepairShopSytem())):"null");
  }
}