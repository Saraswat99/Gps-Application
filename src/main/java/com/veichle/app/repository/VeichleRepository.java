package com.veichle.app.repository;

import com.veichle.app.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeichleRepository extends JpaRepository<Vehicle, Long > {
}
