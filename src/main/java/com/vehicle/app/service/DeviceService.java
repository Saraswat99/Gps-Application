package com.vehicle.app.service;

import com.vehicle.app.entity.User;
import com.vehicle.app.repository.DeviceRepository;
import com.vehicle.app.entity.Device;
import com.vehicle.app.model.DeviceDTO;
import com.vehicle.app.repository.UserRepository;
import com.vehicle.app.repository.VehicleRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Data
@Service
public class DeviceService {

    @Autowired
    private final DeviceRepository deviceRepository;
    @Autowired
    private final VehicleRepository vehicleRepository;
    @Autowired
    private final UserRepository userRepository;

    //get all the devices
    public List<Device> findAll(Authentication authentication) {
        final User user =(User) authentication.getPrincipal();
        final String createdBy = user.getUsername();
        return deviceRepository.findByCreatedBy(createdBy);
    }

    //save device
    public DeviceDTO save(DeviceDTO deviceDTO,Authentication authentication) {
        final User userAuth = (User) authentication.getPrincipal();
        final Long userId = userAuth.getId();
        Optional.ofNullable(userId).filter(us->us>0).orElseThrow(()->new RuntimeException("Please enter user id"));
        final User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not Found"));
        final Device device = DeviceDTO.convertToDevice(deviceDTO);
        device.setUser(user);
        final Device updatedDevice = deviceRepository.save(device);
        return DeviceDTO.convertToDTO(updatedDevice);
    }

    //delete device
    public void deleteById(Long id,Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        String createdBy = user.getUsername();
        Device existingDevice = deviceRepository.findByIdAndCreatedBy(id,createdBy).orElseThrow(()->new RuntimeException("Device does not exist"));
        if(!existingDevice.isAssigned())
            deviceRepository.deleteById(id);
        else
            throw new RuntimeException("Device already in use");
    }

    //update device
    public DeviceDTO update(DeviceDTO deviceDTO, Authentication authentication) {
        Long deviceId = deviceDTO.getId();
        Optional.ofNullable(deviceId).filter(di->di>0).orElseThrow(()-> new RuntimeException("Please provide Device Id"));
        User user = (User) authentication.getPrincipal();
        String createdBy = user.getUsername();
        Device existingDevice = deviceRepository.findByIdAndCreatedBy(deviceId,createdBy).orElseThrow(()-> new RuntimeException("Device does not exist"));
        log.info("Id {}",existingDevice.getId());
        log.info("Existing Device {}",existingDevice);
        DeviceDTO.convertToExistingDevice(existingDevice,deviceDTO);
        existingDevice = deviceRepository.save(existingDevice);
        return DeviceDTO.convertToDTO(existingDevice);
    }
}