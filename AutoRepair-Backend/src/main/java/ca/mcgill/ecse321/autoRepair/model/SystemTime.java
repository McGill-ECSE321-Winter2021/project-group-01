package ca.mcgill.ecse321.autoRepair.model;
import java.time.LocalDate;
import java.time.LocalTime;
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

    public static  void setSysDateAndTime(String string){
        String date = string.substring(0, 10);
        String time = string.substring(11, 16);
        String[] dateInArray = date.split("-");
        String[] timeInArray = time.split(":");
        LocalDate localDate = LocalDate.of(Integer.parseInt(dateInArray[0]), Integer.parseInt(dateInArray[1]), Integer.parseInt(dateInArray[2]));
        LocalTime localTime = LocalTime.of(Integer.parseInt(timeInArray[0]), Integer.parseInt(timeInArray[1]));
        systemDate = Date.valueOf(localDate);
        systemTime = Time.valueOf(localTime);
    }

    private static Date systemDate;

    private static Time systemTime;


}