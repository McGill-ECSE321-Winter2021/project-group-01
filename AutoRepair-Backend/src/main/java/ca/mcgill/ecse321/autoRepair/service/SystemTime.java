package ca.mcgill.ecse321.autoRepair.service;
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

    public static  Date getSysDate(){
        return sysDate;
    }

    public static  Time getSysTime(){
        return sysTime;
    }

    public static  void setSysDate(Date date){
        sysDate = date;
    }

    public static  void setSysTime(Time time){
        sysTime = time;
    }

    public static  void setSysDateAndTime(String s){
        String date = s.substring(0, 10);
        String time = s.substring(11, 16);
        String[] dateInArray = date.split("-");
        String[] timeInArray = time.split(":");
        LocalDate localDate = LocalDate.of(Integer.parseInt(dateInArray[0]), Integer.parseInt(dateInArray[1]), Integer.parseInt(dateInArray[2]));
        LocalTime localTime = LocalTime.of(Integer.parseInt(timeInArray[0]), Integer.parseInt(timeInArray[1]));
        sysDate = Date.valueOf(localDate);
        sysTime = Time.valueOf(localTime);
    }

    private static Date sysDate;

    private static Time sysTime;


}