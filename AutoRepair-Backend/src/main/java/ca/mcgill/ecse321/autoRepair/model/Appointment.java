package ca.mcgill.ecse321.autoRepair.model;

import javax.persistence.*;

@Entity
@Table(name = "Appointments")
public class Appointment {
    public Appointment(){

    }
    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId(){
        return this.id;
    }

    public void setId(Long aId){
        this.id=aId;
    }

    private Customer customer;

    @ManyToOne
    public Customer getCustomer(){return this.customer;}

    public void setCustomer(Customer aCustomer){
        this.customer=aCustomer;
    }

    private ChosenService chosenService;

    @ManyToOne
    public ChosenService getChosenService(){
        return this.chosenService;
    }

    public void setChosenService(ChosenService service){
        this.chosenService=service;
    }

    private TimeSlot timeSlot;

    @OneToOne
    public TimeSlot getTimeSlot(){
        return this.timeSlot;
    }

    public void setTimeSlot(TimeSlot aTimeSlot){
        this.timeSlot=aTimeSlot;
    }

    public String toString()
    {
        return super.toString() + "["+
                "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
                "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null") + System.getProperties().getProperty("line.separator") +
                "  " + "bookableService = "+(getChosenService()!=null?Integer.toHexString(System.identityHashCode(getChosenService())):"null") + System.getProperties().getProperty("line.separator") +
                "  " + "timeSlot = "+(getTimeSlot()!=null?Integer.toHexString(System.identityHashCode(getTimeSlot())):"null") + System.getProperties().getProperty("line.separator");
    }

}