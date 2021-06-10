package com.vehicle.app.entity;


import com.vehicle.app.enums.VehicleStatus;
import com.vehicle.app.enums.VehicleType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document
public class Vehicle extends BaseEntity {

    @Indexed(unique = true)
    private String number;
    private double lat;
    private double lng;
    private boolean active;
    private VehicleType vehicleType;
    private VehicleStatus vehicleStatus;
    @DBRef
    private Device device;
    @DBRef
    private User user;
    private String level;
}