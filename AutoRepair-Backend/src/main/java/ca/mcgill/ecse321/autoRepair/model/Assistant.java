
  
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.autoRepair.model;
import javax.persistence.*;
import java.util.*;

// line 60 "../../../../../AutoRepair.ump"
// line 184 "../../../../../AutoRepair.ump"
@Entity
public class Assistant extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Assistant Associations
  private List<Reminder> reminders;
  private AutoRepairShopSystem AutoRepairShopSystem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Assistant(){ super();}

  public Assistant(String aUsername, String aPassword, AutoRepairShopSystem aAutoRepairShopSystem)
  {
    super(aUsername, aPassword);
    reminders = new ArrayList<Reminder>();
    boolean didAddAutoRepairShopSystem = setAutoRepairShopSystem(aAutoRepairShopSystem);
    if (!didAddAutoRepairShopSystem)
    {
      throw new RuntimeException("Unable to create assistant due to AutoRepairShopSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Reminder getReminder(int index)
  {
    Reminder aReminder = reminders.get(index);
    return aReminder;
  }

  @OneToMany(cascade = { CascadeType.ALL })
  public List<Reminder> getReminders()
  {
    if(reminders==null) return null;
    List<Reminder> newReminders = Collections.unmodifiableList(reminders);
    return newReminders;
  }

  public void setReminders(List<Reminder> reminders) {
	  this.reminders=reminders;
  }
  
  public int numberOfReminders()
  {
    int number = reminders.size();
    return number;
  }

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
  @OneToOne(fetch = FetchType.LAZY)
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

  public boolean removeReminder(Reminder aReminder)
  {
    boolean wasRemoved = false;
    if (reminders.contains(aReminder))
    {
      reminders.remove(aReminder);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addReminderAt(Reminder aReminder, int index)
  {  
    boolean wasAdded = false;
    if(addReminder(aReminder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReminders()) { index = numberOfReminders() - 1; }
      reminders.remove(aReminder);
      reminders.add(index, aReminder);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveReminderAt(Reminder aReminder, int index)
  {
    boolean wasAdded = false;
    if(reminders.contains(aReminder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReminders()) { index = numberOfReminders() - 1; }
      reminders.remove(aReminder);
      reminders.add(index, aReminder);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addReminderAt(aReminder, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setAutoRepairShopSystem(AutoRepairShopSystem aNewAutoRepairShopSystem)
  {
    boolean wasSet = false;
    if (aNewAutoRepairShopSystem == null)
    {
      //Unable to setAutoRepairShopSystem to null, as assistant must always be associated to a AutoRepairShopSystem
      return wasSet;
    }
    
    Assistant existingAssistant = aNewAutoRepairShopSystem.getAssistant();
    if (existingAssistant != null && !equals(existingAssistant))
    {
      //Unable to setAutoRepairShopSystem, the current AutoRepairShopSystem already has a assistant, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    AutoRepairShopSystem anOldAutoRepairShopSystem = AutoRepairShopSystem;
    AutoRepairShopSystem = aNewAutoRepairShopSystem;
    AutoRepairShopSystem.setAssistant(this);

    if (anOldAutoRepairShopSystem != null)
    {
      anOldAutoRepairShopSystem.setAssistant(null);
    }
    wasSet = true;
    return wasSet;
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
    super.delete();
  }

}
