package com.vehicle.app.ServiceImpl;

import com.vehicle.app.entity.Device;
import com.vehicle.app.entity.User;
import com.vehicle.app.entity.Vehicle;
import com.vehicle.app.model.VehicleDTO;
import com.vehicle.app.repository.DeviceRepository;
import com.vehicle.app.repository.UserRepository;
import com.vehicle.app.repository.VehicleRepository;
import com.vehicle.app.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public VehicleDTO save(VehicleDTO vehicleDTO, Authentication authentication) {
        final Long deviceId = vehicleDTO.getDeviceId();
        final User userAuth = (User) authentication.getPrincipal();
        final String createdBy = userAuth.getUsername();
        final Long userId = userAuth.getId();
        Optional.ofNullable(deviceId).filter(dev -> dev > 0).orElseThrow(() -> new RuntimeException("Please enter device Id"));
        Device device = deviceRepository.findByIdAndCreatedBy(deviceId, createdBy).orElseThrow(() -> new RuntimeException("Device doesn't exist"));
        final User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User doesn't exist"));
        if (!device.isAssigned()) {
            device.setAssigned(true);
            Vehicle vehicle = VehicleDTO.convertToVehicle(vehicleDTO);
            vehicle.setUser(user);
            vehicle.setDevice(device);
            vehicle = vehicleRepository.save(vehicle);
            return VehicleDTO.convertToVehicleDTO(vehicle);
        }
        throw new RuntimeException("Device already in use");
    }

    @Override
    public List<Vehicle> getVehicleList(Authentication authentication) {
        final User user = (User) authentication.getPrincipal();
        final String createdBy = user.getUsername();
        return vehicleRepository.findAllByCreatedBy(createdBy);
    }

    @Override
    public Vehicle getVehicleById(Long vehicleId) {
        Optional.ofNullable(vehicleId).filter(vi -> vi > 0).orElseThrow(() -> new RuntimeException("Please provide vehicle Id"));
        return vehicleRepository.findById(vehicleId).get();
    }

    @Override
    public VehicleDTO update(VehicleDTO vehicleDTO, Authentication authentication) {
        final User user = (User) authentication.getPrincipal();
        final String createdBy = user.getUsername();
        final Long vehicleId = vehicleDTO.getId();
        final Long deviceId = vehicleDTO.getDeviceId();
        Optional.ofNullable(vehicleId).filter(vi -> vi > 0).orElseThrow(() -> new RuntimeException("Please provide vehicle Id"));
        Vehicle existingVehicle = vehicleRepository.findByIdAndCreatedBy(vehicleId, createdBy).orElseThrow(() -> new RuntimeException("Vehicle does not exists"));
        final Device device = deviceRepository.findByIdAndCreatedBy(deviceId, createdBy).orElseThrow(() -> new RuntimeException("Device does not exists"));
        final Device existingDevice = existingVehicle.getDevice();
        if (device.getId() != existingDevice.getId()) {
            if (device.isAssigned()) throw new RuntimeException("Device already assigned");
            device.setAssigned(true);
            existingDevice.setAssigned(false);
        }
        VehicleDTO.convertToExistingVehicle(existingVehicle, vehicleDTO);
        existingVehicle = vehicleRepository.save(existingVehicle);
        return VehicleDTO.convertToVehicleDTO(existingVehicle);
    }

    @Override
    public void delete(Long vehicleId, Authentication authentication) {
        final User user = (User) authentication.getPrincipal();
        final String createdBy = user.getUsername();
        final Vehicle existingVehicle = vehicleRepository.findByIdAndCreatedBy(vehicleId, createdBy).orElseThrow(() -> new RuntimeException("Vehicle does not exists"));
        final Device device = existingVehicle.getDevice();
        device.setAssigned(false);
        vehicleRepository.deleteById(vehicleId);
    }
}