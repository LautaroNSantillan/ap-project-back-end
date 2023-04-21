package com.ap.portfolio.models;

import com.ap.portfolio.security.roles.IUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Getter @Setter @NoArgsConstructor
@Entity
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String eduName;
    private String eduDescription;
    private boolean active;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private WebUser user;

    public Education(String eduName, String eduDescription,WebUser user) {
        this.eduName = eduName;
        this.eduDescription = eduDescription;
        this.active = true;
        this.user = user;
    }
}
