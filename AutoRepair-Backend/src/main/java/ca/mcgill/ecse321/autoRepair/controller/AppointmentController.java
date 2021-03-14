package ca.mcgill.ecse321.autoRepair.controller;

import ca.mcgill.ecse321.autoRepair.dao.AppointmentRepository;
import ca.mcgill.ecse321.autoRepair.dao.CustomerRepository;
import ca.mcgill.ecse321.autoRepair.dao.ChosenServiceRepository;
import ca.mcgill.ecse321.autoRepair.dao.TimeSlotRepository;
import ca.mcgill.ecse321.autoRepair.dto.AppointmentDTO;
import ca.mcgill.ecse321.autoRepair.dto.ChosenServiceDTO;
import ca.mcgill.ecse321.autoRepair.dto.CustomerDTO;
import ca.mcgill.ecse321.autoRepair.dto.TimeSlotDTO;
import ca.mcgill.ecse321.autoRepair.model.*;
import ca.mcgill.ecse321.autoRepair.service.AppointmentService;
import ca.mcgill.ecse321.autoRepair.service.AutoRepairService;
import ca.mcgill.ecse321.autoRepair.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class AppointmentController {
    @Autowired
    AppointmentService appointmentService;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    TimeSlotRepository timeSlotRepository;

    @Autowired
    ChosenServiceRepository chosenServiceRepository;

    @Autowired
    TimeSlotService timeSlotService;

    @Autowired
    AutoRepairService autoRepairService;
//    @Autowired
//    CustomerService customerService;

    @PostMapping(value = { "/make appointment/{username}/{serviceName}" })
    public AppointmentDTO makeAppointment(@PathVariable("username") CustomerDTO customerDTO, @RequestParam Date date,
                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm")
                                                  LocalTime startTime,
                                          @PathVariable("serviceName") ChosenServiceDTO serviceNameDTO) throws IllegalArgumentException {
        Customer customer = customerRepository.findCustomerByUsername(customerDTO.getUsername());
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setStartTime(Time.valueOf(startTime));
        timeSlot.setStartDate(date);
        timeSlot.setEndDate(date);
        ChosenService service = chosenServiceRepository.findChosenServiceByName(serviceNameDTO.getName());
        timeSlot.setEndTime(findEndTimeOfApp(service,startTime));
        if(customer==null) throw new IllegalArgumentException("The customer does not exist");
        if(service== null) throw new IllegalArgumentException("The chosen service does not exist");
        Appointment appointment = appointmentService.makeAppointment(customer,service,timeSlot);
        return convertToDTO(appointment);
    }

    @PostMapping(value = {"/update appointment/{username}/{appointment}"})
    public AppointmentDTO updateAppointment(@PathVariable("username") CustomerDTO customerDTO, @PathVariable("appointment") AppointmentDTO appointmentDTO,
                                             @RequestParam Date newDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm")
                                                        LocalTime newStartTime, @RequestParam ChosenServiceDTO newServiceDTO){
        Customer customer = customerRepository.findCustomerByUsername(customerDTO.getUsername());
        if(customer==null) throw new IllegalArgumentException("The customer can not be null");
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setStartTime(appointmentDTO.getTimeSlot().getStartTime());
        timeSlot.setStartDate(appointmentDTO.getTimeSlot().getStartDate());
        timeSlot.setEndDate(appointmentDTO.getTimeSlot().getEndDate());
        timeSlot.setEndTime(appointmentDTO.getTimeSlot().getEndTime());

        Appointment updatedAppointment = new Appointment();
        TimeSlot newTimeSlot = new TimeSlot();

        ChosenService oldService = chosenServiceService.getService(oldServiceDTO.getName());
        ChosenService newService = chosenServiceService.getService(newServiceDTO.getName());
        if(oldService==null) throw new IllegalArgumentException("The service is not available");
        if(newService==null && newStartTime==null && newDate==null) throw new IllegalArgumentException("To update the appointment at least one of " +
                "the following fields should be chosen: New Service, New Date, New Start Time");

        Appointment appointment = appointmentRepository.findAppointmentByTimeSlot(timeSlot);
        if(newService!=null){
            if(newDate==null && newStartTime==null){
                newTimeSlot=timeSlot;
                updatedAppointment=appointmentService.updateAppointment(appointment,timeSlot,newService);
            }else if (newDate!=null && newStartTime==null){
                newTimeSlot.setStartTime(timeSlot.getStartTime());
                newTimeSlot.setEndTime(timeSlot.getEndTime());
                newTimeSlot.setStartDate(newDate);
                newTimeSlot.setEndDate(newDate);
                updatedAppointment=appointmentService.updateAppointment(appointment,newTimeSlot,newService);
            }else if (newDate==null && newStartTime!=null){
                newTimeSlot.setStartTime(Time.valueOf(newStartTime));
                newTimeSlot.setEndTime(findEndTimeOfApp(newService,newStartTime));
                newTimeSlot.setStartDate(timeSlot.getStartDate());
                newTimeSlot.setEndDate(timeSlot.getEndDate());
                updatedAppointment=appointmentService.updateAppointment(appointment,newTimeSlot,newService);
            }else if(newDate!=null && newStartTime!=null){
                newTimeSlot.setStartTime(Time.valueOf(newStartTime));
                newTimeSlot.setEndTime(findEndTimeOfApp(newService,newStartTime));
                newTimeSlot.setStartDate(newDate);
                newTimeSlot.setEndDate(newDate);
                updatedAppointment=appointmentService.updateAppointment(appointment,newTimeSlot,newService);
            }
        }else{
            if(newDate==null && newStartTime==null){
                newTimeSlot=timeSlot;
                updatedAppointment=appointmentService.updateAppointment(appointment,timeSlot,newService);
            }else if (newDate!=null && newStartTime==null){
                newTimeSlot.setStartTime(timeSlot.getStartTime());
                newTimeSlot.setEndTime(timeSlot.getEndTime());
                newTimeSlot.setStartDate(newDate);
                newTimeSlot.setEndDate(newDate);
                updatedAppointment=appointmentService.updateAppointment(appointment,newTimeSlot,oldService);
            }else if (newDate==null && newStartTime!=null){
                newTimeSlot.setStartTime(Time.valueOf(newStartTime));
                newTimeSlot.setEndTime(findEndTimeOfApp(newService,newStartTime));
                newTimeSlot.setStartDate(timeSlot.getStartDate());
                newTimeSlot.setEndDate(timeSlot.getEndDate());
                updatedAppointment=appointmentService.updateAppointment(appointment,newTimeSlot,oldService);
            }else if(newDate!=null && newStartTime!=null){
                newTimeSlot.setStartTime(Time.valueOf(newStartTime));
                newTimeSlot.setEndTime(findEndTimeOfApp(oldService,newStartTime));
                newTimeSlot.setStartDate(newDate);
                newTimeSlot.setEndDate(newDate);
                updatedAppointment=appointmentService.updateAppointment(appointment,newTimeSlot,oldService);
            }
        }
        return convertToDTO(updatedAppointment);
    }

    @PostMapping(value = {"/cancel appointment/{username}/{appointment}"})
    public void cancelAppointment(@PathVariable("username") CustomerDTO customerDTO, @PathVariable("appointment") AppointmentDTO appointmentDTO){
        if(customerDTO==null) throw new IllegalArgumentException("The customer cannot be null");
        if(appointmentDTO==null) throw new IllegalArgumentException("The appointment cannot be null");

        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setStartTime(appointmentDTO.getTimeSlot().getStartTime());
        timeSlot.setStartDate(appointmentDTO.getTimeSlot().getStartDate());
        timeSlot.setEndDate(appointmentDTO.getTimeSlot().getEndDate());
        timeSlot.setEndTime(appointmentDTO.getTimeSlot().getEndTime());

        appointmentService.cancelAppointment(timeSlot);
    }

    private Time findEndTimeOfApp(ChosenService service, LocalTime startTime){
        LocalTime localEndTime = startTime.plusMinutes(service.getDuration());
        return Time.valueOf(localEndTime);
    }

    @GetMapping(value = { "/appointments", "/events/" })
    public List<AppointmentDTO> getAllEvents() {
        List<AppointmentDTO> appDtos = new ArrayList<>();
        for (Appointment appointment: appointmentService.getAllAppointments()) {
            appDtos.add(convertToDto(appointment));
        }
        return appDtos;
    }

    @GetMapping(value = { "/registrations/person/{name}", "/registrations/person/{name}/" })
    public List<AppointmentDTO> getAppointmentsOfCustomer(@PathVariable("name") CustomerDTO customerDTO) {
        Customer customer = convertToDomainObject(customerDTO);
        return createAppointmentDtosForCustomer(customer);
    }

    @GetMapping(value = {"/availableTimeSlots/{date}"})
    public List<TimeSlotDTO> getAvailableTimeSlotsForDay(@PathVariable("date") Date date){
        List<TimeSlot> timeSlotsList = timeSlotService.getAvailableTimeSlots(date);
        List<TimeSlotDTO> availableTimeSlots = new ArrayList<>();
        for(TimeSlot timeSlot : timeSlotsList ){
            availableTimeSlots.add(convertToDTO(timeSlot));
        }
        return availableTimeSlots;
    }

    @GetMapping(value = {"/unavailableTimeSlots/{date}"})
    public List<TimeSlotDTO> getUnavailableTimeSlotsForDay(@PathVariable("date") Date date){
        List<TimeSlot> timeSlotsList = timeSlotService.getUnavailableTimeSlots(date);
        List<TimeSlotDTO> unavailableTimeSlots = new ArrayList<>();
        for(TimeSlot timeSlot : timeSlotsList ){
            unavailableTimeSlots.add(convertToDTO(timeSlot));
        }
        return unavailableTimeSlots;
    }
    private List<AppointmentDTO> createAppointmentDtosForCustomer(Customer customer) {
        List<Appointment> appointmentsForCustomer = appointmentRepository.findAppointmentsByCustomer(customer);
        List<AppointmentDTO> appointments = new ArrayList<>();
        for (Appointment appointment : appointmentsForCustomer) {
            appointments.add(convertToDTO(appointment));
        }
        return appointments;
    }

    private AppointmentDTO convertToDTO(Appointment appointment){
        if(appointment==null)throw new IllegalArgumentException("There is no such appointment");
        AppointmentDTO appointmentDTO= new AppointmentDTO();
        appointmentDTO.setService(convertToDTO(appointment.getChosenService()));
        appointmentDTO.setTimeSlot(convertToDTO(appointment.getTimeSlot()));
        appointmentDTO.setCustomer(convertToDTO(appointment.getCustomer()));
        return appointmentDTO;
    }

    private TimeSlotDTO convertToDTO(TimeSlot timeSlot){
        if(timeSlot==null) throw new IllegalArgumentException("There is no such time slot");
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO(timeSlot.getStartTime(), timeSlot.getEndTime()
                ,timeSlot.getStartDate(), timeSlot.getEndDate());

        return timeSlotDTO;
    }

}