package com.veichle.app.controller;

import com.veichle.app.entity.Device;
import com.veichle.app.entity.Vehicle;
import com.veichle.app.model.ApiResponse;
import com.veichle.app.model.DeviceDTO;
import com.veichle.app.model.VehicleDTO;
import com.veichle.app.service.VehicleService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping(value = "/list")
    public List<VehicleDTO> getAllVehicles(Authentication authentication){
        String username = authentication.getName();
        log.info("User ==>> {}",username);
        List<Vehicle> vehiclesList = vehicleService.findAll(username);
        return vehiclesList.stream().map(VehicleDTO::convertToDTO).collect(Collectors.toList());
    }

    @RequestMapping(method= RequestMethod.POST,value="/save")
    public VehicleDTO save(@RequestBody VehicleDTO vehicleDTO) {
        log.info(vehicleDTO.toString());
        return vehicleService.save(vehicleDTO);
    }


    @PutMapping(value="/update")
    public VehicleDTO update(@RequestBody VehicleDTO vehicleDTO) {
        log.info(vehicleDTO.toString());
        return vehicleService.update(vehicleDTO);
    }

    @RequestMapping(method= RequestMethod.DELETE, value = "/{id}")
    public ApiResponse<String> deleteVehicle(@PathVariable Long id){

        vehicleService.deleteById(id);
        return new ApiResponse<>("Vehicle deleted");
    }
}