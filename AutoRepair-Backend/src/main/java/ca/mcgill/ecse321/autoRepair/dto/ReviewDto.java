package ca.mcgill.ecse321.autoRepair.dto;

public class ReviewDTO {
	
	private String description;
	private int serviceRating;
	
	public ReviewDTO(String description, int serviceRating) {
		this.description = description;
		this.serviceRating = serviceRating;
	}
	
	public String getDescription() {
		return description;
	}
	
	public int getServiceRating() {
		return serviceRating;
	}

}
