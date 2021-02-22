package ca.mcgill.ecse321.autoRepair.model;
import javax.persistence.*;
import java.util.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name="reminders")
public class Reminder
{

  private Long id;



  private String description;
  private Date date;
  private Time time;
  private Customer customer;
  private List<BookableService> bookableServices;


//  public Reminder(long aId, String aDescription, Date aDate, Time aTime, AutoRepairShopSystem aAutoRepairShopSystem, Customer aCustomer)
//  {
//    description = aDescription;
//    date = aDate;
//    time = aTime;
//    this.AutoRepairShopSystem=aAutoRepairShopSystem;
//    this.customer=aCustomer;
//    this.bookableServices=new ArrayList<BookableService>();
//  }

  public Reminder() {
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

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Time getTime() {
    return time;
  }

  public void setTime(Time time) {
    this.time = time;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  public Customer getCustomer()
  {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }


  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "reminders")
  public List<BookableService> getBookableServices()
  {
    return this.bookableServices;
  }

  public void setBookableServices(List<BookableService> bookableServices) {
    this.bookableServices=bookableServices;
  }



  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "time" + "=" + (getTime() != null ? !getTime().equals(this)  ? getTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator")
            + System.getProperties().getProperty("line.separator") + "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null");
  }
}