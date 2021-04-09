package com.vehicle.app.service;

import com.vehicle.app.entity.Vehicle;
import com.vehicle.app.model.VehicleDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface VehicleService {

    VehicleDTO save(VehicleDTO vehicleDTO, Authentication authentication);

    List<Vehicle> getVehicleList(Authentication authentication);

    Vehicle getVehicleById(Long vehicleId);

    VehicleDTO update(VehicleDTO vehicleDTO, Authentication authentication);

    void delete(Long vehicleId, Authentication authentication);
}
