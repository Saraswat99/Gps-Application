package com.veichle.app.service;

import com.veichle.app.entity.Device;
import com.veichle.app.entity.User;
import com.veichle.app.entity.Vehicle;
import com.veichle.app.model.VehicleDTO;
import com.veichle.app.repository.DeviceRepository;
import com.veichle.app.repository.UserRepository;
import com.veichle.app.repository.VehicleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private UserRepository userRepository;

    //get all the vehicle
    public List<Vehicle> findAll(String username) {
        return vehicleRepository.findAll();
    }



    public VehicleDTO save(VehicleDTO vehicleDTO) {
        Device device = deviceRepository.findById(vehicleDTO.getDeviceId()).get();
        User user = userRepository.findById(vehicleDTO.getUserId()).get();
        if(device.isAssigned()) {
            throw new RuntimeException("Device already in use");
        }else {
            device.setAssigned(true);
            Vehicle vehicle = VehicleDTO.convertToVehicle(vehicleDTO);
            vehicle.setDevice(device);
            vehicle.setUser(user);
            vehicle = vehicleRepository.save(vehicle);
            return VehicleDTO.convertToDTO(vehicle);
        }
    }

    //update device
    public VehicleDTO update(VehicleDTO vehicleDTO) {
        Long id=vehicleDTO.getId();
        if(id==null||id<0){
            throw new RuntimeException("Please enter ID");
        }
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);
        if(optionalVehicle.isPresent()){
            Vehicle existingVehicle = optionalVehicle.get();
            log.info("Existing Vehicle {}",existingVehicle);
            Device existingDevice = existingVehicle.getDevice();
            if(existingDevice.isAssigned()) {
                existingDevice.setAssigned(false);
                Device device = deviceRepository.findById(vehicleDTO.getDeviceId()).get();
                if (device.isAssigned())
                    throw new RuntimeException("Device already in use");
                else {
                    device.setAssigned(true);
                    existingVehicle.setDevice(device);
                }
            }
            VehicleDTO.convertToExistingVehicle(existingVehicle,vehicleDTO);
            existingVehicle=vehicleRepository.save(existingVehicle);
            return VehicleDTO.convertToDTO(existingVehicle);
        }
        throw new RuntimeException("Vehicle not found");
    }


    public void deleteById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id).get();
        Device device=vehicle.getDevice();
        if(device.isAssigned()) {
            device.setAssigned(false);
        }
        vehicleRepository.deleteById(id);
    }
}