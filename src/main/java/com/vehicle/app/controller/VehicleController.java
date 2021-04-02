package com.vehicle.app.controller;

import com.vehicle.app.entity.Vehicle;
import com.vehicle.app.model.ApiResponse;
import com.vehicle.app.model.VehicleDTO;
import com.vehicle.app.service.VehicleService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
//        log.info(vehicleDTO.toString());
        return vehicleService.save(vehicleDTO);
    }

    @GetMapping(value="/list")
    public List<VehicleDTO> getVehicleList() {
        List<Vehicle> vehicle=vehicleService.getVehicleList();
        return vehicle.stream().map(VehicleDTO::convertToVehicleDTO).collect(Collectors.toList());
    }

    @GetMapping(value="/list/{id}")
    public VehicleDTO getVehicleById(@PathVariable long id) {
        Vehicle vehicle= vehicleService.getVehicleById(id);
        return VehicleDTO.convertToVehicleDTO(vehicle);
    }

    @PutMapping(value="/update")
    public VehicleDTO update(@RequestBody VehicleDTO vehicleDTO) {
//        log.info(vehicleDTO.toString());
        return vehicleService.update(vehicleDTO);
    }

    @DeleteMapping(value="/delete/{id}")
    public ApiResponse<String> delete(@PathVariable long id) {
        vehicleService.delete(id);
        return new ApiResponse<>("Vehicle Deleted");
    }

}
