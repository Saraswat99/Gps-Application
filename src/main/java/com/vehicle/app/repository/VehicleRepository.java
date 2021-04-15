package com.vehicle.app.repository;

import com.vehicle.app.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByUserId(Long userId);

    Optional<Vehicle> findByIdAndCreatedBy(Long id, String createdBy);

    @Transactional
    @Modifying
    @Query("UPDATE Vehicle vehicle SET vehicle.active = :active  WHERE vehicle.id = :id AND vehicle.level LIKE :level%")
    int update(@Param("id") Long id, @Param("active") boolean active, @Param("level") String level);

    Optional<Vehicle> findByIdAndLevelLike(Long vehicleId, String level);

    List<Vehicle> findAllByLevelLike(String s);
}
