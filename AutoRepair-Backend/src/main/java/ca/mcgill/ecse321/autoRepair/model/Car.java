package ca.mcgill.ecse321.autoRepair.model;

import javax.persistence.*;

@Entity
@Table(name="cars")
public class Car{

  public Car(){

  }
  public enum CarTransmission { Manual, Automatic }
  private String model;
  private CarTransmission transmission;

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public CarTransmission getTransmission() {
    return transmission;
  }

  public void setTransmission(CarTransmission transmission) {
    this.transmission = transmission;
  }

  @Id
  public String getPlateNumber() {
    return plateNumber;
  }

  public void setPlateNumber(String plateNumber) {
    this.plateNumber = plateNumber;
  }

  private String plateNumber;

  public String toString()
  {
    return super.toString() + "["+
            "model" + ":" + getModel()+ "," +
            "plateNumber" + ":" + getPlateNumber()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "transmission" + "=" + (getTransmission() != null ? !getTransmission().equals(this)  ? getTransmission().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator");
  }

}