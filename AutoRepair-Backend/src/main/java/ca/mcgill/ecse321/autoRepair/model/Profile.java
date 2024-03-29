package ca.mcgill.ecse321.autoRepair.model;
import javax.persistence.*;

@Entity
@Table(name = "profiles")
public class Profile
{

  private Long id;
  private String firstName;
  private String lastName;
  private String address;
  private String zipCode;
  private String phoneNumber;
  private String email;


  public Profile(String aFirstName, String aLastName, String aAddress, String aZipCode, String aPhoneNumber, String aEmail)
  {
    firstName = aFirstName;
    lastName = aLastName;
    address = aAddress;
    zipCode = aZipCode;
    phoneNumber = aPhoneNumber;
    email = aEmail;

  }

  public Profile() {

  }

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
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


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "firstName" + ":" + getFirstName()+ "," +
            "lastName" + ":" + getLastName()+ "," +
            "address" + ":" + getAddress()+ "," +
            "zipCode" + ":" + getZipCode()+ "," +
            "phoneNumber" + ":" + getPhoneNumber()+ "," +
            "email" + ":" + getEmail()+ "]" + System.getProperties().getProperty("line.separator");
  }
}