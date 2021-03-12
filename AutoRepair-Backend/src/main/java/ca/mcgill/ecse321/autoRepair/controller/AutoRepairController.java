package ca.mcgill.ecse321.autoRepair.controller;

import ca.mcgill.ecse321.autoRepair.dao.CustomerRepository;
import ca.mcgill.ecse321.autoRepair.dao.ChosenServiceRepository;
import ca.mcgill.ecse321.autoRepair.dao.TimeSlotRepository;
import ca.mcgill.ecse321.autoRepair.dto.AppointmentDTO;
import ca.mcgill.ecse321.autoRepair.dto.BusinessDTO;
import ca.mcgill.ecse321.autoRepair.dto.CustomerDTO;
import ca.mcgill.ecse321.autoRepair.dto.OperatingHourDTO;
import ca.mcgill.ecse321.autoRepair.dto.TimeSlotDTO;
import ca.mcgill.ecse321.autoRepair.model.*;
import ca.mcgill.ecse321.autoRepair.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class AutoRepairController {
    @Autowired
    AppointmentService appointmentService;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    TimeSlotRepository timeSlotRepository;

    @Autowired
    ChosenServiceRepository chosenServiceRepository;

//    @Autowired
//    CustomerService customerService;

    @PostMapping(value = { "/make appointment/{username}/{serviceName}" })
    public AppointmentDTO makeAppointment(@PathVariable CustomerDTO customerDTO, @RequestParam Date date,
                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm")
                                                  LocalTime startTime,
                                          @PathVariable ChosenService serviceName) throws IllegalArgumentException {
        Customer customer = customerRepository.findCustomerByUsername(customerDTO.getUsername());
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setStartTime(Time.valueOf(startTime));
        timeSlot.setStartDate(date);
        timeSlot.setEndDate(date);
        ChosenService service = chosenServiceRepository.findChosenServiceByName(serviceName.getName());
        timeSlot.setEndTime(findEndTimeOfApp(service,startTime));
        if(customer==null) throw new IllegalArgumentException("The customer does not exist");
        if(timeSlot!=null) throw new IllegalArgumentException("The chosen time slot is unavailable");
        if(service== null) throw new IllegalArgumentException("The chosen service does not exist");
        Appointment appointment = appointmentService.makeAppointment(customer,service,timeSlot);
        return null;
       // return convertToDTO(appointment);
    }

    private Time findEndTimeOfApp(ChosenService service, LocalTime startTime){
        LocalTime localEndTime = startTime.plusMinutes(service.getDuration());
        return Time.valueOf(localEndTime);
    }

//    private AppointmentDTO convertToDTO(Appointment appointment){
//        if(appointment==null) throw new IllegalArgumentException("There is no such appointment");
//        AppointmentDTO appointmentDTO = new AppointmentDTO();
//        appointmentDTO.setCustomer(convertToDTO(appointment.getCustomer()));
//        TimeSlotDTO timeSlotDTO = new TimeSlotDTO(appointment.getTimeSlot().getStartTime(), appointment.getTimeSlot().getEndTime()
//                ,appointment.getTimeSlot().getStartDate(), appointment.getTimeSlot().getEndDate());
//        appointmentDTO.setTimeSlot(timeSlotDTO);
//        appointmentDTO.setService(convertToDTO(appointment.getService()));
//        appointmentDTO.setComboItemList(null);
//        return appointmentDTO;
//    }
    
    private TimeSlotDTO convertToDTO(TimeSlot timeSlot) {
    	if(timeSlot==null) throw new IllegalArgumentException("Time slot not found.");
    	return new TimeSlotDTO(timeSlot.getStartTime(), timeSlot.getEndTime(), timeSlot.getStartDate(), timeSlot.getEndDate());
    }
    private OperatingHourDTO convertToDTO(OperatingHour operatingHour) {
		if(operatingHour==null) throw new IllegalArgumentException("Operating hour not found.");
		return new OperatingHourDTO(operatingHour.getId(), operatingHour.getDayOfWeek(), operatingHour.getStartTime(), operatingHour.getEndTime());
	}
    private BusinessDTO convertToDTO(Business business) {
    	if(business==null) throw new IllegalArgumentException("Business not found.");
    	List<OperatingHourDTO> operatingHours = new List<OperatingHourDTO>();
    	for(int i=0; i<business.getBusinessHours().size(); i++) {
    		operatingHours.add(convertToDTO(business.getBusinessHours().get(i)));
    	}
    	List<TimeSlotDTO> holidays = new List<TimeSlotDTO>();
    	for(int i=0; i<business.getHolidays().size(); i++) {
    		holidays.add(convertToDTO(business.getHolidays().get(i)));
    	}
		return new BusinessDTO(business.getName(), business.getEmail(), business.getAddress(), business.getPhoneNumber(), operatingHours, holidays);
    }

}