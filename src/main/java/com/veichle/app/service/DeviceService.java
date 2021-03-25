package com.veichle.app.service;

import com.veichle.app.entity.Device;
import com.veichle.app.model.DeviceDTO;
import com.veichle.app.repository.DeviceRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

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
        deviceRepository.deleteById(id);
    }

    //update device
    public DeviceDTO update(DeviceDTO deviceDTO) {
        Long id=deviceDTO.getId();
        if(id==null||id<0){
            throw new RuntimeException("Please enter ID");
        }
        Optional<Device> optionalDevice=deviceRepository.findById(id);
        if(optionalDevice.isPresent()){
            Device existingDevice=optionalDevice.get();
            log.info("Id {}",existingDevice.getId());
            log.info("Existing Device {}",existingDevice);
            //chnages
            DeviceDTO.convertToExistingDevice(existingDevice,deviceDTO);
            existingDevice=deviceRepository.save(existingDevice);
            return DeviceDTO.convertToDTO(existingDevice);
        }
        throw new RuntimeException("Employee not found");
    }
}