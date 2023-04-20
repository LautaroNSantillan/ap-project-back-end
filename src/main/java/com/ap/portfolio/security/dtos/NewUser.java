package com.ap.portfolio.security.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@Getter @Setter @NoArgsConstructor
public class NewUser {
    private String name;
    private String username;
    private String password;
    private Set<String> roles = new HashSet<>();

    public NewUser(String name, String username, String password) {
        this.name=name;
        this.username = username;
        this.password = password;
    }
}
