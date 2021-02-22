package ca.mcgill.ecse321.autoRepair.model;
import javax.persistence.*;

@Entity
public class Owner extends User
{

  private AutoRepairShopSystem AutoRepairShopSystem;



  public Owner(String aUsername, String aPassword, AutoRepairShopSystem aAutoRepairShopSystem)
  {
    super(aUsername, aPassword);
    this.AutoRepairShopSystem=aAutoRepairShopSystem;
  }

  public Owner() {
    super();
  }


  @OneToOne(fetch = FetchType.LAZY)
  public AutoRepairShopSystem getAutoRepairShopSystem()
  {
    return AutoRepairShopSystem;
  }

  public void setAutoRepairShopSystem(AutoRepairShopSystem autoRepairShopSystem) {
    AutoRepairShopSystem = autoRepairShopSystem;
  }

}