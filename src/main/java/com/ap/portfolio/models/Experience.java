package com.ap.portfolio.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter @Setter @NoArgsConstructor
@Entity
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String expName;
    private String expDescription;
    private boolean active;

    public Experience(String expName, String expDescription) {
        this.expName = expName;
        this.expDescription = expDescription;
        this.setActive(true);
    }
}
