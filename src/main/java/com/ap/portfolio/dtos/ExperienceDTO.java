package com.ap.portfolio.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ExperienceDTO {
    @NotBlank
    private String expName;
    @NotBlank
    private String expDescription;
}
