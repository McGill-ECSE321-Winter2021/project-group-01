package ca.mcgill.ecse321.autoRepair.model;
import javax.persistence.*;
import java.sql.Time;

// line 79 "../../../../../AutoRepair.ump"
// line 194 "../../../../../AutoRepair.ump"
@Entity
public class OperatingHour
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum DayOfWeek { Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private Long id;
  private DayOfWeek dayOfWeek;
  private Time startTime;
  private Time endTime;

  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public DayOfWeek getDayOfWeek() {
    return dayOfWeek;
  }

  public void setDayOfWeek(DayOfWeek dayOfWeek) {
    this.dayOfWeek = dayOfWeek;
  }

  public Time getStartTime() {
    return startTime;
  }

  public void setStartTime(Time startTime) {
    this.startTime = startTime;
  }

  public Time getEndTime() {
    return endTime;
  }

  public void setEndTime(Time endTime) {
    this.endTime = endTime;
  }

  public OperatingHour() {
  }


}