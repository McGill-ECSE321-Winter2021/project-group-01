/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.autoRepair.model;
import javax.persistence.*;

// line 26 "../../../../../AutoRepair.ump"
// line 164 "../../../../../AutoRepair.ump"
@Entity
public class Owner extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Owner Associations
  private AutoRepairShopSystem AutoRepairShopSystem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Owner(String aUsername, String aPassword, AutoRepairShopSystem aAutoRepairShopSystem)
  {
    super(aUsername, aPassword);
    boolean didAddAutoRepairShopSystem = setAutoRepairShopSystem(aAutoRepairShopSystem);
    if (!didAddAutoRepairShopSystem)
    {
      throw new RuntimeException("Unable to create owner due to AutoRepairShopSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  @OneToOne(fetch = FetchType.LAZY)
  public AutoRepairShopSystem getAutoRepairShopSystem()
  {
    return AutoRepairShopSystem;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setAutoRepairShopSystem(AutoRepairShopSystem aNewAutoRepairShopSystem)
  {
    boolean wasSet = false;
    if (aNewAutoRepairShopSystem == null)
    {
      //Unable to setAutoRepairShopSystem to null, as owner must always be associated to a AutoRepairShopSystem
      return wasSet;
    }
    
    Owner existingOwner = aNewAutoRepairShopSystem.getOwner();
    if (existingOwner != null && !equals(existingOwner))
    {
      //Unable to setAutoRepairShopSystem, the current AutoRepairShopSystem already has a owner, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    AutoRepairShopSystem anOldAutoRepairShopSystem = AutoRepairShopSystem;
    AutoRepairShopSystem = aNewAutoRepairShopSystem;
    AutoRepairShopSystem.setOwner(this);

    if (anOldAutoRepairShopSystem != null)
    {
      anOldAutoRepairShopSystem.setOwner(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    AutoRepairShopSystem existingAutoRepairShopSystem = AutoRepairShopSystem;
    AutoRepairShopSystem = null;
    if (existingAutoRepairShopSystem != null)
    {
      existingAutoRepairShopSystem.setOwner(null);
    }
    super.delete();
  }

}