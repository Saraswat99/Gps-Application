package com.vehicle.app.service;

import com.vehicle.app.entity.Device;
import com.vehicle.app.entity.User;
import com.vehicle.app.entity.Vehicle;
import com.vehicle.app.model.VehicleDTO;
import com.vehicle.app.repository.DeviceRepository;
import com.vehicle.app.repository.UserRepository;
import com.vehicle.app.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private UserRepository userRepository;

    public VehicleDTO save(VehicleDTO vehicleDTO) {
        Device device=deviceRepository.findById(vehicleDTO.getDeviceId()).get();
        User user= userRepository.findById(vehicleDTO.getUserId()).get();
        if(device.isAssigned())
            throw new RuntimeException("Device already in use");
        else {
            device.setAssigned(true);
            Vehicle vehicle = VehicleDTO.convertToVehicle(vehicleDTO);
            vehicle.setUser(user);
            vehicle.setDevice(device);
            vehicle = vehicleRepository.save(vehicle);
            return VehicleDTO.convertToVehicleDTO(vehicle);
        }
    }

    public List<Vehicle> getVehicleList() {
        return vehicleRepository.findAll();
    }

    public Vehicle getVehicleById(long id) {
         return vehicleRepository.findById(id).get();
    }

    public VehicleDTO update(VehicleDTO vehicleDTO) {
        Long id= vehicleDTO.getId();
        Device device=deviceRepository.findById(vehicleDTO.getDeviceId()).get();
        if(id==null||id<0)
            throw new RuntimeException("Please Enter Vehicle ID");
        Optional<Vehicle> optionalVehicle= vehicleRepository.findById(id);
        if(optionalVehicle.isPresent()) {
            if(!device.isAssigned()) {
                device.setAssigned(true);
                Vehicle exisitingVehicle = optionalVehicle.get();
                Device exisitingDevice = exisitingVehicle.getDevice();
                System.out.println(exisitingDevice);
                exisitingDevice.setAssigned(false);
                VehicleDTO.convertToExistingVehicle(exisitingVehicle, vehicleDTO);
                exisitingVehicle.setDevice(device);
                exisitingVehicle = vehicleRepository.save(exisitingVehicle);
                return VehicleDTO.convertToVehicleDTO(exisitingVehicle);
            }
            else
                throw new RuntimeException("Device is already assigned");
        }
        throw new RuntimeException("Vehicle not found");
    }

    public void delete(Long id) {
        Vehicle vehicle=vehicleRepository.findById(id).get();
        Device device=vehicle.getDevice();
        if(device.isAssigned()) {
            device.setAssigned(false);
            deviceRepository.save(device);
        }
       vehicleRepository.deleteById(id);
        throw new RuntimeException("Vehicle deleted successfully");
    }
}
