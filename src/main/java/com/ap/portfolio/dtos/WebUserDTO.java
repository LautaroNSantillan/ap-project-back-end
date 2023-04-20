package com.ap.portfolio.dtos;

import com.ap.portfolio.models.WebUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter @NoArgsConstructor
public class WebUserDTO {
    private int id;
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @NotBlank
    private String email;
    private String about;
    private String img;

    public WebUserDTO(WebUser webUser) {
        this.id= webUser.getId();
        this.name = webUser.getName();
        this.lastName = webUser.getLastName();
        this.img = webUser.getImg();
        this.email= webUser.getEmail();
        this.about= webUser.getAbout();
    }
}
