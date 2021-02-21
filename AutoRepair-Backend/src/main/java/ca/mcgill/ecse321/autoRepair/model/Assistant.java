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
  private AutoRepairShopSytem autoRepairShopSytem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Assistant(String aUsername, String aPassword, AutoRepairShopSytem aAutoRepairShopSytem)
  {
    super(aUsername, aPassword);
    reminders = new ArrayList<Reminder>();
    boolean didAddAutoRepairShopSytem = setAutoRepairShopSytem(aAutoRepairShopSytem);
    if (!didAddAutoRepairShopSytem)
    {
      throw new RuntimeException("Unable to create assistant due to autoRepairShopSytem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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

  @OneToMany
  public List<Reminder> getReminders()
  {
    List<Reminder> newReminders = Collections.unmodifiableList(reminders);
    return newReminders;
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
  @OneToOne
  public AutoRepairShopSytem getAutoRepairShopSytem()
  {
    return autoRepairShopSytem;
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
  public boolean setAutoRepairShopSytem(AutoRepairShopSytem aNewAutoRepairShopSytem)
  {
    boolean wasSet = false;
    if (aNewAutoRepairShopSytem == null)
    {
      //Unable to setAutoRepairShopSytem to null, as assistant must always be associated to a autoRepairShopSytem
      return wasSet;
    }
    
    Assistant existingAssistant = aNewAutoRepairShopSytem.getAssistant();
    if (existingAssistant != null && !equals(existingAssistant))
    {
      //Unable to setAutoRepairShopSytem, the current autoRepairShopSytem already has a assistant, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    AutoRepairShopSytem anOldAutoRepairShopSytem = autoRepairShopSytem;
    autoRepairShopSytem = aNewAutoRepairShopSytem;
    autoRepairShopSytem.setAssistant(this);

    if (anOldAutoRepairShopSytem != null)
    {
      anOldAutoRepairShopSytem.setAssistant(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    reminders.clear();
    AutoRepairShopSytem existingAutoRepairShopSytem = autoRepairShopSytem;
    autoRepairShopSytem = null;
    if (existingAutoRepairShopSytem != null)
    {
      existingAutoRepairShopSytem.setAssistant(null);
    }
    super.delete();
  }

}