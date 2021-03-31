package ca.mcgill.ecse321.autoRepair.dto;

public class ChosenServiceDTO {
	
	private String name;
	private int duration;
	private Double price;
	private Double rating;
	
	public ChosenServiceDTO(String name,int duration,Double price,Double rating) {
		this.name = name;
		this.duration = duration;
		this.price = price;
		this.rating = rating;
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

	public Double getRating() {
		return rating;
	}
}
