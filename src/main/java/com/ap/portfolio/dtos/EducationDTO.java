package com.ap.portfolio.dtos;

import com.ap.portfolio.models.Education;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor @Getter
public class EducationDTO {
    @NotBlank
    private String eduName;
    @NotBlank
    private String eduDescription;

    public EducationDTO(Education education){
        this.eduName=education.getEduName();
        this.eduDescription=education.getEduDescription();
    }
}
