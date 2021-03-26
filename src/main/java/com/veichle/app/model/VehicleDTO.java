package com.veichle.app.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.veichle.app.entity.Device;
import com.veichle.app.entity.Vehicle;
import com.veichle.app.enums.VehicleStatus;
import com.veichle.app.enums.VehicleType;
import com.veichle.app.utils.DateTimeUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleDTO {

    private Long id;
    private String number;
    private double lat;
    private double lng;
    private boolean active;
    private String vehicleType;
    private String vehicleStatus;
    private String createdBy;
    private String modifiedBy;
    private String added;
    private String updated;
    private Device device;

    public static Vehicle convertToVehicle(VehicleDTO vehicleDTO){
        Vehicle vehicle=new Vehicle();
        vehicle.setNumber(vehicleDTO.getNumber());
        vehicle.setVehicleType(VehicleType.valueOf(vehicleDTO.getVehicleType()));
        vehicle.setVehicleStatus(VehicleStatus. valueOf(vehicleDTO.getVehicleStatus()));
        vehicle.setActive(vehicleDTO.isActive());
        vehicle.setLat(vehicleDTO.getLat());
        vehicle.setLng(vehicleDTO.getLng());
        vehicle.setDevice(vehicleDTO.getDevice());
        return vehicle;
    }

    public static VehicleDTO convertToVehicleDTO(Vehicle vehicle)
    {
        VehicleDTO vehicleDTO=new VehicleDTO();
        vehicleDTO.setId(vehicle.getId());
        vehicleDTO.setModifiedBy(vehicle.getModifiedBy());
        vehicleDTO.setCreatedBy(vehicle.getCreatedBy());
        vehicleDTO.setNumber(vehicle.getNumber());
        vehicleDTO.setVehicleType(vehicle.getVehicleType().name());
        vehicleDTO.setVehicleStatus(vehicle.getVehicleStatus().name());
        vehicleDTO.setActive(vehicle.isActive());
        vehicleDTO.setLat(vehicle.getLat());
        vehicleDTO.setLng(vehicle.getLng());
        vehicleDTO.setAdded(DateTimeUtils.convertToString(vehicle.getAdded()));
        vehicleDTO.setUpdated(DateTimeUtils.convertToString(vehicle.getUpdated()));
        vehicleDTO.setDevice(vehicle.getDevice());
        return vehicleDTO;
    }

    public static void convertToExistingVehicle(Vehicle exisitingVehicle, VehicleDTO vehicleDTO) {
        exisitingVehicle.setLat(vehicleDTO.getLat());
        exisitingVehicle.setLng(vehicleDTO.getLng());
        exisitingVehicle.setActive(vehicleDTO.isActive());
        exisitingVehicle.setVehicleStatus(VehicleStatus.valueOf(vehicleDTO.getVehicleStatus()));
        exisitingVehicle.setVehicleType(VehicleType.valueOf(vehicleDTO.getVehicleType()));
        exisitingVehicle.setNumber(vehicleDTO.getNumber());
        exisitingVehicle.setDevice(vehicleDTO.getDevice());
    }
}
