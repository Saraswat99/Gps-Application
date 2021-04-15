package com.vehicle.app.entity;

import com.vehicle.app.enums.VehicleStatus;
import com.vehicle.app.enums.VehicleType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table
public class Vehicle extends BaseEntity {

    @Column(unique = true)
    private String number;
    @Column
    private double lat;
    @Column
    private double lng;
    @Column(nullable = false, name = "active", columnDefinition = "boolean default false")
    private boolean active;
    @Column
    private VehicleType vehicleType;
    @Column
    private VehicleStatus vehicleStatus;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = true)
    private Device device;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(nullable = false)
    private String level;
}