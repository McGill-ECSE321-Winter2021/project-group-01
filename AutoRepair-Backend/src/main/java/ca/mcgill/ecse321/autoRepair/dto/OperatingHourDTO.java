package ca.mcgill.ecse321.autoRepair.dto;

import java.sql.Time;

import ca.mcgill.ecse321.autoRepair.model.OperatingHour.DayOfWeek;

public class OperatingHourDTO
{


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
