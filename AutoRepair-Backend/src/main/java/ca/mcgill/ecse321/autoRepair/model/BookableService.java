package ca.mcgill.ecse321.autoRepair.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "services")
public abstract class BookableService{

  private String name;
  private List<Reminder> reminders;
  private List<Review> reviews;
  private AutoRepairShopSystem AutoRepairShopSystem;

  public BookableService(){

  }
  @Id
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @ManyToMany
  public List<Reminder> getReminders() {
    return reminders;
  }

  public void setReminders(List<Reminder> reminders) {
    this.reminders = reminders;
  }

  @OneToMany(mappedBy = "bookableService")
  public List<Review> getReviews() {
    return reviews;
  }

  public void setReviews(List<Review> reviews) {
    this.reviews = reviews;
  }

  @ManyToOne
  public AutoRepairShopSystem getAutoRepairShopSystem() {
    return AutoRepairShopSystem;
  }

  public void setAutoRepairShopSystem(ca.mcgill.ecse321.autoRepair.model.AutoRepairShopSystem autoRepairShopSystem) {
    AutoRepairShopSystem = autoRepairShopSystem;
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