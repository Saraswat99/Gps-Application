package com.vehicle.app.repository;

import com.vehicle.app.entity.User;
import com.vehicle.app.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<User, Long> {

    List<Roles> saveUsers(List<Roles> roleUsers);
}
