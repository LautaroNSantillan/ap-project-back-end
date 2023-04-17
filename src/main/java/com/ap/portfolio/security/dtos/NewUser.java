package com.ap.portfolio.security.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@Getter @Setter
public class NewUser {
    private String name;
    private String username;
    private String email;
    private String password;
    private Set<String> roles = new HashSet<>();
}
