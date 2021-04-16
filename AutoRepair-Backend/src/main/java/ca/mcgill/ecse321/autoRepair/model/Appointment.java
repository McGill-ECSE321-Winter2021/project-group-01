package ca.mcgill.ecse321.autoRepair.model;

import javax.persistence.*;

@Entity
@Table(name = "Appointments")
public class Appointment {
    public Appointment(){

    }
    private Long id;
    private Customer customer;
    private ChosenService chosenService;
    private TimeSlot timeSlot;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId(){
        return this.id;
    }

    public void setId(Long aId){
        this.id=aId;
    }

    @ManyToOne
    public Customer getCustomer(){return this.customer;}

    public void setCustomer(Customer aCustomer){
        this.customer=aCustomer;
    }

    @ManyToOne
    public ChosenService getChosenService(){
        return this.chosenService;
    }

    public void setChosenService(ChosenService service){
        this.chosenService=service;
    }

    @OneToOne
    public TimeSlot getTimeSlot(){
        return this.timeSlot;
    }

    public void setTimeSlot(TimeSlot aTimeSlot){
        this.timeSlot=aTimeSlot;
    }


    public String toString(){
        return this.chosenService.getName() + "; " + this.timeSlot.getStartDate().toString() + "; " + this.timeSlot.getStartTime().toString();
    }

}