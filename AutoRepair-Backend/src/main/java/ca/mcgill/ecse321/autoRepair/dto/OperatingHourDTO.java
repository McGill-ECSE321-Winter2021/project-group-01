package ca.mcgill.ecse321.autoRepair.dto;

import java.sql.Time;

import ca.mcgill.ecse321.autoRepair.model.OperatingHour.DayOfWeek;

public class OperatingHourDTO
{


  //------------------------
  // STATIC VARIABLES
  //------------------------

  private DayOfWeek dayOfWeek;
  private Time startTime;
  private Time endTime;
  
  public OperatingHourDTO(DayOfWeek dayOfWeek, Time startTime, Time endTime) {
	  this.dayOfWeek=dayOfWeek;
	  this.startTime=startTime;
	  this.endTime=endTime;
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
