package com.ap.portfolio.security.roles;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
@Getter @Setter @NoArgsConstructor
@Entity
public class IUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Size(min = 1, max = 70, message = "Invalid Value")
    private String name;
    @NotNull
    @Column(unique = true)
    private String username;
    private String email;
    @NotNull
    private String pwd;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name ="role_id"))
    private Set<Role> roles = new HashSet<>();

    public IUser(String name, String username, String email, String pwd) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.pwd = pwd;
    }
}
