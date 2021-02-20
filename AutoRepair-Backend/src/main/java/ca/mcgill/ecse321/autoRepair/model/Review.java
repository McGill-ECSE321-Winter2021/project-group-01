/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.autoRepair.model;
import javax.persistence.*;

// line 119 "../../../../../AutoRepair.ump"
// line 217 "../../../../../AutoRepair.ump"
@Entity
public class Review
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Review Attributes
  private String description;
  private int serviceRating;

  //Review Associations
  private AutoRepairShopSytem autoRepairShopSytem;
  private Customer customer;
  private BookableService bookableService;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Review(String aDescription, int aServiceRating, AutoRepairShopSytem aAutoRepairShopSytem, Customer aCustomer, BookableService aBookableService)
  {
    description = aDescription;
    serviceRating = aServiceRating;
    boolean didAddAutoRepairShopSytem = setAutoRepairShopSytem(aAutoRepairShopSytem);
    if (!didAddAutoRepairShopSytem)
    {
      throw new RuntimeException("Unable to create review due to autoRepairShopSytem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddCustomer = setCustomer(aCustomer);
    if (!didAddCustomer)
    {
      throw new RuntimeException("Unable to create review due to customer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddBookableService = setBookableService(aBookableService);
    if (!didAddBookableService)
    {
      throw new RuntimeException("Unable to create review due to bookableService. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public boolean setServiceRating(int aServiceRating)
  {
    boolean wasSet = false;
    serviceRating = aServiceRating;
    wasSet = true;
    return wasSet;
  }

  public String getDescription()
  {
    return description;
  }

  public int getServiceRating()
  {
    return serviceRating;
  }
  /* Code from template association_GetOne */
  public AutoRepairShopSytem getAutoRepairShopSytem()
  {
    return autoRepairShopSytem;
  }
  /* Code from template association_GetOne */
  @ManyToOne
  public Customer getCustomer()
  {
    return customer;
  }
  /* Code from template association_GetOne */
  @ManyToOne
  public BookableService getBookableService()
  {
    return bookableService;
  }
  /* Code from template association_SetOneToMany */
  public boolean setAutoRepairShopSytem(AutoRepairShopSytem aAutoRepairShopSytem)
  {
    boolean wasSet = false;
    if (aAutoRepairShopSytem == null)
    {
      return wasSet;
    }

    AutoRepairShopSytem existingAutoRepairShopSytem = autoRepairShopSytem;
    autoRepairShopSytem = aAutoRepairShopSytem;
    if (existingAutoRepairShopSytem != null && !existingAutoRepairShopSytem.equals(aAutoRepairShopSytem))
    {
      existingAutoRepairShopSytem.removeReview(this);
    }
    autoRepairShopSytem.addReview(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCustomer(Customer aCustomer)
  {
    boolean wasSet = false;
    if (aCustomer == null)
    {
      return wasSet;
    }

    Customer existingCustomer = customer;
    customer = aCustomer;
    if (existingCustomer != null && !existingCustomer.equals(aCustomer))
    {
      existingCustomer.removeReview(this);
    }
    customer.addReview(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setBookableService(BookableService aBookableService)
  {
    boolean wasSet = false;
    if (aBookableService == null)
    {
      return wasSet;
    }

    BookableService existingBookableService = bookableService;
    bookableService = aBookableService;
    if (existingBookableService != null && !existingBookableService.equals(aBookableService))
    {
      existingBookableService.removeReview(this);
    }
    bookableService.addReview(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    AutoRepairShopSytem placeholderAutoRepairShopSytem = autoRepairShopSytem;
    this.autoRepairShopSytem = null;
    if(placeholderAutoRepairShopSytem != null)
    {
      placeholderAutoRepairShopSytem.removeReview(this);
    }
    Customer placeholderCustomer = customer;
    this.customer = null;
    if(placeholderCustomer != null)
    {
      placeholderCustomer.removeReview(this);
    }
    BookableService placeholderBookableService = bookableService;
    this.bookableService = null;
    if(placeholderBookableService != null)
    {
      placeholderBookableService.removeReview(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "description" + ":" + getDescription()+ "," +
            "serviceRating" + ":" + getServiceRating()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "autoRepairShopSytem = "+(getAutoRepairShopSytem()!=null?Integer.toHexString(System.identityHashCode(getAutoRepairShopSytem())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "bookableService = "+(getBookableService()!=null?Integer.toHexString(System.identityHashCode(getBookableService())):"null");
  }
}