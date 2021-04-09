package com.vehicle.app.service;

import com.vehicle.app.entity.Device;
import com.vehicle.app.model.DeviceDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface DeviceService {

    List<Device> findAll(Authentication authentication);

    DeviceDTO save(DeviceDTO deviceDTO, Authentication authentication);

    void deleteById(Long id, Authentication authentication);

    DeviceDTO update(DeviceDTO deviceDTO, Authentication authentication);
}
