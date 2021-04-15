package com.vehicle.app.repository;

import com.vehicle.app.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findByUserId(Long userId);

    Optional<Device> findByIdAndCreatedBy(Long id, String createdBy);

    List<Device> findAllByLevelLike(String s);

    Optional<Device> findByIdAndLevelLike(Long id, String s);

    @Transactional
    @Modifying
    @Query("UPDATE Device device SET device.active = :active  WHERE device.id = :id AND device.level LIKE :level%")
    int update(@Param("id") Long id, @Param("active") boolean active, @Param("level") String level);

    Optional<Device> findByIdAndAssignedAndCreatedByAndActive(Long deviceId, boolean b, String createdBy, boolean b1);
}
