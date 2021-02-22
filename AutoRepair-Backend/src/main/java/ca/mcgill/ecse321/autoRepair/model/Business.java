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

	@OneToOne
	public AutoRepairShopSystem getAutoRepairShopSystem() {
		return AutoRepairShopSystem;
	}

	public void setAutoRepairShopSystem(ca.mcgill.ecse321.autoRepair.model.AutoRepairShopSystem autoRepairShopSystem) {
		AutoRepairShopSystem = autoRepairShopSystem;
	}

	private List<OperatingHour> businessHours;
	private List<TimeSlot> holidays;
	private AutoRepairShopSystem AutoRepairShopSystem;

	public String toString()
	{
		return super.toString() + "["+
				"name" + ":" + getName()+ "," +
				"address" + ":" + getAddress()+ "," +
				"phoneNumber" + ":" + getPhoneNumber()+ "," +
				"email" + ":" + getEmail()+ "]" + System.getProperties().getProperty("line.separator") +
				"  " + "AutoRepairShopSystem = "+(getAutoRepairShopSystem()!=null?Integer.toHexString(System.identityHashCode(getAutoRepairShopSystem())):"null");
	}
}