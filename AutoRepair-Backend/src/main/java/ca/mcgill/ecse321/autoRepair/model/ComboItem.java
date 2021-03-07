package ca.mcgill.ecse321.autoRepair.model;
import javax.persistence.*;

@Entity
@Table(name = "ComboItems")
public class ComboItem
{
  private Long id;
 
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "mandatory" + ":" + getMandatory()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "service = "+(getService()!=null?Integer.toHexString(System.identityHashCode(getService())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "serviceCombo = "+(getServiceCombo()!=null?Integer.toHexString(System.identityHashCode(getServiceCombo())):"null");
  }


}