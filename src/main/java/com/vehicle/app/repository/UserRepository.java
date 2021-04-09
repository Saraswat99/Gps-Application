package com.vehicle.app.repository;

import com.vehicle.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    List<User> findByCreatedBy(String name);

    Optional<User> findByIdAndCreatedBy(Long userId, String name);
}
