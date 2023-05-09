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
    private String imgURL;
    private boolean active;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private WebUser user;

    public Experience(String expName, String expDescription, String imgURL) {
        this.expName = expName;
        this.expDescription = expDescription;
        this.imgURL=imgURL;
        this.setActive(true);
    }
}
