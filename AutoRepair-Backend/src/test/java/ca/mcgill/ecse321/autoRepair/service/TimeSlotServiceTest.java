package ca.mcgill.ecse321.autoRepair.service;

import ca.mcgill.ecse321.autoRepair.dao.OperatingHourRepository;
import ca.mcgill.ecse321.autoRepair.dao.TimeSlotRepository;
import ca.mcgill.ecse321.autoRepair.model.OperatingHour;
import ca.mcgill.ecse321.autoRepair.model.TimeSlot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

@ExtendWith(MockitoExtension.class)
public class TimeSlotServiceTest {

    @Mock
    OperatingHourRepository operatingHourRepository;

    @Mock
    TimeSlotRepository timeSlotRepository;

    @InjectMocks
    TimeSlotService timeSlotService;

    private static final String STRING_DATE_FRIDAY = "2021-04-30";
    private static final String STRING_START_TIME1 = "12:00:00";
    private static final String STRING_START_TIME2 = "14:00:00";
    private static final String STRING_START_TIME3 = "15:00:00";
    private static final String STRING_END_TIME1 = "13:00:00";
    private static final String STRING_END_TIME2 = "14:30:00";
    private static final String STRING_END_TIME3 = "16:30:00";

    private static final String STRING_DATE_MONDAY = "2021-05-03";
    private static final String STRING_START_TIME_NEW1 = "06:00:00";
    private static final String STRING_END_TIME_NEW1 = "07:00:00";
    private static final String STRING_START_TIME_NEW2 = "19:00:00";
    private static final String STRING_END_TIME_NEW2 = "20:00:00";

    private static final String STRING_DATE_TUESDAY = "2021-05-04";
    private static final String STRING_DATE_WEDNESDAY = "2021-05-05";
    private static final String STRING_DATE_THURSDAY = "2021-05-06";
    private static final String STRING_DATE_SATURDAY = "2021-05-08";
    private static final String STRING_DATE_SUNDAY = "2021-05-09";

    private static final OperatingHour.DayOfWeek FRIDAY = OperatingHour.DayOfWeek.Friday;
    private static final OperatingHour.DayOfWeek SATURDAY = OperatingHour.DayOfWeek.Saturday;
    private static final OperatingHour.DayOfWeek SUNDAY = OperatingHour.DayOfWeek.Sunday;
    private static final OperatingHour.DayOfWeek MONDAY = OperatingHour.DayOfWeek.Monday;
    private static final OperatingHour.DayOfWeek TUESDAY = OperatingHour.DayOfWeek.Tuesday;
    private static final OperatingHour.DayOfWeek WEDNESDAY = OperatingHour.DayOfWeek.Wednesday;
    private static final OperatingHour.DayOfWeek THURSDAY = OperatingHour.DayOfWeek.Thursday;
    private static final String DAY_START_TIME_STRING = "06:00:00";
    private static final String DAY_END_TIME_STRING = "20:00:00";

