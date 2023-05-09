package com.ap.portfolio.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

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
    private LocalDate  birthdate;
    private boolean active;

    public MockUser(String name, String lastName, LocalDate bday){
        this.setActive(true);
        this.setName(name);
        this.setLastName(lastName);
        this.setBirthdate(bday);
    }
}
