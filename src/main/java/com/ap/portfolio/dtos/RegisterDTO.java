package com.ap.portfolio.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RegisterDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String pwd;
}
