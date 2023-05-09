package com.ap.portfolio.dtos;

import com.ap.portfolio.models.Education;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor @Getter
public class EducationDTO {
    int id;
    @NotBlank
    private String eduName;
    @NotBlank
    private String eduDescription;
    private String imgURL;

    public EducationDTO(Education education){
        this.id= education.getId();
        this.eduName=education.getEduName();
        this.eduDescription=education.getEduDescription();
        this.imgURL=education.getImgURL();
    }
}
