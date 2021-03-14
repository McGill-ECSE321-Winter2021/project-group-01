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
import ca.mcgill.ecse321.autoRepair.service.UserService;
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
    ChosenServiceService chosenServiceService;

    @Autowired
    UserService userService;

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
        Appointment appointment = appointmentService.makeAppointment(customer.getUsername(),service.getName(),date, Time.valueOf(startTime));
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
        ChosenServiceDTO oldServiceDTO = appointmentDTO.getService();

        ChosenService oldService = chosenServiceService.getChosenService(oldServiceDTO.getName());
        ChosenService newService = chosenServiceService.getChosenService(newServiceDTO.getName());
        if(oldService==null) throw new IllegalArgumentException("The service is not available");
        if(newService==null && newStartTime==null && newDate==null) throw new IllegalArgumentException("To update the appointment at least one of " +
                "the following fields should be chosen: New Service, New Date, New Start Time");

        Appointment appointment = appointmentRepository.findAppointmentByStartDateAndStartTime(timeSlot.getStartDate().toString(), timeSlot.getStartTime().toString());
        if(newService!=null){
            if(newDate==null && newStartTime==null){
                updatedAppointment=appointmentService.updateAppointment(timeSlot.getStartDate(),timeSlot.getStartTime(),oldService.getName(), timeSlot.getStartDate(), timeSlot.getStartTime(),newService.getName());
            }else if (newDate!=null && newStartTime==null){
                updatedAppointment=appointmentService.updateAppointment(timeSlot.getStartDate(),timeSlot.getStartTime(),oldService.getName(), newDate, timeSlot.getStartTime(),newService.getName());
            }else if (newDate==null && newStartTime!=null){
                updatedAppointment=appointmentService.updateAppointment(timeSlot.getStartDate(),timeSlot.getStartTime(),oldService.getName(), timeSlot.getStartDate(), Time.valueOf(newStartTime),newService.getName());
            }else if(newDate!=null && newStartTime!=null){
                updatedAppointment=appointmentService.updateAppointment(timeSlot.getStartDate(),timeSlot.getStartTime(),oldService.getName(), newDate, Time.valueOf(newStartTime),newService.getName());
            }
        }else{
            if (newDate!=null && newStartTime==null){
                updatedAppointment=appointmentService.updateAppointment(timeSlot.getStartDate(),timeSlot.getStartTime(),oldService.getName(), newDate, timeSlot.getStartTime(),oldService.getName());
            }else if (newDate==null && newStartTime!=null){
                updatedAppointment=appointmentService.updateAppointment(timeSlot.getStartDate(),timeSlot.getStartTime(),oldService.getName(), timeSlot.getStartDate(), Time.valueOf(newStartTime),oldService.getName());
            }else if(newDate!=null && newStartTime!=null){
                updatedAppointment=appointmentService.updateAppointment(timeSlot.getStartDate(),timeSlot.getStartTime(),oldService.getName(), newDate, Time.valueOf(newStartTime),oldService.getName());
            }
        }
        return convertToDTO(updatedAppointment);
    }

    @PostMapping(value = {"/cancel appointment/{username}/{appointment}"})
    public void cancelAppointment(@PathVariable("username") CustomerDTO customerDTO, @PathVariable("appointment") AppointmentDTO appointmentDTO){
        if(customerDTO==null) throw new IllegalArgumentException("The customer cannot be null");
        if(appointmentDTO==null) throw new IllegalArgumentException("The appointment cannot be null");

        Date date = appointmentDTO.getTimeSlot().getStartDate();
        Time startTime = appointmentDTO.getTimeSlot().getStartTime();
        String serviceName = appointmentDTO.getService().getName();
        appointmentService.cancelAppointment(serviceName,date,startTime);
    }

    private Time findEndTimeOfApp(ChosenService service, LocalTime startTime){
        LocalTime localEndTime = startTime.plusMinutes(service.getDuration());
        return Time.valueOf(localEndTime);
    }

    @GetMapping(value = { "/appointments", "/events/" })
    public List<AppointmentDTO> getAllEvents() {
        List<AppointmentDTO> appDtos = new ArrayList<>();
        for (Appointment appointment: appointmentService.getAllAppointments()) {
            appDtos.add(convertToDTO(appointment));
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

    private ChosenServiceDTO convertToDTO(ChosenService servicito) {
        if(servicito==null) throw new IllegalArgumentException("Service not found.");
        return new ChosenServiceDTO(servicito.getName(), servicito.getDuration());
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

    private Customer convertToDomainObject(CustomerDTO customerDTO){
        List<Customer> allCustomers = userService.getAllCustomers();
        for(Customer customer : allCustomers){
            if (customer.getUsername().equals(customerDTO.getUsername())) {
                return customer;
            }
        }
        return null;
    }


}