    @BeforeEach
    public void setMockOutput() {
        lenient().when(timeSlotRepository.findTimeSlotsByDate(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(STRING_DATE_FRIDAY) || invocation.getArgument(0).equals(STRING_DATE_SATURDAY) || invocation.getArgument(0).equals(STRING_DATE_SUNDAY) ||
                    invocation.getArgument(0).equals(STRING_DATE_TUESDAY) || invocation.getArgument(0).equals(STRING_DATE_WEDNESDAY) || invocation.getArgument(0).equals(STRING_DATE_THURSDAY)
            || invocation.getArgument(0).equals(STRING_DATE_MONDAY)) {
                List<TimeSlot> timeSlotList = new ArrayList<>();
                String date = null;

                if(invocation.getArgument(0).equals(STRING_DATE_MONDAY)){
                    date = STRING_DATE_MONDAY;
                    TimeSlot timeSlot1 = new TimeSlot();
                    timeSlot1.setEndDate(Date.valueOf(date));
                    timeSlot1.setStartDate(Date.valueOf(date));
                    timeSlot1.setStartTime(Time.valueOf(STRING_START_TIME_NEW1));
                    timeSlot1.setEndTime(Time.valueOf(STRING_END_TIME_NEW1));
                    timeSlotList.add(timeSlot1);

                    TimeSlot timeSlot2 = new TimeSlot();
                    timeSlot2.setEndDate(Date.valueOf(date));
                    timeSlot2.setStartDate(Date.valueOf(date));
                    timeSlot2.setStartTime(Time.valueOf(STRING_START_TIME_NEW2));
                    timeSlot2.setEndTime(Time.valueOf(STRING_END_TIME_NEW2));
                    timeSlotList.add(timeSlot2);

                }else {
                    if (invocation.getArgument(0).equals(STRING_DATE_FRIDAY)) {
                        date = STRING_DATE_FRIDAY;
                    }
                    if (invocation.getArgument(0).equals(STRING_DATE_SATURDAY)) {
                        date = STRING_DATE_SATURDAY;
                    }
                    if (invocation.getArgument(0).equals(STRING_DATE_SUNDAY)) {
                        date = STRING_DATE_SUNDAY;
                    }
                    if (invocation.getArgument(0).equals(STRING_DATE_TUESDAY)) {
                        date = STRING_DATE_TUESDAY;
                    }
                    if (invocation.getArgument(0).equals(STRING_DATE_WEDNESDAY)) {
                        date = STRING_DATE_WEDNESDAY;
                    }
                    if (invocation.getArgument(0).equals(STRING_DATE_THURSDAY)) {
                        date = STRING_DATE_THURSDAY;
                    }

                    TimeSlot timeSlot1 = new TimeSlot();
                    timeSlot1.setEndDate(Date.valueOf(date));
                    timeSlot1.setStartDate(Date.valueOf(date));
                    timeSlot1.setStartTime(Time.valueOf(STRING_START_TIME1));
                    timeSlot1.setEndTime(Time.valueOf(STRING_END_TIME1));
                    timeSlotList.add(timeSlot1);

                    TimeSlot timeSlot2 = new TimeSlot();
                    timeSlot2.setEndDate(Date.valueOf(date));
                    timeSlot2.setStartDate(Date.valueOf(date));
                    timeSlot2.setStartTime(Time.valueOf(STRING_START_TIME2));
                    timeSlot2.setEndTime(Time.valueOf(STRING_END_TIME2));
                    timeSlotList.add(timeSlot2);

                    TimeSlot timeSlot3 = new TimeSlot();
                    timeSlot3.setEndDate(Date.valueOf(date));
                    timeSlot3.setStartDate(Date.valueOf(date));
                    timeSlot3.setStartTime(Time.valueOf(STRING_START_TIME3));
                    timeSlot3.setEndTime(Time.valueOf(STRING_END_TIME3));
                    timeSlotList.add(timeSlot3);

                }
                return timeSlotList;

            } else {
                return null;
            }
        });

