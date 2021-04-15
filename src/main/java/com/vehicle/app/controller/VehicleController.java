package com.vehicle.app.controller;

import com.vehicle.app.entity.Vehicle;
import com.vehicle.app.model.ApiResponse;
import com.vehicle.app.model.VehicleDTO;
import com.vehicle.app.service.VehicleService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
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

    private final VehicleService vehicleService;

    @PostMapping(value = "/create")
    @PreAuthorize("hasRole('ROLE_TRANSPORTER')")
    public ApiResponse<VehicleDTO> createVehicle(@RequestBody VehicleDTO vehicleDTO, Authentication authentication) {
        return new ApiResponse<>(vehicleService.saveVehicle(vehicleDTO, authentication));
    }

    @PutMapping(value = "/update")
    @PreAuthorize("hasRole('ROLE_TRANSPORTER')")
    public ApiResponse<VehicleDTO> updateVehicle(@RequestBody VehicleDTO vehicleDTO, Authentication authentication) {
        return new ApiResponse<>(vehicleService.updateVehicle(vehicleDTO, authentication));
    }

    @GetMapping(value = "/list")
    public ApiResponse<List<VehicleDTO>> listVehicle(Authentication authentication) {
        List<Vehicle> vehicle = vehicleService.listVehicleList(authentication);
        return new ApiResponse<>(vehicle.stream().map(VehicleDTO::convertToVehicleDTO).collect(Collectors.toList()));
    }

    @GetMapping(value = "/list/{id}")
    public ApiResponse<VehicleDTO> listVehicle(@PathVariable long id,Authentication authentication) {
        Vehicle vehicle = vehicleService.getVehicleById(id,authentication);
        return new ApiResponse<>(VehicleDTO.convertToVehicleDTO(vehicle));
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("!hasRole('ROLE_TRANSPORTER')")
    public ApiResponse<String> deleteVehicle(@PathVariable Long id, Authentication authentication) {
        vehicleService.deleteVehicle(id, authentication);
        return new ApiResponse<>("Vehicle successfully Deleted");
    }

    @PutMapping(value = "/{active}/{vehicleId}")
    @PreAuthorize("!hasRole('ROLE_TRANSPORTER')")
    public ApiResponse<String> activeVehicle(@PathVariable("active") boolean active, @PathVariable("vehicleId") Long vehicleId, Authentication authentication) {
        int updateCount=vehicleService.activeVehicle(active, vehicleId, authentication);
        if(updateCount>0){
            return new ApiResponse<>("Vehicle Successfully Updated");
        }
        return new ApiResponse<>("Vehicle did not Updated");
    }
}