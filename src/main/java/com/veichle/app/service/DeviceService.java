package com.veichle.app.service;

import com.veichle.app.entity.Device;
import com.veichle.app.model.DeviceDTO;
import com.veichle.app.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    public List<Device> getDeviceList(String username) {
        return deviceRepository.findAll();
    }

    public Device save(DeviceDTO deviceDTO) {
        Device device= DeviceDTO.convertToDevice(deviceDTO);
        Device deviceSaved= deviceRepository.save(device);
        return deviceSaved;
    }

    public Device update(Device device) {
        Long id=device.getId();
        Optional<Device> optionalDevice=deviceRepository.findById(id);
        if(optionalDevice.isPresent())
            return deviceRepository.save(device);
        return null;
    }

    public void delete(long id) {
        deviceRepository.deleteById(id);
    }

}
