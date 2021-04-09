package com.vehicle.app.repository;

import com.vehicle.app.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findByUserId(Long userId);

    Optional<Device> findByIdAndCreatedBy(Long id, String createdBy);

    List<Device> findByCreatedBy(String createdBy);

    List<Device> findAllByCreatedBy(String createdBy);
}
