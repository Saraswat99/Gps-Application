package com.vehicle.app;

import com.vehicle.app.entity.Role;
import com.vehicle.app.entity.User;
import com.vehicle.app.enums.Roles;
import com.vehicle.app.repository.UserRepository;
import com.vehicle.app.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import java.util.Optional;
import java.util.Set;

@Slf4j
@EnableJpaAuditing
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            try {
                String userName = SecurityContextHolder.getContext().getAuthentication().getName();
                return Optional.ofNullable(userName);
            } catch (Exception ex) {
                return Optional.ofNullable("System Generated");
            }
        };
    }

    @Bean("passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        log.info("PasswordEncoder bean initialized");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TilesViewResolver viewResolver() {
        TilesViewResolver viewResolver = new TilesViewResolver();
        return viewResolver;
    }

    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions("classpath:tiles-def.xml");
        tilesConfigurer.setCheckRefresh(true);
        return tilesConfigurer;
    }

    @Override
    public void run(String... args) throws Exception {
        createSuperAdmin();
    }

    private void createSuperAdmin() {
        log.info("############## User Init Start #################");
        User user = userRepository.findByUsername("superadmin");
        if (user == null) {
            user = new User();
            user.setUsername("superadmin");
            user.setActive(true);
            user.setName("Super Admin");
            user.setEmailId("superadmin@gmail.com");
            user.setRoleAlisa(Roles.SUPERADMIN.getAlisa());
            user.setPassword(passwordEncoder.encode("superadmin"));
            Role role = roleService.findById(Roles.SUPERADMIN.getId());
            user.setRoles(Set.of(role));
            user.setLevel("superadmin");
            userRepository.save(user);
            log.info("Super admin created");
        } else {
            log.info("Super admin already exists");
        }
        log.info("############## User Init Stop #################");
    }
}