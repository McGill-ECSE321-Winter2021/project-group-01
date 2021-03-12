package ca.mcgill.ecse321.autoRepair.model;
import javax.persistence.*;
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
  private ChosenService chosenService;

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

  @ManyToOne(fetch = FetchType.EAGER)
  public Customer getCustomer()
  {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }


  @ManyToOne(fetch = FetchType.LAZY)
  public ChosenService getChosenService()
  {
    return this.chosenService;
  }

  public void setChosenService(ChosenService service) {
    this.chosenService=service;
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