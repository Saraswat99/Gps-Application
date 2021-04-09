package com.vehicle.app.ServiceImpl;

import com.vehicle.app.entity.Role;
import com.vehicle.app.enums.Roles;
import com.vehicle.app.repository.RoleRepository;
import com.vehicle.app.service.RoleService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Optional;

@Data
@Slf4j
@Service
public class RoleServiceImpl implements RoleService {


    private final RoleRepository roleRepository;

    @PostConstruct
    private void init() {
        log.info("############## Role Init Start #################");
        Arrays.stream(Roles.values()).forEach((role) -> {
            Optional<Role> optional = roleRepository.findById(role.getId());
            if (!optional.isPresent()) {
                Role role1 = new Role();
                role1.setDefault(true);
                role1.setId(role.getId());
                role1.setName(role.name());
                roleRepository.save(role1);
                log.info("Role save {}", role.name());
            } else {
                log.info("Role already exists {}", role.name());
            }
        });
        log.info("############## Role Init Stop #################");
    }


}
