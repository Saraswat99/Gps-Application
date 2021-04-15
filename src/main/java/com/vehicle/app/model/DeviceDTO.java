package com.vehicle.app.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vehicle.app.entity.Device;
import com.vehicle.app.entity.User;
import com.vehicle.app.enums.DeviceType;
import com.vehicle.app.enums.SimOperator;
import com.vehicle.app.utils.DateTimeUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceDTO {

    private Long id;
    private String imei;
    private String simOperator;
    private String deviceType;
    private String simNumber;
    private boolean active;
    private boolean assigned;
    private String createdBy;
    private String modifiedBy;
    private String added;
    private String updated;
    private Long userId;
    private User user;

    public static Device convertToDevice(DeviceDTO deviceDTO) {
        Device device = new Device();
        device.setImei(deviceDTO.getImei());
        device.setSimNumber(deviceDTO.getSimNumber());
        device.setSimOperator(SimOperator.valueOf(deviceDTO.getSimOperator()));
        device.setDeviceType(DeviceType.valueOf(deviceDTO.getDeviceType()));
        return device;
    }

    public static DeviceDTO convertToDTO(Device device) {
        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setId(device.getId());
        deviceDTO.setUserId(device.getUser().getId());
        deviceDTO.setImei(device.getImei());
        deviceDTO.setSimOperator(device.getSimOperator().name());
        deviceDTO.setDeviceType(device.getDeviceType().name());
        deviceDTO.setSimNumber(device.getSimNumber());
        deviceDTO.setActive(device.isActive());
        deviceDTO.setAssigned(device.isAssigned());
        deviceDTO.setCreatedBy(device.getCreatedBy());
        deviceDTO.setModifiedBy(device.getModifiedBy());
        deviceDTO.setAdded(DateTimeUtils.convertToString(device.getAdded()));
        deviceDTO.setUpdated(DateTimeUtils.convertToString(device.getUpdated()));
        return deviceDTO;
    }

    public static void convertToExistingDevice(Device existingDevice, DeviceDTO deviceDTO) {
        existingDevice.setImei(deviceDTO.getImei());
        existingDevice.setSimNumber(deviceDTO.getSimNumber());
        existingDevice.setDeviceType(DeviceType.valueOf(deviceDTO.getDeviceType()));
        existingDevice.setSimOperator(SimOperator.valueOf(deviceDTO.getSimOperator()));
    }
}