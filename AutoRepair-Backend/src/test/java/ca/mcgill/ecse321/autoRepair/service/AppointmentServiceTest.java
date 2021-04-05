package ca.mcgill.ecse321.autoRepair.service;

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

import ca.mcgill.ecse321.autoRepair.model.Car.CarTransmission;


@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private CustomerRepository cusRepo;

    @Mock
    private TimeSlotRepository timeSlotRepository;

    @Mock
    private ChosenServiceRepository chosenServiceRepository;

    @Mock
    private OperatingHourRepository operatingHourRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    private static final String CUSTOMER_USERNAME = "TestCustomer";
    private static final String CUSTOMER_PASSWORD = "TestPassword123";

    private static final String PROFILE_EMAIL = "TestCustomer@mail.com";
    private static final String PROFILE_FIRSTNAME = "Bob";
    private static final String PROFILE_LASTNAME = "Fisher";
    private static final String PROFILE_ADDRESS = "1000, MEMORY LANE";
    private static final String PROFILE_PHONE = "5141234567";
    private static final String PROFILE_ZIP = "55555";

    private static final String CAR_MODEL = "BMW X6";
    private static final String CAR_PLATE = "123 ABC";
    private static final CarTransmission CAR_TRANSMISSION = CarTransmission.Automatic;

    private static final String SERVICE_NAME = "TestService";
    private static final int SERVICE_DURATION = 10;

    private static final String SERVICE_NAME2 = "Test Service 2";
    private static final int SERVICE_DURATION2 = 30;

    private static final String STRING_DATE = "2021-04-30";
    private static final Date DATE = Date.valueOf(STRING_DATE);
    private static final String TODAY_DATE_STRING = "2021-03-31";
    private static final Date TODAY_DATE = Date.valueOf(TODAY_DATE_STRING);
    private static final String STRING_START_TIME1 = "12:00:00";
    private static final String STRING_START_TIME2 = "14:00:00";
    private static final String STRING_START_TIME3 = "15:00:00";
    private static final String STRING_END_TIME1 = "13:00:00";
    private static final String STRING_END_TIME2 = "14:30:00";
    private static final String STRING_END_TIME3 = "16:30:00";

    private static final OperatingHour.DayOfWeek DAY_OF_WEEK = OperatingHour.DayOfWeek.Friday;
    private static final OperatingHour.DayOfWeek DAY_OF_WEEK2 = OperatingHour.DayOfWeek.Wednesday;
    private static final String DAY_START_TIME_STRING = "06:00:00";
    private static final String DAY_END_TIME_STRING = "20:00:00";

    private static final String START_DATE_STRING = "2021-04-30";
    private static final Date START_DATE = Date.valueOf(START_DATE_STRING);
    private static final String START_DATE2_STRING = "2021-03-31";
    private static final Date START_DATE2 = Date.valueOf(START_DATE2_STRING);
    private static final String START_TIME_STRING = "10:00:00";
    private static final Time START_TIME = Time.valueOf(START_TIME_STRING);
    private static final String END_TIME_STRING = "10:30:00";
    private static final Time END_TIME = Time.valueOf(END_TIME_STRING);


    @BeforeEach
    public void setMockOutput() {
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
        lenient().when(appointmentRepository.save(any(Appointment.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(timeSlotRepository.save(any(TimeSlot.class))).thenAnswer(returnParameterAsAnswer);

        lenient().when(cusRepo.findCustomerByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(CUSTOMER_USERNAME)) {

                Profile profile = new Profile();
                profile.setFirstName(PROFILE_FIRSTNAME);
                profile.setLastName(PROFILE_LASTNAME);
                profile.setAddress(PROFILE_ADDRESS);
                profile.setEmail(PROFILE_EMAIL);
                profile.setPhoneNumber(PROFILE_PHONE);
                profile.setZipCode(PROFILE_ZIP);

                Car car = new Car();
                car.setModel(CAR_MODEL);
                car.setTransmission(CAR_TRANSMISSION);
                car.setPlateNumber(CAR_PLATE);
                List<Car> cars = new ArrayList<Car>();


                Customer customer = new Customer();
                customer.setUsername(CUSTOMER_USERNAME);
                customer.setPassword(CUSTOMER_PASSWORD);
                customer.setProfile(profile);
                customer.setCars(cars);

                return customer;

            } else {
                return null;
            }
        });

        lenient().when(chosenServiceRepository.findChosenServiceByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(SERVICE_NAME) || invocation.getArgument(0).equals(SERVICE_NAME2)) {
                ChosenService chosenService = new ChosenService();
                if (invocation.getArgument(0).equals(SERVICE_NAME)) {
                    chosenService.setName(SERVICE_NAME);
                    chosenService.setDuration(SERVICE_DURATION);
                }
                if (invocation.getArgument(0).equals(SERVICE_NAME2)) {
                    chosenService.setName(SERVICE_NAME2);
                    chosenService.setDuration(SERVICE_DURATION2);
                }
                return chosenService;
            } else {
                return null;
            }
        });

        lenient().when(timeSlotRepository.findTimeSlotsByStartDate(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (((Date) invocation.getArgument(0)).compareTo(DATE)==0 || ((Date) invocation.getArgument(0)).compareTo(TODAY_DATE)==0) {
                List<TimeSlot> timeSlotList = new ArrayList<>();

                String date = null;
                if (((Date) invocation.getArgument(0)).compareTo(DATE)==0) date = STRING_DATE;
                if (((Date) invocation.getArgument(0)).compareTo(TODAY_DATE)==0) date = TODAY_DATE_STRING;

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
            if (invocation.getArgument(0).equals(DAY_OF_WEEK) || invocation.getArgument(0).equals(DAY_OF_WEEK2)) {
                OperatingHour operatingHour = new OperatingHour();
                if(invocation.getArgument(0).equals(DAY_OF_WEEK)) {
                    operatingHour.setDayOfWeek(DAY_OF_WEEK);
                }
                if(invocation.getArgument(0).equals(DAY_OF_WEEK2)){
                    operatingHour.setDayOfWeek(DAY_OF_WEEK2);
                }
                operatingHour.setStartTime(Time.valueOf(DAY_START_TIME_STRING));
                operatingHour.setEndTime(Time.valueOf(DAY_END_TIME_STRING));
                return operatingHour;
            } else {
                return null;
            }
        });


        lenient().when(appointmentRepository.findAppointmentByTimeSlot(any(TimeSlot.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (((TimeSlot) invocation.getArgument(0)).getStartDate().compareTo(START_DATE)==0 || ((TimeSlot) invocation.getArgument(0)).getStartDate().compareTo(START_DATE2)==0 && ((TimeSlot) invocation.getArgument(0)).getStartTime().compareTo(START_TIME)==0) {
                TimeSlot timeSlot = new TimeSlot();
                if (((TimeSlot)invocation.getArgument(0)).getStartDate().compareTo(START_DATE)==0) {
                    timeSlot.setStartDate(START_DATE);
                    timeSlot.setEndDate(START_DATE);
                }
                if (((TimeSlot) invocation.getArgument(0)).getStartDate().compareTo(START_DATE2)==0) {
                    timeSlot.setStartDate(START_DATE2);
                    timeSlot.setEndDate(START_DATE2);
                }
                timeSlot.setEndTime(END_TIME);
                timeSlot.setStartTime(START_TIME);

                Appointment appointment = new Appointment();
                appointment.setTimeSlot(timeSlot);
                appointment.setCustomer(cusRepo.findCustomerByUsername("TestCustomer"));
                appointment.setChosenService(chosenServiceRepository.findChosenServiceByName("Test Service 2"));
                return appointment;
            } else {
                return null;
            }
        });
        lenient().when(appointmentRepository.findAppointmentsByCustomer(any(Customer.class))).thenAnswer((InvocationOnMock invocation) -> {
            List<Appointment> list = new ArrayList<>();
            TimeSlot timeSlot1 = new TimeSlot();
            timeSlot1.setStartDate(START_DATE);
            timeSlot1.setStartTime(START_TIME);
            
            TimeSlot timeSlot2 = new TimeSlot();
            timeSlot2.setStartDate(START_DATE2);
            timeSlot2.setStartTime(START_TIME);
            list.add(appointmentRepository.findAppointmentByTimeSlot(timeSlot1));
            list.add(appointmentRepository.findAppointmentByTimeSlot(timeSlot2));

            return list;
        });
        lenient().when(timeSlotRepository.findTimeSlotByStartDateAndStartTimeAndEndTime(any(Date.class), any(Time.class), any(Time.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (( invocation.getArgument(0)).equals(START_DATE) || (invocation.getArgument(0)).equals(START_DATE2)
                    && (invocation.getArgument(1)).equals(START_TIME) && ( invocation.getArgument(2)).equals(END_TIME)) {
                TimeSlot timeSlot = new TimeSlot();
                if ((invocation.getArgument(0)).equals(START_DATE)) {
                    timeSlot.setStartDate(START_DATE);
                    timeSlot.setEndDate(START_DATE);
                }
                if ((invocation.getArgument(0)).equals(START_DATE2)) {
                    timeSlot.setStartDate(START_DATE2);
                    timeSlot.setEndDate(START_DATE2);
                }
                timeSlot.setEndTime(END_TIME);
                timeSlot.setStartTime(START_TIME);


                return timeSlot;
            } else {
                return null;
            }
        });
    }

    @Test
    public void testMakeAppointmentDayAfter() {
        assertEquals(0, appointmentService.getAllAppointments().size());

        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        String serviceName = "TestService";
        String customerName = "TestCustomer";
        Date startDate = Date.valueOf("2021-04-30");
        Time startTime = Time.valueOf("11:00:00");

        Appointment appointment = null;

        try {
            appointment = appointmentService.makeAppointment(customerName, serviceName, startDate, startTime);
        } catch (IllegalArgumentException e) {
            fail();
        }

        assertNotNull(appointment);
        assertEquals(customerName, appointment.getCustomer().getUsername());
        assertEquals(serviceName, appointment.getChosenService().getName());
        assertEquals(startDate, appointment.getTimeSlot().getStartDate());
        assertEquals(startTime, appointment.getTimeSlot().getStartTime());
    }

    @Test
    public void testMakeAppointmentSameDay2HoursDiff() {
        String serviceName = "TestService";
        String customerName = "TestCustomer";
        Date startDate = Date.valueOf("2021-03-31");
        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));
        Time startTime = Time.valueOf("10:00:00");

        Appointment appointment = null;

        try {
            appointment = appointmentService.makeAppointment(customerName, serviceName, startDate, startTime);
        } catch (IllegalArgumentException e) {
            fail();
        }

        assertNotNull(appointment);
        assertEquals(customerName, appointment.getCustomer().getUsername());
        assertEquals(serviceName, appointment.getChosenService().getName());
        assertEquals(startDate, appointment.getTimeSlot().getStartDate());
        assertEquals(startTime, appointment.getTimeSlot().getStartTime());
    }

    @Test
    public void testMakeAppointmentSameDay3HoursDiff() {
        String serviceName = "TestService";
        String customerName = "TestCustomer";
        Date startDate = Date.valueOf("2021-03-31");
        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));
        Time startTime = Time.valueOf("11:00:00");

        Appointment appointment = null;

        try {
            appointment = appointmentService.makeAppointment(customerName, serviceName, startDate, startTime);
        } catch (IllegalArgumentException e) {
            fail();
        }

        assertNotNull(appointment);
        assertEquals(customerName, appointment.getCustomer().getUsername());
        assertEquals(serviceName, appointment.getChosenService().getName());
        assertEquals(startDate, appointment.getTimeSlot().getStartDate());
        assertEquals(startTime, appointment.getTimeSlot().getStartTime());
    }

    @Test
    public void testMakeAppointmentCustomerNull() {
        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        String serviceName = null;
        String customerName = null;
        String error = null;
        Date startDate = null;
        Time startTime = null;

        Appointment appointment = null;

        try {
            appointment = appointmentService.makeAppointment(customerName, serviceName, startDate, startTime);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(appointment);
        assertEquals(error, "The username cannot be empty or null");
    }

    @Test
    public void testMakeAppointmentServiceNull() {
        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        String serviceName = null;
        String customerName = "TestCustomer";
        String error = null;
        Date startDate = null;
        Time startTime = null;

        Appointment appointment = null;

        try {
            appointment = appointmentService.makeAppointment(customerName, serviceName, startDate, startTime);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(appointment);
        assertEquals(error, "The chosen service cannot be empty or null");
    }
    
   
    @Test
    public void testMakeAppointmentCustomerEmpty() {
        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        String serviceName = "";
        String customerName = "";
        String error = null;
        Date startDate = null;
        Time startTime = null;

        Appointment appointment = null;

        try {
            appointment = appointmentService.makeAppointment(customerName, serviceName, startDate, startTime);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(appointment);
        assertEquals(error, "The username cannot be empty or null");
    }

    @Test
    public void testMakeAppointmentServiceEmpty() {
        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        String serviceName = "";
        String customerName = "TestCustomer";
        String error = null;
        Date startDate = null;
        Time startTime = null;

        Appointment appointment = null;

        try {
            appointment = appointmentService.makeAppointment(customerName, serviceName, startDate, startTime);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(appointment);
        assertEquals(error, "The chosen service cannot be empty or null");
    }

    @Test
    public void testMakeAppointmentCustomerSpace() {
        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        String serviceName = " ";
        String customerName = " ";
        String error = null;
        Date startDate = null;
        Time startTime = null;

        Appointment appointment = null;

        try {
            appointment = appointmentService.makeAppointment(customerName, serviceName, startDate, startTime);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(appointment);
        assertEquals(error, "The username cannot be empty or null");
    }
    @Test
    public void testMakeAppointmentServiceSpace() {
        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        String serviceName = " ";
        String customerName = "TestCustomer";
        String error = null;
        Date startDate = null;
        Time startTime = null;

        Appointment appointment = null;

        try {
            appointment = appointmentService.makeAppointment(customerName, serviceName, startDate, startTime);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(appointment);
        assertEquals(error, "The chosen service cannot be empty or null");
    }

    @Test
    public void testMakeAppointmentOverlappingTimeSlot() {
        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        String serviceName = "TestService";
        String customerName = "TestCustomer";
        Date startDate = Date.valueOf("2021-04-30");
        Time startTime = Time.valueOf("12:00:00");
        String error = null;

        Appointment appointment = null;

        try {
            appointment = appointmentService.makeAppointment(customerName, serviceName, startDate, startTime);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(appointment);
        assertEquals("Chosen time slot is unavailable.", error);
    }

    @Test
    public void testMakeAppointmentNotInOperatingHours() {
        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        String serviceName = "TestService";
        String customerName = "TestCustomer";
        Date startDate = Date.valueOf("2021-04-30");
        Time startTime = Time.valueOf("22:00:00");
        String error = null;

        Appointment appointment = null;

        try {
            appointment = appointmentService.makeAppointment(customerName, serviceName, startDate, startTime);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(appointment);
        assertEquals("Chosen time slot is unavailable.", error);
    }

    @Test
    public void testMakeAppointmentNonExistingService() {
        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        String serviceName = "Oil Change";
        String customerName = "TestCustomer";
        Date startDate = Date.valueOf("2021-04-30");
        Time startTime = Time.valueOf("11:00:00");
        String error = null;

        Appointment appointment = null;

        try {
            appointment = appointmentService.makeAppointment(customerName, serviceName, startDate, startTime);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(appointment);
        assertEquals("The following service does not exist: Oil Change", error);
    }

    @Test
    public void testMakeAppointmentNonExistingCustomer() {
        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        String serviceName = "TestService";
        String customerName = "Tamara";
        Date startDate = Date.valueOf("2021-04-30");
        Time startTime = Time.valueOf("11:00:00");
        String error = null;

        Appointment appointment = null;

        try {
            appointment = appointmentService.makeAppointment(customerName, serviceName, startDate, startTime);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(appointment);
        assertEquals("The following user does not exist: Tamara", error);
    }

    @Test
    public void testMakeAppointmentOldDate() {
        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        String serviceName = "TestService";
        String customerName = "TestCustomer";
        Date startDate = Date.valueOf("2020-04-30");
        Time startTime = Time.valueOf("11:00:00");
        String error = null;

        Appointment appointment = null;

        try {
            appointment = appointmentService.makeAppointment(customerName, serviceName, startDate, startTime);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(appointment);
        assertEquals("The date has already passed.", error);
    }

    @Test
    public void testMakeAppointmentSameDayOldTime() {
        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        String serviceName = "TestService";
        String customerName = "TestCustomer";
        Date startDate = Date.valueOf("2021-03-31");
        Time startTime = Time.valueOf("07:00:00");
        String error = null;

        Appointment appointment = null;

        try {
            appointment = appointmentService.makeAppointment(customerName, serviceName, startDate, startTime);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(appointment);
        assertEquals("The time has already passed.", error);
    }

    @Test
    public void testUpdateAppointmentServiceDifferentDays() {
        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        Date oldStartDate = Date.valueOf("2021-04-30");
        Time oldStartTime = Time.valueOf("10:00:00");
        String oldServiceName = "Test Service 2";
        String newServiceName = "TestService";

        Appointment updatedApp = null;

        try {
            updatedApp = appointmentService.updateAppointment(oldStartDate, oldStartTime, oldServiceName, oldStartDate, oldStartTime, newServiceName);
        } catch (IllegalArgumentException e) {
            fail();
        }

        assertNotNull(updatedApp);
        assertEquals(oldStartDate, updatedApp.getTimeSlot().getStartDate());
        assertEquals(oldStartTime, updatedApp.getTimeSlot().getStartTime());
        assertEquals(newServiceName, updatedApp.getChosenService().getName());
    }

    @Test
    public void testUpdateAppointmentServiceSameDay() {
        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        Date oldStartDate = Date.valueOf("2021-03-31");
        Time oldStartTime = Time.valueOf("10:00:00");
        String oldServiceName = "Test Service 2";
        String newServiceName = "TestService";

        Appointment updatedApp = null;

        try {
            updatedApp = appointmentService.updateAppointment(oldStartDate, oldStartTime, oldServiceName, oldStartDate, oldStartTime, newServiceName);
        } catch (IllegalArgumentException e) {
            fail();
        }

        assertNotNull(updatedApp);
        assertEquals(oldStartDate, updatedApp.getTimeSlot().getStartDate());
        assertEquals(oldStartTime, updatedApp.getTimeSlot().getStartTime());
        assertEquals(newServiceName, updatedApp.getChosenService().getName());
    }

    @Test
    public void testUpdateAppointmentDateTimeSameDay() {
        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        Date oldStartDate = Date.valueOf("2021-04-30");
        Time oldStartTime = Time.valueOf("10:00:00");
        String oldServiceName = "Test Service 2";
        Date newStartDate = Date.valueOf("2021-03-31");
        Time newStartTime = Time.valueOf("11:00:00");

        Appointment updatedApp = null;

        try {
            updatedApp = appointmentService.updateAppointment(oldStartDate, oldStartTime, oldServiceName, newStartDate, newStartTime, oldServiceName);
        } catch (IllegalArgumentException e) {
            fail();
        }

        assertNotNull(updatedApp);
        assertEquals(newStartDate, updatedApp.getTimeSlot().getStartDate());
        assertEquals(newStartTime, updatedApp.getTimeSlot().getStartTime());
        assertEquals(oldServiceName, updatedApp.getChosenService().getName());
    }

    @Test
    public void testUpdateAppointmentToDifferentDayDifferentServiceDifferentTime() {
        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        Date oldStartDate = Date.valueOf("2021-04-30");
        Time oldStartTime = Time.valueOf("10:00:00");
        String oldServiceName = "Test Service 2";
        Date newStartDate = Date.valueOf("2021-04-02");
        Time newStartTime = Time.valueOf("11:00:00");
        String newServiceName = "TestService";

        Appointment updatedApp = null;

        try {
            updatedApp = appointmentService.updateAppointment(oldStartDate, oldStartTime, oldServiceName, newStartDate, newStartTime, newServiceName);
        } catch (IllegalArgumentException e) {
            fail();
        }

        assertNotNull(updatedApp);
        assertEquals(newStartDate, updatedApp.getTimeSlot().getStartDate());
        assertEquals(newStartTime, updatedApp.getTimeSlot().getStartTime());
        assertEquals(newServiceName, updatedApp.getChosenService().getName());
    }

    @Test
    public void testUpdateAppointmentToDifferentDayDifferentServiceAtFirstHour() {
        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        Date oldStartDate = Date.valueOf("2021-04-30");
        Time oldStartTime = Time.valueOf("10:00:00");
        String oldServiceName = "Test Service 2";
        Date newStartDate = Date.valueOf("2021-04-02");
        Time newStartTime = Time.valueOf("06:00:00");
        String newServiceName = "TestService";

        Appointment updatedApp = null;

        try {
            updatedApp = appointmentService.updateAppointment(oldStartDate, oldStartTime, oldServiceName, newStartDate, newStartTime, newServiceName);
        } catch (IllegalArgumentException e) {
            fail();
        }

        assertNotNull(updatedApp);
        assertEquals(newStartDate, updatedApp.getTimeSlot().getStartDate());
        assertEquals(newStartTime, updatedApp.getTimeSlot().getStartTime());
        assertEquals(newServiceName, updatedApp.getChosenService().getName());
    }
    @Test
    public void testUpdateAppointmentToDifferentDayDifferentServiceBeforeOpening() {
        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        Date oldStartDate = Date.valueOf("2021-04-30");
        Time oldStartTime = Time.valueOf("10:00:00");
        String oldServiceName = "Test Service 2";
        Date newStartDate = Date.valueOf("2021-04-02");
        Time newStartTime = Time.valueOf("05:00:00");
        String newServiceName = "TestService";
        String error = null;

        Appointment updatedApp = null;

        try {
            updatedApp = appointmentService.updateAppointment(oldStartDate, oldStartTime, oldServiceName, newStartDate, newStartTime, newServiceName);
        } catch (IllegalArgumentException e) {
            error= e.getMessage();
        }
        assertNull(updatedApp);
        assertEquals("The time slot is not available.", error);
    }
    @Test
    public void testUpdateAppointmentToSameDayDifferentServiceBeforeOpening2() {
        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        Date oldStartDate = Date.valueOf("2021-04-30");
        Time oldStartTime = Time.valueOf("10:00:00");
        String oldServiceName = "Test Service 2";
        Date newStartDate = Date.valueOf("2021-04-30");
        Time newStartTime = Time.valueOf("05:00:00");
        String newServiceName = "TestService";
        String error = null;

        Appointment updatedApp = null;

        try {
            updatedApp = appointmentService.updateAppointment(oldStartDate, oldStartTime, oldServiceName, newStartDate, newStartTime, newServiceName);
        } catch (IllegalArgumentException e) {
            error= e.getMessage();
        }
        assertNull(updatedApp);
        assertEquals("The time slot is not available.", error);
    }
    @Test
    public void testUpdateAppointmentToSameDayDifferentServiceAtOpening() {
        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        Date oldStartDate = Date.valueOf("2021-04-30");
        Time oldStartTime = Time.valueOf("10:00:00");
        String oldServiceName = "Test Service 2";
        Date newStartDate = Date.valueOf("2021-04-30");
        Time newStartTime = Time.valueOf("06:00:00");
        String newServiceName = "TestService";

        Appointment updatedApp = null;

        try {
            updatedApp = appointmentService.updateAppointment(oldStartDate, oldStartTime, oldServiceName, newStartDate, newStartTime, newServiceName);
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(updatedApp);
        assertEquals(newStartDate, updatedApp.getTimeSlot().getStartDate());
        assertEquals(newStartTime, updatedApp.getTimeSlot().getStartTime());
        assertEquals(newServiceName, updatedApp.getChosenService().getName());
    }

    @Test
    public void testUpdateAppointmentNullOldService(){
        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        Date oldStartDate = null;
        Time oldStartTime =  null;
        String oldServiceName = null;
        Date newStartDate = null;
        Time newStartTime =  null;
        String newServiceName = "TestService";
        String error = null;

        Appointment updatedApp = null;

        try{
            updatedApp=appointmentService.updateAppointment(oldStartDate, oldStartTime, oldServiceName, newStartDate, newStartTime, newServiceName);
        }catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNull(updatedApp);
        assertEquals("The old service cannot be empty or null", error);
    }

    @Test
    public void testUpdateAppointmentNullNewService(){
        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        Date oldStartDate = null;
        Time oldStartTime =  null;
        String oldServiceName = null;
        Date newStartDate = null;
        Time newStartTime =  null;
        String newServiceName = null;
        String error = null;

        Appointment updatedApp = null;

        try{
            updatedApp=appointmentService.updateAppointment(oldStartDate, oldStartTime, oldServiceName, newStartDate, newStartTime, newServiceName);
        }catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNull(updatedApp);
        assertEquals("The chosen service cannot be empty or null",error);
    }

    
   
    @Test
    public void testUpdateAppointmentSpacesNewService(){
        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        Date oldStartDate = null;
        Time oldStartTime =  null;
        String oldServiceName =  " ";
        Date newStartDate = null;
        Time newStartTime =  null;
        String newServiceName = " ";
        String error = null;

        Appointment updatedApp = null;

        try{
            updatedApp=appointmentService.updateAppointment(oldStartDate, oldStartTime, oldServiceName, newStartDate, newStartTime, newServiceName);
        }catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNull(updatedApp);
        assertEquals("The chosen service cannot be empty or null",error);

    }
    @Test
    public void testUpdateAppointmentSpacesOldService(){
        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        Date oldStartDate = null;
        Time oldStartTime =  null;
        String oldServiceName =  " ";
        Date newStartDate = null;
        Time newStartTime =  null;
        String newServiceName = "TestService";
        String error = null;

        Appointment updatedApp = null;

        try{
            updatedApp=appointmentService.updateAppointment(oldStartDate, oldStartTime, oldServiceName, newStartDate, newStartTime, newServiceName);
        }catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNull(updatedApp);
        assertEquals("The old service cannot be empty or null",error);

    }

    @Test
    public void testUpdateAppointmentEmptyNewService(){
        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        Date oldStartDate = null;
        Time oldStartTime =  null;
        String oldServiceName =  "";
        Date newStartDate = null;
        Time newStartTime =  null;
        String newServiceName = "";
        String error = null;

        Appointment updatedApp = null;

        try{
            updatedApp=appointmentService.updateAppointment(oldStartDate, oldStartTime, oldServiceName, newStartDate, newStartTime, newServiceName);
        }catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNull(updatedApp);
        assertEquals("The chosen service cannot be empty or null",error);

    }
    @Test
    public void testUpdateAppointmentEmptyOldService(){
        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        Date oldStartDate = null;
        Time oldStartTime =  null;
        String oldServiceName =  "";
        Date newStartDate = null;
        Time newStartTime =  null;
        String newServiceName = "TestService";
        String error = null;

        Appointment updatedApp = null;

        try{
            updatedApp=appointmentService.updateAppointment(oldStartDate, oldStartTime, oldServiceName, newStartDate, newStartTime, newServiceName);
        }catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNull(updatedApp);
        assertEquals("The old service cannot be empty or null",error);

    }

    @Test
    public void testUpdateAppointmentAppDoesNotExist(){
        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        Date oldStartDate = Date.valueOf("2021-05-04");
        Time oldStartTime =  Time.valueOf("10:00:00");
        String oldServiceName = "TestService";
        String newServiceName = "Test Service 2";
        String error = null;

        Appointment updatedApp = null;

        try{
            updatedApp=appointmentService.updateAppointment(oldStartDate, oldStartTime, oldServiceName, oldStartDate, oldStartTime, newServiceName);
        }catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNull(updatedApp);
        assertEquals("The appointment does not exist",error);

    }

    @Test
    public void testUpdateAppointmentServiceDoesNotExist(){

        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        Date oldStartDate = Date.valueOf("2021-04-30");
        Time oldStartTime =  Time.valueOf("10:00:00");
        String oldServiceName = "TestService";
        String newServiceName = "Test S";
        String error = null;

        Appointment updatedApp = null;

        try{
            updatedApp=appointmentService.updateAppointment(oldStartDate, oldStartTime, oldServiceName, oldStartDate, oldStartTime, newServiceName);
        }catch (IllegalArgumentException e){
            error = e.getMessage();
        }

        assertNull(updatedApp);
        assertEquals("The chosen service does not exist",error);

    }

    @Test
    public void testUpdateAppointmentUnavailableTimeSlotOldService(){
        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        Date oldStartDate = Date.valueOf("2021-03-31");
        Time oldStartTime = Time.valueOf("10:00:00");
        String oldServiceName = "Test Service 2";
        Time newStartTime = Time.valueOf("11:55:00");
        String error = null;

        Appointment updatedApp = null;

        try {
            updatedApp = appointmentService.updateAppointment(oldStartDate, oldStartTime, oldServiceName, oldStartDate, newStartTime, oldServiceName);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("The time slot is not available.", error);
        assertNull(updatedApp);
    }

    @Test
    public void testUpdateAppointmentUnavailableTimeSlotNewService(){
        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        Date oldStartDate = Date.valueOf("2021-03-31");
        Time oldStartTime = Time.valueOf("10:00:00");
        String oldServiceName = "Test Service 2";
        Time newStartTime = Time.valueOf("11:55:00");
        String newServiceName = "TestService";
        String error = null;

        Appointment updatedApp = null;

        try {
            updatedApp = appointmentService.updateAppointment(oldStartDate, oldStartTime, oldServiceName, oldStartDate, newStartTime, newServiceName);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("The time slot is not available.", error);
        assertNull(updatedApp);
    }

    @Test
    public void testUpdateAppointmentTimeSlotNotInOH(){
        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        Date oldStartDate = Date.valueOf("2021-03-31");
        Time oldStartTime = Time.valueOf("10:00:00");
        String oldServiceName = "Test Service 2";
        Time newStartTime = Time.valueOf("23:00:00");
        String error = null;

        Appointment updatedApp = null;

        try {
            updatedApp = appointmentService.updateAppointment(oldStartDate, oldStartTime, oldServiceName, oldStartDate, newStartTime, oldServiceName);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("The time slot is not available.", error);
        assertNull(updatedApp);
    }

    @Test
    public void testUpdateAppointmentDatePassed(){
        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        Date oldStartDate = Date.valueOf("2021-03-31");
        Time oldStartTime = Time.valueOf("10:00:00");
        String oldServiceName = "Test Service 2";
        Date newStartDate = Date.valueOf("2020-10-09");
        String error = null;

        Appointment updatedApp = null;

        try {
            updatedApp = appointmentService.updateAppointment(oldStartDate, oldStartTime, oldServiceName, newStartDate, oldStartTime, oldServiceName);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("The date has already passed.", error);
        assertNull(updatedApp);
    }
    @Test
    public void testUpdateAppointmentSameDayAppStartsLessThan2Hours(){
        SystemTime.setSysTime(Time.valueOf("09:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        Date oldStartDate = Date.valueOf("2021-03-31");
        Time oldStartTime = Time.valueOf("10:00:00");
        String oldServiceName = "Test Service 2";
        Date newStartDate = Date.valueOf("2020-10-09");
        String error = null;

        Appointment updatedApp = null;

        try {
            updatedApp = appointmentService.updateAppointment(oldStartDate, oldStartTime, oldServiceName, newStartDate, oldStartTime, oldServiceName);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Updating an appointment on the same day has to be at least 2 hours before the appointment.", error);
        assertNull(updatedApp);
    }

    @Test
    public void testUpdateAppointmentSameDayNot2Hours(){
        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        Date oldStartDate = Date.valueOf("2021-03-31");
        Time oldStartTime = Time.valueOf("10:00:00");
        String oldServiceName = "Test Service 2";
        Date newStartDate = Date.valueOf("2021-03-31");
        Time newStartTime = Time.valueOf("09:00:00");
        String error = null;

        Appointment updatedApp = null;

        try {
            updatedApp = appointmentService.updateAppointment(oldStartDate, oldStartTime, oldServiceName, newStartDate, newStartTime, oldServiceName);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("Updating an appointment on the same day has to be at least 2 hours before the appointment.", error);
        assertNull(updatedApp);
    }

    @Test
    public void testUpdateAppointmentSameDayTimePassed(){
        SystemTime.setSysTime(Time.valueOf("08:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        Date oldStartDate = Date.valueOf("2021-03-31");
        Time oldStartTime = Time.valueOf("10:00:00");
        String oldServiceName = "Test Service 2";
        Date newStartDate = Date.valueOf("2021-03-31");
        Time newStartTime = Time.valueOf("07:00:00");
        String error = null;

        Appointment updatedApp = null;

        try {
            updatedApp = appointmentService.updateAppointment(oldStartDate, oldStartTime, oldServiceName, newStartDate, newStartTime, oldServiceName);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertEquals("The time has already passed.", error);
        assertNull(updatedApp);

    }

    @Test
    public void testCancelAppointmentDifferentDay(){
        SystemTime.setSysTime(Time.valueOf("09:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        String serviceName = "Test Service 2";
        Date startDate = Date.valueOf("2021-04-30");
        Time startTime = Time.valueOf("10:00:00");
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setStartDate(startDate);
        timeSlot.setStartTime(startTime);
        timeSlot.setEndDate(startDate);
        timeSlot.setEndTime(startTime);

        Appointment appointment = appointmentRepository.findAppointmentByTimeSlot(timeSlot);

        try{
            appointmentService.cancelAppointment(serviceName, startDate,startTime);
            appointment=null;
        }catch(IllegalArgumentException e){
           fail();
        }
        assertNull(appointment);
    }

    @Test
    public void testCancelAppointmentSameDay(){
        SystemTime.setSysTime(Time.valueOf("07:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        String serviceName = "Test Service 2";
        Date startDate = Date.valueOf("2021-03-31");
        Time startTime = Time.valueOf("10:00:00");
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setStartDate(startDate);
        timeSlot.setStartTime(startTime);
        timeSlot.setEndDate(startDate);
        timeSlot.setEndTime(startTime);

        Appointment appointment = appointmentRepository.findAppointmentByTimeSlot(timeSlot);

        try{
            appointmentService.cancelAppointment(serviceName, startDate,startTime);
            appointment=null;
        }catch(IllegalArgumentException e){
            fail();
        }
        assertNull(appointment);

    }

    @Test
    public void testCancelAppointmentSameDayNot2Hours(){
        SystemTime.setSysTime(Time.valueOf("09:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        String serviceName = "Test Service 2";
        Date startDate = Date.valueOf("2021-03-31");
        Time startTime = Time.valueOf("10:00:00");
        String error = null;


        try{
            appointmentService.cancelAppointment(serviceName, startDate,startTime);
        }catch(IllegalArgumentException e){
            error = e.getMessage();
        }

        assertEquals("An appointment can be cancelled on the same day of the appointment with a 2 hours notice.", error);
    }

    @Test
    public void testCancelAppointmentAppDoesNotExist(){
        SystemTime.setSysTime(Time.valueOf("09:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        String serviceName = "Test Service 2";
        Date startDate = Date.valueOf("2021-05-31");
        Time startTime = Time.valueOf("10:00:00");
        String error = null;


        try{
            appointmentService.cancelAppointment(serviceName, startDate,startTime);
        }catch(IllegalArgumentException e){
            error = e.getMessage();
        }

        assertEquals("The appointment does not exist.", error);
    }

    @Test
    public void testCancelAppointmentServiceNull(){
        SystemTime.setSysTime(Time.valueOf("09:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        String serviceName = null;
        Date startDate = null;
        Time startTime = null;
        String error = null;


        try{
            appointmentService.cancelAppointment(serviceName, startDate,startTime);
        }catch(IllegalArgumentException e){
            error = e.getMessage();
        }

        assertEquals("The service to cancel cannot be empty or null", error);

    }
    @Test
    public void testCancelAppointmentDateNull(){
        SystemTime.setSysTime(Time.valueOf("09:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        String serviceName = "TestService";
        Date startDate = null;
        Time startTime = null;
        String error = null;


        try{
            appointmentService.cancelAppointment(serviceName, startDate,startTime);
        }catch(IllegalArgumentException e){
            error = e.getMessage();
        }

        assertEquals("The start date cannot be null", error);

    }
    @Test
    public void testCancelAppointmentTimeNull(){
        SystemTime.setSysTime(Time.valueOf("09:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        String serviceName = "TestService";
        Date startDate = Date.valueOf("2021-04-30");
        Time startTime = null;
        String error = null;


        try{
            appointmentService.cancelAppointment(serviceName, startDate,startTime);
        }catch(IllegalArgumentException e){
            error = e.getMessage();
        }

        assertEquals("The start time cannot be null", error);

    }

    @Test
    public void testCancelAppointmentSpaces(){
        SystemTime.setSysTime(Time.valueOf("09:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        String serviceName = " ";
        Date startDate = null;
        Time startTime = null;
        String error = null;


        try{
            appointmentService.cancelAppointment(serviceName, startDate,startTime);
        }catch(IllegalArgumentException e){
            error = e.getMessage();
        }

        assertEquals("The service to cancel cannot be empty or null", error);

    }

    @Test
    public void testCancelAppointmentEmpty(){
        SystemTime.setSysTime(Time.valueOf("09:00:00"));
        SystemTime.setSysDate(Date.valueOf("2021-03-31"));

        String serviceName = "";
        Date startDate = null;
        Time startTime = null;
        String error = null;


        try{
            appointmentService.cancelAppointment(serviceName, startDate,startTime);
        }catch(IllegalArgumentException e){
            error = e.getMessage();
        }

        assertEquals("The service to cancel cannot be empty or null", error);
    }

    @Test
    public void getAppointmentTest(){
        Date startDate = Date.valueOf("2021-04-30");
        Time startTime = Time.valueOf("10:00:00");
        Time endTime = Time.valueOf("10:30:00");
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setStartDate(startDate);
        timeSlot.setStartTime(startTime);
        timeSlot.setEndTime(endTime);
        timeSlot.setEndDate(startDate);
        Appointment appointment = null;
        String customerName = "TestCustomer";
        String serviceName= "Test Service 2";

        try{
            appointment = appointmentService.getAppointment(timeSlot);
        }catch (IllegalArgumentException e){
            fail();
        }

        assertNotNull(appointment);
        assertEquals(startDate,appointment.getTimeSlot().getStartDate());
        assertEquals(startTime,appointment.getTimeSlot().getStartTime());
        assertEquals(endTime,appointment.getTimeSlot().getEndTime());
        assertEquals(startDate,appointment.getTimeSlot().getEndDate());
        assertEquals(customerName,appointment.getCustomer().getUsername());
        assertEquals(serviceName,appointment.getChosenService().getName());
    }

    @Test
    public void getAppointmentsOfCustomerTest(){
        String customerUsername = "TestCustomer";
        Date startDate = Date.valueOf("2021-04-30");
        Time startTime = Time.valueOf("10:00:00");
        Time endTime = Time.valueOf("10:30:00");
        String serviceName= "Test Service 2";
        Date startDate2 = Date.valueOf("2021-03-31");

        List<Appointment> appointmentList = null;

        try{
            appointmentList=appointmentService.getAppointmentsOfCustomer(cusRepo.findCustomerByUsername(customerUsername));
        }catch (IllegalArgumentException e){
            fail();
        }

        assertNotNull(appointmentList);
        assertEquals(2, appointmentList.size());

        assertEquals(customerUsername, appointmentList.get(0).getCustomer().getUsername());
        assertEquals(startDate, appointmentList.get(0).getTimeSlot().getStartDate());
        assertEquals(startDate, appointmentList.get(0).getTimeSlot().getEndDate());
        assertEquals(startTime, appointmentList.get(0).getTimeSlot().getStartTime());
        assertEquals(endTime, appointmentList.get(0).getTimeSlot().getEndTime());
        assertEquals(serviceName, appointmentList.get(0).getChosenService().getName());

        assertEquals(customerUsername, appointmentList.get(1).getCustomer().getUsername());
        assertEquals(startDate2, appointmentList.get(1).getTimeSlot().getStartDate());
        assertEquals(startDate2, appointmentList.get(1).getTimeSlot().getEndDate());
        assertEquals(startTime, appointmentList.get(1).getTimeSlot().getStartTime());
        assertEquals(endTime, appointmentList.get(1).getTimeSlot().getEndTime());
        assertEquals(serviceName, appointmentList.get(1).getChosenService().getName());

    }

}
