/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.autoRepair.model;
import java.util.*;

// line 37 "../../../../../AutoRepair.ump"
// line 155 "../../../../../AutoRepair.ump"
public class Profile
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Profile Attributes
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

  public Profile(String aFirstName, String aLastName, String aAddress, String aZipCode, String aPhoneNumber, String aEmail, Customer aCustomer)
  {
    firstName = aFirstName;
    lastName = aLastName;
    address = aAddress;
    zipCode = aZipCode;
    phoneNumber = aPhoneNumber;
    email = aEmail;
    if (aCustomer == null || aCustomer.getProfile() != null)
    {
      throw new RuntimeException("Unable to create Profile due to aCustomer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    customer = aCustomer;
  }

  public Profile(String aFirstName, String aLastName, String aAddress, String aZipCode, String aPhoneNumber, String aEmail, String aUsernameForCustomer, String aPasswordForCustomer, int aNoShowForCustomer, int aShowForCustomer, AutoRepairShopSytem aAutoRepairShopSytemForCustomer)
  {
    firstName = aFirstName;
    lastName = aLastName;
    address = aAddress;
    zipCode = aZipCode;
    phoneNumber = aPhoneNumber;
    email = aEmail;
    customer = new Customer(aUsernameForCustomer, aPasswordForCustomer, aNoShowForCustomer, aShowForCustomer, this, aAutoRepairShopSytemForCustomer);
  }

  //------------------------
  // INTERFACE
  //------------------------

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
  public Customer getCustomer()
  {
    return customer;
  }

  public void delete()
  {
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
            "firstName" + ":" + getFirstName()+ "," +
            "lastName" + ":" + getLastName()+ "," +
            "address" + ":" + getAddress()+ "," +
            "zipCode" + ":" + getZipCode()+ "," +
            "phoneNumber" + ":" + getPhoneNumber()+ "," +
            "email" + ":" + getEmail()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null");
  }
}