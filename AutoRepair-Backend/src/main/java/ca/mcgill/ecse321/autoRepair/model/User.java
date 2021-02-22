/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.autoRepair.model;
import javax.persistence.*;
import java.util.*;

// line 19 "../../../../../AutoRepair.ump"
// line 159 "../../../../../AutoRepair.ump"
@Entity
@Table(name = "Accounts")
public abstract class User
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

 // private static Map<String, User> usersByUsername = new HashMap<String, User>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //User Attributes
  @Column(unique = true)
  private String username;
  private String password;
  private long id;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public User(String aUsername, String aPassword)
  {
    password = aPassword;
//    if (!setUsername(aUsername))
//    {
//      throw new RuntimeException("Cannot create due to duplicate username. See http://manual.umple.org?RE003ViolationofUniqueness.html");
//    }
    username=aUsername;
  }

  public User() {
	  
  }
  //------------------------
  // INTERFACE
  //------------------------

  public void setUsername(String aUsername)
  {
    this.username=aUsername;
//    boolean wasSet = false;
//    String anOldUsername = getUsername();
//    if (anOldUsername != null && anOldUsername.equals(aUsername)) {
//      return true;
//    }
//    if (hasWithUsername(aUsername)) {
//      return wasSet;
//    }
//    username = aUsername;
//    wasSet = true;
//    if (anOldUsername != null) {
//      usersByUsername.remove(anOldUsername);
//    }
//    usersByUsername.put(aUsername, this);
//    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public long getId() {
	  return this.id;
  }
  
  public void setId(long id) {
	  this.id = id;
  }
  
  public String getUsername()
  {
    return username;
  }
  /* Code from template attribute_GetUnique */
//  public static User getWithUsername(String aUsername)
//  {
//    return usersByUsername.get(aUsername);
//  }
  /* Code from template attribute_HasUnique */
//  public static boolean hasWithUsername(String aUsername)
//  {
//    return getWithUsername(aUsername) != null;
//  }

  public String getPassword()
  {
    return password;
  }

//  public void delete()
//  {
//    usersByUsername.remove(getUsername());
//  }


  public String toString()
  {
    return super.toString() + "["+
            "username" + ":" + getUsername()+ "," +
            "password" + ":" + getPassword()+ "]";
  }
}