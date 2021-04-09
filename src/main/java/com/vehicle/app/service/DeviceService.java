package com.vehicle.app.service;

import com.vehicle.app.entity.Device;
import com.vehicle.app.model.DeviceDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface DeviceService {

    public List<Device> findAll(Authentication authentication);

    public DeviceDTO save(DeviceDTO deviceDTO, Authentication authentication);

    public void deleteById(Long id,Authentication authentication);

    public DeviceDTO update(DeviceDTO deviceDTO, Authentication authentication);
    }
