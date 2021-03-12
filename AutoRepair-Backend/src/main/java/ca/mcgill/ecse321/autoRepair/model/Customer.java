package ca.mcgill.ecse321.autoRepair.model;
import javax.persistence.*;
import java.util.*;

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

  @OneToMany(fetch = FetchType.EAGER)
  public List<Car> getCars() {
    return cars;
  }

  public void setCars(List<Car> cars) {
    this.cars = cars;
  }


  private Profile profile;

  @OneToOne
  public Profile getProfile() {
    return profile;
  }

  public void setProfile(Profile profile) {
    this.profile = profile;
  }

  public String toString()
  {
    return super.toString() + "["+
            "noShow" + ":" + getNoShow()+ "," +
            "show" + ":" + getShow()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "profile = "+(getProfile()!=null?Integer.toHexString(System.identityHashCode(getProfile())):"null") + System.getProperties().getProperty("line.separator");
  }

}