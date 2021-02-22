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
  private long id;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  public long getId() {
	return id;
}

public void setId(long id) {
	this.id = id;
}


private boolean mandatory;
public boolean getMandatory() {
	return mandatory;
}

public void setMandatory(boolean mandatory) {
	this.mandatory = mandatory;
}

  private Service service;
  @ManyToOne
  public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}
  private ServiceCombo serviceCombo;
  
  @ManyToOne
  public ServiceCombo getServiceCombo() {
	return serviceCombo;
}

public void setServiceCombo(ServiceCombo serviceCombo) {
	this.serviceCombo = serviceCombo;
}

public ComboItem() {
	
}
                  


  public void delete()
  {
    comboitemsById.remove(getId());
    setService(null);
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