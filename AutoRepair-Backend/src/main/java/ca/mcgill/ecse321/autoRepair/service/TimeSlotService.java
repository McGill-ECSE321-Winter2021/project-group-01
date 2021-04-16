package ca.mcgill.ecse321.autoRepair.service;

import ca.mcgill.ecse321.autoRepair.dao.OperatingHourRepository;
import ca.mcgill.ecse321.autoRepair.dao.TimeSlotRepository;
import ca.mcgill.ecse321.autoRepair.model.OperatingHour;
import ca.mcgill.ecse321.autoRepair.model.TimeSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class TimeSlotService {

    @Autowired
    TimeSlotRepository timeSlotRepository;

    @Autowired
    OperatingHourRepository operatingHourRepository;

    /**
     * @author Tamara Zard Aboujaoudeh
     * Gets all the available time slots
     * @param startDate
     * @return list containing all the available time slots
     */
    @Transactional
    public List<TimeSlot> getAvailableTimeSlots(Date startDate) {
        List<TimeSlot> availableTimeSlots = new ArrayList<>();
        Locale locale = new Locale("en");
        OperatingHour operatingHour = operatingHourRepository.findByDayOfWeek(getDayString(startDate, locale));
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setStartTime(operatingHour.getStartTime());
        timeSlot.setEndTime(operatingHour.getEndTime());
        timeSlot.setStartDate(startDate);
        timeSlot.setEndDate(startDate);
        availableTimeSlots.add(timeSlot);

        List<TimeSlot> timeSlotsPerDay = timeSlotRepository.findTimeSlotsByStartDate(startDate);
        for(TimeSlot timeSlot1: timeSlotsPerDay){
            for (int j = 0; j < availableTimeSlots.size(); j++) {
               TimeSlot timeSlot2 = availableTimeSlots.get(j);
                if (isOverlap(timeSlot1, timeSlot2)) {

                    LocalTime startTime1 = timeSlot1.getStartTime().toLocalTime();
                    LocalTime startTime2 = timeSlot2.getStartTime().toLocalTime();
                    LocalTime endTime1 = timeSlot1.getEndTime().toLocalTime();
                    LocalTime endTime2 = timeSlot2.getEndTime().toLocalTime();

                    TimeSlot temporaryTimeSlot = new TimeSlot();

                    if (startTime1.compareTo(startTime2) == 0 && endTime1.compareTo(endTime2) == 0) {
                        availableTimeSlots.remove(timeSlot2);
                    } else if (startTime1.compareTo(startTime2) == 0) {
                        temporaryTimeSlot=calculateTimeSlot(startDate,timeSlot1.getEndTime(),startDate,timeSlot2.getEndTime());
                        availableTimeSlots.add(temporaryTimeSlot);
                        availableTimeSlots.remove(timeSlot2);
                    } else if (endTime1.compareTo(endTime2) == 0) {
                        temporaryTimeSlot=calculateTimeSlot(startDate,timeSlot2.getStartTime(),startDate,timeSlot1.getStartTime());
                        availableTimeSlots.add(temporaryTimeSlot);
                        availableTimeSlots.remove(timeSlot2);
                    } else {
                        temporaryTimeSlot=calculateTimeSlot(startDate,timeSlot2.getStartTime(),startDate,timeSlot1.getStartTime());
                        TimeSlot temporaryTimeSlot1 = calculateTimeSlot(startDate,timeSlot1.getEndTime(),startDate,timeSlot2.getEndTime());
                        availableTimeSlots.remove(timeSlot2);
                        availableTimeSlots.add(temporaryTimeSlot);
                        availableTimeSlots.add(temporaryTimeSlot1);
                    }
                }
            }
        }
        return availableTimeSlots;
    }

    /**
     * @author Tamara Zard Aboujaoudeh
     * Gets a list of all the unavailable time slots
     * @param date
     * @return list containing all the unavailable time slots
     */
    @Transactional
    public List<TimeSlot> getUnavailableTimeSlots(Date date){
        return timeSlotRepository.findTimeSlotsByStartDate(date);
    }

    /**
     * This method checks if two time slots are overlapping
     * @param timeSlot1
     * @param timeSlot2
     * @return
     */
    private static boolean isOverlap(TimeSlot timeSlot1, TimeSlot timeSlot2) {
        LocalTime startTime1 = timeSlot1.getStartTime().toLocalTime();
        LocalTime startTime2 = timeSlot2.getStartTime().toLocalTime();
        LocalTime endTime1 = timeSlot1.getEndTime().toLocalTime();
        LocalTime endTime2 = timeSlot2.getEndTime().toLocalTime();

        return startTime1.isBefore(endTime2) && startTime2.isBefore(endTime1);
    }

    /**
     * This method gets the day of the week of a specific date
     * @param date
     * @param locale
     * @return
     */
    public static OperatingHour.DayOfWeek getDayString(Date date, Locale locale) {
        DateFormat formatter = new SimpleDateFormat("EEEE", locale);
        String stringDate = formatter.format(date);
        if(OperatingHour.DayOfWeek.Friday.toString().equals(stringDate)){
            return OperatingHour.DayOfWeek.Friday;
        }
        else if(OperatingHour.DayOfWeek.Thursday.toString().equals(stringDate)){
            return OperatingHour.DayOfWeek.Thursday;
        }
        else if(OperatingHour.DayOfWeek.Saturday.toString().equals(stringDate)){
            return OperatingHour.DayOfWeek.Saturday;
        }
        else if(OperatingHour.DayOfWeek.Sunday.toString().equals(stringDate)){
            return OperatingHour.DayOfWeek.Sunday;
        }
        else if(OperatingHour.DayOfWeek.Wednesday.toString().equals(stringDate)){
            return OperatingHour.DayOfWeek.Wednesday;
        }
        else if(OperatingHour.DayOfWeek.Tuesday.toString().equals(stringDate)){
            return OperatingHour.DayOfWeek.Tuesday;
        }
        else if(OperatingHour.DayOfWeek.Monday.toString().equals(stringDate)){
            return OperatingHour.DayOfWeek.Monday;
        }
        return null;
    }

    /**
     * This method gets a time slot by the start date and start time
     * @param startDate
     * @param startTime
     * @return
     */
    @Transactional
    public TimeSlot getTimeSlot(Date startDate, Time startTime){
        return timeSlotRepository.findTimeSlotByStartDateAndStartTime(startDate,startTime);
    }

    /**
     * This helper method calculates a time slot
     * @param startDate
     * @param startTime
     * @param endDate
     * @param endTime
     * @return
     */
    private TimeSlot calculateTimeSlot(Date startDate, Time startTime, Date endDate, Time endTime){
        TimeSlot timeSlot=new TimeSlot();
        timeSlot.setStartDate(startDate);
        timeSlot.setStartTime(startTime);
        timeSlot.setEndDate(endDate);
        timeSlot.setEndTime(endTime);
        return timeSlot;
    }

}