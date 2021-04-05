package com.vehicle.app.controller;

import com.vehicle.app.entity.User;
import com.vehicle.app.entity.Vehicle;
import com.vehicle.app.model.ApiResponse;
import com.vehicle.app.model.VehicleDTO;
import com.vehicle.app.repository.VehicleRepository;
import com.vehicle.app.service.VehicleService;
import org.springframework.security.core.Authentication;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping(value="/save")
    public VehicleDTO save(@RequestBody VehicleDTO vehicleDTO, Authentication authentication) {
        return vehicleService.save(vehicleDTO,authentication);
    }

    @GetMapping(value="/list")
    public List<VehicleDTO> getVehicleList(Authentication authentication) {
        User user= (User) authentication.getPrincipal();

        List<Vehicle> vehicles = vehicleService.getVehicleList();
        return vehicles.stream().map(VehicleDTO::convertToVehicleDTO).collect(Collectors.toList());
    }

    @GetMapping(value="/list/{id}")
    public VehicleDTO getVehicleById(@PathVariable long id) {
        Vehicle vehicle= vehicleService.getVehicleById(id);
        return VehicleDTO.convertToVehicleDTO(vehicle);
    }

    @PutMapping(value="/update")
    public VehicleDTO update(@RequestBody VehicleDTO vehicleDTO, Authentication authentication) {
        return vehicleService.update(vehicleDTO,authentication);
    }

    @DeleteMapping(value="/delete/{id}")
    public ApiResponse<String> delete(@PathVariable Long id, Authentication authentication) {
        vehicleService.delete(id,authentication);
        return new ApiResponse<>("Vehicle successfully Deleted");
    }
}