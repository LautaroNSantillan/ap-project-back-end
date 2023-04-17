package com.ap.portfolio.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter @NoArgsConstructor
@Entity
public class MockUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Size(min = 1, max = 70, message = "Invalid Value")
    private String name;
    @NotNull
    @Size(min = 1, max = 70, message = "Invalid Value")
    private String lastName;
    @Size(min = 1, max = 70, message = "Invalid Value")
    private String email;
    private boolean active;

    public MockUser(String name, String lastName, String email){
        this.setActive(true);
        this.setName(name);
        this.setLastName(lastName);
        this.setEmail(email);
    }
}
