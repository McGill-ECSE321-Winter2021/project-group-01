

package ca.mcgill.ecse321.autoRepair.model;
import javax.persistence.*;
import java.util.*;
import java.sql.Date;
import java.sql.Time;

@Entity
public class Customer extends User
{

	private int noShow;



	private int show;
	private List<Car> cars;
	private List<Reminder> reminders;
	private List<Review> reviews;
	private Profile profile;
	private List<Appointment> appointments;


	public Customer(String aUsername, String aPassword, int aNoShow, int aShow, Profile aProfile)
	{
		super(aUsername, aPassword);
		noShow = aNoShow;
		show = aShow;
		cars = new ArrayList<Car>();
		reminders = new ArrayList<Reminder>();
		reviews = new ArrayList<Review>();
		this.profile=aProfile;
		appointments = new ArrayList<Appointment>();
	}

	public Customer() {
		super();
	}

	public int getNoShow() {
		return noShow;
	}

	public void setNoShow(int noShow) {
		this.noShow = noShow;
	}

	public int getShow() {
		return show;
	}

	public void setShow(int show) {
		this.show = show;
	}

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "customer")
	public List<Car> getCars()
	{
		List<Car> newCars = Collections.unmodifiableList(cars);
		return newCars;
	}

	public void setCars(List<Car> cars) {
		this.cars=cars;
	}

	

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "customer")
	public List<Reminder> getReminders()
	{
		List<Reminder> newReminders = Collections.unmodifiableList(reminders);
		return newReminders;
	}

	public void setReminders(List<Reminder> reminders) {
		this.reminders=reminders;
	}

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "customer")
	public List<Review> getReviews()
	{
		List<Review> newReviews = Collections.unmodifiableList(reviews);
		return newReviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews=reviews;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "customer")
	public Profile getProfile()
	{
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile=profile;
	}


	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "customer")
	public List<Appointment> getAppointments()
	{
		List<Appointment> newAppointments = Collections.unmodifiableList(appointments);
		return newAppointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments=appointments;
	}


	public String toString()
	{
		return super.toString() + "["+
				"noShow" + ":" + getNoShow()+ "," +
				"show" + ":" + getShow()+ "]" + System.getProperties().getProperty("line.separator") +
				"  " + "profile = "+(getProfile()!=null?Integer.toHexString(System.identityHashCode(getProfile())):"null") + System.getProperties().getProperty("line.separator") +
				"  " + "AutoRepairShopSystem = ";
	}
}