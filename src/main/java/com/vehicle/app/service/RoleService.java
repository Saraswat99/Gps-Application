package com.vehicle.app.service;

import com.vehicle.app.entity.Role;

public interface RoleService {

    Role findById(String id);

    Role findByName(String name);
}
