package ca.mcgill.ecse321.autoRepair.model;

import javax.persistence.*;

@Entity
@Table(name = "ChosenServices")
public class ChosenService
{

    private int duration;
    private String name;
   
    public ChosenService() {  }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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