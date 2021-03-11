package ca.mcgill.ecse321.autoRepair.dto;

import java.sql.Time;

public class OperatingHourDTO
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
  
  public OperatingHourDTO(Long id, DayOfWeek dayOfWeek, Time startTime, Time endTime) {
	  this.id=id;
	  this.dayOfWeek=dayOfWeek;
	  this.startTime=startTime;
	  this.endTime=endTime;
  }
  public Long getId() {
    return id;
  }

  public DayOfWeek getDayOfWeek() {
    return dayOfWeek;
  }
  
  public Time getStartTime() {
    return startTime;
  }

  public Time getEndTime() {
    return endTime;
  }

}
