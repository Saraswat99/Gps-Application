package com.veichle.app.entity;

import com.veichle.app.enums.VeichleStatus;
import com.veichle.app.enums.VehicleType;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table
public class Vehicle extends BaseEntity {

    private String number;
    private double lat;
    private double lng;
    private boolean active;
    private VehicleType vehicleType;
    private VeichleStatus veichleStatus;
    @OneToOne(cascade = CascadeType.ALL)
    private Device device;
}
