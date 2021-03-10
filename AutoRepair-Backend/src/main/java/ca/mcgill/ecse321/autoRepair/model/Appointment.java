package ca.mcgill.ecse321.autoRepair.model;

import javax.persistence.*;
import java.util.List;

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

    private ChosenService service;

    @ManyToOne
    public ChosenService getService(){
        return this.service;
    }

    public void setService(ChosenService service){
        this.service=service;
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
                "  " + "bookableService = "+(getService()!=null?Integer.toHexString(System.identityHashCode(getService())):"null") + System.getProperties().getProperty("line.separator") +
                "  " + "timeSlot = "+(getTimeSlot()!=null?Integer.toHexString(System.identityHashCode(getTimeSlot())):"null") + System.getProperties().getProperty("line.separator");
    }

}