package com.vehicle.app.controller;

import com.vehicle.app.ServiceImpl.VehicleServiceImpl;
import com.vehicle.app.entity.Vehicle;
import com.vehicle.app.model.ApiResponse;
import com.vehicle.app.model.VehicleDTO;
import com.vehicle.app.repository.VehicleRepository;
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
@PreAuthorize("hasRole('ROLE_USER')")
public class VehicleController {

    @Autowired
    private VehicleServiceImpl vehicleServiceImpl;
    @Autowired
    private VehicleRepository vehicleRepository;

    @PostMapping(value = "/save")
    public VehicleDTO save(@RequestBody VehicleDTO vehicleDTO, Authentication authentication) {
        return vehicleServiceImpl.save(vehicleDTO, authentication);
    }

    @GetMapping(value = "/list")
    public List<VehicleDTO> getVehicleList(Authentication authentication) {
        List<Vehicle> vehicle = vehicleServiceImpl.getVehicleList(authentication);
        return vehicle.stream().map(VehicleDTO::convertToVehicleDTO).collect(Collectors.toList());
    }

    @GetMapping(value = "/list/{id}")
    public VehicleDTO getVehicleById(@PathVariable long id) {
        Vehicle vehicle = vehicleServiceImpl.getVehicleById(id);
        return VehicleDTO.convertToVehicleDTO(vehicle);
    }

    @PutMapping(value = "/update")
    public VehicleDTO update(@RequestBody VehicleDTO vehicleDTO, Authentication authentication) {
        return vehicleServiceImpl.update(vehicleDTO, authentication);
    }

    @DeleteMapping(value = "{id}")
    public ApiResponse<String> delete(@PathVariable Long id, Authentication authentication) {
        vehicleServiceImpl.delete(id, authentication);
        return new ApiResponse<>("Vehicle successfully Deleted");
    }
}