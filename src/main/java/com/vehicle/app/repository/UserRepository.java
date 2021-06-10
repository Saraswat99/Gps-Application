package com.vehicle.app.repository;

import com.vehicle.app.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);

    List<User> findByCreatedBy(String name);

    Optional<User> findByIdAndCreatedBy(String userId, String name);
}
