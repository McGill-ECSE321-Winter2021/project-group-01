package ca.mcgill.ecse321.autoRepair.model;
import javax.persistence.*;
import java.util.*;
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

  //private static Map<String, OperatingHour> operatinghoursById = new HashMap<String, OperatingHour>();

//  public static Map<String, OperatingHour> getOperatinghoursById() {
//    return operatinghoursById;
//  }
//
//  public static void setOperatinghoursById(Map<String, OperatingHour> operatinghoursById) {
//    OperatingHour.operatinghoursById = operatinghoursById;
//  }

  private String id;
  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE)
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  private DayOfWeek dayOfWeek;

  public DayOfWeek getDayOfWeek() {
    return dayOfWeek;
  }

  public void setDayOfWeek(DayOfWeek dayOfWeek) {
    this.dayOfWeek = dayOfWeek;
  }


  private Time startTime;
  public Time getStartTime() {
    return startTime;
  }

  public void setStartTime(Time startTime) {
    this.startTime = startTime;
  }

  private Time endTime;

  public Time getEndTime() {
    return endTime;
  }

  public void setEndTime(Time endTime) {
    this.endTime = endTime;
  }

  public OperatingHour() {
  }


}