package com.vehicle.app.service;


import com.vehicle.app.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Role findByName(String name);
}
