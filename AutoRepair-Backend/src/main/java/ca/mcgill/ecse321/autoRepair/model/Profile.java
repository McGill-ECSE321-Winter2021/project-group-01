/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.autoRepair.model;
import javax.persistence.*;
import java.util.*;

// line 41 "../../../../../AutoRepair.ump"
// line 174 "../../../../../AutoRepair.ump"
@Entity
@Table(name = "profiles")
public class Profile
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Profile> profilesById = new HashMap<String, Profile>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Profile Attributes
  private String id;
  private String firstName;
  private String lastName;
  private String address;
  private String zipCode;
  private String phoneNumber;
  private String email;

  //Profile Associations
  private Customer customer;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Profile(String aId, String aFirstName, String aLastName, String aAddress, String aZipCode, String aPhoneNumber, String aEmail)
  {
    firstName = aFirstName;
    lastName = aLastName;
    address = aAddress;
    zipCode = aZipCode;
    phoneNumber = aPhoneNumber;
    email = aEmail;
    if (!setId(aId))
    {
      throw new RuntimeException("Cannot create due to duplicate id. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
  }

  public Profile(String aId, String aFirstName, String aLastName, String aAddress, String aZipCode, String aPhoneNumber, String aEmail, String aUsernameForCustomer, String aPasswordForCustomer, int aNoShowForCustomer, int aShowForCustomer, AutoRepairShopSystem aAutoRepairShopSystemForCustomer)
  {
    if (!setId(aId))
    {
      throw new RuntimeException("Cannot create due to duplicate id. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    firstName = aFirstName;
    lastName = aLastName;
    address = aAddress;
    zipCode = aZipCode;
    phoneNumber = aPhoneNumber;
    email = aEmail;
    customer = new Customer(aUsernameForCustomer, aPasswordForCustomer, aNoShowForCustomer, aShowForCustomer, this, aAutoRepairShopSystemForCustomer);
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
      profilesById.remove(anOldId);
    }
    profilesById.put(aId, this);
    return wasSet;
  }

  public boolean setFirstName(String aFirstName)
  {
    boolean wasSet = false;
    firstName = aFirstName;
    wasSet = true;
    return wasSet;
  }

  public boolean setLastName(String aLastName)
  {
    boolean wasSet = false;
    lastName = aLastName;
    wasSet = true;
    return wasSet;
  }

  public boolean setAddress(String aAddress)
  {
    boolean wasSet = false;
    address = aAddress;
    wasSet = true;
    return wasSet;
  }

  public boolean setZipCode(String aZipCode)
  {
    boolean wasSet = false;
    zipCode = aZipCode;
    wasSet = true;
    return wasSet;
  }

  public boolean setPhoneNumber(String aPhoneNumber)
  {
    boolean wasSet = false;
    phoneNumber = aPhoneNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  public String getId()
  {
    return id;
  }
  /* Code from template attribute_GetUnique */
  public static Profile getWithId(String aId)
  {
    return profilesById.get(aId);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithId(String aId)
  {
    return getWithId(aId) != null;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public String getAddress()
  {
    return address;
  }

  public String getZipCode()
  {
    return zipCode;
  }

  public String getPhoneNumber()
  {
    return phoneNumber;
  }

  public String getEmail()
  {
    return email;
  }
  /* Code from template association_GetOne */
  @OneToOne(fetch = FetchType.LAZY)
  public Customer getCustomer()
  {
    return customer;
  }
  
  public void setCustomer(Customer customer) {
	  this.customer=customer;
  }

  public void delete()
  {
    profilesById.remove(getId());
    Customer existingCustomer = customer;
    customer = null;
    if (existingCustomer != null)
    {
      existingCustomer.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "firstName" + ":" + getFirstName()+ "," +
            "lastName" + ":" + getLastName()+ "," +
            "address" + ":" + getAddress()+ "," +
            "zipCode" + ":" + getZipCode()+ "," +
            "phoneNumber" + ":" + getPhoneNumber()+ "," +
            "email" + ":" + getEmail()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null");
  }
}