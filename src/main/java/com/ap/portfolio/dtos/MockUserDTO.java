package com.ap.portfolio.dtos;

import com.ap.portfolio.models.MockUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class MockUserDTO {
    private String name;
    private String lastName;
    private String email;

    public MockUserDTO(MockUser mockUser) {
        this.name = mockUser.getName();
        this.lastName = mockUser.getLastName();
        this.email = mockUser.getEmail();
    }
}
