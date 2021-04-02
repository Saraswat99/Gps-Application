package com.vehicle.app.service;

import com.vehicle.app.repository.DeviceRepository;
import com.vehicle.app.entity.Device;
import com.vehicle.app.model.DeviceDTO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Data
@Service
public class DeviceService {

    @Autowired
    private final DeviceRepository deviceRepository;

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
    public DeviceDTO update(DeviceDTO deviceDTO) {
        Long id=deviceDTO.getId();
        if(id==null||id<0){
            throw new RuntimeException("Please enter Device ID");
        }
        Optional<Device> optionalDevice=deviceRepository.findById(id);
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