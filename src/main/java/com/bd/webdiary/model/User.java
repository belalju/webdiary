package com.bd.webdiary.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

import static com.bd.webdiary.util.CommonConstant.PASSWORD_ENCODER;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @NotBlank
    private String userName;

    @NotBlank
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public void createPassword(String password){
        if(!password.isEmpty()){
            this.password = PASSWORD_ENCODER.encode(password);
        }

    }
}
