package ca.mcgill.ecse321.autoRepair.service;

import ca.mcgill.ecse321.autoRepair.dao.*;
import ca.mcgill.ecse321.autoRepair.model.*;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ObjectStreamException;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    @Transactional
    public Appointment makeAppointment(String customerName, String serviceName,Date startDate, Time startTime) {
        LocalTime toCompare = LocalTime.parse("02:00:00");
        if(startDate!=null) {
            if (startDate.toLocalDate().isBefore(SystemTime.getSysDate().toLocalDate())) {
                throw new IllegalArgumentException("The date has already passed.");
            } else if (startDate.toLocalDate().isEqual(SystemTime.getSysDate().toLocalDate())) {
                if (startTime != null) {
                    if (startTime.toLocalTime().isBefore(SystemTime.getSysTime().toLocalTime())) {
                        throw new IllegalArgumentException("The time has already passed.");
                    } else if ((startTime.toLocalTime().minusHours(SystemTime.getSysTime().toLocalTime().getHour()).compareTo(toCompare)) < 0) {
                        throw new IllegalArgumentException("Booking an appointment on the same day has to be at least 2 hours before the appointment.");
                    }
                }
            }
        }
            if (customerName == null || customerName.equals(" ") || serviceName == null || serviceName.equals(" ") ||
                    startTime == null || startDate == null) {
                throw new IllegalArgumentException("The following fields cannot not be null: Username, " +
                        "Service Name, Start Date and Start Time.");
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
    public Appointment getAppointment(Date startDate, Time startTime, Date endDate, Time endTime){
        Appointment appointment = appointmentRepository.findAppointmentByStartDateAndStartTime(startDate.toString(),startTime.toString());
        return appointment;
    }

    @Transactional
    public Appointment updateAppointment(Date oldStartDate, Time oldStartTime, String oldServiceName, Date newStartDate, Time newStartTime, String newServiceName){
        if(oldStartDate==null || oldStartTime==null || oldServiceName==null || oldServiceName.equals(" ") || newServiceName.equals(" ") || newStartTime==null || newStartDate==null || newServiceName==null){
            throw new IllegalArgumentException("To update a service the following fields cannot be null: Old Date, Old Time, Old Service, New Date, New Time, New Service.");
        }
        LocalTime toCompare = LocalTime.parse("02:00:00");
        if(oldStartDate!=null){
            if(oldStartDate.toLocalDate().isEqual(SystemTime.getSysDate().toLocalDate())){
                if((oldStartTime.toLocalTime().minusHours(SystemTime.getSysTime().toLocalTime().getHour()).compareTo(toCompare))<0){
                    throw new IllegalArgumentException("Updating an appointment on the same day has to be at least 2 hours before the appointment.");
                }
            }
        }
        if(newStartDate!=null) {
            if (newStartDate.toLocalDate().isBefore(SystemTime.getSysDate().toLocalDate())) {
                throw new IllegalArgumentException("The date has already passed.");
            } else if (newStartDate.toLocalDate().isEqual(SystemTime.getSysDate().toLocalDate())) {
                if (newStartTime != null) {
                    if (newStartTime.toLocalTime().isBefore(SystemTime.getSysTime().toLocalTime())) {
                        throw new IllegalArgumentException("The time has already passed.");
                    } else if ((newStartTime.toLocalTime().minusHours(SystemTime.getSysTime().toLocalTime().getHour()).compareTo(toCompare)) < 0) {
                        throw new IllegalArgumentException("Updating an appointment on the same day has to be at least 2 hours before the appointment.");
                    }
                }
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

        Appointment appointment= appointmentRepository.findAppointmentByStartDateAndStartTime(oldStartDate.toString(),oldStartTime.toString());
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
        if(serviceName==null || serviceName.equals(" ") || startDate==null || startTime==null){
            throw new IllegalArgumentException("To cancel an appointment, the following fields cannot be null or empty: " +
                    "Service Name, Start Date, Start Time");
        }
        LocalTime localTime = LocalTime.parse("02:00:00");
        if(startDate.toLocalDate().equals(SystemTime.getSysDate().toLocalDate())){
            if((startTime.toLocalTime().minusHours(SystemTime.getSysTime().toLocalTime().getHour())).compareTo(localTime)<0){
                throw new IllegalArgumentException("An appointment can be cancelled on the same day of the appointment with a 2 hours notice.");
            }
        }
        ChosenService chosenService =chosenServiceRepository.findChosenServiceByName(serviceName);
        Time endTime = findEndTimeOfApp(chosenService,startTime.toLocalTime());
        TimeSlot timeSlot = calcTimeSlot(startDate,startTime,startDate,endTime);
        Appointment appointment = appointmentRepository.findAppointmentByStartDateAndStartTime(startDate.toString(),startTime.toString());
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

    @Transactional
    public void addNoShow(TimeSlot timeSlot){
        Appointment appointment = appointmentRepository.findAppointmentByStartDateAndStartTime(timeSlot.getStartDate().toString(), timeSlot.getStartTime().toString());
        Customer customer = appointment.getCustomer();
        int noShows = customer.getNoShow()+ 1;
        customer.setNoShow(noShows);
        appointmentRepository.delete(appointment);
    }

    @Transactional
    public void addShow(TimeSlot timeSlot){
        Appointment appointment = appointmentRepository.findAppointmentByStartDateAndStartTime(timeSlot.getStartDate().toString(), timeSlot.getStartTime().toString());
        Customer customer = appointment.getCustomer();
        int shows = customer.getShow()+ 1;
        customer.setShow(shows);
        appointmentRepository.delete(appointment);
    }

    @Transactional
    public void startAppointment(TimeSlot timeSlot){
        Appointment appointment = appointmentRepository.findAppointmentByStartDateAndStartTime(timeSlot.getStartDate().toString(), timeSlot.getStartTime().toString());
        addShow(timeSlot);
        appointmentRepository.delete(appointment);
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
        OperatingHour operatingHour = operatingHourRepository.findByDayOfWeek(getDayString(startDate,locale));
        LocalTime startTime =timeSlot.getStartTime().toLocalTime();
        LocalTime endTime = timeSlot.getEndTime().toLocalTime();
        LocalTime startTimeOH = operatingHour.getStartTime().toLocalTime();
        LocalTime endTimeOH = operatingHour.getEndTime().toLocalTime();
        List<TimeSlot> timeSlot1 = timeSlotRepository.findTimeSlotsByDate(startDate.toString());
        if(timeSlot1==null){
            if((startTimeOH.isBefore(startTime) || startTimeOH.equals(startTime)) && (endTimeOH.isAfter(endTime) || endTimeOH.equals(endTime))) {
                return true;
            }
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

    private static OperatingHour.DayOfWeek getDayString(Date date, Locale locale) {
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
}