        lenient().when(operatingHourRepository.findByDayOfWeek(any(OperatingHour.DayOfWeek.class))).thenAnswer((InvocationOnMock invocation) -> {
            OperatingHour operatingHour = new OperatingHour();
            if (invocation.getArgument(0).equals(MONDAY)) {
                operatingHour.setDayOfWeek(MONDAY);
            }else if (invocation.getArgument(0).equals(TUESDAY)) {
                operatingHour.setDayOfWeek(TUESDAY);
            }else if (invocation.getArgument(0).equals(WEDNESDAY)) {
                operatingHour.setDayOfWeek(WEDNESDAY);
            }else if (invocation.getArgument(0).equals(THURSDAY)) {
                operatingHour.setDayOfWeek(THURSDAY);
            }else if (invocation.getArgument(0).equals(FRIDAY)) {
                operatingHour.setDayOfWeek(FRIDAY);
            }else if (invocation.getArgument(0).equals(SATURDAY)) {
                operatingHour.setDayOfWeek(SATURDAY);
            }else if (invocation.getArgument(0).equals(SUNDAY)) {
                operatingHour.setDayOfWeek(SUNDAY);
            } else {
                return null;
            }
            operatingHour.setStartTime(Time.valueOf(DAY_START_TIME_STRING));
            operatingHour.setEndTime(Time.valueOf(DAY_END_TIME_STRING));
            return operatingHour;
        });
    }

    @Test
    public void getAvailableTimeSlotNotOpeningAndClosingHours(){
        Date startDate=Date.valueOf("2021-04-30");
        Time startTime1 = Time.valueOf("06:00:00");
        Time endTime1 = Time.valueOf("12:00:00");

        Time startTime2 = Time.valueOf("13:00:00");
        Time endTime2 = Time.valueOf("14:00:00");

        Time startTime3 = Time.valueOf("14:30:00");
        Time endTime3 = Time.valueOf("15:00:00");

        Time startTime4 = Time.valueOf("16:30:00");
        Time endTime4 = Time.valueOf("20:00:00");

        List<TimeSlot> timeSlotList = null;

        try{
            timeSlotList = timeSlotService.getAvailableTimeSlots(startDate);
        }catch (IllegalArgumentException e){
            fail();
        }

        assertNotNull(timeSlotList);

        assertEquals(startDate, timeSlotList.get(0).getStartDate());
        assertEquals(startDate, timeSlotList.get(0).getEndDate());
        assertEquals(startTime1, timeSlotList.get(0).getStartTime());
        assertEquals(endTime1, timeSlotList.get(0).getEndTime());

        assertEquals(startDate, timeSlotList.get(1).getStartDate());
        assertEquals(startDate, timeSlotList.get(1).getEndDate());
        assertEquals(startTime2, timeSlotList.get(1).getStartTime());
        assertEquals(endTime2, timeSlotList.get(1).getEndTime());

        assertEquals(startDate, timeSlotList.get(2).getStartDate());
        assertEquals(startDate, timeSlotList.get(2).getEndDate());
        assertEquals(startTime3, timeSlotList.get(2).getStartTime());
        assertEquals(endTime3, timeSlotList.get(2).getEndTime());

        assertEquals(startDate, timeSlotList.get(3).getStartDate());
        assertEquals(startDate, timeSlotList.get(3).getEndDate());
        assertEquals(startTime4, timeSlotList.get(3).getStartTime());
        assertEquals(endTime4, timeSlotList.get(3).getEndTime());

    }
    @Test
    public void getAvailableTimeSlotOpeningAndClosingHours(){
        Date startDate=Date.valueOf("2021-05-03");
        Time startTime1 = Time.valueOf("07:00:00");
        Time endTime1 = Time.valueOf("19:00:00");

        List<TimeSlot> timeSlotList = null;

        try{
            timeSlotList = timeSlotService.getAvailableTimeSlots(startDate);
        }catch (IllegalArgumentException e){
            fail();
        }

        assertNotNull(timeSlotList);

        assertEquals(startDate, timeSlotList.get(0).getStartDate());
        assertEquals(startDate, timeSlotList.get(0).getEndDate());
        assertEquals(startTime1, timeSlotList.get(0).getStartTime());
        assertEquals(endTime1, timeSlotList.get(0).getEndTime());

    }


    @Test
    public void getUnavailableTimeSlotsTest(){
        Date startDate=Date.valueOf("2021-04-30");
        Time startTime1 = Time.valueOf("12:00:00");
        Time endTime1 = Time.valueOf("13:00:00");

        Time startTime2 = Time.valueOf("14:00:00");
        Time endTime2 = Time.valueOf("14:30:00");

        Time startTime3 = Time.valueOf("15:00:00");
        Time endTime3 = Time.valueOf("16:30:00");

        List<TimeSlot> timeSlotList = null;

        try{
            timeSlotList = timeSlotService.getUnavailableTimeSlots(startDate);
        }catch (IllegalArgumentException e){
            fail();
        }

        assertNotNull(timeSlotList);

        assertEquals(startDate, timeSlotList.get(0).getStartDate());
        assertEquals(startDate, timeSlotList.get(0).getEndDate());
        assertEquals(startTime1, timeSlotList.get(0).getStartTime());
        assertEquals(endTime1, timeSlotList.get(0).getEndTime());

        assertEquals(startDate, timeSlotList.get(1).getStartDate());
        assertEquals(startDate, timeSlotList.get(1).getEndDate());
        assertEquals(startTime2, timeSlotList.get(1).getStartTime());
        assertEquals(endTime2, timeSlotList.get(1).getEndTime());

        assertEquals(startDate, timeSlotList.get(2).getStartDate());
        assertEquals(startDate, timeSlotList.get(2).getEndDate());
        assertEquals(startTime3, timeSlotList.get(2).getStartTime());
        assertEquals(endTime3, timeSlotList.get(2).getEndTime());
    }

    @Test
    public void getAvailableTimeSlotTuesday(){
        Date startDate=Date.valueOf("2021-05-04");
        Time startTime1 = Time.valueOf("06:00:00");
        Time endTime1 = Time.valueOf("12:00:00");

        Time startTime2 = Time.valueOf("13:00:00");
        Time endTime2 = Time.valueOf("14:00:00");

        Time startTime3 = Time.valueOf("14:30:00");
        Time endTime3 = Time.valueOf("15:00:00");

        Time startTime4 = Time.valueOf("16:30:00");
        Time endTime4 = Time.valueOf("20:00:00");

        List<TimeSlot> timeSlotList = null;

        try{
            timeSlotList = timeSlotService.getAvailableTimeSlots(startDate);
        }catch (IllegalArgumentException e){
            fail();
        }

        assertNotNull(timeSlotList);

        assertEquals(startDate, timeSlotList.get(0).getStartDate());
        assertEquals(startDate, timeSlotList.get(0).getEndDate());
        assertEquals(startTime1, timeSlotList.get(0).getStartTime());
        assertEquals(endTime1, timeSlotList.get(0).getEndTime());

        assertEquals(startDate, timeSlotList.get(1).getStartDate());
        assertEquals(startDate, timeSlotList.get(1).getEndDate());
        assertEquals(startTime2, timeSlotList.get(1).getStartTime());
        assertEquals(endTime2, timeSlotList.get(1).getEndTime());

        assertEquals(startDate, timeSlotList.get(2).getStartDate());
        assertEquals(startDate, timeSlotList.get(2).getEndDate());
        assertEquals(startTime3, timeSlotList.get(2).getStartTime());
        assertEquals(endTime3, timeSlotList.get(2).getEndTime());

        assertEquals(startDate, timeSlotList.get(3).getStartDate());
        assertEquals(startDate, timeSlotList.get(3).getEndDate());
        assertEquals(startTime4, timeSlotList.get(3).getStartTime());
        assertEquals(endTime4, timeSlotList.get(3).getEndTime());

    }
    @Test
    public void getAvailableTimeSlotWednesday(){
        Date startDate=Date.valueOf("2021-05-05");
        Time startTime1 = Time.valueOf("06:00:00");
        Time endTime1 = Time.valueOf("12:00:00");

        Time startTime2 = Time.valueOf("13:00:00");
        Time endTime2 = Time.valueOf("14:00:00");

        Time startTime3 = Time.valueOf("14:30:00");
        Time endTime3 = Time.valueOf("15:00:00");

        Time startTime4 = Time.valueOf("16:30:00");
        Time endTime4 = Time.valueOf("20:00:00");

        List<TimeSlot> timeSlotList = null;

        try{
            timeSlotList = timeSlotService.getAvailableTimeSlots(startDate);
        }catch (IllegalArgumentException e){
            fail();
        }

        assertNotNull(timeSlotList);

        assertEquals(startDate, timeSlotList.get(0).getStartDate());
        assertEquals(startDate, timeSlotList.get(0).getEndDate());
        assertEquals(startTime1, timeSlotList.get(0).getStartTime());
        assertEquals(endTime1, timeSlotList.get(0).getEndTime());

        assertEquals(startDate, timeSlotList.get(1).getStartDate());
        assertEquals(startDate, timeSlotList.get(1).getEndDate());
        assertEquals(startTime2, timeSlotList.get(1).getStartTime());
        assertEquals(endTime2, timeSlotList.get(1).getEndTime());

        assertEquals(startDate, timeSlotList.get(2).getStartDate());
        assertEquals(startDate, timeSlotList.get(2).getEndDate());
        assertEquals(startTime3, timeSlotList.get(2).getStartTime());
        assertEquals(endTime3, timeSlotList.get(2).getEndTime());

        assertEquals(startDate, timeSlotList.get(3).getStartDate());
        assertEquals(startDate, timeSlotList.get(3).getEndDate());
        assertEquals(startTime4, timeSlotList.get(3).getStartTime());
        assertEquals(endTime4, timeSlotList.get(3).getEndTime());

    }
    @Test
    public void getAvailableTimeSlotThursday(){
        Date startDate=Date.valueOf("2021-05-06");
        Time startTime1 = Time.valueOf("06:00:00");
        Time endTime1 = Time.valueOf("12:00:00");

        Time startTime2 = Time.valueOf("13:00:00");
        Time endTime2 = Time.valueOf("14:00:00");

        Time startTime3 = Time.valueOf("14:30:00");
        Time endTime3 = Time.valueOf("15:00:00");

        Time startTime4 = Time.valueOf("16:30:00");
        Time endTime4 = Time.valueOf("20:00:00");

        List<TimeSlot> timeSlotList = null;

        try{
            timeSlotList = timeSlotService.getAvailableTimeSlots(startDate);
        }catch (IllegalArgumentException e){
            fail();
        }

        assertNotNull(timeSlotList);

        assertEquals(startDate, timeSlotList.get(0).getStartDate());
        assertEquals(startDate, timeSlotList.get(0).getEndDate());
        assertEquals(startTime1, timeSlotList.get(0).getStartTime());
        assertEquals(endTime1, timeSlotList.get(0).getEndTime());

        assertEquals(startDate, timeSlotList.get(1).getStartDate());
        assertEquals(startDate, timeSlotList.get(1).getEndDate());
        assertEquals(startTime2, timeSlotList.get(1).getStartTime());
        assertEquals(endTime2, timeSlotList.get(1).getEndTime());

        assertEquals(startDate, timeSlotList.get(2).getStartDate());
        assertEquals(startDate, timeSlotList.get(2).getEndDate());
        assertEquals(startTime3, timeSlotList.get(2).getStartTime());
        assertEquals(endTime3, timeSlotList.get(2).getEndTime());

        assertEquals(startDate, timeSlotList.get(3).getStartDate());
        assertEquals(startDate, timeSlotList.get(3).getEndDate());
        assertEquals(startTime4, timeSlotList.get(3).getStartTime());
        assertEquals(endTime4, timeSlotList.get(3).getEndTime());

    }
    @Test
    public void getAvailableTimeSlotSaturday(){
        Date startDate=Date.valueOf("2021-05-08");
        Time startTime1 = Time.valueOf("06:00:00");
        Time endTime1 = Time.valueOf("12:00:00");

        Time startTime2 = Time.valueOf("13:00:00");
        Time endTime2 = Time.valueOf("14:00:00");

        Time startTime3 = Time.valueOf("14:30:00");
        Time endTime3 = Time.valueOf("15:00:00");

        Time startTime4 = Time.valueOf("16:30:00");
        Time endTime4 = Time.valueOf("20:00:00");

        List<TimeSlot> timeSlotList = null;

        try{
            timeSlotList = timeSlotService.getAvailableTimeSlots(startDate);
        }catch (IllegalArgumentException e){
            fail();
        }

        assertNotNull(timeSlotList);

        assertEquals(startDate, timeSlotList.get(0).getStartDate());
        assertEquals(startDate, timeSlotList.get(0).getEndDate());
        assertEquals(startTime1, timeSlotList.get(0).getStartTime());
        assertEquals(endTime1, timeSlotList.get(0).getEndTime());

        assertEquals(startDate, timeSlotList.get(1).getStartDate());
        assertEquals(startDate, timeSlotList.get(1).getEndDate());
        assertEquals(startTime2, timeSlotList.get(1).getStartTime());
        assertEquals(endTime2, timeSlotList.get(1).getEndTime());

        assertEquals(startDate, timeSlotList.get(2).getStartDate());
        assertEquals(startDate, timeSlotList.get(2).getEndDate());
        assertEquals(startTime3, timeSlotList.get(2).getStartTime());
        assertEquals(endTime3, timeSlotList.get(2).getEndTime());

        assertEquals(startDate, timeSlotList.get(3).getStartDate());
        assertEquals(startDate, timeSlotList.get(3).getEndDate());
        assertEquals(startTime4, timeSlotList.get(3).getStartTime());
        assertEquals(endTime4, timeSlotList.get(3).getEndTime());

    }
    @Test
    public void getAvailableTimeSlotSunday(){
        Date startDate=Date.valueOf("2021-05-09");
        Time startTime1 = Time.valueOf("06:00:00");
        Time endTime1 = Time.valueOf("12:00:00");

        Time startTime2 = Time.valueOf("13:00:00");
        Time endTime2 = Time.valueOf("14:00:00");

        Time startTime3 = Time.valueOf("14:30:00");
        Time endTime3 = Time.valueOf("15:00:00");

        Time startTime4 = Time.valueOf("16:30:00");
        Time endTime4 = Time.valueOf("20:00:00");

        List<TimeSlot> timeSlotList = null;

        try{
            timeSlotList = timeSlotService.getAvailableTimeSlots(startDate);
        }catch (IllegalArgumentException e){
            fail();
        }

        assertNotNull(timeSlotList);

        assertEquals(startDate, timeSlotList.get(0).getStartDate());
        assertEquals(startDate, timeSlotList.get(0).getEndDate());
        assertEquals(startTime1, timeSlotList.get(0).getStartTime());
        assertEquals(endTime1, timeSlotList.get(0).getEndTime());

        assertEquals(startDate, timeSlotList.get(1).getStartDate());
        assertEquals(startDate, timeSlotList.get(1).getEndDate());
        assertEquals(startTime2, timeSlotList.get(1).getStartTime());
        assertEquals(endTime2, timeSlotList.get(1).getEndTime());

        assertEquals(startDate, timeSlotList.get(2).getStartDate());
        assertEquals(startDate, timeSlotList.get(2).getEndDate());
        assertEquals(startTime3, timeSlotList.get(2).getStartTime());
        assertEquals(endTime3, timeSlotList.get(2).getEndTime());

        assertEquals(startDate, timeSlotList.get(3).getStartDate());
        assertEquals(startDate, timeSlotList.get(3).getEndDate());
        assertEquals(startTime4, timeSlotList.get(3).getStartTime());
        assertEquals(endTime4, timeSlotList.get(3).getEndTime());

    }
}
