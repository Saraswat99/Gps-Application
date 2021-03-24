package com.veichle.app.controller;

import com.veichle.app.entity.Device;
import com.veichle.app.model.DeviceDTO;
import com.veichle.app.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping(value = "/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping(value = "/list")
    public List<DeviceDTO> getDeviceList(Authentication authentication){
        String username=authentication.getName();
        log.info("User ==>> {}",username);
        List<Device> deviceList=deviceService.getDeviceList(username);
        return deviceList.stream().map(DeviceDTO::convertToDTO).collect(Collectors.toList());
    }

    @PostMapping(value="/save")
    public DeviceDTO save(@RequestBody DeviceDTO deviceDTO){
        log.info(deviceDTO.toString());
        Device deviceSaved=deviceService.save(deviceDTO);
        return DeviceDTO.convertToDTO(deviceSaved);
    }

    @PutMapping(value="/update")
    public DeviceDTO update(@RequestBody DeviceDTO deviceDTO) {
        log.info(deviceDTO.toString());
        Device device=DeviceDTO.convertToDevice(deviceDTO);
        Device deviceUpdated=deviceService.update(device);
        return DeviceDTO.convertToDTO(deviceUpdated);
    }

    @DeleteMapping(value="/delete/{id}")
    public void delete(@PathVariable long id ){
        deviceService.delete(id);
    }


}
