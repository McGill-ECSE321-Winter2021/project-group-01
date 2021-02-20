/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.autoRepair.model;
import java.util.*;

// line 91 "../../../../../AutoRepair.ump"
// line 191 "../../../../../AutoRepair.ump"
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

  public Service(String aName, AutoRepairShopSytem aAutoRepairShopSytem, int aDuration)
  {
    super(aName, aAutoRepairShopSytem);
    duration = aDuration;
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