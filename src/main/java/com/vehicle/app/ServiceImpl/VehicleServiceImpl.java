package com.vehicle.app.ServiceImpl;

import com.vehicle.app.entity.Device;
import com.vehicle.app.entity.User;
import com.vehicle.app.entity.Vehicle;
import com.vehicle.app.enums.ApiConstant;
import com.vehicle.app.model.VehicleDTO;
import com.vehicle.app.repository.DeviceRepository;
import com.vehicle.app.repository.UserRepository;
import com.vehicle.app.repository.VehicleRepository;
import com.vehicle.app.service.VehicleService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data
@Slf4j
@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;

    @Override
    public VehicleDTO saveVehicle(VehicleDTO vehicleDTO, Authentication authentication) {
        final Long deviceId = vehicleDTO.getDeviceId();
        final User user = (User) authentication.getPrincipal();
        final String createdBy = user.getUsername();
        Optional.ofNullable(deviceId).filter(dId -> dId > 0).orElseThrow(() -> new RuntimeException(ApiConstant.PROVIDE_DEVICE_ID));
        Device device = deviceRepository.findByIdAndAssignedAndCreatedByAndActive(deviceId, false, createdBy, true).orElseThrow(() -> new RuntimeException("Device doesn't exist or already assigned or not active"));
        device.setAssigned(true);
        Vehicle vehicle = VehicleDTO.convertToVehicle(vehicleDTO);
        vehicle.setUser(user);
        vehicle.setLevel(user.getLevel());
        vehicle.setDevice(device);
        vehicle = vehicleRepository.save(vehicle);
        return VehicleDTO.convertToVehicleDTO(vehicle);
    }

    @Override
    public VehicleDTO updateVehicle(VehicleDTO vehicleDTO, Authentication authentication) {
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
            if (!device.isActive()) throw new RuntimeException("Device is not active");
            device.setAssigned(true);
            existingDevice.setAssigned(false);
            existingVehicle.setDevice(device);
        }
        VehicleDTO.convertToExistingVehicle(existingVehicle, vehicleDTO);
        existingVehicle = vehicleRepository.save(existingVehicle);
        return VehicleDTO.convertToVehicleDTO(existingVehicle);
    }

    @Override
    public List<Vehicle> listVehicleList(Authentication authentication) {
        final User user = (User) authentication.getPrincipal();
        return vehicleRepository.findAllByLevelLike("%" + user.getLevel() + "%");
    }

    @Override
    public Vehicle getVehicleById(Long vehicleId, Authentication authentication) {
        Optional.ofNullable(vehicleId).filter(vi -> vi > 0).orElseThrow(() -> new RuntimeException("Please provide vehicle Id"));
        User user = (User) authentication.getPrincipal();
        return vehicleRepository.findByIdAndLevelLike(vehicleId, user.getLevel() + "%").get();
    }

    @Override
    public void deleteVehicle(Long vehicleId, Authentication authentication) {
        final User user = (User) authentication.getPrincipal();
        final Vehicle existingVehicle = vehicleRepository.findByIdAndLevelLike(vehicleId, "%" + user.getLevel() + "%").orElseThrow(() -> new RuntimeException("Vehicle does not exists"));
        final Device device = existingVehicle.getDevice();
        device.setAssigned(false);
        vehicleRepository.deleteById(vehicleId);
    }

    @Override
    public int activeVehicle(boolean active, Long vehicleId, Authentication authentication) {
        Optional.ofNullable(vehicleId).filter(vi -> vi > 0).orElseThrow(() -> new RuntimeException("Please provide vehicle Id"));
        User user = (User) authentication.getPrincipal();
        String level = user.getLevel();
        return vehicleRepository.update(vehicleId, active, level);
    }
}