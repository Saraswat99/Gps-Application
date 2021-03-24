package com.veichle.app.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.veichle.app.entity.Device;
import com.veichle.app.enums.VeichleStatus;
import com.veichle.app.enums.VehicleType;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleDTO {

    private long id;
    private String createdBy;
    private String modifiedBy;
    private Date added;
    private Date updated;
    private String number;
    private double lat;
    private double lng;
    private boolean active;
    private VehicleType vehicleType;
    private VeichleStatus veichleStatus;
    private Device device;
}
