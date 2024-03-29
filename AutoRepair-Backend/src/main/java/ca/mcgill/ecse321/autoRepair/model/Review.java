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
  private ChosenService chosenService;

  @OneToOne
  public Appointment getAppointment() {
    return appointment;
  }

  public void setAppointment(Appointment appointment) {
    this.appointment = appointment;
  }

  private Appointment appointment;

  public Review() {

  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
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




  @ManyToOne
  public Customer getCustomer()
  {
    return customer;
  }

  public void setCustomer(Customer aCustomer)
  {
    this.customer=aCustomer;
  }

  @ManyToOne
  public ChosenService getChosenService()
  {
    return chosenService;
  }

  public void setChosenService(ChosenService aService)
  {
    this.chosenService=aService;
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "description" + ":" + getDescription()+ "," +
            "serviceRating" + ":" + getServiceRating()+ "]" + System.getProperties().getProperty("line.separator") + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "bookableService = "+(getChosenService()!=null?Integer.toHexString(System.identityHashCode(getChosenService())):"null");
  }
}