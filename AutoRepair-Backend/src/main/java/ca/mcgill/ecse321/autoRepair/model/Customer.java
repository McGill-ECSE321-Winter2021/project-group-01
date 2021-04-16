package ca.mcgill.ecse321.autoRepair.model;
import javax.persistence.*;
import java.util.*;

@Entity
public class Customer extends User
{

  private Profile profile;
  private List<Car> cars;

  public Customer() {
    super();
  }

  @OneToMany(fetch = FetchType.EAGER)
  public List<Car> getCars() {
    return cars;
  }

  public void setCars(List<Car> cars) {
    this.cars = cars;
  }

  @OneToOne
  public Profile getProfile() {
    return profile;
  }

  public void setProfile(Profile profile) {
    this.profile = profile;
  }

  public String toString()
  {
    return super.toString() + "profile = "+(getProfile()!=null?Integer.toHexString(System.identityHashCode(getProfile())):"null") + System.getProperties().getProperty("line.separator");
  }

}