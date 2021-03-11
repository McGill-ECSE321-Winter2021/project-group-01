package ca.mcgill.ecse321.autoRepair.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ChosenServices")
public class ChosenService
{

    private int duration;
    private String name;
    private List<Review> reviews;
    private List<Appointment> appointments;

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

    @OneToMany(mappedBy = "chosenService",cascade = { CascadeType.ALL })
    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @OneToMany
    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public String toString()
    {
        return super.toString() + "["+
                "duration" + ":" + getDuration()+ "]";
    }


}