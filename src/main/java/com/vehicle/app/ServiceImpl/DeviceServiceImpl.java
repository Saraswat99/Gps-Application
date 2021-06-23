package com.vehicle.app.ServiceImpl;


import com.mongodb.client.result.UpdateResult;
import com.vehicle.app.entity.Device;
import com.vehicle.app.entity.User;
import com.vehicle.app.model.DeviceDTO;
import com.vehicle.app.repository.Aggregation.DeviceAggregation;
import com.vehicle.app.repository.DeviceRepository;
import com.vehicle.app.repository.UserRepository;
import com.vehicle.app.repository.VehicleRepository;
import com.vehicle.app.service.DeviceService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoOperations;
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
    private final MongoOperations mongoOperations;

    public List<Device> listAllDevices(Authentication authentication) {
        final User user = (User) authentication.getPrincipal();
        log.info("level :- "+user.getLevel()+"-------------------------------------------");
        Query query=new Query();
        query.addCriteria(Criteria.where("level").regex("^"+user.getLevel()));
        List<Device> device=mongoOperations.find(query,Device.class);
        log.info(device+"===========");
//        return mongoOperations.find(query,Device.class);
//        return deviceRepository.findAllByLevelLike(user.getLevel());
        return device;
    }

    public DeviceDTO saveDevice(DeviceDTO deviceDTO, Authentication authentication) {
        Device device = DeviceDTO.convertToDevice(deviceDTO);
        User authUser = (User) authentication.getPrincipal();
        final String id = authUser.getId();
        User user = userRepository.findById(id).get();
        device.setUser(user);
        device.setLevel(user.getLevel());
        Device savedDevice = deviceRepository.save(device);
        return DeviceDTO.convertToDTO(savedDevice);
    }

    public DeviceDTO updateDevice(DeviceDTO deviceDTO, Authentication authentication) {
        final String id = deviceDTO.getId();
        final User user = (User) authentication.getPrincipal();
        final String createdBy = user.getUsername();
        Optional.ofNullable(id).filter(dev -> !dev.isEmpty()).
                orElseThrow(() -> new RuntimeException("Please provide device id"));
        Query query=new Query();
        query.addCriteria(Criteria.where("id").is(id).and("createdBy").is(createdBy));
//        Device existingDevice=mongoOperations.findOne(query,Device.class);
//        Optional.ofNullable(existingDevice).orElseThrow(() -> new RuntimeException("Device doesn't exist"));
//        Device existingDevice = deviceRepository.findByIdAndCreatedBy(id, createdBy).
//                orElseThrow(() -> new RuntimeException("Device doesn't exist"));
//        existingDevice = deviceRepository.save(existingDevice);
        Update updateDevice=DeviceDTO.convertToExistingDevice(deviceDTO);
        Device updatedDevice=mongoOperations.findAndModify(query,updateDevice,Device.class);
        Optional.ofNullable(updatedDevice).orElseThrow(() -> new RuntimeException("Device doesn't exist"));
        return DeviceDTO.convertToDTO(updatedDevice);
    }

    public void deleteById(String id, Authentication authentication) {
        final User user = (User) authentication.getPrincipal();
        final Device device = deviceRepository.findByIdAndLevelLike(id, user.getLevel())
                .orElseThrow(() -> new RuntimeException("Device does not exists"));
        if (device.isAssigned()) {
            throw new RuntimeException("Device already in use");
        }
        mongoOperations.remove(device);
//        deviceRepository.deleteById(id);
    }

    @Override
    public UpdateResult activeDevice(boolean active, String deviceId, Authentication authentication) {
        Optional.ofNullable(deviceId).filter(di -> !di.isEmpty()).orElseThrow(() -> new RuntimeException("Please provide device Id"));
        User user = (User) authentication.getPrincipal();
        UpdateResult result;
        final String level = user.getLevel();
        log.info("device Id -: "+deviceId+"-------------");
        Query query=new Query();
        query.addCriteria(Criteria.where("level").regex("^"+level).and("id").is(deviceId));
        final Device device = mongoOperations.findOne(query,Device.class);
        log.info(device+"+++++++++++++++++++++++++++++++++++++++++++++++++++");
        if (device.isAssigned()) {
            throw new RuntimeException("Your Device is assigned, we can't update the active status");
        }
        Update update=new Update();
        update.set("active",active);
        result=mongoOperations.updateFirst(query,update,Device.class);
//            deviceRepository.update(deviceId,active,"^"+level);
        return result;
    }

    @Override
    public int getTotalDevices(Authentication authentication) {
        User user=(User)authentication.getPrincipal();
        int devices=new DeviceAggregation().totalDevices(user.getLevel());
        log.info("DeviceServiceImpl devices-: "+devices+"-------------------------------------");
        return devices;
    }
}