package com.vehicle.app.repository;

import com.vehicle.app.entity.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends MongoRepository<Vehicle, String> {

    List<Vehicle> findByUserId(String userId);

    Optional<Vehicle> findByIdAndCreatedBy(String id, String createdBy);

    /*
        @Transactional
        @Modifying
        @Query("UPDATE Vehicle vehicle SET vehicle.active = :active  WHERE vehicle.id = :id AND vehicle.level LIKE :level%")
        int update(@Param("id") String id, @Param("active") boolean active, @Param("level") String level);
    */
    Optional<Vehicle> findByIdAndLevelLike(String vehicleId, String level);

    List<Vehicle> findAllByLevelLike(String s);
}
