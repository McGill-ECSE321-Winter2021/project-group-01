package ca.mcgill.ecse321.autoRepair.controller;

import ca.mcgill.ecse321.autoRepair.dao.CustomerRepository;
import ca.mcgill.ecse321.autoRepair.dao.ChosenServiceRepository;
import ca.mcgill.ecse321.autoRepair.dao.TimeSlotRepository;
import ca.mcgill.ecse321.autoRepair.dto.AppointmentDTO;
import ca.mcgill.ecse321.autoRepair.dto.AssistantDTO;
import ca.mcgill.ecse321.autoRepair.dto.CarDTO;
import ca.mcgill.ecse321.autoRepair.dto.CustomerDTO;
import ca.mcgill.ecse321.autoRepair.dto.OwnerDTO;
import ca.mcgill.ecse321.autoRepair.dto.CarDTO;
import ca.mcgill.ecse321.autoRepair.dto.CustomerDTO;

import ca.mcgill.ecse321.autoRepair.dto.ProfileDTO;
import ca.mcgill.ecse321.autoRepair.model.*;
import ca.mcgill.ecse321.autoRepair.service.AppointmentService;
import ca.mcgill.ecse321.autoRepair.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

//	@PostMapping(value = { "/make appointment/eusername}/{serviceName}" })
//	public AppointmentDTO makeAppointment(@PathVariable CustomerDTO customerDTO, @RequestParam Date date,
//			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME, pattern = "HH:mm")
//	LocalTime startTime,
//	@PathVariable ChosenService serviceName) throws IllegalArgumentException {
//		Customer customer = customerRepository.findCustomerByUsername(customerDTO.getUsername());
//		TimeSlot timeSlot = new TimeSlot();
//		timeSlot.setStartTime(Time.valueOf(startTime));
//		timeSlot.setStartDate(date);
//		timeSlot.setEndDate(date);
//		ChosenService service = chosenServiceRepository.findChosenServiceByName(serviceName.getName());
//		timeSlot.setEndTime(findEndTimeOfApp(service,startTime));
//		if(customer==null) throw new IllegalArgumentException("The customer does not exist");
//		if(timeSlot!=null) throw new IllegalArgumentException("The chosen time slot is unavailable");
//		if(service== null) throw new IllegalArgumentException("The chosen service does not exist");
//		Appointment appointment = appointmentService.makeAppointment(customer,service,timeSlot);
//		return null;
//		// return convertToDTO(appointment);
//	}

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
}
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
