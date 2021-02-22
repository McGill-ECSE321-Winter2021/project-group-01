

package ca.mcgill.ecse321.autoRepair.model;
import javax.persistence.*;
import java.util.*;
import java.sql.Time;

@Entity
@Table(name = "opSlots")
public class OperatingHour
{


	public enum DayOfWeek { Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday }

	private Long id;



	private DayOfWeek dayOfWeek;
	private Time startTime;
	private Time endTime;


	public OperatingHour(DayOfWeek aDayOfWeek, Time aStartTime, Time aEndTime)
	{
		dayOfWeek = aDayOfWeek;
		startTime = aStartTime;
		endTime = aEndTime;
	}

	public OperatingHour() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(DayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}


	public String toString()
	{
		return super.toString() + "["+
				"id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
				"  " + "dayOfWeek" + "=" + (getDayOfWeek() != null ? !getDayOfWeek().equals(this)  ? getDayOfWeek().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
				"  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
				"  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
				"  " + "AutoRepairShopSystem = ";
	}
}