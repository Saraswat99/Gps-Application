package com.vehicle.app.controller;

import com.mongodb.client.result.UpdateResult;
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
@RestController
@RequestMapping(value = "/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping(value = "/list")
    public ApiResponse<List<DeviceDTO>> listAllDevices(Authentication authentication) {
        List<Device> deviceList = deviceService.listAllDevices(authentication);
        log.info("list of devices:- "+deviceList+"--------------------------------------------");
        return new ApiResponse<>(deviceList.stream().map(DeviceDTO::convertToDTO).collect(Collectors.toList()));
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ROLE_TRANSPORTER')")
    public ApiResponse<DeviceDTO> saveDevice(@RequestBody DeviceDTO deviceDTO, Authentication authentication) {
        return new ApiResponse<>(deviceService.saveDevice(deviceDTO, authentication));
    }

    @PutMapping(value = "/update")
    @PreAuthorize("hasRole('ROLE_TRANSPORTER')")
    public ApiResponse<DeviceDTO> updateDevice(@RequestBody DeviceDTO deviceDTO, Authentication authentication) {
        return new ApiResponse<>(deviceService.updateDevice(deviceDTO, authentication));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "delete/{id}")
    @PreAuthorize("!hasRole('ROLE_TRANSPORTER')")
    public ApiResponse<String> deleteDevice(@PathVariable String id, Authentication authentication) {
        deviceService.deleteById(id, authentication);
        return new ApiResponse<>("Device deleted successfully");
    }

    @PutMapping(value = "/{active}/{deviceId}")
    @PreAuthorize("!hasRole('ROLE_TRANSPORTER')")
    public ApiResponse<String> activeDevice(@PathVariable("active") boolean active, @PathVariable("deviceId") String deviceId, Authentication authentication) {
        UpdateResult result=deviceService.activeDevice(active, deviceId, authentication);
        if(result.getModifiedCount()==1) {
            return new ApiResponse<>("Device Successfully Updated");
        }
        return new ApiResponse<>("Unsuccessfull to Update your device");
    }
}