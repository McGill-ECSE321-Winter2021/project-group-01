package ca.mcgill.ecse321.autoRepair.model;
import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;


@Entity
@Table(name = "slots")
public class TimeSlot
{


  private long id;
  private Date startDate;
  private Time startTime;
  private Date endDate;
  private Time endTime;
  private AutoRepairShopSystem AutoRepairShopSystem;

  public TimeSlot(Date aStartDate, Time aStartTime, Date aEndDate, Time aEndTime, AutoRepairShopSystem aAutoRepairShopSystem)
  {
    startDate = aStartDate;
    startTime = aStartTime;
    endDate = aEndDate;
    endTime = aEndTime;
    this.AutoRepairShopSystem=aAutoRepairShopSystem;
  }

  public TimeSlot() {

  }


  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Time getStartTime() {
    return startTime;
  }

  public void setStartTime(Time startTime) {
    this.startTime = startTime;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public Time getEndTime() {
    return endTime;
  }

  public void setEndTime(Time endTime) {
    this.endTime = endTime;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  public AutoRepairShopSystem getAutoRepairShopSystem() {
    return AutoRepairShopSystem;
  }

  public void setAutoRepairShopSystem(AutoRepairShopSystem autoRepairShopSystem) {
    AutoRepairShopSystem = autoRepairShopSystem;
  }




  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startDate" + "=" + (getStartDate() != null ? !getStartDate().equals(this)  ? getStartDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endDate" + "=" + (getEndDate() != null ? !getEndDate().equals(this)  ? getEndDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator");
  }
}