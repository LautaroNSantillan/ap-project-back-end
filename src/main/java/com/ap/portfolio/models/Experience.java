package com.ap.portfolio.models;

import com.ap.portfolio.security.roles.IUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter @NoArgsConstructor
@Entity
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String expName;
    private String expDescription;
    private boolean active;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private IUser user;

    public Experience(String expName, String expDescription) {
        this.expName = expName;
        this.expDescription = expDescription;
        this.setActive(true);
    }
}