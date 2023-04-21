package com.ap.portfolio.models;

import com.ap.portfolio.security.roles.IUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter @NoArgsConstructor
@Entity
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String skillName;
    private double percentage;
    private boolean active;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private WebUser user;

    public Skill(String skillName, double percentage, WebUser user) {
        this.skillName = skillName;
        this.percentage = percentage;
        this.user=user;
        this.active=true;
    }
}
