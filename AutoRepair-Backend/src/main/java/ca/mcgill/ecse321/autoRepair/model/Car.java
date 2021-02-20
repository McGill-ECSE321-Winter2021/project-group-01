/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.autoRepair.model;
import java.util.*;

// line 46 "../../../../../AutoRepair.ump"
// line 160 "../../../../../AutoRepair.ump"
public class Car
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum CarTransmission { Manual, Automatic }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Car> carsByPlateNumber = new HashMap<String, Car>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Car Attributes
  private String model;
  private CarTransmission transmission;
  private String plateNumber;

  //Car Associations
  private Customer customer;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Car(String aModel, CarTransmission aTransmission, String aPlateNumber, Customer aCustomer)
  {
    model = aModel;
    transmission = aTransmission;
    if (!setPlateNumber(aPlateNumber))
    {
      throw new RuntimeException("Cannot create due to duplicate plateNumber. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddCustomer = setCustomer(aCustomer);
    if (!didAddCustomer)
    {
      throw new RuntimeException("Unable to create car due to customer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setModel(String aModel)
  {
    boolean wasSet = false;
    model = aModel;
    wasSet = true;
    return wasSet;
  }

  public boolean setTransmission(CarTransmission aTransmission)
  {
    boolean wasSet = false;
    transmission = aTransmission;
    wasSet = true;
    return wasSet;
  }

  public boolean setPlateNumber(String aPlateNumber)
  {
    boolean wasSet = false;
    String anOldPlateNumber = getPlateNumber();
    if (anOldPlateNumber != null && anOldPlateNumber.equals(aPlateNumber)) {
      return true;
    }
    if (hasWithPlateNumber(aPlateNumber)) {
      return wasSet;
    }
    plateNumber = aPlateNumber;
    wasSet = true;
    if (anOldPlateNumber != null) {
      carsByPlateNumber.remove(anOldPlateNumber);
    }
    carsByPlateNumber.put(aPlateNumber, this);
    return wasSet;
  }

  public String getModel()
  {
    return model;
  }

  public CarTransmission getTransmission()
  {
    return transmission;
  }

  public String getPlateNumber()
  {
    return plateNumber;
  }
  /* Code from template attribute_GetUnique */
  public static Car getWithPlateNumber(String aPlateNumber)
  {
    return carsByPlateNumber.get(aPlateNumber);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithPlateNumber(String aPlateNumber)
  {
    return getWithPlateNumber(aPlateNumber) != null;
  }
  /* Code from template association_GetOne */
  public Customer getCustomer()
  {
    return customer;
  }
  /* Code from template association_SetOneToMandatoryMany */
  public boolean setCustomer(Customer aCustomer)
  {
    boolean wasSet = false;
    //Must provide customer to car
    if (aCustomer == null)
    {
      return wasSet;
    }

    if (customer != null && customer.numberOfCars() <= Customer.minimumNumberOfCars())
    {
      return wasSet;
    }

    Customer existingCustomer = customer;
    customer = aCustomer;
    if (existingCustomer != null && !existingCustomer.equals(aCustomer))
    {
      boolean didRemove = existingCustomer.removeCar(this);
      if (!didRemove)
      {
        customer = existingCustomer;
        return wasSet;
      }
    }
    customer.addCar(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    carsByPlateNumber.remove(getPlateNumber());
    Customer placeholderCustomer = customer;
    this.customer = null;
    if(placeholderCustomer != null)
    {
      placeholderCustomer.removeCar(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "model" + ":" + getModel()+ "," +
            "plateNumber" + ":" + getPlateNumber()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "transmission" + "=" + (getTransmission() != null ? !getTransmission().equals(this)  ? getTransmission().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null");
  }
}