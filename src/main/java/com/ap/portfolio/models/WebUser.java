package com.ap.portfolio.models;

import com.ap.portfolio.security.roles.IUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter @Setter @NoArgsConstructor
@Entity
public class WebUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String lastName;
    private String about;
    private String email;
    private String img;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "webUser")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private IUser account;
    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Skill> skills;
    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Experience> experience;
    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Education> education;

    public WebUser(String name, String lastName,String email, String img, String about, IUser account) {
        this.name = name;
        this.lastName = lastName;
        this.img = img;
        this.account = account;
        this.about=about;
        this.email=email;
    }
}
