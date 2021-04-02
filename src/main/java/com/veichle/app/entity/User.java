package com.veichle.app.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@ToString
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User extends  BaseEntity{
    @Column(nullable = false)
    private String name;
    @Column(nullable = false,unique = true)
    private String userName;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private boolean active;
    @OneToMany(mappedBy = "user")
    private List<Vehicle> vehicles;
}