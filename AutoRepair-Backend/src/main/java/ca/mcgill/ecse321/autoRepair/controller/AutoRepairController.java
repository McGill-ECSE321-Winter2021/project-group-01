package ca.mcgill.ecse321.autoRepair.controller;

import ca.mcgill.ecse321.autoRepair.dto.AppointmentDTO;
import ca.mcgill.ecse321.autoRepair.dto.TimeSlotDTO;
import ca.mcgill.ecse321.autoRepair.model.Appointment;
import ca.mcgill.ecse321.autoRepair.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class AutoRepairController {
    AppointmentService appointmentService;

//    @PostMapping(value = { "/make appointment" })
//    public AppointmentDTO createPerson(@PathVariable("timeslot")TimeSlotDTO timeSlotDTO, ) throws IllegalArgumentException {
//        AppointmentDTO appointment = appointmentService.makeAppointment(timeSlotDTO)
//        Person person = service.createPerson(name);
//        return convertToDto(person);
//    }

}