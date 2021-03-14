package ca.mcgill.ecse321.autoRepair.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


public class CustomerDTO {

    @NotBlank(message = "Username cannot be blank.")
    @NotEmpty(message = "Username cannot be blank.")
    private String username;

    @NotBlank(message = "Password cannot be blank.")
    @NotEmpty(message = "Password cannot be blank.")
    private String password;
    private int noShow;
    private int show;
    private List<CarDTO> cars;
    private ProfileDTO profile;



    public CustomerDTO(String username, String password, int noShow, int show, List<CarDTO> cars, ProfileDTO profile) {

        this.noShow=noShow;
        this.show = show;
        this.cars=cars;
        this.profile=profile;

    }



    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }


    public int getNoShow() {
        return noShow;
    }

    public int getShow() {
        return show;
    }

    public List<CarDTO> getCars() {
        return cars;
    }

    public ProfileDTO getProfile() {
        return profile;
    }

}
