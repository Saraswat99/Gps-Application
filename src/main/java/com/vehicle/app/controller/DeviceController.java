package com.vehicle.app.controller;

import com.vehicle.app.entity.Device;
import com.vehicle.app.model.ApiResponse;
import com.vehicle.app.model.DeviceDTO;
import com.vehicle.app.service.DeviceService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Data
@RequestMapping(value = "/devices")
@RestController
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping(value = "/list")
    public List<DeviceDTO> getAllDevices(Authentication authentication){
        String username = authentication.getName();
//        log.info("User ==>> {}",username);
        List<Device> deviceList = deviceService.findAll(username);
        return deviceList.stream().map(DeviceDTO::convertToDTO).collect(Collectors.toList());
    }

    @PostMapping("/save")
    public DeviceDTO saveDevice(@RequestBody DeviceDTO deviceDTO){
//        log.info(deviceDTO.toString());
        return deviceService.save(deviceDTO);
    }

    @PutMapping(value="/update")
    public DeviceDTO update(@RequestBody DeviceDTO deviceDTO) {
//        log.info(deviceDTO.toString());
        return deviceService.update(deviceDTO);
    }

    @RequestMapping(method= RequestMethod.DELETE, value = "/{id}")
    public ApiResponse<String> deleteDevice(@PathVariable Long id){
        deviceService.deleteById(id);
        return new ApiResponse<>("Device deleted successfully");
    }
}