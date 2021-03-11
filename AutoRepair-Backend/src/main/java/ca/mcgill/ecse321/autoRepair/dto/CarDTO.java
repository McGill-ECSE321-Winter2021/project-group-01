package ca.mcgill.ecse321.autoRepair.dto;


public class CarDTO {


	public enum CarTransmission { Manual, Automatic }
	private String model;
	private CarTransmission transmission;
	private String plateNumber;

	
	public CarDTO(String model, CarTransmission transmission, String plateNumber) {
		this.model=model;
		this.transmission=transmission;
		this.plateNumber=plateNumber;
	}

	public String getModel() {
		return model;
	}


	public CarTransmission getTransmission() {
		return transmission;
	}


	public String getPlateNumber() {
		return plateNumber;
	}


}
