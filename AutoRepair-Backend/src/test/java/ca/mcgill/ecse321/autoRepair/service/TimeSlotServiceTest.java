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

import ca.mcgill.ecse321.autoRepair.dao.*;
import ca.mcgill.ecse321.autoRepair.model.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;


import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TimeSlotServiceTest {

    @Mock
    OperatingHourRepository operatingHourRepository;

    @Mock
    TimeSlotRepository timeSlotRepository;

    @InjectMocks
    TimeSlotService timeSlotService;

    private static final String STRING_DATE = "2021-04-30";
    private static final String STRING_START_TIME1 = "12:00:00";
    private static final String STRING_START_TIME2 = "14:00:00";
    private static final String STRING_START_TIME3 = "15:00:00";
    private static final String STRING_END_TIME1 = "13:00:00";
    private static final String STRING_END_TIME2 = "14:30:00";
    private static final String STRING_END_TIME3 = "16:30:00";

    private static final OperatingHour.DayOfWeek DAY_OF_WEEK = OperatingHour.DayOfWeek.Friday;
    private static final String DAY_START_TIME_STRING = "06:00:00";
    private static final String DAY_END_TIME_STRING = "20:00:00";

    @BeforeEach
    public void setMockOutput() {
        lenient().when(timeSlotRepository.findTimeSlotsByDate(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(STRING_DATE)) {
                List<TimeSlot> timeSlotList = new ArrayList<>();

                String date = STRING_DATE;

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

                return timeSlotList;

            } else {
                return null;
            }
        });

        lenient().when(operatingHourRepository.findByDayOfWeek(any(OperatingHour.DayOfWeek.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(DAY_OF_WEEK)) {
                OperatingHour operatingHour = new OperatingHour();
                operatingHour.setDayOfWeek(DAY_OF_WEEK);
                operatingHour.setStartTime(Time.valueOf(DAY_START_TIME_STRING));
                operatingHour.setEndTime(Time.valueOf(DAY_END_TIME_STRING));
                return operatingHour;
            } else {
                return null;
            }
        });
    }

    @Test
    public void getAvailableTimeSlot(){
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
}
