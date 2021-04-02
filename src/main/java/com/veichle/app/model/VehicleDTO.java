package com.veichle.app.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.veichle.app.entity.Device;
import com.veichle.app.entity.User;
import com.veichle.app.entity.Vehicle;
import com.veichle.app.enums.DeviceType;
import com.veichle.app.enums.SimOperator;
import com.veichle.app.enums.VehicleStatus;
import com.veichle.app.enums.VehicleType;
import com.veichle.app.repository.DeviceRepository;
import com.veichle.app.utils.DateTimeUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;


@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleDTO {

    @Autowired
    private static DeviceRepository deviceRepository;

    private long id;
    private String number;
    private double latitude;
    private double longitude;
    private boolean active;
    private String vehicleType;
    private String vehicleStatus;
    private String createdBy;
    private String modifiedBy;
    private String added;
    private String updated;
    private Device device;
    private Long deviceId;
    private Long userId;
    private DeviceDTO deviceDTO;

    public static VehicleDTO convertToDTO(Vehicle vehicle) {
        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setId(vehicle.getId());
        vehicleDTO.setNumber(vehicle.getNumber());
        vehicleDTO.setLatitude(vehicle.getLatitude());
        vehicleDTO.setLongitude(vehicle.getLongitude());
        vehicleDTO.setVehicleType(vehicle.getVehicleType().name());
        vehicleDTO.setVehicleStatus(vehicle.getVehicleStatus().name());
        vehicleDTO.setActive(vehicle.isActive());
        vehicleDTO.setCreatedBy(vehicle.getCreatedBy());
        vehicleDTO.setModifiedBy(vehicle.getModifiedBy());
        vehicleDTO.setAdded(DateTimeUtils.convertToString(vehicle.getAdded()));
        vehicleDTO.setUpdated(DateTimeUtils.convertToString(vehicle.getUpdated()));
        vehicleDTO.setDeviceDTO(DeviceDTO.convertToDTO(vehicle.getDevice()));
        return vehicleDTO;
    }

    public static Vehicle convertToVehicle(VehicleDTO vehicleDTO) {
        Vehicle vehicle = new Vehicle();
        vehicle.setNumber(vehicleDTO.getNumber());
        vehicle.setLatitude(vehicleDTO.getLatitude());
        vehicle.setLongitude(vehicleDTO.getLongitude());
        vehicle.setVehicleStatus(Enum.valueOf(VehicleStatus.class, vehicleDTO.getVehicleStatus()));
        vehicle.setVehicleType(Enum.valueOf(VehicleType.class, vehicleDTO.getVehicleType()));
        vehicle.setActive(vehicleDTO.isActive());
        return vehicle;
    }

    public static void convertToExistingVehicle(Vehicle existingVehicle, VehicleDTO vehicleDTO) {
        existingVehicle.setLatitude(vehicleDTO.getLatitude());
        existingVehicle.setLongitude(vehicleDTO.getLongitude());
        existingVehicle.setVehicleType(VehicleType.valueOf(vehicleDTO.getVehicleType()));
        existingVehicle.setVehicleStatus(VehicleStatus.valueOf(vehicleDTO.getVehicleStatus()));
        existingVehicle.setActive(vehicleDTO.isActive());
    }
}