package com.ap.portfolio.dtos;

import com.ap.portfolio.models.MockUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
public class MockUserDTO {
    private long id;
    private String name;
    private String lastName;
    private LocalDate birthdate;

    public MockUserDTO(MockUser mockUser) {
        this.id=mockUser.getId();
        this.name = mockUser.getName();
        this.lastName = mockUser.getLastName();
        this.birthdate = mockUser.getBirthdate();
    }
}
