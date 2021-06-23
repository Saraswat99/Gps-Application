package com.vehicle.app.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vehicle.app.entity.Vehicle;
import com.vehicle.app.enums.VehicleStatus;
import com.vehicle.app.enums.VehicleType;
import com.vehicle.app.utils.DateTimeUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleDTO {

    private String id;
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
    private DeviceDTO deviceDTO;
    private String deviceId;

    public static Vehicle convertToVehicle(VehicleDTO vehicleDTO) {
        Vehicle vehicle = new Vehicle();
        vehicle.setNumber(vehicleDTO.getNumber());
        vehicle.setVehicleType(VehicleType.valueOf(vehicleDTO.getVehicleType()));
        vehicle.setVehicleStatus(VehicleStatus.valueOf(vehicleDTO.getVehicleStatus()));
        vehicle.setLat(vehicleDTO.getLat());
        vehicle.setLng(vehicleDTO.getLng());
        return vehicle;
    }

    public static VehicleDTO convertToVehicleDTO(Vehicle vehicle) {
        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setId(vehicle.getId());
        vehicleDTO.setModifiedBy(vehicle.getModifiedBy());
        vehicleDTO.setCreatedBy(vehicle.getCreatedBy());
        vehicleDTO.setNumber(vehicle.getNumber());
        vehicleDTO.setVehicleType(vehicle.getVehicleType().name());
        vehicleDTO.setVehicleStatus(vehicle.getVehicleStatus().name());
        vehicleDTO.setActive(vehicle.isActive());
        vehicleDTO.setLat(vehicle.getLat());
        vehicleDTO.setLng(vehicle.getLng());
//        vehicleDTO.setAdded(DateTimeUtils.convertToString(vehicle.getAdded()));
//        vehicleDTO.setUpdated(DateTimeUtils.convertToString(vehicle.getUpdated()));
        vehicleDTO.setDeviceDTO(DeviceDTO.convertToDTO(vehicle.getDevice()));
        return vehicleDTO;
    }

    public static void convertToExistingVehicle(Vehicle existingVehicle, VehicleDTO vehicleDTO) {
        existingVehicle.setNumber(vehicleDTO.getNumber());
        existingVehicle.setVehicleType(VehicleType.valueOf(vehicleDTO.getVehicleType()));
    }
}
