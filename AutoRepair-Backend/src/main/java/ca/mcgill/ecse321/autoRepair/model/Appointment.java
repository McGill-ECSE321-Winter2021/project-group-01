package ca.mcgill.ecse321.autoRepair.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Appointments")
public class Appointment {
	
	public Appointment(){

	}
	private long id;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public long getId(){
		return this.id;
	}

	public void setId(long aId){
		this.id=aId;
	}

	private Customer customer;

	@ManyToOne
	public Customer getCustomer(){return this.customer;}

	public void setCustomer(Customer aCustomer){
		this.customer=aCustomer;
	}

	private BookableService bookableService;

	@ManyToOne
	public BookableService getBookableService(){
		return this.bookableService;
	}

	public void setBookableService(BookableService bookableService){
		this.bookableService=bookableService;
	}

	private List<ComboItem> comboItems;

	@OneToMany(cascade = CascadeType.ALL)
	public List<ComboItem> getComboItems(){
		return this.comboItems;
	}

	public void setComboItems(List<ComboItem> list){
		this.comboItems=list;
	}

	private TimeSlot timeSlot;

	@OneToOne
	public TimeSlot getTimeSlot(){
		return this.timeSlot;
	}

	public void setTimeSlot(TimeSlot aTimeSlot){
		this.timeSlot=aTimeSlot;
	}

	private AutoRepairShopSystem system;

	@ManyToOne
	public AutoRepairShopSystem getAutoRepairShopSystem(){
		return this.system;
	}

	public void setAutoRepairShopSystem(AutoRepairShopSystem autoRepairShopSystem){
		this.system=autoRepairShopSystem;
	}

	public String toString()
	{
		return super.toString() + "["+
				"id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
				"  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null") + System.getProperties().getProperty("line.separator") +
				"  " + "bookableService = "+(getBookableService()!=null?Integer.toHexString(System.identityHashCode(getBookableService())):"null") + System.getProperties().getProperty("line.separator") +
				"  " + "timeSlot = "+(getTimeSlot()!=null?Integer.toHexString(System.identityHashCode(getTimeSlot())):"null") + System.getProperties().getProperty("line.separator") +
				"  " + "AutoRepairShopSystem = "+(getAutoRepairShopSystem()!=null?Integer.toHexString(System.identityHashCode(getAutoRepairShopSystem())):"null");
	}

}