package ca.mcgill.ecse321.autoRepair.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse321.autoRepair.dto.*;

import ca.mcgill.ecse321.autoRepair.model.*;
import ca.mcgill.ecse321.autoRepair.service.ReviewService;


public class Conversion {
   
//	@Autowired
//	private static ReviewService reviewService;
	
	
	/**
     * This method converts an Appointment object to an Appointment DTO.
     * @param appointment
     * @return
     */
    public static AppointmentDTO convertToDTO(Appointment appointment, ReviewService reviewService){
        if(appointment==null)throw new IllegalArgumentException("There is no such appointment");
        AppointmentDTO appointmentDTO= new AppointmentDTO();
        appointmentDTO.setService(convertToDTO(appointment.getChosenService(), reviewService));
        appointmentDTO.setTimeSlot(convertToDTO(appointment.getTimeSlot()));
        appointmentDTO.setCustomer(convertToDTO(appointment.getCustomer()));
        return appointmentDTO;
    }

    /**
     * This method converts a TimeSlot object to a TimeSlotDTO
     * @param timeSlot
     * @return
     */
    public static TimeSlotDTO convertToDTO(TimeSlot timeSlot){
        if(timeSlot==null) throw new IllegalArgumentException("There is no such time slot");
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO(timeSlot.getStartTime(), timeSlot.getEndTime()
                ,timeSlot.getStartDate(), timeSlot.getEndDate());

        return timeSlotDTO;
    }

    /**
     * This method converts a ChosenService object to a ChosenServiceDTO
     * @param service
     * @return
     */
    public static ChosenServiceDTO convertToDTO(ChosenService service, ReviewService reviewService) {
        if(service==null) throw new IllegalArgumentException("Service not found.");
        
        Double averageRating = null;
        try {
            averageRating = reviewService.getAverageServiceReview(service.getName());
        }
        catch (Exception e){
        	averageRating = -1.0;
        }

        return new ChosenServiceDTO(service.getName(), service.getDuration(), service.getPayment(), averageRating);
    }

    /**
     * This method converts a Customer object to a CustomerDTO
     * @param customer
     * @return
     */
    public static CustomerDTO convertToDTO(Customer customer) {
        if(customer==null) throw new IllegalArgumentException("Customer not found.");
        List<CarDTO> cars = new ArrayList<CarDTO>();

        for (Car car : customer.getCars()) {
            cars.add(convertToDTO(car));
        }

        return new CustomerDTO(customer.getUsername(), customer.getPassword(), customer.getNoShow(),
                customer.getShow(), cars, convertToDTO(customer.getProfile()));
    }

    /**
     * This method converts a Car object to a CarDTO
     * @param car
     * @return
     */
    public static CarDTO convertToDTO(Car car) {
        if(car==null) throw new IllegalArgumentException("Car not found.");
        return new CarDTO(car.getModel(), car.getTransmission(), car.getPlateNumber());
    }

    /**
     * This method converts a Profile object to a ProfileDTO
     * @param profile
     * @return
     */
    public static ProfileDTO convertToDTO(Profile profile) {
        if(profile == null) throw new IllegalArgumentException("Profile not found.");
        return new ProfileDTO(profile.getFirstName(), profile.getLastName(), profile.getAddress(),
                profile.getZipCode(), profile.getPhoneNumber(), profile.getEmail());
    }
    
    /**
	 * This method converts an Assistant object to an Assistant DTO.
	 * @param assistant
	 * @return AssistantDTO
	 */
	public static AssistantDTO convertToDTO(Assistant assistant) {
		if(assistant == null) throw new IllegalArgumentException("Assistant not found.");
		return new AssistantDTO(assistant.getUsername(),assistant.getPassword());
	}
	
	/**
	 * This method converts an Owner object to an Owner DTO.
	 * @param owner
	 * @return OwnerDTO
	 */
	public static OwnerDTO convertToDTO(Owner owner) {
		if(owner == null) throw new IllegalArgumentException("Owner not found.");
		return new OwnerDTO(owner.getUsername(),owner.getPassword());
	}

	/**
	 * This method converts an Review object to an Review DTO.
	 * @param review
	 * @return ReviewDTO
	 */
	public static ReviewDTO convertToDTO(Review review, ReviewService reviewService) {
	if(review==null) throw new IllegalArgumentException("Review not found.");
	return new ReviewDTO(review.getDescription(), review.getServiceRating(),
			convertToDTO(review.getCustomer()), convertToDTO(review.getAppointment(), reviewService),
			convertToDTO(review.getChosenService(), reviewService));
}

	/**
	 * This method converts an Reminder object to an Reminder DTO.
	 * @param reminder
	 * @return ReminderDTO
	 */
	public static ReminderDTO convertToDTO(Reminder reminder, ReviewService reviewService) {
	if(reminder==null) throw new IllegalArgumentException("Service not found.");
	return new ReminderDTO(convertToDTO(reminder.getCustomer()), convertToDTO(reminder.getChosenService(), reviewService), reminder.getDate(), reminder.getTime(),reminder.getDescription());
}
	
  public static BusinessDTO convertToDTO(Business business) {
 	if(business==null) throw new IllegalArgumentException("Business not found.");
	List<OperatingHourDTO> operatingHours = new ArrayList<OperatingHourDTO>();
	for(int i=0; i<business.getBusinessHours().size(); i++) {
		operatingHours.add(convertToDTO(business.getBusinessHours().get(i)));
	}
	List<TimeSlotDTO> holidays = new ArrayList<TimeSlotDTO>();
	for(int i=0; i<business.getHolidays().size(); i++) {
		holidays.add(convertToDTO(business.getHolidays().get(i)));
	}
	return new BusinessDTO(business.getName(), business.getEmail(), business.getAddress(), business.getPhoneNumber(), operatingHours, holidays);
}
  
  public static OperatingHourDTO convertToDTO(OperatingHour operatingHour) {
	if(operatingHour==null) throw new IllegalArgumentException("Operating hour not found.");
	return new OperatingHourDTO(operatingHour.getDayOfWeek(), operatingHour.getStartTime(), operatingHour.getEndTime());
}
	
}