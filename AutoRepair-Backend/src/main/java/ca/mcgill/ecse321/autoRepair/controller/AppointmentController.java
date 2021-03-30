package ca.mcgill.ecse321.autoRepair.controller;

import ca.mcgill.ecse321.autoRepair.dto.*;
import ca.mcgill.ecse321.autoRepair.model.*;
import ca.mcgill.ecse321.autoRepair.service.AppointmentService;
import ca.mcgill.ecse321.autoRepair.service.ChosenServiceService;
import ca.mcgill.ecse321.autoRepair.service.CustomerService;
import ca.mcgill.ecse321.autoRepair.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class AppointmentController {
    @Autowired
    AppointmentService appointmentService;

    @Autowired
    CustomerService customerService;

    @Autowired
    TimeSlotService timeSlotService;

    @Autowired
    ChosenServiceService chosenServiceService;

    /**
     * @author Tamara Zard Aboujaoudeh
     * 
     * Makes an appointment
     * 
     * @param username
     * @param appointmentDate
     * @param appointmentTime
     * @param serviceName
     * @return
     * @throws IllegalArgumentException
     */
    @PostMapping(value = { "/make_appointment/" })
    public ResponseEntity<?> makeAppointment(@RequestParam String username, @RequestParam String appointmentDate,
                                          @RequestParam String appointmentTime,
                                          @RequestParam String serviceName) throws IllegalArgumentException {
        if(appointmentDate=="") {
            return new ResponseEntity<>("The date cannot be null", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(appointmentTime=="") {
            return new ResponseEntity<>("The time cannot be null", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        try {
            SystemTime.setSysTime(Time.valueOf(LocalTime.now()));
            SystemTime.setSysDate(Date.valueOf(LocalDate.now()));
            Date date = Date.valueOf(appointmentDate);
            Time startTime = Time.valueOf(appointmentTime + ":00");

            Appointment appointment = appointmentService.makeAppointment(username, serviceName, date, startTime);
            return new ResponseEntity<>(convertToDTO(appointment), HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @author Tamara Zard Aboujaoudeh
     * 
     * Updates an appointment
     * 
     * @param username
     * @param appointmentDate
     * @param appointmentTime
     * @param newAppointmentDate
     * @param serviceName
     * @param newAppointmentTime
     * @param newServiceName
     * @return
     */
    @PostMapping(value = {"/update_appointment/"})
    public ResponseEntity<?> updateAppointment(@RequestParam String username, @RequestParam String appointmentDate, @RequestParam String appointmentTime,
                                            @RequestParam String newAppointmentDate, @RequestParam String serviceName, @RequestParam
                                                    String newAppointmentTime, @RequestParam String newServiceName){
        if(appointmentTime == "") return new ResponseEntity<>("The old start time cannot be null", HttpStatus.INTERNAL_SERVER_ERROR) ;
        if(appointmentDate == "")  return new ResponseEntity<>("The old start date cannot be null", HttpStatus.INTERNAL_SERVER_ERROR);
        if(newAppointmentTime == "") return new ResponseEntity<>("The new start time cannot be null", HttpStatus.INTERNAL_SERVER_ERROR) ;
        if(newAppointmentDate == "")  return new ResponseEntity<>("The new start date cannot be null", HttpStatus.INTERNAL_SERVER_ERROR);
        SystemTime.setSysTime(Time.valueOf(LocalTime.now()));
        SystemTime.setSysDate(Date.valueOf(LocalDate.now()));
        Customer customer = customerService.getCustomer(username);
        Date oldDate = Date.valueOf(appointmentDate);
        Time oldTime = Time.valueOf(appointmentTime + ":00");
        ChosenService oldService = chosenServiceService.getChosenService(serviceName);
        Time endOldTime = findEndTimeOfApp(oldService, oldTime.toLocalTime());

        TimeSlot timeSlot = timeSlotService.getTimeSlot(oldDate, oldTime);

        Appointment appointment = appointmentService.getAppointment(timeSlot);
        List<Appointment> appointmentLists = appointmentService.getAppointmentsOfCustomer(customer);
        boolean exists = false;
        for(int i=0; i<appointmentLists.size(); i++){
            if(appointment.equals(appointmentLists.get(i))) exists=true;
        }
        if(exists==false) return new ResponseEntity<>("The appointment does not exist for the customer", HttpStatus.INTERNAL_SERVER_ERROR);

        Date newDate = null;
        Time newStartTime = null;

        ChosenService newService = chosenServiceService.getChosenService(newServiceName);
        if(newAppointmentDate!=null && containsCharacter(newAppointmentDate)) {
            newDate = Date.valueOf(newAppointmentDate);
        }
        if(newAppointmentTime!=null && containsCharacter(newAppointmentTime)) {
            newStartTime = Time.valueOf(newAppointmentTime + ":00");
        }
        try {
            if (newService != null) {
                if (newDate == null && newStartTime == null) {
                    appointmentService.updateAppointment(timeSlot.getStartDate(), timeSlot.getStartTime(), oldService.getName(), timeSlot.getStartDate(), timeSlot.getStartTime(), newService.getName());
                } else if (newDate != null && newStartTime == null) {
                    appointmentService.updateAppointment(timeSlot.getStartDate(), timeSlot.getStartTime(), oldService.getName(), newDate, timeSlot.getStartTime(), newService.getName());
                } else if (newDate == null && newStartTime != null) {
                    appointmentService.updateAppointment(timeSlot.getStartDate(), timeSlot.getStartTime(), oldService.getName(), timeSlot.getStartDate(), newStartTime, newService.getName());
                } else if (newDate != null && newStartTime != null) {
                    appointmentService.updateAppointment(timeSlot.getStartDate(), timeSlot.getStartTime(), oldService.getName(), newDate, newStartTime, newService.getName());
                }
            } else {
                if (newDate != null && newStartTime == null) {
                    appointmentService.updateAppointment(timeSlot.getStartDate(), timeSlot.getStartTime(), oldService.getName(), newDate, timeSlot.getStartTime(), oldService.getName());
                } else if (newDate == null && newStartTime != null) {
                    appointmentService.updateAppointment(timeSlot.getStartDate(), timeSlot.getStartTime(), oldService.getName(), timeSlot.getStartDate(), newStartTime, oldService.getName());
                } else if (newDate != null && newStartTime != null) {
                    appointmentService.updateAppointment(timeSlot.getStartDate(), timeSlot.getStartTime(), oldService.getName(), newDate, newStartTime, oldService.getName());
                }
            }
            return new ResponseEntity<>(convertToDTO(appointment), HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @author Tamara Zard Aboujaoudeh
     * 
     * Deletes an appointment
     * 
     * @param username
     * @param appointmentDate
     * @param appointmentTime
     * @param serviceName
     * @return true when successfully deleted
     */
    @DeleteMapping(value = {"/cancel_appointment/"})
    public ResponseEntity<?> cancelAppointment(@RequestParam("username") String username, @RequestParam String appointmentDate, @RequestParam String appointmentTime, @RequestParam String serviceName){
        if(appointmentTime == "" || appointmentDate=="" || serviceName=="") return new ResponseEntity<>("Please choose an appointment", HttpStatus.INTERNAL_SERVER_ERROR) ;
        SystemTime.setSysTime(Time.valueOf(LocalTime.now()));
        SystemTime.setSysDate(Date.valueOf(LocalDate.now()));
        Date date = Date.valueOf(appointmentDate);
        Time startTime = Time.valueOf(appointmentTime + ":00");

        Customer customer = customerService.getCustomer(username);
        Time oldTime = Time.valueOf(appointmentTime + ":00");
        ChosenService oldService = chosenServiceService.getChosenService(serviceName);
        Time endOldTime = findEndTimeOfApp(oldService, oldTime.toLocalTime());

        TimeSlot timeSlot = timeSlotService.getTimeSlot(date, startTime);

        Appointment appointment = appointmentService.getAppointment(timeSlot);
        List<Appointment> appointmentLists = appointmentService.getAppointmentsOfCustomer(customer);
        boolean exists = false;
        for(int i=0; i<appointmentLists.size(); i++){
            if(appointment.equals(appointmentLists.get(i))) exists=true;
        }
        if(exists==false) return new ResponseEntity<>("The appointment does not exist for the customer", HttpStatus.INTERNAL_SERVER_ERROR);
        try {
            appointmentService.cancelAppointment(serviceName, date, startTime);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Time findEndTimeOfApp(ChosenService service, LocalTime startTime){
        LocalTime localEndTime = startTime.plusMinutes(service.getDuration());
        return Time.valueOf(localEndTime);
    }

    /**
     * @author Tamara Zard Aboujaoudeh
     * Gets a list of all the appointments
     * @return list of all appointments 
     */
    @GetMapping(value = { "/appointments" })
    public List<AppointmentDTO> getAllAppointments() {
        List<AppointmentDTO> appDtos = new ArrayList<>();
        for (Appointment appointment: appointmentService.getAllAppointments()) {
            appDtos.add(convertToDTO(appointment));
        }
        return appDtos;
    }

    /**
     * @author Tamara Zard Aboujaoudeh
     * Gets a list of all the appointments for a specific customer
     * @param username
     * @return list of all the appointments for a specific customer
     */
    @GetMapping(value = {"/appointmentsOf/"})
    public ResponseEntity<?> getAppointmentsOfCustomer(@RequestParam String username) {
        Customer customer = customerService.getCustomer(username);
        List<Appointment> appointmentsForCustomer = appointmentService.getAppointmentsOfCustomer(customer);
        List<AppointmentDTO> appointments = new ArrayList<>();
        for (Appointment appointment : appointmentsForCustomer) {
            appointments.add(convertToDTO(appointment));
        }
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    /**
     * @author Tamara Zard Aboujaoudeh
     * Gets all the available time slots
     * @param appointmentDate
     * @return list containing all the available time slots
     */
    @GetMapping(value = {"/availableTimeSlots/"})
    public ResponseEntity<?> getAvailableTimeSlotsForDay(@RequestParam("appointmentDate") String appointmentDate){
        Date date1 = Date.valueOf(appointmentDate);
        List<TimeSlot> timeSlotsList = timeSlotService.getAvailableTimeSlots(date1);
        List<TimeSlotDTO> availableTimeSlots = new ArrayList<>();
        for(TimeSlot timeSlot : timeSlotsList ){
            availableTimeSlots.add(convertToDTO(timeSlot));
        }
        return new ResponseEntity<>(availableTimeSlots, HttpStatus.OK);
    }

    /**
     * @author Tamara Zard Aboujaoudeh
     * Gets a list of all the unavailable time slots
     * @param stringDate
     * @return list of all the unavailable time slots
     */
    @GetMapping(value = {"/unavailableTimeSlots/{date}"})
    public ResponseEntity<?> getUnavailableTimeSlotsForDay(@PathVariable("date") String stringDate){
        Date date = Date.valueOf(stringDate);
        List<TimeSlot> timeSlotsList = timeSlotService.getUnavailableTimeSlots(date);
        List<TimeSlotDTO> unavailableTimeSlots = new ArrayList<>();
        for(TimeSlot timeSlot : timeSlotsList ){
            unavailableTimeSlots.add(convertToDTO(timeSlot));
        }
        return new ResponseEntity<>(unavailableTimeSlots, HttpStatus.OK);
    }
    private List<AppointmentDTO> createAppointmentDtosForCustomer(String username) {
        Customer customer = customerService.getCustomer(username);
        List<Appointment> appointmentsForCustomer = appointmentService.getAppointmentsOfCustomer(customer);
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

    private ChosenServiceDTO convertToDTO(ChosenService service) {
        if(service==null) throw new IllegalArgumentException("Service not found.");
        return new ChosenServiceDTO(service.getName(), service.getDuration(), service.getPayment());
    }
    private CustomerDTO convertToDTO(Customer customer) {
        if(customer==null) throw new IllegalArgumentException("Customer not found.");
        List<CarDTO> cars = new ArrayList<CarDTO>();

        for (Car car : customer.getCars()) {
            cars.add(convertToDTO(car));
        }

        return new CustomerDTO(customer.getUsername(), customer.getPassword(), customer.getNoShow(),
                customer.getShow(), cars, convertToDTO(customer.getProfile()));

    }

    private CarDTO convertToDTO(Car car) {
        if(car==null) throw new IllegalArgumentException("Car not found.");
        return new CarDTO(car.getModel(), car.getTransmission(), car.getPlateNumber());
    }

    private ProfileDTO convertToDTO(Profile profile) {
        if(profile == null) throw new IllegalArgumentException("Profile not found.");
        return new ProfileDTO(profile.getFirstName(), profile.getLastName(), profile.getAddress(),
                profile.getZipCode(), profile.getPhoneNumber(), profile.getEmail());
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