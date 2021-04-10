package com.vehicle.app.service;

import com.vehicle.app.entity.Vehicle;
import com.vehicle.app.model.VehicleDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface VehicleService {

    Vehicle getVehicleById(Long vehicleId);

    VehicleDTO saveVehicle(VehicleDTO vehicleDTO, Authentication authentication);

    List<Vehicle> listVehicleList(Authentication authentication);

    VehicleDTO updateVehicle(VehicleDTO vehicleDTO, Authentication authentication);

    void deleteVehicle(Long id, Authentication authentication);
}
