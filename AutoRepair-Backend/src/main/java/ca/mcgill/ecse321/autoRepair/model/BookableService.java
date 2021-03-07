package ca.mcgill.ecse321.autoRepair.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "services")
public abstract class BookableService{

  private String name;
  private List<Review> reviews;

  public BookableService(){

  }
  @Id
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  
  @OneToMany(mappedBy = "bookableService")
  public List<Review> getReviews() {
    return reviews;
  }

  public void setReviews(List<Review> reviews) {
    this.reviews = reviews;
  }

  @OneToMany(mappedBy = "bookableService")
  public List<Appointment> getAppointments() {
    return appointments;
  }

  public void setAppointments(List<Appointment> appointments) {
    this.appointments = appointments;
  }

  private List<Appointment> appointments;

}