package com.vehicle.app.controller;

import com.vehicle.app.entity.Vehicle;
import com.vehicle.app.model.ApiResponse;
import com.vehicle.app.model.VehicleDTO;
import com.vehicle.app.repository.VehicleRepository;
import com.vehicle.app.service.VehicleService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Slf4j
@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private VehicleRepository vehicleRepository;

    @PostMapping(value = "/save")
    @PreAuthorize("hasRole('ROLE_TRANSPORTER')")
    public ApiResponse<VehicleDTO> saveVehicle(@RequestBody VehicleDTO vehicleDTO, Authentication authentication) {
        return new ApiResponse<>(vehicleService.saveVehicle(vehicleDTO, authentication));
    }

    @GetMapping(value = "/list")
    public ApiResponse<List<VehicleDTO>> listVehicleList(Authentication authentication) {
        List<Vehicle> vehicle = vehicleService.listVehicleList(authentication);
        return new ApiResponse<>(vehicle.stream().map(VehicleDTO::convertToVehicleDTO).collect(Collectors.toList()));
    }

    @GetMapping(value = "/list/{id}")
    public ApiResponse<VehicleDTO> getVehicleById(@PathVariable long id) {
        Vehicle vehicle = vehicleService.getVehicleById(id);
        return new ApiResponse<>(VehicleDTO.convertToVehicleDTO(vehicle));
    }

    @PutMapping(value = "/update")
    @PreAuthorize("hasRole('ROLE_TRANSPORTER')")
    public ApiResponse<VehicleDTO> updateVehicle(@RequestBody VehicleDTO vehicleDTO, Authentication authentication) {
        return new ApiResponse<>(vehicleService.updateVehicle(vehicleDTO, authentication));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ApiResponse<String> deleteVehicle(@PathVariable Long id, Authentication authentication) {
        vehicleService.deleteVehicle(id, authentication);
        return new ApiResponse<>("Vehicle successfully Deleted");
    }
}