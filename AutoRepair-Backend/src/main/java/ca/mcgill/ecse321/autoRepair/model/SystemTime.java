package ca.mcgill.ecse321.autoRepair.model;
import java.sql.Date;
import java.sql.Time;


public class SystemTime
{

    public SystemTime()
    {}

    public void delete()
    {}

    public static  Date getSystemDate(){
        return systemDate;
    }

    public static  Time getSystemTime(){
        return systemTime;
    }

    public static  void setSystemDate(Date date){
        systemDate = date;
    }

    public static  void setSystemTime(Time time){
        systemTime = time;
    }

    private static Date systemDate;

    private static Time systemTime;


}