package ca.mcgill.ecse321.autoRepair.service;

import ca.mcgill.ecse321.autoRepair.dao.*;
import ca.mcgill.ecse321.autoRepair.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class AppointmentService {
    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    TimeSlotRepository timeSlotRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ChosenServiceRepository chosenServiceRepository;

    @Autowired
    OperatingHourRepository operatingHourRepository;

    @Autowired
    TimeSlotService timeSlotService;

    @Transactional
    public Appointment makeAppointment(String customerName, String serviceName,Date startDate, Time startTime) {
        if (customerName == null || !containsCharacter(customerName)) throw new IllegalArgumentException("The username cannot be empty or null");
        if(serviceName == null || !containsCharacter(serviceName)) throw new IllegalArgumentException("The chosen service cannot be empty or null");
        if(startTime == null) throw new IllegalArgumentException("The start time cannot be null") ;
        if(startDate == null)  throw new IllegalArgumentException("The start date cannot be null");

        LocalTime toCompare = LocalTime.parse("02:00:00");
        if (startDate.toLocalDate().isBefore(SystemTime.getSysDate().toLocalDate())) {
            throw new IllegalArgumentException("The date has already passed.");
        } else if (startDate.toLocalDate().isEqual(SystemTime.getSysDate().toLocalDate())) {
            if (startTime.toLocalTime().isBefore(SystemTime.getSysTime().toLocalTime())) {
                throw new IllegalArgumentException("The time has already passed.");
            } else if ((startTime.toLocalTime().minusHours(SystemTime.getSysTime().toLocalTime().getHour()).compareTo(toCompare)) < 0) {
                throw new IllegalArgumentException("Booking an appointment on the same day has to be at least 2 hours before the appointment.");
            }

        }

        Customer customer = customerRepository.findCustomerByUsername(customerName);
            if (customer == null)
                throw new IllegalArgumentException("The following user does not exist: " + customerName);
        ChosenService chosenService = chosenServiceRepository.findChosenServiceByName(serviceName);
            if (chosenService == null)
                throw new IllegalArgumentException("The following service does not exist: " + serviceName);
        Time endTime = findEndTimeOfApp(chosenService,startTime.toLocalTime());
        TimeSlot timeSlot = calcTimeSlot(startDate,startTime,startDate,endTime);
        Appointment app = new Appointment();
        app.setId(Long.valueOf(customer.getUsername().hashCode()*timeSlot.getStartDate().hashCode()*timeSlot.getStartTime().hashCode()));
        app.setCustomer(customer);
        app.setChosenService(chosenService);
        if(isAvailable(timeSlot)){
            app.setTimeSlot(timeSlot);
        }else throw new IllegalArgumentException("Chosen time slot is unavailable.");
        appointmentRepository.save(app);
        timeSlotRepository.save(timeSlot);
        return app;
    }

    @Transactional
    public Appointment getAppointment(TimeSlot timeSlot){
        Appointment appointment = appointmentRepository.findAppointmentByTimeSlot(timeSlot);
        return appointment;
    }

    @Transactional
    public Appointment updateAppointment(Date oldStartDate, Time oldStartTime, String oldServiceName, Date newStartDate, Time newStartTime, String newServiceName){
        if(newServiceName == null || !containsCharacter(newServiceName)) throw new IllegalArgumentException("The chosen service cannot be empty or null");
        if(oldServiceName == null || !containsCharacter(oldServiceName)) throw new IllegalArgumentException("The old service cannot be empty or null");
        if(oldStartTime == null) throw new IllegalArgumentException("The old start time cannot be null") ;
        if(oldStartDate == null)  throw new IllegalArgumentException("The old start date cannot be null");
        if(newStartTime == null) throw new IllegalArgumentException("The new start time cannot be null") ;
        if(newStartDate == null)  throw new IllegalArgumentException("The new start date cannot be null");

        LocalTime toCompare = LocalTime.parse("02:00:00");
        if(oldStartDate.toLocalDate().isEqual(SystemTime.getSysDate().toLocalDate())){
            if((oldStartTime.toLocalTime().minusHours(SystemTime.getSysTime().toLocalTime().getHour()).compareTo(toCompare))<0){
                throw new IllegalArgumentException("Updating an appointment on the same day has to be at least 2 hours before the appointment.");
            }
        }
        if (newStartDate.toLocalDate().isBefore(SystemTime.getSysDate().toLocalDate())) {
            throw new IllegalArgumentException("The date has already passed.");
        } else if (newStartDate.toLocalDate().isEqual(SystemTime.getSysDate().toLocalDate())) {
            if (newStartTime.toLocalTime().isBefore(SystemTime.getSysTime().toLocalTime())) {
                throw new IllegalArgumentException("The time has already passed.");
            } else if ((newStartTime.toLocalTime().minusHours(SystemTime.getSysTime().toLocalTime().getHour()).compareTo(toCompare)) < 0) {
                throw new IllegalArgumentException("Updating an appointment on the same day has to be at least 2 hours before the appointment.");
            }
        }

        ChosenService oldService = chosenServiceRepository.findChosenServiceByName(oldServiceName);
        ChosenService newService = chosenServiceRepository.findChosenServiceByName(newServiceName);
        if(newService==null){
            throw new IllegalArgumentException("The chosen service does not exist");
        }
        Time oldEndTime = findEndTimeOfApp(oldService,oldStartTime.toLocalTime());
        Time newEndTime= findEndTimeOfApp(newService, newStartTime.toLocalTime());
        TimeSlot timeSlot = calcTimeSlot(oldStartDate,oldStartTime,oldStartDate,oldEndTime);
        TimeSlot newTimeSlot = calcTimeSlot(newStartDate,newStartTime,newStartDate, newEndTime);

        Appointment appointment= appointmentRepository.findAppointmentByTimeSlot(timeSlot);
        if(appointment==null) throw new IllegalArgumentException("The appointment does not exist");
        Appointment updatedApp = appointment;
        appointmentRepository.delete(appointment);

        if(!(oldServiceName.equals(newServiceName))){
            timeSlotRepository.delete(timeSlot);
            if(isAvailable(newTimeSlot)){
                updatedApp.setTimeSlot(newTimeSlot);
                updatedApp.setChosenService(newService);
                appointmentRepository.save(updatedApp);
                timeSlotRepository.save(newTimeSlot);
            }else throw new IllegalArgumentException("The time slot is not available.");
        }else{
            timeSlotRepository.delete(timeSlot);
            if(isAvailable(newTimeSlot)){
                updatedApp.setTimeSlot(newTimeSlot);
                appointmentRepository.save(updatedApp);
                timeSlotRepository.save(newTimeSlot);
            }else throw new IllegalArgumentException("The time slot is not available.");
        }
        return updatedApp;
    }

    private Time findEndTimeOfApp(ChosenService service, LocalTime startTime){
        LocalTime localEndTime = startTime.plusMinutes(service.getDuration());
        return Time.valueOf(localEndTime);
    }

    @Transactional
    public void cancelAppointment(String serviceName,Date startDate, Time startTime){
        if(serviceName==null || !containsCharacter(serviceName)) throw new IllegalArgumentException("The service to cancel cannot be empty or null");
        if(startDate==null) throw new IllegalArgumentException("The start date cannot be null");
        if(startTime==null) throw new IllegalArgumentException("The start time cannot be null");
        LocalTime localTime = LocalTime.parse("02:00:00");
        if(startDate.toLocalDate().equals(SystemTime.getSysDate().toLocalDate())){
            if((startTime.toLocalTime().minusHours(SystemTime.getSysTime().toLocalTime().getHour())).compareTo(localTime)<0){
                throw new IllegalArgumentException("An appointment can be cancelled on the same day of the appointment with a 2 hours notice.");
            }
        }
        ChosenService chosenService =chosenServiceRepository.findChosenServiceByName(serviceName);
        Time endTime = findEndTimeOfApp(chosenService,startTime.toLocalTime());
        TimeSlot timeSlot = calcTimeSlot(startDate,startTime,startDate,endTime);
        Appointment appointment = appointmentRepository.findAppointmentByTimeSlot(timeSlot);
        if(appointment==null) throw new IllegalArgumentException("The appointment does not exist.");
        appointmentRepository.delete(appointment);
        timeSlotRepository.delete(timeSlot);
    }

    @Transactional
    public List<Appointment> getAllAppointments(){
        return toList(appointmentRepository.findAll());
    }

    @Transactional
    public List<Appointment> getAppointmentsOfCustomer(Customer customer){
        return toList(appointmentRepository.findAppointmentsByCustomer(customer));
    }

    private TimeSlot calcTimeSlot(Date startDate, Time startTime, Date endDate, Time endTime){
        TimeSlot timeSlot=new TimeSlot();
        timeSlot.setStartDate(startDate);
        timeSlot.setStartTime(startTime);
        timeSlot.setEndDate(endDate);
        timeSlot.setEndTime(endTime);
        return timeSlot;
    }

    private <T> List<T> toList(Iterable<T> iterable){
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }

    private boolean isAvailable(TimeSlot timeSlot){
        boolean isAvailable=true;
        Date startDate = timeSlot.getStartDate();
        Locale locale = new Locale("en");
        OperatingHour operatingHour = operatingHourRepository.findByDayOfWeek(TimeSlotService.getDayString(startDate,locale));
        LocalTime startTime =timeSlot.getStartTime().toLocalTime();
        LocalTime endTime = timeSlot.getEndTime().toLocalTime();
        LocalTime startTimeOH = operatingHour.getStartTime().toLocalTime();
        LocalTime endTimeOH = operatingHour.getEndTime().toLocalTime();
        List<TimeSlot> timeSlot1 = timeSlotRepository.findTimeSlotsByStartDate(startDate);
        if(timeSlot1==null){
            if((startTimeOH.isBefore(startTime) || startTimeOH.equals(startTime)) && (endTimeOH.isAfter(endTime) || endTimeOH.equals(endTime))) {
                return true;
            }else return false;
        }
        for(int i=0; i<timeSlot1.size();i++){
            if(((startTimeOH.isBefore(startTime) || startTimeOH.equals(startTime))&&(endTimeOH.isAfter(endTime) || endTimeOH.equals(endTime)))) {
                if (isOverlap(timeSlot1.get(i), timeSlot)) isAvailable = false;
            }else return false;
        }
        return isAvailable;
    }

    private static boolean isOverlap(TimeSlot TS1, TimeSlot TS2) {
        LocalTime S1 = TS1.getStartTime().toLocalTime();
        LocalTime S2 = TS2.getStartTime().toLocalTime();
        LocalTime E1 = TS1.getEndTime().toLocalTime();
        LocalTime E2 = TS2.getEndTime().toLocalTime();

        return S1.isBefore(E2) && S2.isBefore(E1);
    }

    private static boolean containsCharacter(String input){
            for(int i = 0; i < input.length(); i++){
                if(!(Character.isWhitespace(input.charAt(i)))){
                    return true;
                }
            }
        return false;
    }

}