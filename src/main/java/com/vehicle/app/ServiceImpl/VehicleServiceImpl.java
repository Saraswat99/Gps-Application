package com.vehicle.app.ServiceImpl;

import com.mongodb.client.result.UpdateResult;
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
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public VehicleDTO saveVehicle(VehicleDTO vehicleDTO, Authentication authentication) {
        final String deviceId = vehicleDTO.getDeviceId();
        final User user = (User) authentication.getPrincipal();
        final String createdBy = user.getUsername();
        Optional.ofNullable(deviceId).filter(dId -> !dId.isEmpty()).orElseThrow(() -> new RuntimeException(ApiConstant.PROVIDE_DEVICE_ID));
        Device device = deviceRepository.findByIdAndAssignedAndCreatedByAndActive(deviceId, false, createdBy, true).orElseThrow(() -> new RuntimeException(ApiConstant.DEVICE_DOES_NOT_EXIST__OR__ALREADY_ASSIGNED__OR__NOT_ACTIVE));
        device.setAssigned(true);
        mongoTemplate.save(device);
        Vehicle vehicle = VehicleDTO.convertToVehicle(vehicleDTO);
        vehicle.setUser(user);
        vehicle.setLevel(user.getLevel());
        vehicle.setDevice(device);
        vehicle = mongoTemplate.save(vehicle);
        return VehicleDTO.convertToVehicleDTO(vehicle);
    }

    @Override
    public VehicleDTO updateVehicle(VehicleDTO vehicleDTO, Authentication authentication) {
        final User user = (User) authentication.getPrincipal();
        final String createdBy = user.getUsername();
        final String vehicleId = vehicleDTO.getId();
        final String deviceId = vehicleDTO.getDeviceId();
        Optional.ofNullable(vehicleId).filter(vi -> !vi.isEmpty()).orElseThrow(() -> new RuntimeException(ApiConstant.PROVIDE_VEHICLE_ID));
        Vehicle existingVehicle = vehicleRepository.findByIdAndCreatedBy(vehicleId, createdBy).orElseThrow(() -> new RuntimeException(ApiConstant.VEHICLE_DOES_NOT_EXIST));
        final Device device = deviceRepository.findByIdAndCreatedBy(deviceId, createdBy).orElseThrow(() -> new RuntimeException(ApiConstant.DEVICE_DOES_NOT_EXIST));
        final Device existingDevice = existingVehicle.getDevice();
        if (!(device.getId().equals(existingDevice.getId()))) {
            if (device.isAssigned()) throw new RuntimeException(ApiConstant.DEVICE_ALREADY_ASSIGNED);
            if (!device.isActive()) throw new RuntimeException(ApiConstant.DEVICE_NOT_ACTIVE);
            device.setAssigned(true);
            existingDevice.setAssigned(false);
            existingVehicle.setDevice(device);
            mongoTemplate.save(device);
            mongoTemplate.save(existingDevice);
        }
        VehicleDTO.convertToExistingVehicle(existingVehicle, vehicleDTO);
        existingVehicle = mongoTemplate.save(existingVehicle);
        return VehicleDTO.convertToVehicleDTO(existingVehicle);
    }

    @Override
    public List<Vehicle> listVehicleList(Authentication authentication) {
        final User user = (User) authentication.getPrincipal();
        //return vehicleRepository.findAllByLevelLike("%" + user.getLevel() + "%");
        Query query = new Query();
        query.addCriteria(Criteria.where("level").regex("^" + user.getLevel()));
        return mongoTemplate.find(query, Vehicle.class);
    }

    @Override
    public Vehicle getVehicleById(String vehicleId, Authentication authentication) {
        Optional.ofNullable(vehicleId).filter(vi -> !vi.isEmpty()).orElseThrow(() -> new RuntimeException(ApiConstant.PROVIDE_VEHICLE_ID));
        User user = (User) authentication.getPrincipal();
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(vehicleId).and("level").regex(user.getLevel() + "/"));
        return mongoTemplate.findOne(query, Vehicle.class);
        //return vehicleRepository.findByIdAndLevelLike(vehicleId, user.getLevel() + "%").get();
    }

    @Override
    public void deleteVehicle(String vehicleId, Authentication authentication) {
        final User user = (User) authentication.getPrincipal();
        final Vehicle existingVehicle = vehicleRepository.findByIdAndLevelLike(vehicleId, user.getLevel()).orElseThrow(() -> new RuntimeException(ApiConstant.VEHICLE_DOES_NOT_EXIST));
        final Device device = existingVehicle.getDevice();
        device.setAssigned(false);
        mongoTemplate.save(device);
        //vehicleRepository.deleteById(vehicleId);
        mongoTemplate.remove(existingVehicle);
    }

    @Override
    public Long activeVehicle(boolean active, String vehicleId, Authentication authentication) {
        Optional.ofNullable(vehicleId).filter(vi -> !vi.isEmpty()).orElseThrow(() -> new RuntimeException(ApiConstant.PROVIDE_VEHICLE_ID));
        User user = (User) authentication.getPrincipal();
        String level = user.getLevel();
        //return 0; //vehicleRepository.update(vehicleId, active, level);
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(vehicleId).and("level").regex(level + "/"));
        Update update = new Update();
        update.set("active", active);
        UpdateResult vehicle1 = mongoTemplate.updateFirst(query, update, Vehicle.class);
        log.info(vehicle1.getModifiedCount() + "");
        return vehicle1.getModifiedCount();
    }
}