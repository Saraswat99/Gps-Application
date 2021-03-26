package com.veichle.app.entity;

import com.veichle.app.enums.VehicleStatus;
import com.veichle.app.enums.VehicleType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;

@ToString
@Setter
@Getter
@Entity
@Table
public class Vehicle extends BaseEntity {

    @Column(nullable = false,unique = true)
    private String number;
    @Column
    private double lat;
    @Column
    private double lng;
    @Column
    private boolean active;
    @Column
    private VehicleType vehicleType;
    @Column
    private VehicleStatus vehicleStatus;
    @OneToOne(cascade = CascadeType.ALL)
    private Device device;
}