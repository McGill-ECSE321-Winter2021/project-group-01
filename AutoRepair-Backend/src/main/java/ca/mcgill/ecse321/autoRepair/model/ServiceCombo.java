package ca.mcgill.ecse321.autoRepair.model;
import javax.persistence.*;
import java.util.*;


@Entity
public class ServiceCombo extends BookableService
{


  private ComboItem mainService;
  private List<ComboItem> services;


//  public ServiceCombo(String aName, AutoRepairShopSystem aAutoRepairShopSystem)
//  {
//    super(aName, aAutoRepairShopSystem);
//    services = new ArrayList<ComboItem>();
//  }


  public ServiceCombo() {
    super();
  }

  @ManyToOne(fetch = FetchType.LAZY)
  public ComboItem getMainService()
  {
    return mainService;
  }


  public void setMainService(ComboItem aNewMainService)
  {
    this.mainService = aNewMainService;
  }



  @OneToMany(cascade = CascadeType.ALL, mappedBy = "serviceCombo")
  public List<ComboItem> getServices()
  {
    return this.services;
  }

  public void setServices(List<ComboItem> services) {
    this.services=services;
  }




}