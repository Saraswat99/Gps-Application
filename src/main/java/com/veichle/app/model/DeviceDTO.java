package com.veichle.app.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.veichle.app.entity.Device;
import com.veichle.app.enums.DeviceType;
import com.veichle.app.enums.Operator;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceDTO{

    private Long id;
    private String createdBy;
    private String modifiedBy;
    private String added;
    private String updated;
    private String imei;
    private String simNumber;
    private String operator;
    private String deviceType;
    private boolean active;

    public static DeviceDTO convertToDTO(Device device){
        DeviceDTO deviceDTO=new DeviceDTO();
        deviceDTO.setId(device.getId());
        deviceDTO.setCreatedBy(device.getCreatedBy());
        deviceDTO.setModifiedBy(device.getModifiedBy());
        deviceDTO.setImei(device.getImei());
        deviceDTO.setSimNumber(device.getSimNumber());
        deviceDTO.setOperator(device.getOperator().name());
        deviceDTO.setDeviceType(device.getDeviceType().name());
        deviceDTO.setActive(device.isActive());
        return deviceDTO;
    }

    public static Device convertToDevice(DeviceDTO deviceDTO) {
        Device device=new Device();
        device.setId(deviceDTO.getId());
        device.setImei(deviceDTO.getImei());
        device.setSimNumber(deviceDTO.getSimNumber());
        device.setOperator(Enum.valueOf(Operator.class,deviceDTO.getOperator()));
        device.setDeviceType(Enum.valueOf(DeviceType.class,deviceDTO.getDeviceType()));
        device.setActive(deviceDTO.isActive());
        return device;
    }
}
