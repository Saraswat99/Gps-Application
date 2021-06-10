package com.vehicle.app.ServiceImpl;

import com.mongodb.client.result.UpdateResult;
import com.vehicle.app.entity.Device;
import com.vehicle.app.entity.User;
import com.vehicle.app.enums.ApiConstant;
import com.vehicle.app.model.DeviceDTO;
import com.vehicle.app.repository.DeviceRepository;
import com.vehicle.app.repository.UserRepository;
import com.vehicle.app.repository.VehicleRepository;
import com.vehicle.app.service.DeviceService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
    private final MongoTemplate mongoTemplate;

    public DeviceDTO saveDevice(DeviceDTO deviceDTO, Authentication authentication) {
        Device device = DeviceDTO.convertToDevice(deviceDTO);
        User authUser = (User) authentication.getPrincipal();
        final String id = authUser.getId();
        User user = mongoTemplate.findById(id, User.class);
        device.setUser(user);
        device.setLevel(user.getLevel());
        Device savedDevice = mongoTemplate.save(device);
        return DeviceDTO.convertToDTO(savedDevice);
    }

    public List<Device> listAllDevices(Authentication authentication) {
        final User user = (User) authentication.getPrincipal();
        // return deviceRepository.findAllByLevelLike(user.getLevel());
        Query query = new Query();
        query.addCriteria(Criteria.where("level").regex("^" + user.getLevel()));
        return mongoTemplate.find(query, Device.class);
    }

    public DeviceDTO updateDevice(DeviceDTO deviceDTO, Authentication authentication) {
        final String id = deviceDTO.getId();
        final User user = (User) authentication.getPrincipal();
        final String createdBy = user.getUsername();
        Optional.ofNullable(id).filter(dev -> !dev.isEmpty()).orElseThrow(() -> new RuntimeException(ApiConstant.PROVIDE_DEVICE_ID));
        Device existingDevice = deviceRepository.findByIdAndCreatedBy(id, createdBy).orElseThrow(() -> new RuntimeException(ApiConstant.DEVICE_DOES_NOT_EXIST));
        DeviceDTO.convertToExistingDevice(existingDevice, deviceDTO);
        existingDevice = mongoTemplate.save(existingDevice);
        return DeviceDTO.convertToDTO(existingDevice);
    }

    public void deleteById(String id, Authentication authentication) {
        final User user = (User) authentication.getPrincipal();
        final Device device = deviceRepository.findByIdAndLevelLike(id, user.getLevel()).orElseThrow(() -> new RuntimeException(ApiConstant.DEVICE_DOES_NOT_EXIST));
        if (device.isAssigned()) {
            throw new RuntimeException(ApiConstant.DEVICE_ALREADY_ASSIGNED);
        }
        mongoTemplate.remove(device);
    }

    @Override
    public Long activeDevice(boolean active, String deviceId, Authentication authentication) {
        Optional.ofNullable(deviceId).filter(di -> !di.isEmpty()).orElseThrow(() -> new RuntimeException(ApiConstant.PROVIDE_DEVICE_ID));
        User user = (User) authentication.getPrincipal();
        String level = user.getLevel();
        final Device device = deviceRepository.findByIdAndLevelLike(deviceId, user.getLevel()).orElseThrow(() -> new RuntimeException(ApiConstant.DEVICE_DOES_NOT_EXIST));
        if (!device.isAssigned()) {
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(deviceId).and("level").regex(level + "/"));
            Update update = new Update();
            update.set("active", active);
            UpdateResult device1 = mongoTemplate.updateFirst(query, update, Device.class);
            log.info(device1.getModifiedCount() + "");
            return device1.getModifiedCount();
        }
        return 0L;
    }
}