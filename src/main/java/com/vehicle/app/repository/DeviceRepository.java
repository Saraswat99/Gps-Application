package com.vehicle.app.repository;

import com.vehicle.app.entity.Device;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends MongoRepository<Device, String> {
    List<Device> findByUserId(String userId);

    Optional<Device> findByIdAndCreatedBy(String id, String createdBy);

    List<Device> findAllByLevelLike(String s);

    Optional<Device> findByIdAndLevelLike(String id, String s);

    Optional<Device> findByIdAndAssignedAndCreatedByAndActive(String deviceId, boolean b, String createdBy, boolean b1);
}
