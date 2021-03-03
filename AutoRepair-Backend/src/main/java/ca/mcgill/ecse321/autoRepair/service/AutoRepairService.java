package ca.mcgill.ecse321.autoRepair.service;

import ca.mcgill.ecse321.autoRepair.dao.AppointmentRepository;
import ca.mcgill.ecse321.autoRepair.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class AutoRepairService {
    @Autowired
    AppointmentRepository appointmentRepository;

    @Transactional
    public Appointment makeAppointment(Customer customer, BookableService bookableService, List<ComboItem> comboItemList,
                                       TimeSlot timeSlot) {
        Appointment app = new Appointment();
        app.setCustomer(customer);
        app.setBookableService(bookableService);
        app.setComboItems(comboItemList);
        app.setTimeSlot(timeSlot);
        appointmentRepository.save(app);
        return app;
    }

    @Transactional
    public Appointment getAppointment(TimeSlot timeSlot){
        Appointment appointment = appointmentRepository.findAppointmentByTimeSlot(timeSlot);
        return appointment;
    }

    @Transactional
    public Appointment updateAppointment(TimeSlot timeSlot, TimeSlot newTimeSlot, BookableService newBookableService){
        Appointment appointment = getAppointment(timeSlot);
        if(!(timeSlot.equals(newTimeSlot))){
            appointment.setTimeSlot(newTimeSlot);
        }
        if(!(appointment.getBookableService().equals(newBookableService))){
            appointment.setBookableService(newBookableService);
        }
        return appointment;
    }

    @Transactional
    public void cancelAppointment(TimeSlot timeSlot){
        Appointment appointment = getAppointment(timeSlot);
        appointmentRepository.delete(appointment);
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