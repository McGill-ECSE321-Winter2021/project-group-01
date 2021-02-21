/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.autoRepair.model;
import javax.persistence.*;
import java.util.*;

// line 119 "../../../../../AutoRepair.ump"
// line 219 "../../../../../AutoRepair.ump"
@Entity
public class ComboItem
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, ComboItem> comboitemsById = new HashMap<String, ComboItem>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ComboItem Attributes
  private String id;
  private boolean mandatory;

  //ComboItem Associations
  private Service service;
  private ServiceCombo serviceCombo;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ComboItem(String aId, boolean aMandatory, Service aService, ServiceCombo aServiceCombo)
  {
    mandatory = aMandatory;
    if (!setId(aId))
    {
      throw new RuntimeException("Cannot create due to duplicate id. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    if (!setService(aService))
    {
      throw new RuntimeException("Unable to create ComboItem due to aService. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddServiceCombo = setServiceCombo(aServiceCombo);
    if (!didAddServiceCombo)
    {
      throw new RuntimeException("Unable to create service due to serviceCombo. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
      comboitemsById.remove(anOldId);
    }
    comboitemsById.put(aId, this);
    return wasSet;
  }

  public boolean setMandatory(boolean aMandatory)
  {
    boolean wasSet = false;
    mandatory = aMandatory;
    wasSet = true;
    return wasSet;
  }

  @Id
  public String getId()
  {
    return id;
  }
  /* Code from template attribute_GetUnique */
  public static ComboItem getWithId(String aId)
  {
    return comboitemsById.get(aId);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithId(String aId)
  {
    return getWithId(aId) != null;
  }

  public boolean getMandatory()
  {
    return mandatory;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isMandatory()
  {
    return mandatory;
  }
  /* Code from template association_GetOne */
  public Service getService()
  {
    return service;
  }
  /* Code from template association_GetOne */
  public ServiceCombo getServiceCombo()
  {
    return serviceCombo;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setService(Service aNewService)
  {
    boolean wasSet = false;
    if (aNewService != null)
    {
      service = aNewService;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetOneToMandatoryMany */
  public boolean setServiceCombo(ServiceCombo aServiceCombo)
  {
    boolean wasSet = false;
    //Must provide serviceCombo to service
    if (aServiceCombo == null)
    {
      return wasSet;
    }

    if (serviceCombo != null && serviceCombo.numberOfServices() <= ServiceCombo.minimumNumberOfServices())
    {
      return wasSet;
    }

    ServiceCombo existingServiceCombo = serviceCombo;
    serviceCombo = aServiceCombo;
    if (existingServiceCombo != null && !existingServiceCombo.equals(aServiceCombo))
    {
      boolean didRemove = existingServiceCombo.removeService(this);
      if (!didRemove)
      {
        serviceCombo = existingServiceCombo;
        return wasSet;
      }
    }
    serviceCombo.addService(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    comboitemsById.remove(getId());
    service = null;
    ServiceCombo placeholderServiceCombo = serviceCombo;
    this.serviceCombo = null;
    if(placeholderServiceCombo != null)
    {
      placeholderServiceCombo.removeService(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "mandatory" + ":" + getMandatory()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "service = "+(getService()!=null?Integer.toHexString(System.identityHashCode(getService())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "serviceCombo = "+(getServiceCombo()!=null?Integer.toHexString(System.identityHashCode(getServiceCombo())):"null");
  }
}