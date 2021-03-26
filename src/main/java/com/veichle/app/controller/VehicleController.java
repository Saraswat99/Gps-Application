package com.veichle.app.controller;

import com.veichle.app.entity.Vehicle;
import com.veichle.app.model.ApiResponse;
import com.veichle.app.model.VehicleDTO;
import com.veichle.app.service.VehicleService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Slf4j
@RestController
@RequestMapping("/vehicles")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @PostMapping(value="/save")
    public VehicleDTO save(@RequestBody VehicleDTO vehicleDTO) {
        log.info(vehicleDTO.toString());
        return vehicleService.save(vehicleDTO);
    }

    @GetMapping(value="/list")
    public List<VehicleDTO> getVeichles() {
        List<Vehicle> vehicle=vehicleService.getVeichles();
        return vehicle.stream().map(VehicleDTO::convertToVehicleDTO).collect(Collectors.toList());
    }

    @GetMapping(value="/list/{id}")
    public VehicleDTO getVeichleById(@PathVariable long id) {
        Optional<Vehicle> veichleOptional= vehicleService.getVeichleById(id);
        if(veichleOptional.isPresent())
            return VehicleDTO.convertToVehicleDTO(veichleOptional.get());
        return null;
    }

    @PutMapping(value="/update")
    public VehicleDTO update(@RequestBody VehicleDTO vehicleDTO) {
        log.info(vehicleDTO.toString());
        return vehicleService.update(vehicleDTO);
    }

    @DeleteMapping(value="/delete/{id}")
    public ApiResponse<String> delete(@PathVariable long id) {
        vehicleService.delete(id);
        return new ApiResponse<>("Vehicle Deleted");
    }

}
