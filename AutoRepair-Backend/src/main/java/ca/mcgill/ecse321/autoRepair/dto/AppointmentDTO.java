package ca.mcgill.ecse321.autoRepair.dto;

import ca.mcgill.ecse321.autoRepair.model.BookableService;
import ca.mcgill.ecse321.autoRepair.model.ComboItem;
import ca.mcgill.ecse321.autoRepair.model.Customer;
import ca.mcgill.ecse321.autoRepair.model.TimeSlot;

import java.util.List;

public class AppointmentDTO {
    private CustomerDTO customer;
    private TimeSlotDTO timeSlot;
    private BookableServiceDTO service;
    private List<ComboItemDTO> comboItemList;

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public void setTimeSlot(TimeSlotDTO timeSlot) {
        this.timeSlot = timeSlot;
    }

    public void setService(BookableServiceDTO service) {
        this.service = service;
    }

    public void setComboItemList(List<ComboItemDTO> comboItemList) {
        this.comboItemList = comboItemList;
    }

    public AppointmentDTO(){}

    public AppointmentDTO(CustomerDTO customer, TimeSlotDTO timeSlot, BookableServiceDTO bookableService,
                          List<ComboItemDTO> comboItemList){
        this.customer=customer;
        this.timeSlot=timeSlot;
        this.service=bookableService;
        this.comboItemList=comboItemList;
    }

    public CustomerDTO getCustomer(){
        return this.customer;
    }

    public TimeSlotDTO getTimeSlot(){
        return this.timeSlot;
    }

    public BookableServiceDTO getBookableService(){
        return this.service;
    }

    public List<ComboItemDTO> getComboItemList() {
        return comboItemList;
    }

}
