

package ca.mcgill.ecse321.autoRepair.model;
import javax.persistence.*;

@Entity
public class Owner extends User
{


	public Owner(String aUsername, String aPassword)
	{
		super(aUsername, aPassword);
	}

	public Owner() {
		super();
	}


}