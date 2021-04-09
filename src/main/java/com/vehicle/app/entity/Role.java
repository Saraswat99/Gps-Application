package com.vehicle.app.entity;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Table
@Entity
public class Role{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String roleUser;
    @Column(nullable = false)
    private boolean isDefault;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private List<User> users;
}