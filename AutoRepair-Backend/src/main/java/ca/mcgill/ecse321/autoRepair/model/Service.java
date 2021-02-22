/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.autoRepair.model;
import javax.persistence.*;

// line 105 "../../../../../AutoRepair.ump"
// line 209 "../../../../../AutoRepair.ump"
@Entity
public class Service extends BookableService
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Service Attributes
  private int duration;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Service(String aName, AutoRepairShopSystem aAutoRepairShopSystem, int aDuration)
  {
    super(aName, aAutoRepairShopSystem);
    duration = aDuration;
  }
  
  public Service() {
	  super();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDuration(int aDuration)
  {
    boolean wasSet = false;
    duration = aDuration;
    wasSet = true;
    return wasSet;
  }

  public int getDuration()
  {
    return duration;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "duration" + ":" + getDuration()+ "]";
  }
}