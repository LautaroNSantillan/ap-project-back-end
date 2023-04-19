package com.ap.portfolio.dtos;

import com.ap.portfolio.models.Skill;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter @NoArgsConstructor
public class SkillDTO {
    @NotBlank
    private String skillName;
    @NotBlank
    private double percentage;

    public SkillDTO(Skill skill) {
        this.skillName = skill.getSkillName();
        this.percentage = skill.getPercentage();
    }
}
