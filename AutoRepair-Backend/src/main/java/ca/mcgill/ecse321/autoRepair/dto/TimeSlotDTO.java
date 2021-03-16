package ca.mcgill.ecse321.autoRepair.dto;

import java.sql.Date;
import java.sql.Time;

public class TimeSlotDTO {
    private Date startDate;
    private Time startTime;
    private Date endDate;
    private Time endTime;

    public TimeSlotDTO(){}

    public TimeSlotDTO(Time startTime, Time endTime, Date startDate, Date endDate){
        this.startDate=startDate;
        this.startTime=startTime;
        this.endDate=endDate;
        this.endTime=endTime;
    }

    public Date getStartDate(){
        return this.startDate;
    }
    public Date getEndDate(){
        return this.endDate;
    }
    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

}
