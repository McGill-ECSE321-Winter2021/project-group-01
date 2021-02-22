package ca.mcgill.ecse321.autoRepair.model;
import javax.persistence.*;

@Entity
@Table(name = "reviews")
public class Review
{

  private Long id;
  private String description;
  private int serviceRating;
  private Customer customer;
  private BookableService bookableService;


//  public Review(String aDescription, int aServiceRating, AutoRepairShopSystem aAutoRepairShopSystem, Customer aCustomer, BookableService aBookableService)
//  {
//    description = aDescription;
//    serviceRating = aServiceRating;
//    this.AutoRepairShopSystem=aAutoRepairShopSystem;
//    this.customer=aCustomer;
//    this.bookableService=aBookableService;
//  }

  public Review() {

  }

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getServiceRating() {
    return serviceRating;
  }

  public void setServiceRating(int serviceRating) {
    this.serviceRating = serviceRating;
  }




  @ManyToOne(fetch = FetchType.LAZY)
  public Customer getCustomer()
  {
    return customer;
  }

  public void setCustomer(Customer aCustomer)
  {
    this.customer=aCustomer;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  public BookableService getBookableService()
  {
    return bookableService;
  }

  public void setBookableService(BookableService aBookableService)
  {
    this.bookableService=aBookableService;
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "description" + ":" + getDescription()+ "," +
            "serviceRating" + ":" + getServiceRating()+ "]" + System.getProperties().getProperty("line.separator") + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "bookableService = "+(getBookableService()!=null?Integer.toHexString(System.identityHashCode(getBookableService())):"null");
  }
}