

package ca.mcgill.ecse321.autoRepair.model;
import javax.persistence.*;

@Entity
public class Service extends BookableService
{

	private int duration;



	public Service(String aName, int aDuration)
	{
		super(aName);
		setDuration(aDuration);
	}

	public Service() {
		super();
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}



	public String toString()
	{
		return super.toString() + "["+
				"duration" + ":" + getDuration()+ "]";
	}


}