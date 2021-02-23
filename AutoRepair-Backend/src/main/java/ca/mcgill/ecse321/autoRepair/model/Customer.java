package ca.mcgill.ecse321.autoRepair.model;
import javax.persistence.*;
import java.util.*;
import java.sql.Date;
import java.sql.Time;

@Entity
public class Customer extends User
{

  public Customer() {
    super();
  }

  private int noShow;

  public int getNoShow() {
    return noShow;
  }

  public void setNoShow(int noShow) {
    this.noShow = noShow;
  }

  private int show;

  public int getShow() {
    return show;
  }

  public void setShow(int show) {
    this.show = show;
  }

  private List<Car> cars;

  
  @OneToMany(fetch = FetchType.EAGER,cascade = { CascadeType.ALL },mappedBy = "customer")
  public List<Car> getCars() {
    return cars;
  }


  public void setCars(List<Car> cars) {
    this.cars = cars;
  }

  private List<Reminder> reminders;
  @OneToMany(cascade = { CascadeType.ALL }, mappedBy = "customer")
  public List<Reminder> getReminders() {
    return reminders;
  }

  public void setReminders(List<Reminder> reminders) {
    this.reminders = reminders;
  }

  private List<Review> reviews;
  @OneToMany(cascade = { CascadeType.ALL }, mappedBy = "customer")
  public List<Review> getReviews() {
    return reviews;
  }

  public void setReviews(List<Review> reviews) {
    this.reviews = reviews;
  }

  private Profile profile;

  @OneToOne(cascade = { CascadeType.ALL },mappedBy = "customer")
  public Profile getProfile() {
    return profile;
  }

  public void setProfile(Profile profile) {
    this.profile = profile;
  }

  private List<Appointment> appointments;

  @OneToMany(cascade = { CascadeType.ALL }, mappedBy = "customer")
  public List<Appointment> getAppointments() {
    return appointments;
  }

  public void setAppointments(List<Appointment> appointments) {
    this.appointments = appointments;
  }

  public String toString()
  {
    return super.toString() + "["+
            "noShow" + ":" + getNoShow()+ "," +
            "show" + ":" + getShow()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "profile = "+(getProfile()!=null?Integer.toHexString(System.identityHashCode(getProfile())):"null") + System.getProperties().getProperty("line.separator");
  }

}