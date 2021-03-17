 
package ca.mcgill.ecse321.autoRepair.dto;

public class AppointmentDTO {
    private CustomerDTO customer;
    private TimeSlotDTO timeSlot;
    private ChosenServiceDTO service;

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public void setTimeSlot(TimeSlotDTO timeSlot) {
        this.timeSlot = timeSlot;
    }

    public void setService(ChosenServiceDTO service) {
        this.service = service;
    }

    public AppointmentDTO(){}

    public AppointmentDTO(CustomerDTO username, TimeSlotDTO timeSlot, ChosenServiceDTO bookableService){
        this.customer=username;
        this.timeSlot=timeSlot;
        this.service=bookableService;
    }

    public CustomerDTO getCustomer(){
        return this.customer;
    }

    public TimeSlotDTO getTimeSlot(){
        return this.timeSlot;
    }

    public ChosenServiceDTO getService(){
        return this.service;
    }

}

