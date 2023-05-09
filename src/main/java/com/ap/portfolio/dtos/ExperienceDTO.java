package com.ap.portfolio.dtos;

import com.ap.portfolio.models.Experience;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ExperienceDTO {
    int id;
    @NotBlank
    private String expName;
    @NotBlank
    private String expDescription;
    private String imgURL;

    public ExperienceDTO(Experience experience) {
        this.id= experience.getId();
        this.expName = experience.getExpName();
        this.expDescription = experience.getExpDescription();
        this.imgURL=experience.getImgURL();
    }
}
