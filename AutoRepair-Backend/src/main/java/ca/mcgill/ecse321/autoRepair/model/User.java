package ca.mcgill.ecse321.autoRepair.model;
import javax.persistence.*;

@Entity
@Table(name = "Accounts")
public abstract class User
{

  public User(String username, String password)
  {
	  this.username=username;
	  this.password=password;
  }

  public User() {  
  }

  private String username;
  
  @Id
  public String getUsername()
  {
    return this.username;
  }
  
  public void setUsername(String username) {
	  this.username=username;
  }
  
  
  
  private String password;
 
  public String getPassword() {
	  return this.password;
  }
  
  public void setPassword(String password) {
	  this.password=password;
  }


  public String toString()
  {
    return super.toString() + "["+
            "username" + ":" + getUsername()+ "," +
            "password" + ":" + getPassword()+ "]";
  }
}