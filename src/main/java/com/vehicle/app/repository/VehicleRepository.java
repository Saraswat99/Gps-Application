package com.vehicle.app.repository;

import com.vehicle.app.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,Long> {

    List<Vehicle> findByUserId(Long userId);

    Optional<Vehicle> findByIdAndCreatedBy(Long id,String createdBy);
}