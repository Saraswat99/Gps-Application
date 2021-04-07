package com.vehicle.app.controller;

import com.vehicle.app.entity.Device;
import com.vehicle.app.model.ApiResponse;
import com.vehicle.app.model.DeviceDTO;
import com.vehicle.app.service.DeviceService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Data
@RequestMapping(value = "/device")
@RestController
@PreAuthorize("hasRole('ROLE_USER')")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping(value = "/list")
    public List<DeviceDTO> getAllDevices(Authentication authentication){
        List<Device> deviceList = deviceService.findAll(authentication);
        return deviceList.stream().map(DeviceDTO::convertToDTO).collect(Collectors.toList());
    }

    @PostMapping("/save")
    public DeviceDTO saveDevice(@RequestBody DeviceDTO deviceDTO, Authentication authentication){
        return deviceService.save(deviceDTO,authentication);
    }

    @PutMapping(value="/update")
    public DeviceDTO update(@RequestBody DeviceDTO deviceDTO, Authentication authentication) {
        return deviceService.update(deviceDTO, authentication);
    }

    @RequestMapping(method= RequestMethod.DELETE, value = "/{id}")
    public ApiResponse<String> deleteDevice(@PathVariable Long id,Authentication authentication){
        deviceService.deleteById(id,authentication);
        return new ApiResponse<>("Device deleted successfully");
    }
}