package ca.mcgill.ecse321.autoRepair.service;

import ca.mcgill.ecse321.autoRepair.dao.AppointmentRepository;
import ca.mcgill.ecse321.autoRepair.dao.TimeSlotRepository;
import ca.mcgill.ecse321.autoRepair.model.*;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    TimeSlotRepository timeSlotRepository;

    @Autowired
    TimeSlotService timeSlotService;

    @Transactional
    public Appointment makeAppointment(Customer customer, ChosenService bookableService,TimeSlot timeSlot) {
        Appointment app = new Appointment();
        app.setId(Long.valueOf(customer.getUsername().hashCode()*timeSlot.getStartDate().hashCode()*timeSlot.getStartTime().hashCode()));
        app.setCustomer(customer);
        app.setChosenService(bookableService);
        if(timeSlotService.isAvailable(timeSlot)){
            app.setTimeSlot(timeSlot);
        }else throw new IllegalArgumentException("Chosen time slot is unavailable.");
        appointmentRepository.save(app);
        return app;
    }

    @Transactional
    public Appointment getAppointment(TimeSlot timeSlot){
        Appointment appointment = appointmentRepository.findAppointmentByTimeSlot(timeSlot);
        return appointment;
    }

    @Transactional
    public Appointment updateAppointment(TimeSlot timeSlot, TimeSlot newTimeSlot, ChosenService newService){
        Appointment appointment = getAppointment(timeSlot);
        Appointment updatedApp = appointment;
        appointmentRepository.delete(appointment);

        if(!(timeSlot.equals(newTimeSlot))){
            timeSlotRepository.delete(timeSlot);
            if(timeSlotService.isAvailable(newTimeSlot)){
                updatedApp.setTimeSlot(newTimeSlot);
                appointmentRepository.save(updatedApp);
                timeSlotRepository.save(newTimeSlot);
            }
        }
        if(!(updatedApp.getChosenService().equals(newService))){
            LocalTime newEndTime = timeSlot.getStartTime().toLocalTime().plusMinutes(newService.getDuration());
            TimeSlot timeSlot1 = new TimeSlot();
            timeSlot1.setStartDate(timeSlot.getStartDate());
            timeSlot1.setStartTime(timeSlot.getStartTime());
            timeSlot1.setEndTime(Time.valueOf(newEndTime));
            timeSlot1.setEndDate(timeSlot.getEndDate());
            timeSlotRepository.delete(timeSlot);
            if(timeSlotService.isAvailable(timeSlot1)){
                updatedApp.setChosenService(newService);
                appointmentRepository.save(updatedApp);
            }
        }
        return updatedApp;
    }

    @Transactional
    public void cancelAppointment(TimeSlot timeSlot){
        Appointment appointment = getAppointment(timeSlot);
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
        Appointment appointment = appointmentRepository.findAppointmentByTimeSlot(timeSlot);
        Customer customer = appointment.getCustomer();
        int noShows = customer.getNoShow()+ 1;
        customer.setNoShow(noShows);
        appointmentRepository.delete(appointment);
    }

    @Transactional
    public void addShow(TimeSlot timeSlot){
        Appointment appointment = appointmentRepository.findAppointmentByTimeSlot(timeSlot);
        Customer customer = appointment.getCustomer();
        int shows = customer.getShow()+ 1;
        customer.setShow(shows);
        appointmentRepository.delete(appointment);
    }

    @Transactional
    public void startAppointment(TimeSlot timeSlot){
        Appointment appointment = appointmentRepository.findAppointmentByTimeSlot(timeSlot);
        addShow(timeSlot);
        appointmentRepository.delete(appointment);
    }

    private <T> List<T> toList(Iterable<T> iterable){
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }
}