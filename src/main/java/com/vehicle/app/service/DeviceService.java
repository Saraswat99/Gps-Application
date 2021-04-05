package com.vehicle.app.service;

import com.vehicle.app.entity.Vehicle;
import com.vehicle.app.repository.DeviceRepository;
import com.vehicle.app.entity.Device;
import com.vehicle.app.model.DeviceDTO;
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
    //get all the devices
    public List<Device> findAll(String username) {
        return deviceRepository.findAll();
    }

    //save device
    public DeviceDTO save(DeviceDTO deviceDTO) {
        Device device=deviceRepository.save(DeviceDTO.convertToDevice(deviceDTO));
        return DeviceDTO.convertToDTO(device);
    }

    //delete device
    public void deleteById(Long id) {
        Device device=deviceRepository.findById(id).get();
        if(!device.isAssigned())
            deviceRepository.deleteById(id);
        else
            throw new RuntimeException("Device already in use");
    }

    //update device
    public DeviceDTO update(DeviceDTO deviceDTO, Authentication authentication) {
        Long id=deviceDTO.getId();
        if(id==null||id<0){
            throw new RuntimeException("Please enter Device ID");
        }
        Optional<Device> optionalDevice=deviceRepository.findById(id);
//          Vehicle vehicle=vehicleRepository.findByDeviceId(id);
//        log.info(vehicle.toString()+"-----------------------------");
        if(optionalDevice.isPresent()){
            Device existingDevice=optionalDevice.get();
            log.info("Id {}",existingDevice.getId());
            log.info("Existing Device {}",existingDevice);
            DeviceDTO.convertToExistingDevice(existingDevice,deviceDTO);
            existingDevice=deviceRepository.save(existingDevice);
            return DeviceDTO.convertToDTO(existingDevice);
        }
        throw new RuntimeException("Device not found");
    }
}