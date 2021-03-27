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
        TimeSlot ts = new TimeSlot();
        ts.setStartTime(operatingHour.getStartTime());
        ts.setEndTime(operatingHour.getEndTime());
        ts.setStartDate(startDate);
        ts.setEndDate(startDate);
        availableTimeSlots.add(ts);

        List<TimeSlot> timeSlotsPerDay = timeSlotRepository.findTimeSlotsByStartDate(startDate);
        for(TimeSlot aTS: timeSlotsPerDay){
            for (int j = 0; j < availableTimeSlots.size(); j++) {
               TimeSlot TS = availableTimeSlots.get(j);
                if (isOverlap(aTS, TS)) {

                    LocalTime S1 = aTS.getStartTime().toLocalTime();
                    LocalTime S2 = TS.getStartTime().toLocalTime();
                    LocalTime E1 = aTS.getEndTime().toLocalTime();
                    LocalTime E2 = TS.getEndTime().toLocalTime();

                    if (S1.compareTo(S2) == 0 && E1.compareTo(E2) == 0) {
                        availableTimeSlots.remove(TS);
                    } else if (S1.compareTo(S2) == 0) {
                        TimeSlot tmp = new TimeSlot();
                        tmp.setEndTime(TS.getEndTime());
                        tmp.setStartTime(aTS.getEndTime());
                        tmp.setEndDate(startDate);
                        tmp.setStartDate(startDate);
                        availableTimeSlots.add(tmp);
                        availableTimeSlots.remove(TS);
                    } else if (E1.compareTo(E2) == 0) {
                        TimeSlot tmp = new TimeSlot();
                        tmp.setEndTime(aTS.getStartTime());
                        tmp.setStartTime(TS.getStartTime());
                        tmp.setEndDate(startDate);
                        tmp.setStartDate(startDate);
                        availableTimeSlots.add(tmp);
                        availableTimeSlots.remove(TS);
                    } else {
                        TimeSlot tmp1 = new TimeSlot();
                        tmp1.setEndTime(aTS.getStartTime());
                        tmp1.setStartTime(TS.getStartTime());
                        tmp1.setEndDate(startDate);
                        tmp1.setStartDate(startDate);
                        TimeSlot tmp2 = new TimeSlot();
                        tmp2.setEndTime(TS.getEndTime());
                        tmp2.setStartTime(aTS.getEndTime());
                        tmp2.setEndDate(startDate);
                        tmp2.setStartDate(startDate);
                        availableTimeSlots.remove(TS);
                        availableTimeSlots.add(tmp1);
                        availableTimeSlots.add(tmp2);
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

    private static boolean isOverlap(TimeSlot TS1, TimeSlot TS2) {
        LocalTime S1 = TS1.getStartTime().toLocalTime();
        LocalTime S2 = TS2.getStartTime().toLocalTime();
        LocalTime E1 = TS1.getEndTime().toLocalTime();
        LocalTime E2 = TS2.getEndTime().toLocalTime();

        return S1.isBefore(E2) && S2.isBefore(E1);
    }


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
    
    @Transactional
    public TimeSlot getTimeSlot(Date date, Time time) {
    	return timeSlotRepository.findTimeSlotByStartDateAndStartTime(date, time);
    }

}