package com.vehicle.app.service;

import com.vehicle.app.entity.Device;
import com.vehicle.app.model.DeviceDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface DeviceService {

    List<Device> listAllDevices(Authentication authentication);

    DeviceDTO saveDevice(DeviceDTO deviceDTO, Authentication authentication);

    DeviceDTO updateDevice(DeviceDTO deviceDTO, Authentication authentication);

    void deleteById(Long id, Authentication authentication);

    int activeDevice(boolean active, Long deviceId, Authentication authentication);
}