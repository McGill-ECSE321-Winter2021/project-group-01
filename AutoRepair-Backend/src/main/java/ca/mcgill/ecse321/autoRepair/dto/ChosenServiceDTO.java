package ca.mcgill.ecse321.autoRepair.dto;

public class ChosenServiceDTO {
	
	private String name;
	private int duration;
	private Double price;

	public ChosenServiceDTO(String name,int duration,Double price) {
		this.name = name;
		this.duration = duration;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public int getDuration() {
		return duration;
	}
	
	public Double getPrice() {
		return price;
	}
}
