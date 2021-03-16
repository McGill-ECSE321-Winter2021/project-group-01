package ca.mcgill.ecse321.autoRepair.dto;
public class ChosenServiceDTO {

	private String name;
	private int duration;

	public ChosenServiceDTO(String name,int duration) {
		this.name = name;
		this.duration = duration;
	}

	public String getName() {
		return name;
	}

	public int getDuration() {
		return duration;
	}

}