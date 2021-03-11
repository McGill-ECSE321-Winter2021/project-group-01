package ca.mcgill.ecse321.autoRepair.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "business")
public class Business{

  public Business(){

  }
  private String name;
  private String address;
  private String phoneNumber;
  private String email;
  private List<OperatingHour> businessHours;
  private List<TimeSlot> holidays;

  @Id
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @OneToMany
  public List<OperatingHour> getBusinessHours() {
    return businessHours;
  }

  public void setBusinessHours(List<OperatingHour> businessHours) {
    this.businessHours = businessHours;
  }

  @OneToMany
  public List<TimeSlot> getHolidays() {
    return holidays;
  }

  public void setHolidays(List<TimeSlot> holidays) {
    this.holidays = holidays;
  }

  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "address" + ":" + getAddress()+ "," +
            "phoneNumber" + ":" + getPhoneNumber()+ "," +
            "email" + ":" + getEmail()+ "]" + System.getProperties().getProperty("line.separator");
  }
}