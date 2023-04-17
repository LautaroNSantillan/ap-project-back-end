package com.ap.portfolio.security.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
@Getter @Setter @NoArgsConstructor
public class LoginUser {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
