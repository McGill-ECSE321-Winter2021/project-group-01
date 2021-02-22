package ca.mcgill.ecse321.autoRepair.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
public class Assistant extends User {
  public Assistant(){
    super();
  }

  @OneToMany
  public List<Reminder> getReminders() {
    return reminders;
  }

  public void setReminders(List<Reminder> reminders) {
    this.reminders = reminders;
  }

<<<<<<< Updated upstream
  public boolean hasReminders()
  {
    boolean has = reminders.size() > 0;
    return has;
  }

  public int indexOfReminder(Reminder aReminder)
  {
    int index = reminders.indexOf(aReminder);
    return index;
  }
  /* Code from template association_GetOne */
//  @OneToOne(fetch = FetchType.LAZY)
  @OneToOne(cascade=CascadeType.ALL)
  public AutoRepairShopSystem getAutoRepairShopSystem()
  {
    return AutoRepairShopSystem;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfReminders()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addReminder(Reminder aReminder)
  {
    boolean wasAdded = false;
    if (reminders.contains(aReminder)) { return false; }
    reminders.add(aReminder);
    wasAdded = true;
    return wasAdded;
  }
=======
  private List<Reminder> reminders;
>>>>>>> Stashed changes

  @OneToOne
  public AutoRepairShopSystem getAutoRepairShopSystem() {
    return autoRepairShopSystem;
  }

  public void setAutoRepairShopSystem(AutoRepairShopSystem autoRepairShopSystem) {
    this.autoRepairShopSystem = autoRepairShopSystem;
  }
<<<<<<< Updated upstream
  /* Code from template association_SetOneToOptionalOne */
  @Transient
  public boolean isSetAutoRepairShopSystem(AutoRepairShopSystem aNewAutoRepairShopSystem)
  {
    boolean wasSet = false;
    if (aNewAutoRepairShopSystem == null)
    {
      //Unable to setAutoRepairShopSystem to null, as assistant must always be associated to a AutoRepairShopSystem
      return wasSet;
    }
    if(aNewAutoRepairShopSystem.getAssistant()==null) {
      Assistant existingAssistant = aNewAutoRepairShopSystem.getAssistant();
      if (existingAssistant != null && !equals(existingAssistant)) {
        //Unable to setAutoRepairShopSystem, the current AutoRepairShopSystem already has a assistant, which would be orphaned if it were re-assigned
        return wasSet;
      }

      AutoRepairShopSystem anOldAutoRepairShopSystem = AutoRepairShopSystem;
      AutoRepairShopSystem = aNewAutoRepairShopSystem;
      AutoRepairShopSystem.setAssistant(this);

      if (anOldAutoRepairShopSystem != null) {
        anOldAutoRepairShopSystem.setAssistant(null);
      }
      wasSet = true;
    }
    return wasSet;
  }
  
  public void setAutoRepairShopSystem(AutoRepairShopSystem autoRepair) {
	  this.AutoRepairShopSystem=autoRepair;
  }

  public void delete()
  {
    reminders.clear();
    AutoRepairShopSystem existingAutoRepairShopSystem = AutoRepairShopSystem;
    AutoRepairShopSystem = null;
    if (existingAutoRepairShopSystem != null)
    {
      existingAutoRepairShopSystem.setAssistant(null);
    }
    //super.delete();
  }
=======

  private AutoRepairShopSystem autoRepairShopSystem;

>>>>>>> Stashed changes

}