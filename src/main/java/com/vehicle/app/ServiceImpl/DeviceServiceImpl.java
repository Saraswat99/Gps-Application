package com.vehicle.app.ServiceImpl;

import com.vehicle.app.entity.Device;
import com.vehicle.app.entity.User;
import com.vehicle.app.model.DeviceDTO;
import com.vehicle.app.repository.DeviceRepository;
import com.vehicle.app.repository.UserRepository;
import com.vehicle.app.repository.VehicleRepository;
import com.vehicle.app.service.DeviceService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data
@Slf4j
@Service
public class DeviceServiceImpl implements DeviceService {

    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;
    private final VehicleRepository vehicleRepository;

    public List<Device> listAllDevices(Authentication authentication) {
        final User user = (User) authentication.getPrincipal();
        return deviceRepository.findAllByLevelLike(user.getLevel() + "%");
    }

    public DeviceDTO saveDevice(DeviceDTO deviceDTO, Authentication authentication) {
        Device device = DeviceDTO.convertToDevice(deviceDTO);
        User authUser = (User) authentication.getPrincipal();
        final Long id = authUser.getId();
        User user = userRepository.findById(id).get();
        device.setUser(user);
        device.setLevel(user.getLevel());
        Device savedDevice = deviceRepository.save(device);
        return DeviceDTO.convertToDTO(savedDevice);
    }

    public DeviceDTO updateDevice(DeviceDTO deviceDTO, Authentication authentication) {
        final Long id = deviceDTO.getId();
        final User user = (User) authentication.getPrincipal();
        final String createdBy = user.getUsername();
        Optional.ofNullable(id).filter(dev -> dev > 0).orElseThrow(() -> new RuntimeException("Please provide device id"));
        Device existingDevice = deviceRepository.findByIdAndCreatedBy(id, createdBy).orElseThrow(() -> new RuntimeException("Device doesn't exist"));
        DeviceDTO.convertToExistingDevice(existingDevice, deviceDTO);
        existingDevice = deviceRepository.save(existingDevice);
        return DeviceDTO.convertToDTO(existingDevice);
    }

    public void deleteById(Long id, Authentication authentication) {
        final User user = (User) authentication.getPrincipal();
        final Device device = deviceRepository.findByIdAndLevelLike(id, user.getLevel() + "%").orElseThrow(() -> new RuntimeException("Device does not exists"));
        if (device.isAssigned()) {
            throw new RuntimeException("Device already in use");
        }
        deviceRepository.deleteById(id);
    }

    @Override
    public int activeDevice(boolean active, Long deviceId, Authentication authentication) {
        Optional.ofNullable(deviceId).filter(di -> di > 0).orElseThrow(() -> new RuntimeException("Please provide device Id"));
        User user = (User) authentication.getPrincipal();
        String level = user.getLevel();
        final Device device = deviceRepository.findByIdAndLevelLike(deviceId, user.getLevel() + "%").orElseThrow(() -> new RuntimeException("Device does not exists"));
        if (!device.isAssigned()) {
            return deviceRepository.update(deviceId, active, level);
        }
        return 0;
    }
}