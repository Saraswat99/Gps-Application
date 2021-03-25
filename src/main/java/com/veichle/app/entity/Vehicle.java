package com.veichle.app.entity;

import com.veichle.app.enums.VehicleStatus;
import com.veichle.app.enums.VehicleType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table
public class Vehicle extends BaseEntity {

    @Column(nullable = false,unique = true)
    private String number;
    @Column
    private double latitude;
    @Column
    private double longitude;
    @Column
    private boolean active;
    @Column
    private VehicleType vehicleType;
    @Column
    private VehicleStatus vehicleStatus;
    @OneToOne(cascade = CascadeType.ALL)
    private Device device;
}