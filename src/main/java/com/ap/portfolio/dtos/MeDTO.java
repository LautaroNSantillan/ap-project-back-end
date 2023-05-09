package com.ap.portfolio.dtos;

import com.ap.portfolio.models.WebUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class MeDTO {
    private int id;
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @NotBlank
    private String email;
    private String about;
    private String img;
    List<EducationDTO> education;
    List<ExperienceDTO> experience;
    List<SkillDTO> skills;

    public MeDTO(WebUser user) {
        this.id = user.getId();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.about = user.getAbout();
        this.img = user.getImg();
        this.education = user.getEducation().stream().map(EducationDTO::new).collect(Collectors.toList());
        this.experience = user.getExperience().stream().map(ExperienceDTO::new).collect(Collectors.toList());
        this.skills = user.getSkills().stream().map(SkillDTO::new).collect(Collectors.toList());;
    }
}
