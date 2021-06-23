package com.vehicle.app.entity;

import com.vehicle.app.enums.VehicleStatus;
import com.vehicle.app.enums.VehicleType;
import io.github.kaiso.relmongo.annotation.FetchType;
import io.github.kaiso.relmongo.annotation.JoinProperty;
import io.github.kaiso.relmongo.annotation.ManyToOne;
import io.github.kaiso.relmongo.annotation.OneToOne;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Setter
@Getter
@Document
public class Vehicle extends BaseEntity {

    @Indexed(unique = true)
    private String number;
    @Field
    private double lat;
    @Field
    private double lng;
    @Field
    private boolean active;
    @Field
    private VehicleType vehicleType;
    @Field
    private VehicleStatus vehicleStatus;
    @DBRef
    private Device device;
    @DBRef
    private User user;
    @Field
    private String level;
}