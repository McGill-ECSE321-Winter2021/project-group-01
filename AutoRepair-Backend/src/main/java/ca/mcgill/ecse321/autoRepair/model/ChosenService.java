package ca.mcgill.ecse321.autoRepair.model;

import javax.persistence.*;

@Entity
@Table(name = "ChosenServices")
public class ChosenService
{

    private int duration;
    private String name;
    private Double paymentCost;
   
    public ChosenService() {  }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Double getPayment() {
    	return paymentCost;
    }
    
    public void setPayment(Double price) {
    	this.paymentCost = price;
    }
    
    @Id
    public String getName(){ return this.name;}

    public void setName(String name){
        this.name=name;
    }


    public String toString()
    {
        return super.toString() + "["+
                "duration" + ":" + getDuration()+ "]";
    }


}