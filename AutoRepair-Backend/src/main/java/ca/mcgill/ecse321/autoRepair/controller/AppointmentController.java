package ca.mcgill.ecse321.autoRepair.controller;

import ca.mcgill.ecse321.autoRepair.dao.AppointmentRepository;
import ca.mcgill.ecse321.autoRepair.dao.CustomerRepository;
import ca.mcgill.ecse321.autoRepair.dao.ChosenServiceRepository;
import ca.mcgill.ecse321.autoRepair.dao.TimeSlotRepository;
import ca.mcgill.ecse321.autoRepair.dto.*;
import ca.mcgill.ecse321.autoRepair.model.*;
import ca.mcgill.ecse321.autoRepair.service.AppointmentService;
import ca.mcgill.ecse321.autoRepair.service.ChosenServiceService;
import ca.mcgill.ecse321.autoRepair.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
    AppointmentRepository appointmentRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ChosenServiceRepository chosenServiceRepository;

    @Autowired
    TimeSlotService timeSlotService;

    @Autowired
    ChosenServiceService chosenServiceService;

    @Autowired
    TimeSlotRepository timeSlotRepository;

    @PostMapping(value = { "/make_appointment/{username}" })
    public AppointmentDTO makeAppointment(@PathVariable("username") String username, @RequestParam String dateString,
                                          @RequestParam String startTimeString,
                                          @RequestParam String serviceName) throws IllegalArgumentException {
        SystemTime.setSysTime(Time.valueOf(LocalTime.now()));
        SystemTime.setSysDate(Date.valueOf(LocalDate.now()));
        Date date = Date.valueOf(dateString);
        Time startTime = Time.valueOf(startTimeString);
        Appointment appointment = appointmentService.makeAppointment(username,serviceName,date, startTime);
        return convertToDTO(appointment);
    }

    @PostMapping(value = {"/update_appointment/{username}"})
    public AppointmentDTO updateAppointment(@PathVariable("username") String username, @RequestParam String oldDateString, @RequestParam String oldTimeString,
                                            @RequestParam String newDateString, @RequestParam String oldServiceString, @RequestParam
                                                    String newStartTimeString, @RequestParam String newServiceString){
        SystemTime.setSysTime(Time.valueOf(LocalTime.now()));
        SystemTime.setSysDate(Date.valueOf(LocalDate.now()));
        Customer customer = customerRepository.findCustomerByUsername(username);
        Date oldDate = Date.valueOf(oldDateString);
        Time oldTime = Time.valueOf(oldTimeString);
        ChosenService oldService = chosenServiceRepository.findChosenServiceByName(oldServiceString);
        Time endOldTime = findEndTimeOfApp(oldService, oldTime.toLocalTime());

        TimeSlot timeSlot = timeSlotRepository.findTimeSlotByStartDateAndStartTimeAndEndTime(oldDate,oldTime,endOldTime);
//        timeSlot.setStartTime(oldTime);
//        timeSlot.setStartDate(oldDate);
//        timeSlot.setEndDate(oldDate);
//        timeSlot.setEndTime(endOldTime);

        Appointment appointment = appointmentRepository.findAppointmentByTimeSlot(timeSlot);
        List<Appointment> appointmentLists = appointmentRepository.findAppointmentsByCustomer(customer);
        boolean exists = false;
        for(int i=0; i<appointmentLists.size(); i++){
            if(appointment.equals(appointmentLists.get(i))) exists=true;
        }
        if(exists==false) throw new IllegalArgumentException("The appointment does not exist for the customer");

        Appointment updatedAppointment = new Appointment();
        Date newDate = null;
        Time newStartTime = null;

        ChosenService newService = chosenServiceService.getChosenService(newServiceString);
        if(newDateString!=null && containsCharacter(newDateString)) {
            newDate = Date.valueOf(newDateString);
        }
        if(newStartTimeString!=null && containsCharacter(newStartTimeString)) {
            newStartTime = Time.valueOf(newStartTimeString);
        }

        if(newService!=null){
            if(newDate==null && newStartTime==null){
                appointmentService.updateAppointment(timeSlot.getStartDate(),timeSlot.getStartTime(),oldService.getName(), timeSlot.getStartDate(), timeSlot.getStartTime(),newService.getName());
            }else if (newDate!=null && newStartTime==null){
                updatedAppointment=appointmentService.updateAppointment(timeSlot.getStartDate(),timeSlot.getStartTime(),oldService.getName(), newDate, timeSlot.getStartTime(),newService.getName());
            }else if (newDate==null && newStartTime!=null){
                updatedAppointment=appointmentService.updateAppointment(timeSlot.getStartDate(),timeSlot.getStartTime(),oldService.getName(), timeSlot.getStartDate(), newStartTime,newService.getName());
            }else if(newDate!=null && newStartTime!=null){
                updatedAppointment=appointmentService.updateAppointment(timeSlot.getStartDate(),timeSlot.getStartTime(),oldService.getName(), newDate, newStartTime,newService.getName());
            }
        }else{
            if (newDate!=null && newStartTime==null){
                updatedAppointment=appointmentService.updateAppointment(timeSlot.getStartDate(),timeSlot.getStartTime(),oldService.getName(), newDate, timeSlot.getStartTime(),oldService.getName());
            }else if (newDate==null && newStartTime!=null){
                updatedAppointment=appointmentService.updateAppointment(timeSlot.getStartDate(),timeSlot.getStartTime(),oldService.getName(), timeSlot.getStartDate(), newStartTime,oldService.getName());
            }else if(newDate!=null && newStartTime!=null){
                appointmentService.updateAppointment(timeSlot.getStartDate(),timeSlot.getStartTime(),oldService.getName(), newDate, newStartTime,oldService.getName());
            }
        }
        return convertToDTO(appointment);
    }

    @PostMapping(value = {"/cancel_appointment/{username}/{date}/{time}/{service}"})
    public boolean cancelAppointment(@PathVariable("username") String username, @PathVariable("date") String dateString, @PathVariable("time") String startTimeString, @PathVariable("service") String serviceName){
        SystemTime.setSysTime(Time.valueOf(LocalTime.now()));
        SystemTime.setSysDate(Date.valueOf(LocalDate.now()));
        Date date = Date.valueOf(dateString);
        Time startTime = Time.valueOf(startTimeString);

        Customer customer = customerRepository.findCustomerByUsername(username);
        //Date oldDate = Date.valueOf(dateString);
        Time oldTime = Time.valueOf(startTimeString);
        ChosenService oldService = chosenServiceRepository.findChosenServiceByName(serviceName);
        Time endOldTime = findEndTimeOfApp(oldService, oldTime.toLocalTime());

        TimeSlot timeSlot = timeSlotRepository.findTimeSlotByStartDateAndStartTimeAndEndTime(date,startTime,endOldTime);
//        timeSlot.setStartTime(oldTime);
//        timeSlot.setStartDate(oldDate);
//        timeSlot.setEndDate(oldDate);
//        timeSlot.setEndTime(endOldTime);

        Appointment appointment = appointmentRepository.findAppointmentByTimeSlot(timeSlot);
        List<Appointment> appointmentLists = appointmentRepository.findAppointmentsByCustomer(customer);
        boolean exists = false;
        for(int i=0; i<appointmentLists.size(); i++){
            if(appointment.equals(appointmentLists.get(i))) exists=true;
        }
        if(exists==false) throw new IllegalArgumentException("The appointment does not exist for the customer");

        appointmentService.cancelAppointment(serviceName,date,startTime);
        return true;
    }

    private Time findEndTimeOfApp(ChosenService service, LocalTime startTime){
        LocalTime localEndTime = startTime.plusMinutes(service.getDuration());
        return Time.valueOf(localEndTime);
    }

    @GetMapping(value = { "/appointments" })
    public List<AppointmentDTO> getAllAppointments() {
        List<AppointmentDTO> appDtos = new ArrayList<>();
        for (Appointment appointment: appointmentService.getAllAppointments()) {
            appDtos.add(convertToDTO(appointment));
        }
        return appDtos;
    }

    @GetMapping(value = { "/appointments/{name}" })
    public List<AppointmentDTO> getAppointmentsOfCustomer(@PathVariable("name") String username) {
        return createAppointmentDtosForCustomer(username);
    }

    @GetMapping(value = {"/availableTimeSlots/{date}"})
    public List<TimeSlotDTO> getAvailableTimeSlotsForDay(@PathVariable("date") String stringDate){
        Date date = Date.valueOf(stringDate);
        List<TimeSlot> timeSlotsList = timeSlotService.getAvailableTimeSlots(date);
        List<TimeSlotDTO> availableTimeSlots = new ArrayList<>();
        for(TimeSlot timeSlot : timeSlotsList ){
            availableTimeSlots.add(convertToDTO(timeSlot));
        }
        return availableTimeSlots;
    }

    @GetMapping(value = {"/unavailableTimeSlots/{date}"})
    public List<TimeSlotDTO> getUnavailableTimeSlotsForDay(@PathVariable("date") String stringDate){
        Date date = Date.valueOf(stringDate);
        List<TimeSlot> timeSlotsList = timeSlotService.getUnavailableTimeSlots(date);
        List<TimeSlotDTO> unavailableTimeSlots = new ArrayList<>();
        for(TimeSlot timeSlot : timeSlotsList ){
            unavailableTimeSlots.add(convertToDTO(timeSlot));
        }
        return unavailableTimeSlots;
    }
    private List<AppointmentDTO> createAppointmentDtosForCustomer(String username) {
        Customer customer = customerRepository.findCustomerByUsername(username);
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