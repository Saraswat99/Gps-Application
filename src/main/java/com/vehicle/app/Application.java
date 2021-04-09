package com.vehicle.app;

import com.vehicle.app.entity.Device;
import com.vehicle.app.repository.DeviceRepository;
import com.vehicle.app.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

@Slf4j
@EnableJpaAuditing
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            return Optional.ofNullable(userName);
        };
    }

    @Override
    public void run(String... args) throws Exception {
        List<Device> devices = deviceRepository.findAll();
        log.info("Device list {}", devices.size());
        devices.stream().forEach(emp -> {
            log.info(emp.toString());
        });
    }
}

