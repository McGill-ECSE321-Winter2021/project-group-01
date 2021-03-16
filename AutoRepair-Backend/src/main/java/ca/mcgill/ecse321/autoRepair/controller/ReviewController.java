package ca.mcgill.ecse321.autoRepair.controller;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.autoRepair.dto.AppointmentDTO;
import ca.mcgill.ecse321.autoRepair.dto.CarDTO;
import ca.mcgill.ecse321.autoRepair.dto.ChosenServiceDTO;
import ca.mcgill.ecse321.autoRepair.dto.CustomerDTO;
import ca.mcgill.ecse321.autoRepair.dto.ProfileDTO;
import ca.mcgill.ecse321.autoRepair.dto.ReviewDTO;
import ca.mcgill.ecse321.autoRepair.dto.TimeSlotDTO;
import ca.mcgill.ecse321.autoRepair.model.Appointment;
import ca.mcgill.ecse321.autoRepair.model.Car;
import ca.mcgill.ecse321.autoRepair.model.ChosenService;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.Profile;
import ca.mcgill.ecse321.autoRepair.model.Review;
import ca.mcgill.ecse321.autoRepair.model.TimeSlot;

public class ReviewController {
	
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
	
	private ChosenServiceDTO convertToDTO(ChosenService service) {
		if(service == null) throw new IllegalArgumentException("Service not found");
		return new ChosenServiceDTO(service.getName(), service.getDuration());
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
	
	private ReviewDTO convertToDTO(Review review) {
		if(review==null) throw new IllegalArgumentException("Review not found.");
		return new ReviewDTO(review.getDescription(), review.getServiceRating(),
				convertToDTO(review.getCustomer()), convertToDTO(review.getAppointment()),
				convertToDTO(review.getChosenService()));
	}

}
