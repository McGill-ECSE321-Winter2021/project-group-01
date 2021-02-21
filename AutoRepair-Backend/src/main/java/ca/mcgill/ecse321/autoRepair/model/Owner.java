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
  private AutoRepairShopSytem autoRepairShopSytem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Owner(String aUsername, String aPassword, AutoRepairShopSytem aAutoRepairShopSytem)
  {
    super(aUsername, aPassword);
    boolean didAddAutoRepairShopSytem = setAutoRepairShopSytem(aAutoRepairShopSytem);
    if (!didAddAutoRepairShopSytem)
    {
      throw new RuntimeException("Unable to create owner due to autoRepairShopSytem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public AutoRepairShopSytem getAutoRepairShopSytem()
  {
    return autoRepairShopSytem;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setAutoRepairShopSytem(AutoRepairShopSytem aNewAutoRepairShopSytem)
  {
    boolean wasSet = false;
    if (aNewAutoRepairShopSytem == null)
    {
      //Unable to setAutoRepairShopSytem to null, as owner must always be associated to a autoRepairShopSytem
      return wasSet;
    }
    
    Owner existingOwner = aNewAutoRepairShopSytem.getOwner();
    if (existingOwner != null && !equals(existingOwner))
    {
      //Unable to setAutoRepairShopSytem, the current autoRepairShopSytem already has a owner, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    AutoRepairShopSytem anOldAutoRepairShopSytem = autoRepairShopSytem;
    autoRepairShopSytem = aNewAutoRepairShopSytem;
    autoRepairShopSytem.setOwner(this);

    if (anOldAutoRepairShopSytem != null)
    {
      anOldAutoRepairShopSytem.setOwner(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    AutoRepairShopSytem existingAutoRepairShopSytem = autoRepairShopSytem;
    autoRepairShopSytem = null;
    if (existingAutoRepairShopSytem != null)
    {
      existingAutoRepairShopSytem.setOwner(null);
    }
    super.delete();
  }

}